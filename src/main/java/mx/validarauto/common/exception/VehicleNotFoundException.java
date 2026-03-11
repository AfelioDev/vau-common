package mx.validarauto.common.exception;

public class VehicleNotFoundException extends RuntimeException {

    private final String identifier;

    public VehicleNotFoundException(String identifier) {
        super("Vehicle not found for identifier: " + identifier);
        this.identifier = identifier;
    }

    public VehicleNotFoundException(String identifier, String message) {
        super(message);
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
