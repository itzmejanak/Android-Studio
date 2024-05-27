### Intent:
- The dictionary meaning of intent is intention or purpose; thus, here intent is defined as the intention to do any action.
- Intent can also be described as a message to perform an action on the screen.

### Actions that can be performed by an Intent:
1. Launch another activity
2. Dial a phone call
3. Display the list of contacts
4. Display a web page
5. Start the service
6. Broadcast a message
   - etc.

- Intents are also used to communicate between application components and can provide connectivity between two apps.
- Programmatically, intents are objects used to pass information between activities within a single application or across multiple applications.

**For example:**
```java
Intent intent = new Intent(----);
startActivity(intent); // or startService(-);
```

### Types of Intent:
1. Implicit Intent
2. Explicit Intent

---

### Implicit Intent:
- Provides information to the available components provided by the system that is to be invoked.
- **Example:** Open a website in our application.

### Explicit Intent:
- Specifies the external component and provides the external class to be invoked.
- **Example:** Open another activity.
- **Note:** Whenever we open a new activity, the current activity is stored in the stack memory. When we press the back button, the previous activity opens, and it is removed from the stack memory.

### Uses of Intent:
- **Intent for an activity:**
  - We can open an activity by using `startActivity(intent);`.
  
- **Intent for services:**
  - Services execute in the background (e.g., downloading a file, playing music). We use the `startService(-)` method.

- **Intent for broadcast receivers:**
- Broadcast receivers handle messages, for example:
  - Displaying a message when the file is downloaded
  - Showing a warning message for low battery
---