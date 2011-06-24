package heartsgame;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class CardComparator implements Comparator<Integer> {

    public int compare(Integer IDcard1, Integer IDcard2) {
        int type1 = IDcard1%4;
        int rank1 = IDcard1/4 + 1;
        int type2 = IDcard2%4;
        int rank2 = IDcard2/4 + 1;

        if (type1 > type2) {
            return 1;
        } else if (type1 < type2) {
            return -1;
        } else {
            if (rank1 > rank2) {
                return 1;
            } else if (rank1 < rank2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}

public class Player {

    // name cua nguoi choi
    protected String name;

    // danh sach cac quan bai hien co
    // luu id cua quan bai
    private List<Integer> dsBai;
    
    // mang chua cac quan bai an duoc
    // luu cac id cua quan bai
    protected ArrayList<Integer> scorecard;

    // mang chua 3 la bai de hoan doi
    // luu cac id cua quan bai
    private ArrayList<Integer> threeCard;

    // score so cua nguoi choi
    protected int score;

    public Player() {
        Init();
    }

    public Player(String argName) {
        Init();
        name = argName;
    }

    private void Init(){
        name = "";
        score = 0;
        dsBai = new ArrayList<Integer>();
        scorecard = new ArrayList<Integer>();
        threeCard = new ArrayList<Integer>();
    }
    // dat ten cho nguoi choi
    public void setName(String name) {
        this.name = name;
    }

    // lay ten nguoi choi
    public String getName() {
        return name;
    }

    // gan score so cho nguoi choi
    public void setScore(int score) {
        this.score = score;
    }

    // lay score cua nguoi choi
    public int getScore() {
        return score;
    }

    // cong score cho nguoi choi
    public void addScore(int score) {
        this.score += score;
    }

    // nhan them quan bai
    public void receiveCard(int id) {
        if(id>0 && id<53)
        {
            getListCard().add(id);
        }
    }

    // lay id cua quan bai tai chi so index
    public int getIDCardAt(int index) {
        if (index < dsBai.size())
            return dsBai.get(index);       
        return -1;
    }

    // danh ra 1 quan bai
    // id : id quan bai can danh ra
    // thanh cong : tra ve quan bai danh ra
    // that bai(khong co quan bai trong ds bai) : tra ve -1

    public int playACard(int idCard) {
        if (!dsBai.isEmpty()){
            for (int index=0; index<dsBai.size();index++){
                if (getIDCardAt(index)==idCard){
                    dsBai.remove(index);
                    return idCard;
                }
            }
        }
        return -1;
    }

    public void sort(){
        Collections.sort(dsBai,new CardComparator());
    }
    public void add4scorecard(ArrayList<Integer> fourcard) {
        for (int i = 0; i <= 3; i++) {
            scorecard.add(fourcard.get(i));
            if (Card.getType(fourcard.get(i)) == GameDef.CHAT_CO) {
                addScore(1);
            }
            if (fourcard.get(i) == GameDef.ISQBICH) {
                addScore(13);
            }
        }
        fourcard.clear();
    }

    public void newRound() {
        getListCard().clear();
        scorecard.clear();
    }

    public void resetScore() {
        score = 0;
    }

    public boolean checkShootTheMoon() {
        int demCo = 0;
        for (int i = 0; i < scorecard.size(); i++) {
            if (Card.getType(scorecard.get(i)) == GameDef.CHAT_CO) {
                demCo++;
            }
        }
        if (demCo == 13) {
            for (int i = 0; i < scorecard.size(); i++) {
                if (scorecard.get(i) == GameDef.ISQBICH) {
                    addScore(-26);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    // danh quan 2 chuon
    public int play2chuon() {
        for (int i = 0; i < dsBai.size(); i++) {
            if (getListCard().get(i) == GameDef.IS2CHUON) {
                int id = getListCard().get(i);;
                playACard(GameDef.IS2CHUON);
                return id;
            }
        }
        return -1;
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
    public ArrayList<Integer> getListCard() {
        return (ArrayList<Integer>) dsBai;
    }

    /**
     * @param dsBai  to set
     */
    public void setHandcard(ArrayList<Integer> handcard) {
        this.dsBai = handcard;
    }

    // kiem tra xem trong danh sach bai con quan nao cung chat voi quan bai co ID = idCArd
    boolean checkAvableRank(int idCard) {
        if (!dsBai.isEmpty()){
            for (int index=0; index<dsBai.size();index++){
                if(Card.getType(dsBai.get(index))== Card.getType(idCard))
                    return true;
            }
        }
        return false;
    }

    boolean isContainCard(int id) {
        for (int i=0; i< getListCard().size(); i++){
            if (getIDCardAt(i)==id)
                return true;
        }
        return false ;
    }

    public int playARandomCard() {
        SecureRandom numGenerate = new SecureRandom();
        int random = 0;
        while (random == 0) {
            if (!dsBai.isEmpty()) {
                if (dsBai.size() == 1) {
                    random = 0;
                } else {
                    random = numGenerate.nextInt(dsBai.isEmpty() ? 0 : dsBai.size() - 1);
                }
            }

        }
        return playACard(dsBai.get(random));
    }
    
    public int playfirst(boolean duocChonCo) 
    {
          SecureRandom numGenerate = new SecureRandom();
        int random = 0;

        // 41 : Q bich
        while (((getListCard().get(random) == 41) || Card.getType(getListCard().get(random)) == GameDef.CHAT_CO) && (!duocChonCo)) {
            if (getListCard().size() == 1)
                random = 0;
            else
                random = numGenerate.nextInt(getListCard().size() - 1);
            System.out.println("Random: " + random + "; Listcard at random:" + getListCard().get(random));
        }
        return playACard(getListCard().get(random));
    }
    
    public int playfollow(int id) {
           ArrayList<Integer> samerank = new ArrayList<Integer>();
        for (int i = 0; i < getListCard().size(); ++i)
        {
            if (Card.dongChat(id, getListCard().get(i)))
                samerank.add(getListCard().get(i));
        }
        if (!samerank.isEmpty()) {
            SecureRandom numGenerate = new SecureRandom();
            int random = 0;
            int size = samerank.size();
            if (size == 1) {
                random = 0;
            } else {
                random = numGenerate.nextInt(samerank.size() - 1);
            }
            return playACard(samerank.get(random));
        } else {
            return playfirst(true);
        }
    }
}
