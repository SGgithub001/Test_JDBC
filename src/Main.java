import java.sql.*;
public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        String url = "jdbc:mysql://127.0.0.1:3306/my_database"  ;
        String username = "root";
        String password = "sg@121";
        String query = "Select * from employee;";
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
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getNString("name");
                String job_title = rs.getNString("job_title");
                Double salary = rs.getDouble("salary");
                System.out.println("-----------------------------");
                System.out.println("ID : "+id);
                System.out.println("Name : "+name);
                System.out.println("Job title : "+job_title);
                System.out.println("Salary : "+salary);
            }
            rs.close();
            stmt.close();
            con.close();
            System.out.println();
            System.out.println("Connection Closed Successfully !!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
}