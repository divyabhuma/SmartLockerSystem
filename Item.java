package amazonlocker;

public class Item {
    String size;
    boolean isEligibleForLocker;

    public Item(String size) {
        this.size = size;
        this.isEligibleForLocker = true;
    }
}
