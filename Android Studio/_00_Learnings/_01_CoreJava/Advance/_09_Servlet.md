### Deployment Descriptor (web.xml)

- The deployment descriptor is a file where we provide all the details of the project into `web.xml`.

#### Use:
Let's think of a client and server scenario where the server has many servlet files, such as `login.java` and `register.java`.
- When the client requests for login, a specific file should execute, so `web.xml` comes into play.
- It decides which servlet file should execute.

#### Syntax:
- First comes the `<web-app>` tag.
    - Then comes the `<servlet-mapping>` tag.
        Inside it:
        - `<servlet-name>` tag
        - `<url-pattern>` tag
    - Then comes the `<servlet>` tag.
        Inside it:
        - `<servlet-name>` tag
        - `<servlet-class>` tag

Example:
```xml
<web-app>
    <servlet>
        <servlet-name>myservlet</servlet-name>
        <servlet-class>in.sp.backend.MyServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>myservlet</servlet-name>
        <url-pattern>/aaa</url-pattern>
    </servlet-mapping>
</web-app>
```

#### Note:
- We create the `web.xml` file inside the `webApp > WEB-INF` folder.
- Also, note it is not compulsory to make it as for updates; we can use annotations to do its work.

### Other Uses of web.xml File
1. **Servlet Configuration**
2. **JSP File Configuration**
3. **Filters Configuration**
4. **Listeners Configuration**
5. **Error Page Configuration**
6. **Welcome File Configuration**

Note:
- Just think That web.xml file is the bridge between java servlet file and html file in where all the details of the java file is give into to to perform the Html file links.
