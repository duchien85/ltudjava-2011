package heartsinjava;

public interface IHeartsPlayer extends IPlayer {
    public Deck.Card[] discardThreeCardsToPass(PassDirection passDirection);
    public Deck.Card playTrick(HeartsTrick.HeartsTrickState state);
    public void looseTrick(Deck.Card card);
    public void winTrick(Deck.Card[] cards);
}