package heartsinjava;

import java.util.*;
import heartsinjava.internal.*;

public final class HeartsGame {

    private static final byte REQUIRED_PLAYER_COUNT = 4;
    private final HeartsPlayerState[] playerStates;
    private boolean isComplete = false;

    public HeartsGame(Set<IHeartsPlayer> players) throws UnsupportedPlayerCountException {
        if (players.size() != HeartsGame.REQUIRED_PLAYER_COUNT) {
            throw new UnsupportedPlayerCountException(HeartsGame.REQUIRED_PLAYER_COUNT, (byte) players.size());
        }

        this.playerStates = new HeartsPlayerState[players.size()];
        int index = 0;

        for (IHeartsPlayer player : players) {
            this.playerStates[index] = new HeartsPlayerState(player, index);
            index++;
        }
    }

    public HeartsResult play() throws InvalidPlayerActionException {
        while (!this.isComplete) {
            HeartsRound round = new HeartsRound(this);
            round.play();
        }

        // TODO: Implement!
        return null;
    }
}