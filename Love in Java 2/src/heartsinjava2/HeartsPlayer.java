package heartsinjava2;

public interface HeartsPlayer {

    public String getName();

    public void addToHand(Deck.Card card);

    public void addToHand(Deck.Card[] cards);

    public Deck.Card[] discardThreeCardsToPass(PassDirection passDirection);

    public Deck.Card playTrick(Deck.Card[] playedCards, Suit trickSuit);

    public void takeTrick(Deck.Card[] cards);
}