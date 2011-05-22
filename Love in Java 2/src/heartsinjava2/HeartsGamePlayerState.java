package heartsinjava2;

public final class HeartsGamePlayerState {

    private final HeartsPlayer player;
    private final int position;
    private int score = 0;

    public HeartsGamePlayerState(HeartsPlayer player, int position) {
        this.player = player;
        this.position = position;
    }

    public HeartsPlayer getPlayer() {
        return this.player;
    }

    public int getPosition() {
        return this.position;
    }

    public int getScore() {
        return this.score;
    }

    public void incrementScore(int amount) {
        this.score += amount;
    }
}