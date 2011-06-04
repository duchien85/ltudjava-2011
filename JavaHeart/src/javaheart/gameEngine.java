package javaheart;

import java.security.SecureRandom;
import java.util.ArrayList;

public class gameEngine implements Runnable {

    private int currentstep;
    private int currentturn, firstturn, roundcount;
    private Human player1, player2, player3, player4;
    private Human[] player;
    private giaodien gdien;
    private ArrayList<card> fourCard;
    //private ArrayList<Integer> threeCard;
    private boolean is2bichplayed, duocChonCo;
    private int[] SignalFlag; //0:card 12:control
    private int delay = 500;

    // khoi tao
    public gameEngine(String st) {
        // khoi tao nguoi choi
        player = new Human[5];
        player[0] = new Human(st);
        player[1] = new Human("Player 2");
        player[2] = new Human("Player 3");
        player[3] = new Human("Player 4");
        player[4] = new Human("Player 5");
        currentturn = 0;
        // khoi tao tin hieu dieu khien
        SignalFlag = new int[2];
        SignalFlag[0] = -1;
        SignalFlag[1] = 0;

        // mang chua 4 la bai danh ra
        fourCard = new ArrayList<card>();

        gdien = new giaodien(this);
        Thread xuly = new Thread(this);
        xuly.start();
    }

    // xac dinh nguoi choi
    public Player getPlayer(int index) {
        return player[index];
    }

    // Thoi gian delay
    public void updateDelay(int time) {
        delay = time;
    }

    // Tin hieu dieu khien 
    // SignalFlag[0] : trao bai
    // SignalFlag[1] : choi bai
    public int[] getSignal() {
        return SignalFlag;
    }

    // chia bai
    public void divideCard() {
        // khoi tao mang cac quan bai gia tri ban dau la 0
        int[] ddau = new int[53];
        for (int i = 1; i < 53; i++) {
            ddau[i] = 0;
        }
        // tao ngau nhien mang tu 1-53
        SecureRandom numGenerate = new SecureRandom();
        int tam = numGenerate.nextInt(53);
        for (int i = 1; i < 53; i++) {
            while ((tam == 0) || (ddau[tam] == 1)) {
                tam = numGenerate.nextInt(53);
            }
            // danh dau la da co
            ddau[tam] = 1;

            // chia bai cho  nguoi choi
            card c = new card(tam);
            int p = (i-1)  % 4;
            try {
                player[p].receiveCard(c);
            }catch (Exception e){

            }
            gdien.drawAllCard();
            try {
                Thread.sleep(delay / 10);
            } catch (Exception e) {
            }
        }
        //gdien.drawAllCard();
    }

    // thong bao
    public void notice(String st) {
        gdien.notice(st);
    }

    // kiem tra la bai thang
    public card check4cardwin() {
        card max = fourCard.get(0);
        for (int i = 1; i <= 3; i++) {
            if (fourCard.get(i).greaterThan(max)) {
                max = fourCard.get(i);
            }
        }
        return max;
    }

    // shoot the moon
    public void checkShootTheMoon() {
        for (int i = 0; i <= 3; i++)
        if (player[i].checkShootTheMoon()) {
            player[(i+1)%4].addScore(26);
            player[(i+2)%4].addScore(26);
            player[(i+3)%4].addScore(26);
        } 
    }

    // kiem tra ket thuc van bai (co diem so lon hon hoac bang 100)
    public boolean check100score() {
         for (int i = 0; i <= 3; i++) {
                if (player[i].getScore() >= 100) {
                    return true;
                }
            }
            return false;
        }
    

    // lay nguoi co diem thap nhat
    public int getMinScore() {
        int min = player[0].getScore();
        int winplayer = 0;
        for (int i = 1; i <= 3; i++) {
            if (player[i].getScore() < min) {
                min = player[i].getScore();
                winplayer = i;
            }
        }
        return winplayer;
    }

    // ket thuc vong choi
    public void processEndRound() {
        // kiem tra shoot the mon
        checkShootTheMoon();

        // chuyen sang trang thai ket thuc
        currentstep = GameDef.GAME_END;

        // thong bao diem so cho nguoi choi
        gdien.notice("Player1: " + player[0].getScore() + "   Player2: " + player[1].getScore()
                + "   Player3: " + player[2].getScore() + "  Player4:  " + player[3].getScore());

        // kiem tra xem ket thuc vong choi chua
        if (check100score() == false) {
            gdien.showbutton("New Round");
            gdien.drawAllCard();
        } else {
            gdien.showbutton("New Game");
            // neu nguoi choi co diem so nho nhat
            if (getMinScore() == 1) {
                gdien.notice("You WIN !!!");
            } else {
                gdien.notice("You LOSE !!!");
            }

            // reset lai diem so
            for (int i=0; i<4; i++){
               player[i].resetScore();
            }
        }
    }


