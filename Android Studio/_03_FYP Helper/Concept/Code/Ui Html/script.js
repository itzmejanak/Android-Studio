document.addEventListener('DOMContentLoaded', function() {
    const chatMessages = document.getElementById('chatMessages');
    const userInput = document.getElementById('userInput');
    const sendButton = document.getElementById('sendButton');
    const toggleHistory = document.getElementById('toggleHistory');
    const closeHistory = document.getElementById('closeHistory');
    const historyPanel = document.getElementById('historyPanel');
    const historyContent = document.getElementById('historyContent');
    const historyOverlay = document.getElementById('historyOverlay');
    const themeToggle = document.getElementById('themeToggle');
    const themeIcon = themeToggle.querySelector('i');
    const newChatButton = document.getElementById('newChatButton');

    // Configure marked options
    marked.setOptions({
        highlight: function(code, lang) {
            if (lang && hljs.getLanguage(lang)) {
                return hljs.highlight(code, { language: lang }).value;
            }
            return hljs.highlightAuto(code).value;
        },
        breaks: true,
        gfm: true
    });

    // Theme handling
    function setTheme(isDark) {
        document.documentElement.setAttribute('data-theme', isDark ? 'dark' : 'light');
        themeIcon.className = isDark ? 'fas fa-sun' : 'fas fa-moon';
        localStorage.setItem('theme', isDark ? 'dark' : 'light');
    }

    // Initialize theme
    const savedTheme = localStorage.getItem('theme') || 'light';
    setTheme(savedTheme === 'dark');

    // Theme toggle handler
    themeToggle.addEventListener('click', () => {
        const isDark = document.documentElement.getAttribute('data-theme') === 'light';
        setTheme(isDark);
    });

    // Load chat history from localStorage
    let chatHistory = JSON.parse(localStorage.getItem('chatHistory') || '[]');
    
    // Save chat to history
    function saveToHistory(userMessage, botResponse) {
        let history = JSON.parse(localStorage.getItem('chatHistory') || '[]');
        const timestamp = new Date().toISOString();
        history.push({
            id: Date.now(),
            timestamp: timestamp,
            userMessage: userMessage,
            botResponse: botResponse
        });
        localStorage.setItem('chatHistory', JSON.stringify(history));
        displayHistory();
    }

    // Display chat history
    function displayHistory() {
        const historyContent = document.getElementById('historyContent');
        const history = JSON.parse(localStorage.getItem('chatHistory') || '[]');
        
        historyContent.innerHTML = '';

        // Add New Chat button at the top
        const newChatBtn = document.createElement('div');
        newChatBtn.className = 'history-item new-chat';
        newChatBtn.innerHTML = `
            <div class="history-content">
                <i class="fas fa-plus"></i>
                <span>New Chat</span>
            </div>
        `;
        newChatBtn.onclick = startNewChat;
        historyContent.appendChild(newChatBtn);
        
        // Add history items in reverse chronological order
        history.reverse().forEach(chat => {
            const historyItem = document.createElement('div');
            historyItem.className = 'history-item';
            
            const date = new Date(chat.timestamp);
            const formattedDate = date.toLocaleString();
            
            historyItem.innerHTML = `
                <div class="history-header">
                    <span class="history-timestamp">${formattedDate}</span>
                    <button class="delete-history" data-id="${chat.id}">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
                <div class="history-content" onclick="loadChat(${chat.id})">
                    <div class="history-message">${chat.userMessage.substring(0, 50)}${chat.userMessage.length > 50 ? '...' : ''}</div>
                </div>
            `;
            
            const deleteBtn = historyItem.querySelector('.delete-history');
            deleteBtn.onclick = (e) => {
                e.stopPropagation();
                deleteHistoryItem(chat.id);
            };
            
            historyContent.appendChild(historyItem);
        });
    }

    // Delete history item
    function deleteHistoryItem(id) {
        let history = JSON.parse(localStorage.getItem('chatHistory') || '[]');
        history = history.filter(chat => chat.id !== id);
        localStorage.setItem('chatHistory', JSON.stringify(history));
        displayHistory();
    }

    function createMessage(content, isUser = false) {
        const messageDiv = document.createElement('div');
        messageDiv.className = `message ${isUser ? 'user-message' : 'bot-message'}`;
        
        const contentDiv = document.createElement('div');
        contentDiv.className = 'message-content';
        
        if (isUser) {
            contentDiv.textContent = content;
        } else {
            contentDiv.textContent = '';
        }
        
        messageDiv.appendChild(contentDiv);
        return { messageDiv, contentDiv };
    }

    function addMessage(content, isUser = false) {
        const messagesContent = document.querySelector('.chat-messages-content');
        const welcomeMessage = document.querySelector('.welcome-message');
        
        if (welcomeMessage && messagesContent.children.length === 1) {
            welcomeMessage.remove();
        }
        
        const { messageDiv } = createMessage(content, isUser);
        messagesContent.appendChild(messageDiv);
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }

    async function handleUserInputForDeepSeek() {
        const userInputElement = document.getElementById('userInput');
        const message = userInputElement.value.trim();
        if (message === '') return;

        // Clear welcome message if it exists
        const welcomeMessage = document.querySelector('.welcome-message');
        if (welcomeMessage) {
            welcomeMessage.remove();
        }

        // Add user message
        addMessage(message, 'user');
        
        // Clear input and adjust height
        userInputElement.value = '';
        adjustTextareaHeight();

        // Create an empty bot message that we'll stream into
        const { messageDiv, contentDiv } = createMessage('', false);
        const messagesContent = document.querySelector('.chat-messages-content');
        messagesContent.appendChild(messageDiv);
        
        let currentText = '';
        let displayedText = '';
        const typingSpeed = 10; // milliseconds per character

        function typeNextCharacter() {
            if (displayedText.length < currentText.length) {
                displayedText = currentText.substring(0, displayedText.length + 1);
                contentDiv.innerHTML = marked.parse(displayedText);
                
                // Apply syntax highlighting to code blocks
                document.querySelectorAll('pre code').forEach((block) => {
                    hljs.highlightElement(block);
                });
                
                // Scroll to bottom
                chatMessages.scrollTop = chatMessages.scrollHeight;
                
                // Schedule next character
                setTimeout(typeNextCharacter, typingSpeed);
            }
        }
        
        // Use fetch with streaming
       // http://myweb.yzz.me/ChatGPT4o.php?text=<query_here>

        fetch(`http://127.0.0.1:8000/generate?prompt=${encodeURIComponent(message)}`, {
            method: 'POST'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server error: ${response.status}`);
            }
            const reader = response.body.getReader();
            const decoder = new TextDecoder();

            // Recursive function to read stream
            function readStream() {
                return reader.read().then(({done, value}) => {
                    if (done) {
                        // Save to history when the response is complete
                        saveToHistory(message, currentText);
                        return;
                    }
                    
                    // Decode and process the chunk
                    const chunk = decoder.decode(value);
                    try {
                        const jsonResponse = JSON.parse(chunk);
                        if (jsonResponse.response) {
                            // Update the current text
                            currentText = jsonResponse.response;
                            
                            // If not already typing, start typing effect
                            if (displayedText.length === 0) {
                                typeNextCharacter();
                            }
                        }
                    } catch (error) {
                        console.error('Error processing chunk:', error);
                    }
                    
                    // Recursively read next chunk
                    return readStream();
                });
            }

            // Start the recursive reading
            return readStream();
        })
        .catch(error => {
            console.error('Error:', error);
            contentDiv.textContent = `Error: ${error.message}`;
        });

    }

    async function handleUserInputForChatGPT4() {
        const message = userInput.value.trim();
        if (message === '') return;

        const welcomeMessage = document.querySelector('.welcome-message');
        if (welcomeMessage) {
            welcomeMessage.remove();
        }

        addMessage(message, true);
        userInput.value = '';
        adjustTextareaHeight();

        const { messageDiv, contentDiv } = createMessage('', false);
        const messagesContent = document.querySelector('.chat-messages-content');
        messagesContent.appendChild(messageDiv);
        
        let currentText = '';
        let displayedText = '';
        const typingSpeed = 10;

        function typeNextCharacter() {
            if (displayedText.length < currentText.length) {
                displayedText = currentText.substring(0, displayedText.length + 1);
                contentDiv.innerHTML = marked.parse(displayedText);
                
                document.querySelectorAll('pre code').forEach((block) => {
                    hljs.highlightElement(block);
                });
                
                chatMessages.scrollTop = chatMessages.scrollHeight;
                setTimeout(typeNextCharacter, typingSpeed);
            }
        }

           // Static response for "who made you"
   
        try {
            // const response = await fetch(`http://127.0.0.1:5000/chat?query=${encodeURIComponent(message)}`);
            
            const response = await fetch(`https://gpt4o-production.up.railway.app/chat?query=${encodeURIComponent(message)}`);
            
            if (!response.ok) {
                throw new Error(`API Error: ${response.status}`);
            }

            const data = await response.json();
            const responseText = data.response?.data || data.response || "No response received";
            
            currentText = responseText;
            typeNextCharacter();
            
            setTimeout(() => {
                saveToHistory(message, currentText);
            }, currentText.length * typingSpeed + 100);
            
        } catch (error) {
            console.error('Error:', error);
            contentDiv.textContent = `Error: ${error.message}`;
            chatMessages.scrollTop = chatMessages.scrollHeight;
        }
    }

    function handleUserInput() {
        const selectedModel = document.getElementById('chatModelSelect').value;
        if (selectedModel === 'deepSeek') {
            handleUserInputForDeepSeek();
        } else if (selectedModel === 'chatGPT4') {
            handleUserInputForChatGPT4();
        }
    }

    function adjustTextareaHeight() {
        userInput.style.height = 'auto';
        userInput.style.height = (userInput.scrollHeight) + 'px';
    }

    function startNewChat() {
        const chatMessages = document.querySelector('.chat-messages-content');
        chatMessages.innerHTML = `
            <div class="welcome-message">
                <div class="welcome-icon">
                    <i class="fas fa-robot"></i>
                </div>
                <h3>Welcome to AI Chat!</h3>
                <p>I'm here to help you with any questions you have. I support markdown formatting!</p>
            </div>
        `;
        
        userInput.value = '';
        adjustTextareaHeight();
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }

    // Event Listeners
    sendButton.addEventListener('click', handleUserInput);
    
    userInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            handleUserInput();
        }
    });

    userInput.addEventListener('input', adjustTextareaHeight);
    newChatButton.addEventListener('click', startNewChat);

    toggleHistory.addEventListener('click', function() {
        historyPanel.classList.toggle('active');
        historyOverlay.classList.toggle('active');
        document.querySelector('.chat-container').classList.toggle('shifted');
    });

    historyOverlay.addEventListener('click', function() {
        historyPanel.classList.remove('active');
        historyOverlay.classList.remove('active');
        document.querySelector('.chat-container').classList.remove('shifted');
    });

    closeHistory.addEventListener('click', function() {
        historyPanel.classList.remove('active');
        historyOverlay.classList.remove('active');
        document.querySelector('.chat-container').classList.remove('shifted');
    });

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && historyPanel.classList.contains('active')) {
            historyPanel.classList.remove('active');
            historyOverlay.classList.remove('active');
            document.querySelector('.chat-container').classList.remove('shifted');
        }
    });

    // Initial setup
    displayHistory();
    adjustTextareaHeight();
});