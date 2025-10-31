import java.io.*;
import java.sql.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        String url = "jdbc:mysql://127.0.0.1:3306/my_database"  ;
        String username = "root";
        String password = "sg@121";
        String withdrawQuery = "Update accounts SET balance = balance - ? Where account_number = ?";
        String depositQuery = "Update accounts SET balance = balance + ? Where account_number = ?";
        try{
         Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully!! ");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully !!");
            con.setAutoCommit(false);
            try {
                PreparedStatement withdrwStatement = con.prepareStatement(withdrawQuery);
                PreparedStatement depositStatement = con.prepareStatement(depositQuery);
                withdrwStatement.setDouble(1, 500.00);
                withdrwStatement.setString(2, "account123");
                depositStatement.setDouble(1, 500.00);
                depositStatement.setString(2, "account456");
                int rowsAffectedWithdraw = withdrwStatement.executeUpdate();
                int rowsAffectedDeposit = depositStatement.executeUpdate();
                if(rowsAffectedWithdraw > 0 && rowsAffectedDeposit > 0){
                    con.commit();
                    System.out.println("Transaction Successfully !!");
                }else {
                    con.rollback();
                    System.out.println("Transaction Failed !!");
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
         }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
