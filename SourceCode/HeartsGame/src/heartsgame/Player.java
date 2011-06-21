package heartsgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class CardComparator implements Comparator<card> {

    public int compare(card card1, card card2) {
        if (card1.getRankOfSet() > card2.getRankOfSet()) {
            return 1;
        } else if (card1.getRankOfSet() < card2.getRankOfSet()) {
            return -1;
        } else {
            if (card1.getID() > card2.getID()) {
                return 1;
            } else if (card1.getID() < card2.getID()) {
                return -1;
            } else {
                return 0;
            }
        }
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
        playedcard = new Boolean[13];
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
        if(c!=null)
        {
            getHandcard().add(c);
            sort();
        }
    }

    /*
    public void receiveCard(int index, card c) {
        handcard[index] = c;
        sortcard();
    }
*/
    public card getHandCard(int index) {
        try {
            if (index < handcard.size()) {
                return handcard.get(index);
            }
        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
        return null;
    }

    public card pullACard(int index) {
        if (index < getHandcard().size()){
            card tam = getHandcard().get(index);
            getHandcard().remove(index);
            sort();
            return tam;
        }
        return null;
    }

    public boolean isContainCard(int cardID) {
        for (int i = 0; i < getHandcard().size(); i++) {
            if (getHandcard().get(i).getID() == cardID) {
                return true;
            }
        }
        return false;
    }

    public boolean playCardContain(int cardID){

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
        for (int i = 0; i < 13; i++) {
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
        for (int i = 0; i < handcard.size(); i++) {
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

    public card playACard(int index) {
        if (index < handcard.size()){
            playedcard[index] = true;
            card c = getHandcard().get(index);
            handcard.remove(index);
            sort();
            return c;
        }
        return null;
    }

    boolean checkAvableRank(card c) {
        for (int i=0;i<handcard.size();i++){
            if (playedcard[i]==false){
                if(c.checkSameRank(handcard.get(i))){
                   return true;
                }
            }
	}
        return false;
    }
}
