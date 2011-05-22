package heartsinjava;

import java.util.*;

public final class Main {

    public static void main(String[] args)
            throws
            UnsupportedPlayerCountException,
            ArgumentNullException,
            InvalidPlayerActionException {
        HeartsGame game = new HeartsGame(new LinkedHashSet<IHeartsPlayer>(Arrays.asList(new IHeartsPlayer[]{
                    new HumanHeartsPlayer("Human Player"),
                    new ComputerHeartsPlayer("AI Player 1"),
                    new ComputerHeartsPlayer("AI Player 2"),
                    new ComputerHeartsPlayer("AI Player 3")
                })));

        game.play();
    }
}