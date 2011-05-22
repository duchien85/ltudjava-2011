package heartsinjava2;

import java.util.*;

public final class HeartsRound {

    private final HeartsRoundState state;

    public HeartsRound(HeartsGameState gameState) {
        this.state = new HeartsRoundState(gameState);
    }

    public void play() throws ArgumentNullException, InvalidPlayerActionException {
        this.dealCardsToPlayers();

        this.passThree();

        this.setFirstPlayer();

        // Keep playing tricks until all cards have been played
        final HeartsRoundPlayerState playerState = this.state.getPlayerStates()[0];

        while (!playerState.getCardsInHand().isEmpty()) {
            final HeartsTrick trick = new HeartsTrick(this.state);
            trick.play();

            // Progress game state if required
            if (this.state.getPlayCondition() == PlayCondition.NO_POINT_CARDS) {
                this.state.setPlayCondition(PlayCondition.HEARTS_NOT_BROKEN);
            }
        }

        this.updateScores();
    }

    private void dealCardsToPlayers() {
        // Get all the player states to make accessing them easier later
        final HeartsRoundPlayerState[] playerStates = this.state.getPlayerStates();

        // Get the random number generator for the game
        final Random random = this.state.getGameState().getRng();

        // Create and shuffle a deck of cards
        final Deck deck = new Deck();
        deck.shuffle(random);

        // Start dealing at a random position (a little OTT but so what!)
        int position = random.nextInt(playerStates.length);

        while (!deck.isEmpty()) {
            // Draw the next card from the deck
            final Deck.Card card = deck.draw();

            // Get the state of the player at the current position
            final HeartsRoundPlayerState playerState = playerStates[position];

            // Give the card to the player
            playerState.getGamePlayerState().getPlayer().addToHand(card);

            // Remember that we gave this card to this player (to prevent cheating)
            playerState.getCardsInHand().add(card);

            // Move to the next position
            position = (position == playerStates.length - 1) ? 0 : (position + 1);
        }
    }

    private void passThree() throws ArgumentNullException, InvalidPlayerActionException {
        /*
        ...selects any three of them to pass. For the first hand, cards
        are passed to the left; for the second, to the right; for the
        third, across; and for the fourth, the passing stage is skipped
        entirely, and the players keep (or "eat") their cards. On the
        fifth hand, the cycle starts again, passing to the left.
         */

        // Get all the player states to make accessing them easier later
        final HeartsRoundPlayerState[] playerStates = this.state.getPlayerStates();

        // Get the pass direction for this round
        final PassDirection passDirection = this.state.getGameState().getNextPassDirection();

        // Ask each player to select 3 cards to pass and validate the actions
        final Deck.Card[][] selectedCards = new Deck.Card[playerStates.length][3];

        for (int position = 0; position < playerStates.length; position++) {
            final HeartsRoundPlayerState playerState = playerStates[position];
            final HeartsPlayer player = playerState.getGamePlayerState().getPlayer();
            final Deck.Card[] cards = player.discardThreeCardsToPass(passDirection);

            // Ensure exactly 3 cards have been selected
            if (cards == null || cards.length != 3) {
                throw new InvalidPlayerActionException(player, String.format("When asked to select 3 cards to pass, %i cards were chosen instead.", cards == null ? 0 : cards.length));
            }

            playerState.checkAndRemoveCards(cards);

            // Record the validated cards for this player
            selectedCards[position] = cards;
        }

        // Pass the cards around
        for (int position = 0; position < playerStates.length; position++) {
            // Get the cards to be passed from this position
            final Deck.Card[] cards = selectedCards[position];

            // Figure out the position the cards are to be passed to
            final int targetPosition = passDirection.target(position);

            // Get the state of the target player
            final HeartsRoundPlayerState targetPlayerState = playerStates[targetPosition];

            // Give the cards to the target player
            targetPlayerState.getGamePlayerState().getPlayer().addToHand(cards);

            // Add these cards to the target player's shadow hand (to prevent cheating)
            targetPlayerState.getCardsInHand().addAll(Arrays.asList(cards));
        }
    }

    private void setFirstPlayer() {
        final HeartsRoundPlayerState[] playerStates = this.state.getPlayerStates();

        // The player with the 2 of clubs is the first player
        for (int position = 0; position < playerStates.length; position++) {
            for (Deck.Card card : playerStates[position].getCardsInHand()) {
                if (card.getRank() == Rank.DEUCE && card.getSuit() == Suit.CLUBS) {
                    this.state.setFirstPlayerPosition(position);
                    return;
                }
            }
        }
    }

    private void updateScores() {
        final HeartsRoundPlayerState[] playerStates = this.state.getPlayerStates();

        for (int position = 0; position < playerStates.length; position++) {
            final Stack<Deck.Card> cardsTaken = playerStates[position].getCardsTaken();

            // Calculate total points
            int total = 0;

            for (Deck.Card card : cardsTaken) {
                if (card.getSuit() == Suit.SPADES && card.getRank() == Rank.QUEEN) {
                    total += 13;
                } else if (card.getSuit() == Suit.HEARTS) {
                    total++;
                }
            }

            // If the player has "shot the moon", give the points to all other players
            // Otherwise, update the player's score
            if (total == 26) {
                for (int otherPosition = 0; otherPosition < playerStates.length; otherPosition++) {
                    if (otherPosition != position) {
                        playerStates[otherPosition].getGamePlayerState().incrementScore(total);
                    }
                }

                return;
            } else {
                playerStates[position].getGamePlayerState().incrementScore(total);
            }
        }
    }
}