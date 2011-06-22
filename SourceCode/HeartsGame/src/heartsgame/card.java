package heartsgame;

public class Card {

    private int ID; // chi so cua quan bai 1 - 52
    private int type;
    private int rank;

    public Card(int id) {
        if ((id > 0) && (id < 53)){
            ID = id;
        } else {
            ID = 0;
        }
        rank = ID / 4 + 1;
        type = ID % 4;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id){
        ID = id;
    }

    // kiem tra quan bai co phai type Co khong
    public boolean checkCo() {
        return (getType() == GameDef.CHAT_CO);
    }

    // kiem tra co cung type voi nhau khong
    public boolean cungChat(int idCard) {
        return (Card.getType(idCard) == this.getType());
    }

    // kiem tra gia tri quan bai co lon hon khong
    public boolean greaterThan(Card othercaCard) {
        return ((getRank() > othercaCard.getRank()) && (getType() == othercaCard.getType()));
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    public static int getType(int idCard) {
        return idCard % 4;
    }
    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    public static int getRank(int idCard) {
        return idCard / 4 + 1;
    }

    public static boolean dongChat(int idCard1, int idCard2){
        int type1 = idCard1 % 4;
        int type2 = idCard2 % 4;
        if (type1 == type2)
            return true;
        return false;
    }
    /**
     * @param rank the rank to set
     */
    public void setRank(int rank) {
        this.rank = rank;
    }
}
