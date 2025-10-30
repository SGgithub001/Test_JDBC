import java.sql.*;
import java.util.Scanner;

public class HotelReservation {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/hotel_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sg@121";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 Scanner scanner = new Scanner(System.in)) {

                while (true) {
                    System.out.println("\nHOTEL MANAGEMENT SYSTEM");
                    System.out.println("1. Reserve a room");
                    System.out.println("2. View Reservations");
                    System.out.println("3. Get Room Number");
                    System.out.println("4. Update Reservations");
                    System.out.println("5. Delete Reservations");
                    System.out.println("0. Exit");
                    System.out.print("Choose an option: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1 -> reserveRoom(connection, scanner);
                        case 2 -> viewReservations(connection);
                        case 3 -> getRoomNumber(connection, scanner);
                        case 4 -> updateReservation(connection, scanner);
                        case 5 -> deleteReservation(connection, scanner);
                        case 0 -> {
                            exit();
                            return;
                        }
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        }
    }

    private static void reserveRoom(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter guest name: ");
            String guestName = sc.nextLine();
            System.out.print("Enter room number: ");
            int roomNumber = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter contact number: ");
            String contact = sc.nextLine();

            String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) " +
                    "VALUES ('" + guestName + "', " + roomNumber + ", '" + contact + "')";

            Statement stmt = conn.createStatement();
            int rows = stmt.executeUpdate(sql);
            System.out.println(rows > 0 ? "Reservation successful!" : "Reservation failed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection conn) {
        String sql = "SELECT * FROM reservations";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("+----------------+-----------------+-------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number | Contact Number       | Reservation Date        |");
            System.out.println("+----------------+-----------------+-------------+----------------------+-------------------------+");

            while (rs.next()) {
                System.out.printf("| %-14d | %-15s | %-11d | %-20s | %-23s |\n",
                        rs.getInt("reservation_id"),
                        rs.getString("guest_name"),
                        rs.getInt("room_number"),
                        rs.getString("contact_number"),
                        rs.getTimestamp("reservation_date").toString());
            }
            System.out.println("+----------------+-----------------+-------------+----------------------+-------------------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getRoomNumber(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter reservation ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "SELECT room_number FROM reservations WHERE reservation_id = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                System.out.println("Room number: " + rs.getInt("room_number"));
            } else {
                System.out.println("Reservation not found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter reservation ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            if (!reservationExists(conn, id)) {
                System.out.println("Reservation not found!");
                return;
            }

            System.out.print("Enter new guest name: ");
            String guestName = sc.nextLine();
            System.out.print("Enter new room number: ");
            int roomNumber = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new contact number: ");
            String contact = sc.nextLine();

            String sql = "UPDATE reservations SET guest_name='" + guestName +
                    "', room_number=" + roomNumber +
                    ", contact_number='" + contact +
                    "' WHERE reservation_id=" + id;

            Statement stmt = conn.createStatement();
            int rows = stmt.executeUpdate(sql);
            System.out.println(rows > 0 ? "Reservation updated successfully!" : "Update failed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter reservation ID to delete: ");
            int id = sc.nextInt();
            sc.nextLine();

            if (!reservationExists(conn, id)) {
                System.out.println("Reservation not found!");
                return;
            }

            String sql = "DELETE FROM reservations WHERE reservation_id=" + id;
            Statement stmt = conn.createStatement();
            int rows = stmt.executeUpdate(sql);
            System.out.println(rows > 0 ? "Reservation deleted successfully!" : "Deletion failed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection conn, int id) {
        try {
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id=" + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void exit() {
        System.out.print("Exiting System");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(500);
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nThank You For Using Hotel Reservation System!");
    }
}
