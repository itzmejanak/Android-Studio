### HttpServletRequest and HttpServletResponse

Whenever a user provides data on the login and register page, we need to bring that data into the servlet file's `service` method, which has `req` and `resp` objects of the `HttpServletRequest` and `HttpServletResponse` interfaces. We extract data from these objects using methods provided by these interfaces.

#### Methods of HttpServletRequest

- `getParameter(String name)`: Returns the value of the request parameter specified by the name.
- `getCookies()`: Returns an array of `Cookie` objects representing the cookies included in the request.
- `getSession(boolean create)`: Returns the current session associated with the request or creates a new one if create is true.
- `getMethod()`: Returns the HTTP method of the request, such as GET, POST, PUT, DELETE, etc.
- `getAttribute(String name)`: Returns the value of the named attribute as an `Object`.
- `setAttribute(String name, Object value)`: Binds an object to a given attribute name in the request scope.
- `getHeader(String name)`: Returns the value of the specified HTTP header.
- `getHeaderNames()`: Returns an enumeration of all the header names sent with the request.

**Note:** If we need to write into the HTML page, we need a `PrintWriter` reference.

#### Methods of HttpServletResponse

- `getWriter()`: Returns a `PrintWriter` object that can be used to send character text to the client.
- `setContentType(String type)`: Sets the MIME type of the response.
- `setContentLength(int len)`: Sets the length of the content being returned in the response.
- `sendRedirect(String location)`: Redirects the client to a different URL.
- `sendError(int sc, String msg)`: Sends an error response to the client with the specified status code and message.
- `addCookie(Cookie cookie)`: Adds a cookie to the response.
- `setStatus(int sc)`: Sets the status code of the response.
- `setHeader(String name, String value)`: Sets the value of the specified response header.
- `addHeader(String name, String value)`: Adds a response header with the given name and value.

### Example: Login Form and Servlet

#### HTML (login.html)
```html
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
    <form action="aaa" method="POST">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username"><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"><br><br>
        <input type="submit" value="Login">
    </form>
</body>
</html>
```

#### Servlet (LoginServlet.java)
```java
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/aaa")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Extract data from the request
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Set response content type
        resp.setContentType("text/html");

        // Get PrintWriter to write response
        PrintWriter out = resp.getWriter();

        // Validate login (dummy validation for demonstration)
        if ("admin".equals(username) && "password".equals(password)) {
            out.println("<h1>Welcome, " + username + "!</h1>");
        } else {
            out.println("<h1>Invalid username or password.</h1>");
        }

        // Close the PrintWriter
        out.close();
    }
}
```

In this example:
- The HTML form uses `action="aaa"` to specify that the form data should be sent to the `/aaa` URL when the form is submitted.
- The `LoginServlet` class is annotated with `@WebServlet("/aaa")`, which maps the servlet to handle requests sent to the `/aaa` URL.
- The servlet's `doPost` method handles the form submission, extracts the `username` and `password` parameters, and writes an appropriate response back to the client.