package target.myretail.model.api;

public record DataResponse<T>(T data) {

    @Override
    public String toString() {
        return "ResponseObject{" + data + '}';
    }
}
