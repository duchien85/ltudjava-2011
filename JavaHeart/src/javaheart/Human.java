package javaheart;

import java.util.ArrayList;

public class Human extends Player {

    // khoi tao nguoi choi voi ten
    public Human(String name) {
        this.name = name;
        score = 0;
        handcard = new card[14];
        playedcard = new Boolean[14];
        scorecard = new ArrayList<card>();
        threeCard = new ArrayList<Integer>();
        ;
    }

    public card playACard(int index) {
        playedcard[index] = true;
        return handcard[index];
    }

    public boolean checkAvableRank(card c) {
        for (int i = 1; i <= 13; i++) {
            if ((playedcard[i] == false) && (c.checkSameRank(handcard[i]))) {
                return true;
            }
        }
        return false;
    }
}
