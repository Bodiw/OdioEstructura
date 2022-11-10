package exception;

public class IllegalModificationException extends RuntimeException {

    public IllegalModificationException() {
        super();
    }

    public IllegalModificationException(String s) {
        super(s);
    }

    public IllegalModificationException(Throwable t) {
        super(t);
    }
}
