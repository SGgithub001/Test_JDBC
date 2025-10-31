import java.sql.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        String url = "jdbc:mysql://127.0.0.1:3306/my_database"  ;
        String username = "root";
        String password = "sg@121";
        String query = "Insert into employee(id, name, job_title, salary) Values(?, ?, ?, ?)";
        try{
         Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully!! ");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully !!");
            Scanner scn = new Scanner(System.in);
            System.out.print("Enter employee ID: ");
            int id = scn.nextInt();
            scn.nextLine();
            System.out.print("Enter employee name: ");
            String name = scn.nextLine();
            System.out.print("Enter job title: ");
            String job_title = scn.nextLine();
            System.out.print("Enter salary: ");
            double salary = scn.nextDouble();

            PreparedStatement preparedStatement = con.prepareStatement(query);
//            preparedStatement.setString(1,"Sushil");
//            preparedStatement.setString(2,"Software Developer");
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,job_title);
            preparedStatement.setDouble(4,salary);
//            ResultSet rs = preparedStatement.executeQuery();
//            while(rs.next()){
//                int id = rs.getInt("id");
//                String name = rs.getNString("name");
//                String job_title = rs.getNString("job_title");
//                double salary = rs.getDouble("salary");
//                System.out.println("ID : "+ id);
//                System.out.println("Name : "+ name);
//                System.out.println("Job Title : "+ job_title);
//                System.out.println("Salary : "+ salary);
//            }

//            rs.close();

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Data Inserted Successfully !!");
            } else{
                System.out.println("Data Insertion Failed !!");
            }
            preparedStatement.close();
            con.close();
            System.out.println();
            System.out.println("Connection Closed Successfully !!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
}