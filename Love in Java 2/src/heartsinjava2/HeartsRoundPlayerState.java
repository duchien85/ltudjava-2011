package heartsinjava2;

import java.util.*;

public final class HeartsRoundPlayerState {

    private final HeartsGamePlayerState gamePlayerState;
    private final Stack<Deck.Card> cardsInHand = new Stack<Deck.Card>();
    private final Stack<Deck.Card> cardsTaken = new Stack<Deck.Card>();

    public HeartsRoundPlayerState(HeartsGamePlayerState gamePlayerState) {
        this.gamePlayerState = gamePlayerState;
    }

    public HeartsGamePlayerState getGamePlayerState() {
        return this.gamePlayerState;
    }

    public Stack<Deck.Card> getCardsInHand() {
        return this.cardsInHand;
    }

    public Stack<Deck.Card> getCardsTaken() {
        return this.cardsTaken;
    }

    public void checkAndRemoveCard(Deck.Card card) throws ArgumentNullException, InvalidPlayerActionException {
        if (card == null) {
            throw new ArgumentNullException("card");
        }

        if (!this.cardsInHand.contains(card)) {
            throw new InvalidPlayerActionException(this.gamePlayerState.getPlayer(), "Player attempted to play a card that was not in their hand.");
        }
        
        this.cardsInHand.remove(card);
    }

    public void checkAndRemoveCards(Deck.Card[] cards) throws ArgumentNullException, InvalidPlayerActionException {
        if (cards == null) {
            throw new ArgumentNullException("cards");
        }

        for (Deck.Card card : cards) {
            this.checkAndRemoveCard(card);
        }
    }
}