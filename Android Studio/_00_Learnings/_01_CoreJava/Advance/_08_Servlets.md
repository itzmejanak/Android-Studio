### Life Cycle of Servlet

When we create the servlet file in Apache Tomcat server, the following steps occur:

#### Loading and Instantiation
- When our Apache Tomcat server begins to start, our `.java` servlet file will be compiled into a `.class` file and will be loaded into memory.
- After that, an object of our servlet class will be created.
- When our object is created, it will come to the initialization phase by calling the `init()` method. We can also say the servlet object will be initialized by invoking the `init()` method.

#### Request Handling
- When a client sends a request, the `service()` method will be invoked first. This method handles the backend part.
- After that, the `service()` method will check the request method type.
  - If it is of type GET, then it will call the `doGet()` method, similarly for other types.

**Note:** Each time when a client sends a request, a different thread will be created for each one. Internally, multi-threading concepts are used here for handling multiple requests.

#### Destroy
- At last, the `destroy()` method will be called, meaning the object of the servlet class will be deleted.
