package heartsinjava2;

import java.util.*;

public final class HeartsGameState {

    private final Random rng;
    private final HeartsGamePlayerState[] playerStates;
    private boolean complete = false;
    private HeartsPlayer winner = null;
    private PassDirection nextPassDirection = PassDirection.LEFT;

    public HeartsGameState(Set<HeartsPlayer> players, Random rng) throws UnsupportedPlayerCountException {
        this.rng = rng;
        this.playerStates = new HeartsGamePlayerState[players.size()];
        int position = 0;

        for (HeartsPlayer player : players) {
            this.playerStates[position] = new HeartsGamePlayerState(player, position);
            position++;
        }
    }

    public Random getRng() {
        return this.rng;
    }

    public HeartsGamePlayerState[] getPlayerStates() {
        return this.playerStates;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public void setComplete(boolean value) {
        this.complete = value;
    }

    public HeartsPlayer getWinner() {
        return this.winner;
    }

    public void setWinner(HeartsPlayer value) {
        this.winner = value;
    }

    public PassDirection getNextPassDirection() {
        return this.nextPassDirection;
    }

    public void setNextPassDirection(PassDirection value) {
        this.nextPassDirection = value;
    }
}