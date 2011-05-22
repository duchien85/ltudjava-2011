package heartsinjava;

public interface IPlayer {
    public String getName();
    public void addToHand(Deck.Card card);
    public void addToHand(Deck.Card[] cards);
}