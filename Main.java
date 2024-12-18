
package amazonlocker;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        return emailPattern.matcher(email).matches();
    }
    private static boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[0-9]{10}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        return phonePattern.matcher(phoneNumber).matches();
    }

    public static void main(String[] args) {
        AmazonLockerSystem amazonLockerSystem = new AmazonLockerSystem();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Amazon Locker System!");

        for (int i = 1; i <= 80; i++) {
            String id = "L" + i;
            String size = (i % 3 == 0) ? "Large" : (i % 2 == 0) ? "Medium" : "Small";
            String location = (i % 2 == 0) ? "40.7128,-74.0060" : "40.7306,-73.9352";
            Locker locker = new Locker(id, size, location, "9:00 AM", "9:00 PM");
            amazonLockerSystem.addLocker(locker);
        }

        System.out.println("\nEnter Customer Details:");
        System.out.print("Customer Name: ");
        String customerName = scanner.nextLine();
        String customerEmail;
        while (true) {
            System.out.print("Customer Email: ");
            customerEmail = scanner.nextLine();
            if (isValidEmail(customerEmail)) {
                break;
            } else {
                System.out.println("Invalid email format! Please enter a valid email.");
            }
        }

        String customerMobileNumber;
        while (true) {
            System.out.print("Customer Mobile Number: ");
            customerMobileNumber = scanner.nextLine();
            if (isValidPhoneNumber(customerMobileNumber)) {
                break;
            } else {
                System.out.println("Invalid phone number! Enter a 10-digit number.");
            }
        }

        LockerLocation customerLocation = new LockerLocation(40.7128, -74.0060);
        Customer customer = new Customer(customerName, customerEmail, customerMobileNumber, customerLocation);
        amazonLockerSystem.addCustomer(customer);


        System.out.println("\nEnter Item Details:");
        System.out.print("Item Size (Small/Medium/Large): ");
        String itemSize = scanner.nextLine();
        Item item = new Item(itemSize);

        System.out.print("\nEnter Order ID: ");
        String orderId = scanner.nextLine();

        Order order = new Order(orderId, item, customer);
        amazonLockerSystem.placeOrder(order);
        amazonLockerSystem.assignLockerToOrder(order);

        System.out.print("\nDo you want to recieve or return the order (return/recieve): ");
        String returnResponse = scanner.nextLine().trim().toLowerCase();

        if (returnResponse.equals("return")) {
            String returnCode = amazonLockerSystem.generateAndSendReturnCode(order);
            if (returnCode != null) {
                System.out.print("Enter the return code to proceed: ");
                String enteredReturnCode = scanner.nextLine();
                if (amazonLockerSystem.validateReturnCode(order, enteredReturnCode)) {
                    System.out.println("Return code is valid. The item has been successfully returned.");
                    order.assignedLocker.isOccupied = false;
                } else {
                    System.out.println("Invalid return code! Unable to return the item.");
                }
            }
        } else {
            System.out.print("\nDo you want to open the locker? (yes/no): ");
            String openResponse = scanner.nextLine().trim().toLowerCase();
            if (openResponse.equals("yes")) {
                System.out.print("Enter the locker code to open the locker: ");
                String enteredLockerCode = scanner.nextLine();
                amazonLockerSystem.validateLockerCode(customer, enteredLockerCode, false);
            }
        }

        scanner.close();
    }
}