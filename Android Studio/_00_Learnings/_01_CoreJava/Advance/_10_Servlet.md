### Servlet Annotations Configurations

Using annotations, we can configure servlets without needing to modify the `web.xml` file.

#### Example:
- Create a Java class that extends `HttpServlet`.
- Override the `service` method.
- Add the annotation in the import section.

```java
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/link_name")
public class MyServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Your code here
        resp.getWriter().println("Hello from MyServlet!");
    }
}
```

In this example:
- The `@WebServlet("/link_name")` annotation maps the servlet to the specified URL pattern `/link_name`.
- The `service` method handles the request and response.

This approach simplifies servlet configuration by removing the need to update the `web.xml` file.

### Common Servlet Annotations

Annotations are part of the Java Servlet API and can be used in any version from Servlet 3.0 onwards, including newer versions like 3.1, 4.0, and 5.0.

1. **@WebServlet**
   - Used to declare a servlet.
   - Example:
     ```java
     @WebServlet("/example")
     public class ExampleServlet extends HttpServlet {
         // servlet code
     }
     ```

2. **@WebFilter**
   - Used to declare a filter.
   - Example:
     ```java
     @WebFilter("/filter_path")
     public class ExampleFilter implements Filter {
         // filter code
     }
     ```

3. **@WebListener**
   - Used to declare a listener.
   - Example:
     ```java
     @WebListener
     public class ExampleListener implements ServletContextListener {
         // listener code
     }
     ```

4. **@MultipartConfig**
   - Used to declare a servlet as supporting file uploads.
   - Example:
     ```java
     @WebServlet("/upload")
     @MultipartConfig
     public class FileUploadServlet extends HttpServlet {
         // servlet code for file upload
     }
     ```

These annotations provide a more convenient and readable way to configure servlets, filters, and listeners compared to traditional `web.xml` configurations.