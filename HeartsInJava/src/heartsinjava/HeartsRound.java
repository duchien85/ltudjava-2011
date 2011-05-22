package heartsinjava;

import java.util.*;

// NOTE: Using term "round" in place of "hand" to avoid confusion with player "hands"
public final class HeartsRound {


    final class HeartsRoundState {

        private final HeartsPlayerRoundState[] playerRoundStates;
        private final Deck deck = new Deck();
        private PlayCondition playCondition = PlayCondition.DeuceOfSpadesRequired;
        /*private PassDirection nextPassDirection = PassDirection.LEFT;
        private int trickFirstPlayerIndex;*/

        private HeartsRoundState(HeartsGame.HeartsGameState gameState) {
            HeartsGame.HeartsPlayerGameState[] playerGameStates = gameState.getPlayerGameStates();
            this.playerRoundStates = new HeartsPlayerRoundState[playerGameStates.length];

            for (int index = 0; index < playerGameStates.length; index++) {
                this.playerRoundStates[index] = new HeartsPlayerRoundState(playerGameStates[index]);
            }
        }

        public HeartsPlayerRoundState[] getPlayerRoundStates() {
            return this.playerRoundStates;
        }

        public PlayCondition getPlayCondition() {
            return this.playCondition;
        }

        public void play() throws InvalidPlayerActionException {
            this.state.deck.shuffle();
            int firstPlayerIndex = this.dealCardsToPlayers();
            this.passThree();

            while (!this.hands[0].isEmpty()) {
                HeartsTrick trick = new HeartsTrick(this.roundState);
                trick.play();
            }

            this.updateScores();
        }

        private int dealCardsToPlayers() {
            int firstPlayerIndex = 0;
            int index = 0;

            while (!this.state.deck.isEmpty()) {
                Deck.Card card = this.state.deck.draw();

                if (card.getRank() == Deck.Rank.DEUCE && card.getSuit() == Deck.Suit.CLUBS) {
                    firstPlayerIndex = index;
                }

                this.players[index].addToHand(card);
                this.hands[index].add(card);
                index = index == this.players.length - 1 ? 0 : index;
            }

            return firstPlayerIndex;
        }

        private void passThree() throws InvalidPlayerActionException {
            /*
            ...selects any three of them to pass. For the first hand, cards
            are passed to the left; for the second, to the right; for the
            third, across; and for the fourth, the passing stage is skipped
            entirely, and the players keep (or "eat") their cards. On the
            fifth hand, the cycle starts again, passing to the left.
             */

            // TODO: This is wrong, no player should receive their cards until after they chosen those to pass
        /*
            for (int index = 0; index < this.players.length; index++) {
            IHeartsPlayer player = this.players[index];
            Deck.Card[] cards = player.discardThreeCardsToPass(this.nextPassDirection);

            if (cards == null || cards.length != 3) {
            throw new InvalidPlayerActionException(player, String.format("When asked to select 3 cards to pass, %i cards were chosen instead.", cards == null ? 0 : cards.length));
            }

            this.checkPlayerOwnsCards(player, cards);
            int target = this.nextPassDirection.target(index);
            this.players[target].addToHand(cards);
            }

            this.nextPassDirection = this.nextPassDirection.next();
             */
        }

        private void checkPlayerOwnsCards(IHeartsPlayer player, Deck.Card[] cards) {
            // TODO: Implement!
        }

        private void updateScores() {
            // TODO: Implement!
            //       Update scores and if any player has a score of 100 or greater,
            //       set the game as complete. Set first player of next trick
        }
    }

    final class HeartsPlayerRoundState {

        private final HeartsGame.HeartsPlayerGameState playerGameState;
        private final Stack<Deck.Card> cardsInHand = new Stack<Deck.Card>();
        private final Stack<Deck.Card> cardsWon = new Stack<Deck.Card>();

        private HeartsPlayerRoundState(HeartsGame.HeartsPlayerGameState playerGameState) {
            this.playerGameState = playerGameState;
        }

        public Stack<Deck.Card> getCardsInHand() {
            return cardsInHand;
        }

        public Stack<Deck.Card> getCardsWon() {
            return cardsWon;
        }
    }
    private final HeartsRoundState state;

    HeartsRound(HeartsGame.HeartsGameState gameState) {
        this.state = new HeartsRoundState(gameState);
    }

    void play() throws InvalidPlayerActionException {
        this.state.deck.shuffle();
        int firstPlayerIndex = this.dealCardsToPlayers();
        this.passThree();

        while (!this.hands[0].isEmpty()) {
            HeartsTrick trick = new HeartsTrick(this.roundState);
            trick.play();
        }

        this.updateScores();
    }
}