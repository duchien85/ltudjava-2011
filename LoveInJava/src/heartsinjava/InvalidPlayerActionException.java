package heartsinjava;

// NOTE: Annoyingly, Java does not permit generic throwables!
public final class InvalidPlayerActionException extends Exception {
    private final IPlayer player;

    public InvalidPlayerActionException(IPlayer player, String message) {
        super(String.format("Player %s performed an invalid action: %s.", player.getName(), message));
        this.player = player;
    }

    public IPlayer getPlayer() {
        return this.player;
    }
}