package heartsinjava2;

import java.util.*;

public final class Main {

    public static void main(String[] args) throws UnsupportedPlayerCountException, ArgumentNullException, InvalidPlayerActionException {
        final Random rng = new Random();

        HeartsGame game = new HeartsGame(new LinkedHashSet<HeartsPlayer>(Arrays.asList(new HeartsPlayer[]{
                    new HumanHeartsPlayer("You"),
                    new RandomHeartsPlayer("Player 2", rng),
                    new RandomHeartsPlayer("Player 3", rng),
                    new RandomHeartsPlayer("Player 4", rng)
                })), rng);

        game.play();
    }
}