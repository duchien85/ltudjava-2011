package heartsinjava.internal;

import heartsinjava.*;

final class HeartsPlayerState {

    private final IHeartsPlayer player;
    private final int position;
    private int score = 0;

    HeartsPlayerState(IHeartsPlayer player, int position) {
        this.player = player;
        this.position = position;
    }

    IHeartsPlayer getPlayer() {
        return this.player;
    }

    int getPosition() {
        return this.position;
    }

    int getScore() {
        return this.score;
    }

    void incrementScore(int amount) {
        this.score += amount;
    }
}