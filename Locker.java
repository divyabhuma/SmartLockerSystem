package amazonlocker;

public class Locker {
    String id;
    String size;
    String location;
    boolean isOccupied;
    String assignedCode;
    String returnCode;
    long codeTimestamp;

    public Locker(String id, String size, String location, String openingTime, String closingTime) {
        this.id = id;
        this.size = size;
        this.location = location;
        this.isOccupied = false;
        this.assignedCode = "";
        this.returnCode = "";
        this.codeTimestamp = System.currentTimeMillis();
    }

    public boolean isCodeExpired() {
        long currentTime = System.currentTimeMillis();
        return currentTime - codeTimestamp > 3 * 24 * 60 * 60 * 1000;
    }

    public void setAssignedCode(String code) {
        this.assignedCode = code;
        this.codeTimestamp = System.currentTimeMillis();
    }

    public void setReturnCode(String code) {
        this.returnCode = code;
        this.codeTimestamp = System.currentTimeMillis();
    }
}
