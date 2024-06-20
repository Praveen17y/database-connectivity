import java.sql.*;
import java.util.ArrayList;

public class UserOperations {


    private static final String URL = "jdbc:mysql://localhost:3306/d1";
    private static final String USER = "root";
    private static final String PASSWORD = "0000";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Scenario 1: Get User Type by UserID
    public static String getUserType(String userID) {
        String userType = null;
        String query = "SELECT UserType FROM Users WHERE UserID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                userType = rs.getString("UserType");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userType;
    }

    // Scenario 2: Get Incorrect Attempts Message by UserID
    public static String getIncorrectAttempts(String userID) {
        String result = null;
        String query = "SELECT IncorrectAttempts FROM Users WHERE UserID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int attempts = rs.getInt("IncorrectAttempts");
                if (attempts == 0) {
                    result = "No Incorrect Attempt";
                } else if (attempts == 1) {
                    result = "One Time";
                } else {
                    result = "Incorrect Attempt Exceeded";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Scenario 3: Change User Type to 'Admin' by UserID
    public static String changeUserType(String userID) {
        String query = "UPDATE Users SET UserType = 'Admin' WHERE UserID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return "Update Success";
            } else {
                return "Update Failed";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Update Failed";
        }
    }

    // Scenario 4: Get Count of Users with Lock Status 0
    public static int getLockStatus() {
        String query = "SELECT COUNT(*) AS Total FROM Users WHERE LockStatus = 0";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Scenario 5: Change User Name by UserID
    public static String changeName(String userID, String newName) {
        String query = "UPDATE Users SET Name = ? WHERE UserID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, userID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return "Success";
            } else {
                return "Failed";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed";
        }
    }

    // Scenario 6: Change User Password by UserID
    public static String changePassword(String userID, String newPassword) {
        String query = "UPDATE Users SET Password = ? WHERE UserID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, userID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return "Password Change Success";
            } else {
                return "Password Change Failed";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Password Change Failed";
        }
    }

    // Scenario 7: Add User to Database
    public static String addUser_2(UserBean bean) {
        if (bean.getLockStatus() != 0) {
            return "Fail";
        }
        String query = "INSERT INTO Users (UserID, Password, Name, IncorrectAttempts, LockStatus, UserType) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, bean.getUserID());
            pstmt.setString(2, bean.getPassword());
            pstmt.setString(3, bean.getName());
            pstmt.setInt(4, bean.getIncorrectAttempts());
            pstmt.setInt(5, bean.getLockStatus());
            pstmt.setString(6, bean.getUserType());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return "Success";
            } else {
                return "Fail";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Fail";
        }
    }

    // Scenario 9: Get Users by UserType
    public static ArrayList<UserBean> getUsers(String userType) {
        ArrayList<UserBean> userList = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE UserType = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userType);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserBean user = new UserBean();
                user.setUserID(rs.getString("UserID"));
                user.setPassword(rs.getString("Password"));
                user.setName(rs.getString("Name"));
                user.setIncorrectAttempts(rs.getInt("IncorrectAttempts"));
                user.setLockStatus(rs.getInt("LockStatus"));
                user.setUserType(rs.getString("UserType"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // Scenario 10: Store All User Records
    public static ArrayList<UserBean> storeAllRecords() {
        ArrayList<UserBean> userList = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserBean user = new UserBean();
                user.setUserID(rs.getString("UserID"));
                user.setPassword(rs.getString("Password"));
                user.setName(rs.getString("Name"));
                user.setIncorrectAttempts(rs.getInt("IncorrectAttempts"));
                user.setLockStatus(rs.getInt("LockStatus"));
                user.setUserType(rs.getString("UserType"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // Scenario 11: Get Names of All Users
    public static String[] getNames() {
        ArrayList<String> nameList = new ArrayList<>();
        String query = "SELECT Name FROM Users";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                nameList.add(rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nameList.toArray(new String[0]);
    }

    // Additional method for printing user details
    public static void printUserDetails(UserBean user) {
        System.out.println("User ID: " + user.getUserID());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Name: " + user.getName());
        System.out.println("Incorrect Attempts: " + user.getIncorrectAttempts());
        System.out.println("Lock Status: " + user.getLockStatus());
        System.out.println("User Type: " + user.getUserType());
        System.out.println();
    }
}
