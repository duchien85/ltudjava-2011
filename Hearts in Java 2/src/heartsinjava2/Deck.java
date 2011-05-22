package heartsinjava2;

import java.util.*;

public final class Deck {

    public final class Card implements Comparable<Card> {

        private final Suit suit;
        private final Rank rank;

        private Card(Suit suit, Rank rank) {
            this.suit = suit;
            this.rank = rank;
        }

        public final Suit getSuit() {
            return this.suit;
        }

        public final Rank getRank() {
            return this.rank;
        }

        @Override
        public String toString() {
            return String.format("%s of %s", this.rank, this.suit);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            final Card other = (Card) obj;

            if (this.suit != other.suit) {
                return false;
            }

            if (this.rank != other.rank) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 61 * hash + (this.suit != null ? this.suit.hashCode() : 0);
            hash = 61 * hash + (this.rank != null ? this.rank.hashCode() : 0);
            return hash;
        }

        // compares two cards(int): if suits are the same, compares ranks.
        public int compareTo(Card card) {

            if (this.suit.compareTo(card.getSuit()) == 0) {
                return this.rank.compareTo(card.getRank());
            } else {
                return this.suit.compareTo(card.getSuit());
            }
        }
    }

    private static final Deck prototype = new Deck(true);
    private final Stack<Card> cards = new Stack<Card>();

    private Deck(boolean dummy) {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.cards.push(new Card(suit, rank));
            }
        }
    }

    public Deck() {
        this.cards.addAll(Deck.prototype.cards);
    }

    public void shuffle(Random rng) {
        final ArrayList<Card> tempCards = new ArrayList<Card>(this.cards);
        this.cards.clear();

        while (tempCards.size() > 0) {
            int index = rng.nextInt(tempCards.size());
            this.cards.push(tempCards.remove(index));
        }
    }

    public Card draw() {
        return this.cards.pop();
    }

    public Card[] draw(int count) {
        final Card[] results = new Card[count];

        for (int index = 0; index < count; index++) {
            results[index] = this.cards.pop();
        }

        return results;
    }

    public boolean isEmpty() {
        return this.cards.size() == 0;
    }

    public int size() {
        return this.cards.size();
    }
}