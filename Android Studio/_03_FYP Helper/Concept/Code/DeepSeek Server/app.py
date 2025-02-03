from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import ollama

app = FastAPI()

# Add CORS middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Allows all origins
    allow_credentials=True,
    allow_methods=["*"],  # Allows all methods
    allow_headers=["*"],  # Allows all headers
)

@app.get("/")
def home():
    return {"message": "Ollama API is running!"}

# This method will handle the complete flow.
@app.post("/generate")
def generate_text(prompt: str):
    try:
        response = ollama.chat(model="deepseek-r1:1.5b", messages=[{"role": "user", "content": prompt}])

        # Call method to return the entire response
        return cleaned_content_response(response)

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# Method to return the complete response
def full_response(response: dict):
    return {"response": response}

# Method to return only the message from the response
def message_response(response: dict):
    return {"response": response["message"]}

# Method to extract the content from the message in the response
def content_response(response: dict):
    content = response["message"]["content"]
    return {"response": content}

# Method to clean the content by removing <think> tags
def cleaned_content_response(response: dict):
    content = response["message"]["content"]
    clean_content = content.replace("<think>", "").replace("</think>", "").strip()
    return {"response": clean_content}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="127.0.0.1", port=8000)