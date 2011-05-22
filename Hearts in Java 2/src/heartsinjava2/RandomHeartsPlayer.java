package heartsinjava2;

import java.util.*;

public final class RandomHeartsPlayer extends AbstractPlayer implements HeartsPlayer {

    private final Random rng;
    private boolean firstTrickPlayed = false;

    public RandomHeartsPlayer(String name, Random rng) throws ArgumentNullException {
        super(name);

        this.rng = rng;
    }

    public Deck.Card[] discardThreeCardsToPass(PassDirection passDirection) {
        final ArrayList<Deck.Card> hand = this.getHand();
        final Deck.Card[] cards = new Deck.Card[3];

        for (int index = 0; index < 3; index++) {
            cards[index] = hand.remove(this.rng.nextInt(hand.size()));
        }

        System.out.printf("%s passes %s %s\n", this.getName(), passDirection, HeartsUtilities.formatCards(cards));
        return cards;
    }

    public Deck.Card playTrick(Deck.Card[] playedCards, Suit trickSuit) {
        final ArrayList<Deck.Card> suitCards = new ArrayList<Deck.Card>();
        final ArrayList<Deck.Card> heartCards = new ArrayList<Deck.Card>();
        final ArrayList<Deck.Card> otherCards = new ArrayList<Deck.Card>();
        final ArrayList<Deck.Card> hand = this.getHand();
        Deck.Card cardToPlay = null;

        for (Deck.Card card : hand) {
            // If I have the 2 of clubs, I must play it
            if (card.getSuit() == Suit.CLUBS && card.getRank() == Rank.DEUCE) {
                cardToPlay = card;
                break;
            }

            if (!firstTrickPlayed && HeartsUtilities.isPointCard(card)) {
                continue;
            }

            if (card.getSuit() == trickSuit) {
                suitCards.add(card);
            } else if (card.getSuit() == Suit.HEARTS) {
                heartCards.add(card);
            } else {
                otherCards.add(card);
            }
        }

        this.firstTrickPlayed = true;

        if (cardToPlay == null) {
            if (suitCards.size() > 0) {
                cardToPlay = suitCards.get(this.rng.nextInt(suitCards.size()));
            } else if (otherCards.size() > 0) {
                cardToPlay = otherCards.get(this.rng.nextInt(otherCards.size()));
            } else {
                cardToPlay = heartCards.get(this.rng.nextInt(heartCards.size()));
            }
        }

        hand.remove(cardToPlay);
        System.out.printf("%s plays %s\n", this.getName(), HeartsUtilities.formatCard(cardToPlay));
        return cardToPlay;
    }
}