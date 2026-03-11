package mx.validarauto.common.exception;

public class InvalidLicensePlateException extends RuntimeException {

    private final String licensePlate;

    public InvalidLicensePlateException(String licensePlate) {
        super("Invalid Mexican license plate format: " + licensePlate);
        this.licensePlate = licensePlate;
    }

    public InvalidLicensePlateException(String licensePlate, String message) {
        super(message);
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}
