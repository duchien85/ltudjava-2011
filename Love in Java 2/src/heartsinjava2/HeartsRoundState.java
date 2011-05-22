package heartsinjava2;

public final class HeartsRoundState {

    private final HeartsGameState gameState;
    private final HeartsRoundPlayerState[] playerStates;
    private PlayCondition playCondition = PlayCondition.DEUCE_OF_CLUBS_REQUIRED;
    private int firstPlayerPosition = 0;

    public HeartsRoundState(HeartsGameState gameState) {
        this.gameState = gameState;
        final HeartsGamePlayerState[] gamePlayerStates = this.gameState.getPlayerStates();
        this.playerStates = new HeartsRoundPlayerState[gamePlayerStates.length];

        for (int index = 0; index < this.playerStates.length; index++) {
            this.playerStates[index] = new HeartsRoundPlayerState(gamePlayerStates[index]);
        }
    }

    public HeartsGameState getGameState() {
        return this.gameState;
    }

    public HeartsRoundPlayerState[] getPlayerStates() {
        return this.playerStates;
    }

    public int getFirstPlayerPosition() {
        return this.firstPlayerPosition;
    }

    public void setFirstPlayerPosition(int value) {
        this.firstPlayerPosition = value;
    }

    public PlayCondition getPlayCondition() {
        return this.playCondition;
    }

    public void setPlayCondition(PlayCondition value) {
        this.playCondition = value;
    }
}