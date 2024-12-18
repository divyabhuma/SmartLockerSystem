package amazonlocker;

import java.util.*;

public class AmazonLockerSystem {
    private LockerService lockerService = new LockerService();
    private List<Order> orders = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private LockerCodeValidator lockerCodeValidator = new LockerCodeValidator();


    public void addLocker(Locker locker) {
        lockerService.addLocker(locker);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void placeOrder(Order order) {
        orders.add(order);
    }

    public void assignLockerToOrder(Order order) {
        Item item = order.getItem();
        Locker assignedLocker = lockerService.assignLocker(item.size, order.customer.getLocation().getLatitude(),
                order.customer.getLocation().getLongitude());
        if (assignedLocker != null) {
            order.assignLocker(assignedLocker);
            String lockerCode = lockerService.generateLockerCode(assignedLocker.id);
            String returnCode = lockerService.generateReturnCode(assignedLocker.id);
            order.customer.setLockerCode(lockerCode);
            assignedLocker.setAssignedCode(lockerCode);
            assignedLocker.setReturnCode(returnCode);

            System.out.println("Notification to " + order.customer.getEmail() + ":");
            System.out.println("Your order " + order.orderId + " has been assigned to Locker " + assignedLocker.id);
            System.out.println("Locker Code: " + lockerCode);
            System.out.println("Locker Location: " + assignedLocker.location + "\n");
        } else {
            System.out.println("No locker available for the specified size!");
        }
    }

    private Map<String, String> returnCodes = new HashMap<>();

    public String generateAndSendReturnCode(Order order) {
        if (order.assignedLocker == null) {
            System.out.println("No locker assigned to this order. Cannot generate a return code.");
            return null;
        }
        String returnCode = String.format("RET%06d", new Random().nextInt(1000000));
        returnCodes.put(order.orderId, returnCode);
        System.out.println("Notification to " + order.customer.getEmail() + ":");
        System.out.println("Your return code for Order " + order.orderId + " is: " + returnCode);
        return returnCode;
    }

    public boolean validateReturnCode(Order order, String enteredCode) {
        String correctReturnCode = returnCodes.get(order.orderId);
        return correctReturnCode != null && correctReturnCode.equals(enteredCode);
    }

    public void validateLockerCode(Customer customer, String enteredCode, boolean isReturn) {
        for (Order order : orders) {
            if (order.customer.equals(customer)) {
                Locker assignedLocker = order.assignedLocker;
                if (isReturn) {
                    if (assignedLocker.returnCode.equals(enteredCode)) {
                        System.out.println("Return code is valid. Item returned successfully.");
                        System.out.println("Notification to " + order.customer.getEmail() + ":"+ " Refund successfull to your account.");
                        assignedLocker.isOccupied = false;
                    } else {
                        System.out.println("Invalid return code.");
                    }
                } else {
                    boolean isValid = lockerCodeValidator.isValidCode(assignedLocker, enteredCode);
                    if (isValid) {
                        System.out.println("Locker code is valid. You can open the locker.\n");
                        System.out.println("Notification to " + order.customer.getEmail() + ":"+ " your order is picked up successfully.");
                        assignedLocker.isOccupied = false;
                    } else {
                        System.out.println("Invalid locker code or the code has expired.");
                    }
                }
                break;
            }
        }
    }

}
