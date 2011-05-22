package heartsinjava2;

import java.util.*;

public final class HeartsGame {

    private static final byte REQUIRED_PLAYER_COUNT = 4;
    private final HeartsGameState state;

    public HeartsGame(Set<HeartsPlayer> players, Random rng) throws UnsupportedPlayerCountException {
        if (players.size() != HeartsGame.REQUIRED_PLAYER_COUNT) {
            throw new UnsupportedPlayerCountException(HeartsGame.REQUIRED_PLAYER_COUNT, (byte) players.size());
        }

        this.state = new HeartsGameState(players, rng);
    }

    public HeartsResult play() throws ArgumentNullException, InvalidPlayerActionException {
        while (!this.state.isComplete()) {
            final HeartsRound round = new HeartsRound(this.state);
            round.play();

            this.evaluateForWinner();

            // If the game isn't over, set the pass direction for the next round
            if (!this.state.isComplete()) {
                this.state.setNextPassDirection(this.state.getNextPassDirection().next());
            }

            System.out.println("\n*** End of round ***");

            for (HeartsGamePlayerState playerState : this.state.getPlayerStates()) {
                System.out.printf("  %s: %d\n", playerState.getPlayer().getName(), playerState.getScore());
            }

            System.out.println();
        }

        return new HeartsResult(this.state.getWinner());
    }

    private void evaluateForWinner() {
        int minScore = 200;
        HeartsPlayer player = null;

        for (HeartsGamePlayerState playerState : this.state.getPlayerStates()) {
            int score = playerState.getScore();

            if (playerState.getScore() >= 100) {
                this.state.setComplete(true);
            }

            if (score < minScore) {
                minScore = score;
                player = playerState.getPlayer();
            }
        }

        if (this.state.isComplete()) {
            this.state.setWinner(player);
        }
    }
}