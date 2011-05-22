package heartsinjava;

public final class HumanHeartsPlayer extends AbstractPlayer implements IHeartsPlayer {
    public HumanHeartsPlayer(String name) throws ArgumentNullException {
        super (name);
    }

    public Deck.Card[] discardThreeCardsToPass(PassDirection passDirection) {
        // TODO: Implement (interact with use through console)!
        return null;
    }

    public Deck.Card playTrick(HeartsTrick.HeartsTrickState state) {
        // TODO: Implement (interact with use through console)!
        return null;
    }

    public void looseTrick(Deck.Card card) {
        // TODO: Implement!
    }

    public void winTrick(Deck.Card[] cards) {
        // TODO: Implement!
    }
}