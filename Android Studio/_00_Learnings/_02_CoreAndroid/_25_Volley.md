### Volley Library

- **Volley** is an HTTP library developed by Google and was introduced first in 2013.
- Its main use is to transmit data over the network.
- It is available through the AOSP (Android Open Source Project) repository, so we need to provide its implementation in our project.

#### Advantages of Volley

1. Provides efficient network management.
2. Easier and faster request management.
3. Caching.

#### Classes in Volley Library

1. **RequestQueue**
2. **Request**

#### Types of Requests

1. **StringRequest**
2. **JsonObjectRequest**
3. **JsonArrayRequest**
4. **ImageRequest**


Note:
Their may come issue about your Android app is trying to connect to a local server using HTTP (non-secure connection), but Android versions 9 (API level 28) and above block non-secure HTTP connections by default for security reasons.

### To fix this issue we have to do this:

1. **Create the Network Security Configuration file**:

   - Create a file `res/xml/network_security_config.xml`.
   - Add the following content to the file:

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <network-security-config>
       <domain-config cleartextTrafficPermitted="true">
           <domain includeSubdomains="true">192.168.1.5</domain>
       </domain-config>
   </network-security-config>
   ```

2. **Modify the AndroidManifest.xml**:

   - Add the `android:networkSecurityConfig` attribute to the `<application>` tag in your `AndroidManifest.xml`.

   ```xml
   <application
       android:networkSecurityConfig="@xml/network_security_config"
       ... >
       ...
   </application>
   ```


