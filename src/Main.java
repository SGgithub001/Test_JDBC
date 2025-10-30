import java.sql.*;
public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        String url = "jdbc:mysql://127.0.0.1:3306/my_database"  ;
        String username = "root";
        String password = "sg@121";
        String query = "INSERT INTO employee(id,name,job_title,salary) VALUES (4,'Golu','Full Stack Developer',1500000);";
        try{
         Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully!! ");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connrction Established Successfully !!");
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(query);
            if(rowsAffected > 0){
                System.out.println("Insert successful " + rowsAffected + "row(s) affected");
            }else{
                System.out.println("Insertion failed !!");
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