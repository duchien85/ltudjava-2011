/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package heartsgame;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 *
 * @author kydrenw
 */
public class GameStatePlayServer extends GameStatePlay {
    public GameStatePlayServer(GameControl gControl, GUI g) {
        player = new Human[4];
        player[0] = new Human("Player1");
        player[1] = new Human("Player2");
        player[2] = new Human("Player3");
        player[3] = new Human("Player4");

        fourCard = new ArrayList<card>();
        this.gameControl = gControl;
        this.gui = g;
    }

    @Override
    public void Update(){
        if(playState == GameDef.GAME_PLAY_START){
            divideCard();
            SendDataCardToClient();
            System.out.println("Switch to Game Exchange !!! ");
            playState = GameDef.GAME_PLAY_EXCHANGE;
        }

        else if(playState == GameDef.GAME_PLAY_EXCHANGE){
            // cho nguoi choi doi bai
            if (!endExchange)
                ReceiveExchange();
            
            if(CheckExchange()){                    
                    FindFirstPlay();
                    // ra lenh cho cac client tu tim nguoi di dau tien
                    gameControl.getServer().SendToAllClient("first");
                    showbutton("play card");
                    btnCommand.setEnabled(false);
                    playState = GameDef.GAME_PLAY_PLAYING;
                }
        }

        else if(playState == GameDef.GAME_PLAY_PLAYING){
            if (currentTurn == 0 && cardClicked!=-1){
                ReceiveCardPlay();
                cardClicked = -1;
            }
        }

        else if(playState == GameDef.GAME_PLAY_END){
            //System.out.println("In Game END !!! ");

        }
    }
    private boolean CheckExchange() {
        if (endExchange && gameControl.getServer().IsExchanged()) {
            ArrayList<Integer> card0 = player[0].getThreeCard();
            ArrayList<Integer> card1 = gameControl.getServer().getCardExchange(0);
            ArrayList<Integer> card2 = gameControl.getServer().getCardExchange(1);
            ArrayList<Integer> card3 = gameControl.getServer().getCardExchange(2);

            int playerchange =0;
            for(int i=0; i<3; i++){
                card _0card = player[0].pullACard(card0.get(i));
                player[(playerchange+1+i)%4].receiveCard(_0card);
            }
            playerchange =1;
            for (int i=0;i<3;i++){
                card _1card = player[1].pullACard(card1.get(i));
                player[(playerchange+1+i)%4].receiveCard(_1card);
            }
            playerchange =2;
            for (int i=0;i<3;i++){
                card _2card = player[2].pullACard(card2.get(i));
                player[(playerchange+1+i)%4].receiveCard(_2card);
            }
            playerchange =3;
            for (int i=0;i<3;i++){
                card _3card = player[3].pullACard(card3.get(i));
                player[(playerchange+1+i)%4].receiveCard(_3card);
            }

            for(int i=0; i<4;i++){
                player[i].getThreeCard().clear();
            }

            SendDataCardToClient();
            btnCommand.setText("play card");
            disableButton();
            drawAllCard();
            return true;
        }
        return false;
    }

    // gui du lieu toi client
    private void SendDataCardToClient(){
        // gui du lieu cho cac client
            String data = "";
            for (int i =0; i<4;i++){
                data +="card";
                for (int j=0;j<player[i].getHandcard().size();j++){
                    if (this.player[i].getHandCard(j) != null) {
                        data += "c";
                        data += this.player[i].getHandCard(j).getID();
                    }
                }
            }
            gameControl.getServer().SendToAllClient(data);

            String fourData = "four";
            for(int i=0; i<fourCard.size();i++){
                fourData += "c";
                fourData += fourCard.get(i).getID();
            }
            gameControl.getServer().SendToAllClient(fourData);
    }

