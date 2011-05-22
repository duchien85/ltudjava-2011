package heartsinjava2;

// NOTE: Annoyingly, Java does not permit generic throwables!
public final class InvalidPlayerActionException extends Exception {
    private final HeartsPlayer player;

    public InvalidPlayerActionException(HeartsPlayer player, String message) {
        super(String.format("Player %s performed an invalid action: %s.", player.getName(), message));
        this.player = player;
    }

    public HeartsPlayer getPlayer() {
        return this.player;
    }
}