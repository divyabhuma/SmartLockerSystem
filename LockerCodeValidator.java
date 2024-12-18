package amazonlocker;

public class LockerCodeValidator {
    public boolean isValidCode(Locker locker, String enteredCode) {
        return locker.assignedCode.equals(enteredCode) && !locker.isCodeExpired();
    }
}
