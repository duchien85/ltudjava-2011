package heartsinjava;

import java.util.*;

public final class Deck {
	public final class Card {
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

        // TODO: Override equals() and hashcode() following standard pattern
	}

    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    public enum Rank {
        DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    private static final Deck prototype = new Deck();

    static {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				Deck.prototype.cards.push(Deck.prototype.new Card(suit, rank));
			}
		}
    }

	private final Stack<Card> cards = new Stack<Card>();

    public Deck() {
        this.reset();
    }

    public void reset() {
        // TODO: Is this still needed?
        this.cards.clear();
        this.cards.addAll(Deck.prototype.cards);
    }

	public Set<Card> getCards()
	{
		Set<Card> set = new LinkedHashSet<Card>();
		set.addAll(this.cards);
		return set;
	}

    public void shuffle() {
        // TODO: shuffle the stack of cards
    }

    public Card draw() {
        // TODO: pop the next card from the stack and return it
        return null;
    }

    public Card[] draw(int count) {
        // TODO: pop the next X cards from the stack and return them
        return null;
    }

    public boolean isEmpty() {
        return this.cards.size() == 0;
    }
}
