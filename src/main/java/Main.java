import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Test Scenario 1
        String userType = UserOperations.getUserType("AB1001");
        System.out.println("User Type: " + userType);

        // Test Scenario 2
        String incorrectAttempts = UserOperations.getIncorrectAttempts("TA1002");
        System.out.println("Incorrect Attempts: " + incorrectAttempts);
        System.out.println("\n");
        // Test Scenario 3
        String changeUserTypeResult = UserOperations.changeUserType("RS1003");
        System.out.println(changeUserTypeResult);
        System.out.println("\n");

        // Test Scenario 4
        int lockStatusCount = UserOperations.getLockStatus();
        System.out.println("Users with Lock Status 0: " + lockStatusCount);
        System.out.println("\n");
        // Test Scenario 5
        String changeNameResult = UserOperations.changeName("AB1001", "Harish");
        System.out.println(changeNameResult);
        System.out.println("\n");
        // Test Scenario 6
        String changePasswordResult = UserOperations.changePassword("TA1002", "newpassword123");
        System.out.println(changePasswordResult);
        System.out.println("\n");
        // Test Scenario 7
        UserBean newUser = new UserBean();
        newUser.setUserID("MK1004");
        newUser.setPassword("MK1004");
        newUser.setName("Mohan");
        newUser.setIncorrectAttempts(0);
        newUser.setLockStatus(0);
        newUser.setUserType("Employee");
        String addUserResult = UserOperations.addUser_2(newUser);
        System.out.println(addUserResult);

        // Test Scenario 9
        ArrayList<UserBean> employees = UserOperations.getUsers("Employee");
        for (UserBean user : employees) {
            System.out.println(user.getUserID() + ", " + user.getName() + ", " + user.getUserType());
        }
        System.out.println("\n");
        // Test Scenario 10
        ArrayList<UserBean> allUsers = UserOperations.storeAllRecords();
        for (UserBean user : allUsers) {
            System.out.println(user.getUserID() + ", " + user.getName() + ", " + user.getUserType());
        }
        System.out.println("\n");
        // Test Scenario 11
        String[] names = UserOperations.getNames();
        for (String name : names) {
            System.out.println("User Name: " + name);
        }
    }
}
