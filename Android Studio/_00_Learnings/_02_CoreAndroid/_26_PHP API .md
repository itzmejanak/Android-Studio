### Creating PHP API

The V8 engine executes JavaScript in web browsers (e.g., Chrome) and Node.js, while the Zend Engine runs PHP on web servers via integrations like Apache with mod_php or Nginx with PHP-FPM.

#### PHP Basics

- **PHP** is a server-side scripting language.

#### How to Create a PHP File

1. Start and end with PHP tag: `<?php ----- ?>`
2. Save the file with `.php` extension.

### Error Fixing for XAMPP

1. Run the command `netstat -ano | findstr :3306` and note the PID number.
2. Go to Task Manager.
3. Select the "Details" tab and search for that PID number there. End the process.

### Error Fixing for MySQL

1. Open the configurator of MySQL and configure it again.
2. Open the terminal of MySQL and it should be fixed.

### How to Create a Simple PHP API

1. Create a PHP file with some echo code:
   ```php
   <?php
   echo "Hello, World!";
   ?>
   ```
2. Open the XAMPP folder in the C drive.
3. Navigate to the `htdocs` folder and paste the PHP file there.
4. Open XAMPP and in the Apache server, select the "Admin" option.
5. Modify the URL by adding `/your_php_file_name.php` at the end.
   - Example: `http://localhost/your_php_file_name.php`
6. Done.

### Example of PHP API

#### Example PHP File (hello.php)
```php
<?php
header("Content-Type: application/json");

$response = array(
    "status" => "success",
    "message" => "Hello, World!"
);

echo json_encode($response);
?>
```

#### Steps to Run the PHP File

1. Save the above PHP code in a file named `hello.php`.
2. Place the `hello.php` file in the `htdocs` folder in the XAMPP directory.
3. Start Apache server from the XAMPP control panel.
4. Open a web browser and navigate to `http://localhost/hello.php`.
5. You should see a JSON response with a message: `{"status":"success","message":"Hello, World!"}`.



### Connecting to MySQL

```php
<?php
$host = "localhost";
$username = "root";
$password = "";
$db = "firstphpdb";

$con = @mysqli_connect($host, $username, $password, $db) or die(mysqli_error($con));

if ($con->connect_error) {
    echo "Failed";
} else {
    echo "Success";
}
?>
```

### Inserting Data into the Database

```php
<?php
//----------database connection starts-----------------------
$host = "localhost";
$username = "root";
$password = "";
$db = "firstphpdb";

$con = @mysqli_connect($host, $username, $password, $db) or die(mysqli_error($con));
//----------database connection ends-------------------------

$name = "deepak";
$email = "deepak@gail.com";
$pass = "deepak123";
$gender = "male";

$my_query = "INSERT INTO register (name, email, password, gender) VALUES ('$name', '$email', '$pass', '$gender')";

$result = mysqli_query($con, $my_query);

if ($result) {
    echo "Success";
} else {
    echo "Fail";
}
?>
```

### Preparing for Android Application to Send Data to PHP

```php
<?php
//----------database connection starts-----------------------
$host = "localhost";
$username = "root";
$password = "";
$db = "firstphpdb";

$con = @mysqli_connect($host, $username, $password, $db) or die(mysqli_error($con));
//----------database connection ends-------------------------

$name = $_POST["key_name"];
$email = $_POST["key_email"];
$pass = $_POST["key_pass"];
$gender = $_POST["key_gender"];

$my_query = "INSERT INTO register (name, email, password, gender) VALUES ('$name', '$email', '$pass', '$gender')";

$result = mysqli_query($con, $my_query);

if ($result) {
    echo "success";
} else {
    echo "fail";
}
?>
```
