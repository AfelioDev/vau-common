package mx.validarauto.common.exception;

public class ExternalApiException extends RuntimeException {

    private final int statusCode;
    private final String serviceName;

    public ExternalApiException(String serviceName, int statusCode, String message) {
        super("External API error from " + serviceName + " [" + statusCode + "]: " + message);
        this.statusCode = statusCode;
        this.serviceName = serviceName;
    }

    public ExternalApiException(String serviceName, int statusCode, String message, Throwable cause) {
        super("External API error from " + serviceName + " [" + statusCode + "]: " + message, cause);
        this.statusCode = statusCode;
        this.serviceName = serviceName;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getServiceName() {
        return serviceName;
    }
}
