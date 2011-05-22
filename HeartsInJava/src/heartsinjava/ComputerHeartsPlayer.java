package heartsinjava;

public final class ComputerHeartsPlayer extends AbstractPlayer implements IHeartsPlayer {
    public ComputerHeartsPlayer(String name) throws ArgumentNullException {
        super (name);
    }

    public Deck.Card[] discardThreeCardsToPass(PassDirection passDirection) {
        // TODO: Implement (AI)!
        return null;
    }

    public Deck.Card playTrick(HeartsTrick.HeartsTrickState state) {
        // TODO: Implement (AI)!
        return null;
    }

    public void looseTrick(Deck.Card card) {
        // TODO: Implement (AI)!
    }

    public void winTrick(Deck.Card[] cards) {
        // TODO: Implement (AI)!
    }
}