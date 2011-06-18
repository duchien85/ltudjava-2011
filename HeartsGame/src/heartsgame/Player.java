package heartsgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class CardComparator implements Comparator<card> {

    public int compare(card card1, card card2) {
        int id1 = card1.getID();
        int id2 = card2.getID();

        if(id1>id2)
            return 1;
        else if(id1==id2)
            return 0;
        else
            return -1;
    }
}
abstract public class Player {

    protected String name;
    private List<card> handcard;
    protected Boolean[] playedcard;
    protected ArrayList<card> scorecard;
    // mang chua 3 la bai de hoan doi
    private ArrayList<Integer> threeCard;
    protected int score;

    public Player() {
        name = "";
        score = 0;
        handcard = new ArrayList<card>();
        playedcard = new Boolean[14];
        scorecard = new ArrayList<card>();
        threeCard = new ArrayList<Integer>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public boolean checkAvableCard(int index) {
        return !(playedcard[index]);
    }

    public void receiveCard(card c) {
        getHandcard().add(c);
        sort();
    }

    /*
    public void receiveCard(int index, card c) {
        handcard[index] = c;
        sortcard();
    }
*/
    public card getHandCard(int index) {
        if (index < getHandcard().size()) {
            return getHandcard().get(index);
        }
        return null;
    }

    public card pullACard(int index) {
        card tam = getHandcard().get(index);
        getHandcard().remove(index);
        sort();
        return tam;
    }

    public boolean isContainCard(int cardID) {
        for (int i = 1; i <= 13; i++) {
            if (getHandcard().get(i).getID() == cardID) {
                return true;
            }
        }
        return false;
    }

    private void sort(){
        Collections.sort(handcard,new CardComparator());
    }
    public void add4scorecard(ArrayList<card> fourcard) {
        for (int i = 0; i <= 3; i++) {
            scorecard.add(fourcard.get(i));
            if (fourcard.get(i).checkCo()) {
                addScore(1);
            }
            if (fourcard.get(i).getID() == 41) {
                addScore(13);
            }
        }
        fourcard.clear();
    }

    public void newRound() {
        getHandcard().clear();
        for (int i = 1; i <= 13; i++) {         
            playedcard[i] = false;
        }
        scorecard.clear();
    }

    public void resetScore() {
        score = 0;
    }

    public boolean checkShootTheMoon() {
        int demCo = 0;
        for (int i = 0; i < scorecard.size(); i++) {
            if (scorecard.get(i).checkCo()) {
                demCo++;
            }
        }
        if (demCo == 13) {
            for (int i = 0; i < scorecard.size(); i++) {
                if (scorecard.get(i).getID() == 41) {
                    addScore(-26);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public card play2bich() {
        for (int i = 1; i <= 13; i++) {
            if (getHandcard().get(i).getID() == 2) {
                playedcard[i] = true;
                return getHandcard().get(i);
            }
        }
        return null;
    }

    /**
     * @return the threeCard
     */
    public ArrayList<Integer> getThreeCard() {
        return threeCard;
    }

    /**
     * @param threeCard the threeCard to set
     */
    public void setThreeCard(ArrayList<Integer> threeCard) {
        this.threeCard = threeCard;
    }

    /**
     * @return
     */
    public ArrayList<card> getHandcard() {
        return (ArrayList<card>) handcard;
    }

    /**
     * @param handcard  to set
     */
    public void setHandcard(ArrayList<card> handcard) {
        this.handcard = handcard;
    }
}
