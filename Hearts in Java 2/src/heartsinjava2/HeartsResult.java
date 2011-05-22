package heartsinjava2;

public final class HeartsResult {

    private final HeartsPlayer winner;

    public HeartsResult(HeartsPlayer winner) {
        this.winner = winner;
    }

    public HeartsPlayer getWinner() {
        return this.winner;
    }
}