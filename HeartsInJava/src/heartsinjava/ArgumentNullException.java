package heartsinjava;

public final class ArgumentNullException extends Exception {
    public ArgumentNullException(String argument) {
        super("The %s argument was null (if a String, it may have been zero length).");
    }
}