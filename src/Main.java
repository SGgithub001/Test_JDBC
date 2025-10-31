import java.io.*;
import java.sql.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        String url = "jdbc:mysql://127.0.0.1:3306/my_database"  ;
        String username = "root";
        String password = "sg@121";
//        String image_path = "/Users/sunilgupta/Downloads/Sushil-modified-Photoroom.png";
//        String query = "Insert into image_table(image_data) Values(?)";
        String folderPath = "/Users/sunilgupta/Downloads";
        String query = "Select image_data from image_table where image_id = (?)";
        try{
         Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully!! ");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully !!");
//            FileInputStream fileInputStream = new FileInputStream(image_path);
//            byte[] imageData = new byte[fileInputStream.available()];
//            fileInputStream.read(imageData);
//            PreparedStatement preparedStatement = con.prepareStatement(query);
//            preparedStatement.setBytes(1,imageData);
//            int affectedRows = preparedStatement.executeUpdate();
//            if(affectedRows > 0){
//                System.out.println("Image Inserted Successfully");
//            }else{
//                System.out.println("Not Inserted !!");
//            }

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,1);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                byte[] image_data = rs.getBytes("image_data");
                String image_path = folderPath + "/extracted.png"; // add slash
                try (OutputStream outputStream = new FileOutputStream(image_path)) {
                    outputStream.write(image_data);
                }
                System.out.println("Image extracted successfully to: " + image_path);
            }else{
                System.out.println("Image not found !!");
            }


        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}