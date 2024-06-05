### Update and Delete Operation using JDBC

#### Update Operation

```java
class DemoUpdate {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            // For update
            String city = "ktm";
            String email = "abc@gmail.com";
            String query = "UPDATE register SET city=? WHERE emailId=?";

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_first", "root", "root");
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, city);
            pstmt.setString(2, email);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("Update successful");
            } else {
                System.out.println("Update failed");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

#### Delete Operation

```java
class DemoDelete {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            // For delete
            String email = "abc@gmail.com";
            String query = "DELETE FROM register WHERE email=?";

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_first", "root", "root");
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, email);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("Delete successful");
            } else {
                System.out.println("Delete failed");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```