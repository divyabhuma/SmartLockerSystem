package amazonlocker;


public class Order {
    String orderId;
    Item item;
    Customer customer;
    Locker assignedLocker;
    boolean isProcessed;
    boolean isExpired;

    public Order(String orderId, Item item, Customer customer) {
        this.orderId = orderId;
        this.item = item;
        this.customer = customer;
        this.isProcessed = false;
        this.isExpired = false;
    }

    public void processOrder() {
        this.isProcessed = true;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void assignLocker(Locker locker) {
        this.assignedLocker = locker;
    }

    public Locker getAssignedLocker() {
        return this.assignedLocker;
    }

    public Item getItem() {
        return this.item;
    }
}
