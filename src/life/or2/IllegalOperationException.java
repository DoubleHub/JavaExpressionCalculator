package life.or2;

public class IllegalOperationException extends Throwable {

    private String reason;

    public IllegalOperationException(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }

}