    // kiem tra sau khi 4 nguoi da ra bai
    public void checkEnd4Card() {
        // neu 4 nguoi choi deu da ra la bai cua minh
        if (fourCard.size() == 4) {
            // kiem tra la bai thang
            card winCard = check4cardwin();

            // thu bai
            for (int i=0; i<4; i++){
                if (player[i].isContainCard(winCard.getID())) {
                    player[i].add4scorecard(fourCard);
                    firstturn = i;
                    currentturn = firstturn;
                }
            }
            gdien.drawAllCard();
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
            }
            roundcount++;

            // neu moi nguoi da ra 13 la bai thi ket thuc vong choi
            if (roundcount == 13) {
                processEndRound();
            }
        }
    }

    // tinh luot choi tiep theo
    public void nextturn() {
        currentturn = (currentturn + 1)%4;
    }

    // tim nguoi co quan bai 2 bich de di dau tien
    public void getFirstTurn() {
        for (int i = 0; i <= 3; i++) {
            if (player[i].isContainCard(2)) {
                firstturn = i;
                break;
            }
        }
    }

    // xu ly chon bai
    public void receiveClick(int cardClicked) {
        if (currentstep == GameDef.GAME_EXCHANGE) { //step1: hoan doi bai
            if (cardClicked != 0) { //click vao 1 la bai
                if (player[currentturn].threeCard.contains(cardClicked)) { // neu click vao la bai da chon thi bo chon
                    for (int i = 0; i < player[currentturn].threeCard.size(); i++) {
                        if (player[currentturn].threeCard.get(i) == cardClicked) {
                            player[currentturn].threeCard.remove(i);
                            break;
                        }
                    }
                    gdien.setnomal(cardClicked);
                } else { // chon bai de hoan doi
                    if (player[currentturn].threeCard.size() != 3) {
                        player[currentturn].threeCard.add(cardClicked);
                        gdien.sethightlight(cardClicked);
                    }
                }
            } else { //click vao button de hoan doi bai
                if (player[0].threeCard.size() == 3
                        && player[1].threeCard.size() == 3
                        && player[2].threeCard.size() == 3
                        && player[3].threeCard.size() == 3) {//doi 3 la bai
                    card card1, card2;
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 3; j++) {
                            card1 = player[i].pullACard(player[i].threeCard.get(j));
                            //card2 = Player[(i+j+1)%4].pullACard(Player[(i+j+1)%4].threeCard.get(j));

                            //Player[i].receiveCard(Player[(i+j+1)%4].threeCard.get(j), card2);
                            player[(i + j + 1) % 4].receiveCard(card1);
                        }
                        // xoa nhung la bai trong mang bai de hoan doi
                        player[i].threeCard.clear();
                    }

                    gdien.hideButton();
                    currentstep = GameDef.GAME_PLAY;

                    // xep lai bai
                    for (int i = 0; i <= 3; i++) {
                        player[i].sortcard();
                    }

                    //init cho step 2
                    getFirstTurn();
                    currentturn = firstturn;
                } else {
                    gdien.notice("Ban phai chon du 3 la bai !!!");
                }
            }
        } else {
            if (currentstep == GameDef.GAME_PLAY) {//step 2:play
                if (!is2bichplayed) {// nguoi choi di 2 bich truoc
                    if (player[currentturn].getHandCard(cardClicked).getID() == 2) {
                        fourCard.add(player[currentturn].playACard(cardClicked));

                        gdien.drawAllCard();
                        try {
                            Thread.sleep(delay);
                        } catch (Exception e) {
                        }
                        is2bichplayed = true;
                        nextturn();
                    } else {
                        gdien.notice("Ban phai di 2 bich truoc");
                    }
                } else {
                    if (fourCard.size() == 0) {//Human di dau tien
                        if ((player[currentturn].getHandCard(cardClicked).checkCo()) && (!duocChonCo)) {
                            gdien.notice("Chua co la Co nao ra");
                        } else {
                            if (player[currentturn].getHandCard(cardClicked).getID() == 41) {
                                gdien.notice("Ban phai di 2 bich truoc");
                            } else {
                                fourCard.add(player[currentturn].playACard(cardClicked));
                                gdien.drawAllCard();
                                try {
                                    Thread.sleep(delay);
                                } catch (Exception e) {
                                }
                                nextturn();
                            }
                        }
                    } else {//Human di theo
                        if ((player[currentturn].getHandCard(cardClicked).checkSameRank(fourCard.get(0)))
                                || (!player[currentturn].checkAvableRank(fourCard.get(0)))) {
                            fourCard.add(player[currentturn].playACard(cardClicked));
                            if (player[currentturn].getHandCard(cardClicked).checkCo()) {
                                duocChonCo = true;
                            }
                            gdien.drawAllCard();
                            try {
                                Thread.sleep(delay);
                            } catch (Exception e) {
                            }
                            nextturn();
                            checkEnd4Card();
                        } else {
                            gdien.notice("Ban phai di cung nuoc voi la dau tien");
                        }
                    }
                }
            } else {//step 3
                newRound();
            }
        }
    }

    // chay tien trinh
    public void run() {
        while (true) {
            if (SignalFlag[0] != -1) {
                receiveClick(SignalFlag[0]);
                SignalFlag[0] = -1;
            }
            if (SignalFlag[1] != 0) {
                newGame();
                SignalFlag[1] = 0;
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    // bat dau vong choi moi
    public void newRound() {
        //bat dau vong choi moi
        for (int i = 0; i < 3; i++) {
            player[i].newRound();
        }
        divideCard();
        for (int i = 0; i < 3; i++) {
            player[i].sortcard();
        }

        is2bichplayed = false;
        duocChonCo = false;
        currentstep = 1;
        roundcount = 0;
        gdien.showbutton("Exchange");
        gdien.drawAllCard();
    }

    // bat dau man choi moi
    public void newGame() {
        // reset lai diem do
        for (int i = 0; i < 3; i++) {
            player[i].resetScore();
        }

        fourCard.clear();
        // bat dau vong choi moi
        newRound();
    }

    public static void main(String[] args) {
        gameEngine gameE = new gameEngine("hehe");
        gameE.newRound();
    }

    ArrayList<card> getFourCard() {
        return fourCard;
    }
}
