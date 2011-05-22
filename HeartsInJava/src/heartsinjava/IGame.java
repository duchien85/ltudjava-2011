package heartsinjava;

public interface IGame<T extends IResult> {
    public T play() throws InvalidPlayerActionException;
}