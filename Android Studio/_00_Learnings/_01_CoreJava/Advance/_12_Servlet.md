### Differences between Http Methods GET and POST

The actions performed in servlet files by the methods are known as HTTP methods.

- **GET Method**:
  - Whenever we want to transfer the data by the URL, we use the GET method (data is visible in the links), but it is fast.
- **POST Method**:
  - If we want to make data transfer more secure, we use the POST method (data is not visible in the links).

#### Difference between GET and POST:

1. **Data Transmission**:
   - **GET**: Sends data through the resource URL.
   - **POST**: Sends data through the HTTP message body.

2. **Security**:
   - **GET**: Not secure as data is visible in the URL.
   - **POST**: More secure as data is not visible in the URL.

3. **Data Capacity**:
   - **GET**: We can send very little data using the GET request because data is transferred using the URL.
   - **POST**: We can send more data using POST because it does not send data using the URL.

4. **Caching**:
   - **GET**: GET request can be cached.
   - **POST**: POST request cannot be cached.

5. **Bookmarking**:
   - **GET**: GET request can be bookmarked.
   - **POST**: POST method cannot be bookmarked.
