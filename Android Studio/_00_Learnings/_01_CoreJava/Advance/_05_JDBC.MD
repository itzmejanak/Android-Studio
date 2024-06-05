### Fetch Data from MySQL Database using JDBC

```java
class RetrieveDemo {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_first", "root", "root");
            String query = "SELECT * FROM your_table_name";
            PreparedStatement pstmt = con.prepareStatement(query);
            
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String value1 = rs.getString("column1");
                // Retrieve other values similarly
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```