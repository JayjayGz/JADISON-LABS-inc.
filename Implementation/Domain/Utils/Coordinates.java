package Implementation.Domain.Utils;

public record Coordinates(double latitude, double longitude) {
    public Coordinates {
        if (!Double.isFinite(latitude) || latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Invalid latitude. Latitude must be between -90 and 90 degrees");
        }

        if (!Double.isFinite(longitude) || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid longitude. Longitude must be between -180 and 180 degrees");
        }
    }

}
