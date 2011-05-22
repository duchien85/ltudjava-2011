package heartsinjava;

public interface IResult<T extends IPlayer> {
    public T getWinner();
}