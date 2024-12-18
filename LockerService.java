package amazonlocker;

import java.util.*;

public class LockerService {
    private List<Locker> lockers = new ArrayList<>();
    private Map<String, String> lockerCodes = new HashMap<>();
    private Set<String> uniqueCodes = new HashSet<>();

    public void addLocker(Locker locker) {
        lockers.add(locker);
    }

    public Locker assignLocker(String size, double customerLatitude, double customerLongitude) {
        Locker bestLocker = null;
        double minDistance = Double.MAX_VALUE;

        for (Locker locker : lockers) {
            if (!locker.isOccupied && locker.size.equalsIgnoreCase(size)) {
                LockerLocation lockerLocation = new LockerLocation(customerLatitude, customerLongitude);
                LockerLocation lockerGeoLocation = new LockerLocation(
                        Double.parseDouble(locker.location.split(",")[0]),
                        Double.parseDouble(locker.location.split(",")[1])
                );
                double distance = lockerGeoLocation.calculateDistance(lockerLocation);
                if (distance < minDistance) {
                    minDistance = distance;
                    bestLocker = locker;
                }
            }
        }

        if (bestLocker != null) {
            bestLocker.isOccupied = true;
        }

        return bestLocker;
    }

    public String generateLockerCode(String lockerId) {
        String code;
        do {
            code = String.format("%06d", new Random().nextInt(1000000));
        } while (uniqueCodes.contains(code));
        uniqueCodes.add(code);
        lockerCodes.put(lockerId, code);
        return code;
    }

    public String generateReturnCode(String lockerId) {
        String returnCode;
        do {
            returnCode = "R" + String.format("%06d", new Random().nextInt(1000000));
        } while (uniqueCodes.contains(returnCode));
        uniqueCodes.add(returnCode);
        lockerCodes.put(lockerId, returnCode);
        return returnCode;
    }
}
