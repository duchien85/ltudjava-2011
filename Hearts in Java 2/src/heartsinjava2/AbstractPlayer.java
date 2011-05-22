package heartsinjava2;

import java.util.*;

public abstract class AbstractPlayer {

    private final ArrayList<Deck.Card> hand = new ArrayList<Deck.Card>();
    private final String name;

    protected AbstractPlayer(String name) throws ArgumentNullException {
        if (name == null || name.length() == 0) {
            throw new ArgumentNullException("name");
        }

        this.name = name;
    }

    public ArrayList<Deck.Card> getHand() {
        return this.hand;
    }

    public String getName() {
        return this.name;
    }

    public void addToHand(Deck.Card card) {
        System.out.printf("%s receives %s\n", this.name, HeartsUtilities.formatCard(card));
        this.hand.add(card);
    }

    public void addToHand(Deck.Card[] cards) {
        System.out.printf("%s receives %s\n", this.name, HeartsUtilities.formatCards(cards));
        this.hand.addAll(Arrays.asList(cards));
    }

    public void takeTrick(Deck.Card[] cards) {
        System.out.printf("%s takes the trick %s\n", this.name, HeartsUtilities.formatCards(cards));
    }
}