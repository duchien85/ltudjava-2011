package heartsinjava;

/**
 * Provides a default/base implementation of IPlayer by maintaining a hand
 * and knowing the players name.
 * @author Valinor
 */
public abstract class AbstractPlayer implements IPlayer {
    private final Hand hand = new Hand();
    private final String name;

    protected AbstractPlayer(String name) throws ArgumentNullException {
        if (name == null || name.length() == 0) {
            throw new ArgumentNullException("name");
        }

        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addToHand(Deck.Card card) {
        this.hand.add(card);
    }

    public void addToHand(Deck.Card[] cards) {
        this.hand.add(cards);
    }
}