    // chia bai
    private void divideCard() {
        // khoi tao mang cac quan bai gia tri ban dau la 0
        int[] ddau = new int[53];
        for (int i = 1; i < 53; i++) {
            ddau[i] = 0;
        }
        // tao ngau nhien mang tu 1-53
        SecureRandom numGenerate = new SecureRandom();
        int tam = numGenerate.nextInt(53);
        int p = 0;
        for (int i = 1; i < 53; i++) {
            while ((tam == 0) || (ddau[tam] == 1)) {
                tam = numGenerate.nextInt(53);
            }
            // danh dau la da co
            ddau[tam] = 1;

            // chia bai cho  nguoi choi
            card c = new card(tam);
            try {
                player[p].receiveCard(c);
                p = (p+1)%4;
            } catch (Exception e) {
            }
            drawAllCard();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
        System.out.println("Finished dicide card !!! ");
    }

    @Override
    public void HaveMessage(String msg) {
        if(playState == GameDef.GAME_PLAY_EXCHANGE){
            if (msg.startsWith("exchange")){
                
            }
        }
        else if (playState == GameDef.GAME_PLAY_PLAYING){
            if(msg.startsWith("play")){ // tin hieu cac la bai cua nguoi choi
                int vitri = Integer.parseInt(msg.split("play")[1]);
                String card = msg.split("play")[2];
                String [] idcard = card.split("c");

                player[vitri].newRound();
                for (int i =1 ;i<idcard.length;i++){
                    card c = new card(Integer.parseInt(idcard[i]));
                    player[vitri].receiveCard(c);
                }
                drawAllCard();
                // doi nhan tin hieu 4 la bai danh ra
            }

            else if (msg.startsWith("four")){// tin hieu 4 la bai danh ra
                fourCard.clear();
                if (playState==GameDef.GAME_PLAY_PLAYING){
                    if (msg.split("four").length>1){
                        String fCard = msg.split("four")[1];
                        String []idcard = fCard.split("c");
                        for(int i=1; i<idcard.length;i++){
                            card c = new card(Integer.parseInt(idcard[i]));
                            fourCard.add(c);
                        }
                    }
                    SendDataCardToClient();
                    drawAllCard();
                    nextturn(); // qua luot cho nguoi ke tiep
                }
            }
        }
    }

    @Override
    public void nextturn(){
        currentTurn = (currentTurn+1)%4;
        if (fourCard.size()==4){
            checkEnd4Card();
        }
        System.out.println("Wait for player " + (currentTurn+1)+" play ....");
    }
    @Override
    public void ReceiveCardPlay() {
        if(have2chuon){// neu quan 2 chuon chua dj
            if (player[0].getHandCard(cardClicked).getID()==2){
                fourCard.add(player[0].playACard(cardClicked));
                //drawAllCard();
                DrawUpdateCard(1);
                DrawUpdateCard(5);
                have2chuon = false;
                SendDataCardToClient();
                nextturn();
                try {
                    Thread.sleep(100);
                }catch (Exception e){

                }                
            }
            else
                this.notice("Ban phai di 2 chuon dau tien !!!");
        }
        else if (fourCard.isEmpty()){ // server di truoc
            if((player[0].getHandCard(cardClicked).checkCo())&&(!duocChonCo)){
                this.notice("Ban khong duoc phep chon quan Co");
            }
            else{
                if (player[0].getHandCard(cardClicked).getID()==41)
                    this.notice("Ban phai di 2 chuon truoc");
                else{
                    fourCard.add(player[0].playACard(cardClicked));
                    //drawAllCard();
                    DrawUpdateCard(1);
                    DrawUpdateCard(5);
                    SendDataCardToClient();
                    nextturn();
                    try{
                        Thread.sleep(100);
                    }catch(Exception e){

                    }                    
                }
            }
        }
        else if (fourCard.size()>0){// server di sau
            if((player[0].getHandCard(cardClicked).checkSameRank(fourCard.get(0)))||
            (!player[0].checkAvableRank(fourCard.get(0)))){
                if (player[0].getHandCard(cardClicked).checkCo())
                    duocChonCo=true;

                fourCard.add(player[0].playACard(cardClicked));
                               
                //drawAllCard();
                DrawUpdateCard(1);
                DrawUpdateCard(5);
                
                SendDataCardToClient();
                nextturn();

                try {
                    Thread.sleep(100);
                }catch(Exception e) {

                }
            }
            else
                this.notice("Ban phai di cung chat voi la dau tien");
        }
    }

    




    
}
