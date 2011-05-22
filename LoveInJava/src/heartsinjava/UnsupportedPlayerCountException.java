package heartsinjava;

public final class UnsupportedPlayerCountException extends Exception {
    private final byte maxSupported;
    private final byte playersProvided;

    public UnsupportedPlayerCountException(byte maxSupported, byte playersProvided) {
        super(String.format("Only %i players are supported but %i were provided.", maxSupported, playersProvided));
        this.maxSupported = maxSupported;
        this.playersProvided = playersProvided;
    }

    public byte getMaxSupported() {
        return this.maxSupported;
    }

    public byte getPlayersProvided() {
        return this.playersProvided;
    }
}