package target.myretail.model.api;

public record ErrorResponse(String message, int status) {
    @Override
    public String toString() {
        return "Error{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
