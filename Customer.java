package amazonlocker;

public class Customer {
    private String name;
    private String email;
    private String mobileNumber;
    private LockerLocation location;
    private String lockerCode;

    public Customer(String name, String email, String mobileNumber, LockerLocation location) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.location = location;
    }

    public LockerLocation getLocation() {
        return location;
    }

    public void setLockerCode(String lockerCode) {
        this.lockerCode = lockerCode;
    }

    public String getEmail() {
        return email;
    }
}
