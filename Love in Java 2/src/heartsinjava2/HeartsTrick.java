package heartsinjava2;

import java.util.*;

public final class HeartsTrick {

    private final HeartsRoundState state;
    private Suit suit = null;

    public HeartsTrick(HeartsRoundState roundState) {
        this.state = roundState;
    }

    public void play() throws ArgumentNullException, InvalidPlayerActionException {
        // Starting with the first player, ask each in turn which card
        // they wish to play, providing a view of the current trick
        // state on each request.
        final int firstPlayerPosition = this.state.getFirstPlayerPosition();
        final HeartsRoundPlayerState[] playerStates = this.state.getPlayerStates();
        final Deck.Card[] playedCards = new Deck.Card[playerStates.length];

        for (int index = 0; index < playerStates.length; index++) {
            // Figure out the actual position to play
            final int position = (firstPlayerPosition + index) % playerStates.length;

            // Get the state of the player at this position
            final HeartsRoundPlayerState playerState = playerStates[position];

            // Ask the player at the current position to play the trick
            final Deck.Card card = playerState.getGamePlayerState().getPlayer().playTrick(playedCards.clone(), this.suit);

            // Check that the player owns this card and remove it from the shadow hand
            playerState.checkAndRemoveCard(card);

            // Check this is a valid play
            this.checkPlay(card, playerState);

            // Record the validated card for this player
            playedCards[position] = card;

            if (this.suit == null) {
                this.suit = card.getSuit();
            }

            // Progress game state if required
            if (this.state.getPlayCondition() == PlayCondition.DEUCE_OF_CLUBS_REQUIRED) {
                this.state.setPlayCondition(PlayCondition.NO_POINT_CARDS);
            } else if (card.getSuit() == Suit.HEARTS) {
                this.state.setPlayCondition(PlayCondition.HEARTS_BROKEN);
            }
        }

        this.completeTrick(playedCards);
    }

    private void checkPlay(Deck.Card playedCard, HeartsRoundPlayerState playerState) throws InvalidPlayerActionException {
        final HeartsPlayer player = playerState.getGamePlayerState().getPlayer();

        switch (this.state.getPlayCondition()) {
            case DEUCE_OF_CLUBS_REQUIRED:
                if (playedCard.getSuit() != Suit.CLUBS || playedCard.getRank() != Rank.DEUCE) {
                    throw new InvalidPlayerActionException(player, "Deuce of clubs must be played.");
                }
            case NO_POINT_CARDS:
                if (HeartsUtilities.isPointCard(playedCard)) {
                    throw new InvalidPlayerActionException(player, "Point cards are not permitted at this stage of the game.");
                }
            case HEARTS_NOT_BROKEN:
                if (playedCard.getSuit() == Suit.HEARTS && this.suit == null) {
                    // First card in trick so check player has only hearts
                    for (Deck.Card card : playerState.getCardsInHand()) {
                        if (card.getSuit() != Suit.HEARTS) {
                            throw new InvalidPlayerActionException(player, "Hearts are not yet broken and non-heart could be played.");
                        }
                    }
                }
        }

        if (this.suit != null) {
            if (playedCard.getSuit() != this.suit) {
                // Check the player has no cards of the trick suit
                for (Deck.Card card : playerState.getCardsInHand()) {
                    if (card.getSuit() == this.suit) {
                        throw new InvalidPlayerActionException(player, "A card of the current suit could be played.");
                    }
                }
            }
        }
    }

    private void completeTrick(Deck.Card[] playedCards) {
        final HeartsRoundPlayerState[] playerStates = this.state.getPlayerStates();
        Rank maxRank = Rank.DEUCE;
        int trickTakingPosition = -1;

        // Identify the position that takes this trick
        for (int position = 0; position < playerStates.length; position++) {
            Deck.Card playedCard = playedCards[position];
            Rank rank = playedCard.getRank();

            if (playedCard.getSuit() == this.suit && rank.compareTo(maxRank) >= 0) {
                maxRank = rank;
                trickTakingPosition = position;
            }
        }

        // Give all the cards to the trick taking position player
        playerStates[trickTakingPosition].getGamePlayerState().getPlayer().takeTrick(playedCards.clone());
        this.state.setFirstPlayerPosition(trickTakingPosition);
        final Stack<Deck.Card> takenCards = playerStates[trickTakingPosition].getCardsTaken();

        for (Deck.Card card : playedCards) {
            takenCards.push(card);
        }
    }
}