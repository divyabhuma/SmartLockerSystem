package amazonlocker;
public class LockerLocation {
    private double latitude;
    private double longitude;

    public LockerLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return latitude + "," + longitude;
    }


    public double calculateDistance(LockerLocation otherLocation) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(otherLocation.latitude - this.latitude);
        double dLon = Math.toRadians(otherLocation.longitude - this.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(otherLocation.latitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}
