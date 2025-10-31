import java.sql.*;
public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        String url = "jdbc:mysql://127.0.0.1:3306/my_database"  ;
        String username = "root";
        String password = "sg@121";
        String query = "Update employee\n" +
                "SET job_title = 'Data Analyst',salary = 120000\n" +
                "Where id = 2";
        try{
         Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully!! ");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully !!");
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(query);
            if(rowsAffected > 0){
                System.out.println("Updation successful " + rowsAffected + "row(s) affected");
            }else{
                System.out.println("Updation failed !!");
            }

            stmt.close();
            con.close();
            System.out.println();
            System.out.println("Connection Closed Successfully !!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
}