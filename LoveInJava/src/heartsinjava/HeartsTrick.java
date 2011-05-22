package heartsinjava;

public final class HeartsTrick {

    public final class HeartsTrickState {

        private final HeartsPlayerTrickState[] playerTrickStates;

        public HeartsTrickState(HeartsRound.HeartsRoundState roundState) {
            HeartsRound.HeartsPlayerRoundState[] playerRoundStates = roundState.getPlayerRoundStates();
            this.playerTrickStates = new HeartsPlayerTrickState[playerRoundStates.length];

            for (int index = 0; index < playerRoundStates.length; index++) {
                this.playerTrickStates[index] = new HeartsPlayerTrickState(playerRoundStates[index]);
            }
        }

        public HeartsPlayerTrickState[] getPlayerTrickStates() {
            return this.playerTrickStates;
        }

        private IHeartsPlayer[] playOrder() {
            // TODO: Implement!
            return null;
        }
    }

    public final class HeartsPlayerTrickState {

        private final HeartsRound.HeartsPlayerRoundState playerRoundState;
        private Deck.Card playedCard = null;

        public HeartsPlayerTrickState(HeartsRound.HeartsPlayerRoundState playerRoundState) {
            this.playerRoundState = playerRoundState;
        }

        public Deck.Card getPlayedCard() {
            return this.playedCard;
        }

        public void setPlayedCard(Deck.Card value) {
            this.playedCard = value;
        }
    }
    private final HeartsTrickState state;

    public HeartsTrick(HeartsRound.HeartsRoundState roundState) {
        this.state = new HeartsTrickState(roundState);
    }

    public void play() {
        for (IHeartsPlayer player : this.state.playOrder()) {
            Deck.Card card = player.playTrick(this.state);
        }

        for (int index = 0; index < playerCount; index++) {
            int playerIndex = (this.firstPlayerIndex + index) % playerCount;
            IHeartsPlayer player = this.players[playerIndex];
            Deck.Card card = player.playCardToTrick(this);
        }
    }

    private void playTrick() {
        int playerCount = this.players.length;

        for (int index = 0; index < playerCount; index++) {
            int playerIndex = (this.trickFirstPlayerIndex + index) % playerCount;
        }
    }
}