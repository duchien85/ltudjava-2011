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
        player = new Player[4];
        player[0] = new Player("Player1");
        player[1] = new Player("Player2");
        player[2] = new Player("Player3");
        player[3] = new Player("Player4");

        fourCard = new ArrayList<Integer>();
        this.gameControl = gControl;
        this.gui = g;
    }

    @Override
    public void Update(){
        if(playState == GameDef.GAME_PLAY_START){
            divideCard();
            SendDataCardToClient();
            System.out.println("Switch to Game Exchange !!! ");
            this.notice("Select 3 cards to exchange !");
            playState = GameDef.GAME_PLAY_EXCHANGE;
        }

        else if(playState == GameDef.GAME_PLAY_EXCHANGE){
            // cho nguoi choi doi bai
            if (!endExchange){
                this.notice("Select 3 cards to exchange !");
                ReceiveExchange();
            }
            else {
                this.notice("Wait for exchange with other player...");

                if (CheckExchange()) {
                    FindFirstPlay();
                    // ra lenh cho cac client tu tim nguoi di dau tien
                    gameControl.getServer().SendToAllClient("first");
                    showbutton("play card");
                    btnCommand.setEnabled(false);
                    playState = GameDef.GAME_PLAY_PLAYING;
                }
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

            for(int i=0; i<4;i++){
                player[i].getThreeCard().clear();
                player[i].setBeginCard(player[i].getListCard());
            }

            SendDataCardToClient();
            btnCommand.setText("play card");
            disableButton();
            drawAllCard();
            return true;
        }
        return false;
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
           
            try {
                player[p].receiveCard(tam);
                p = (p+1)%4;
            } catch (Exception e) {
            }
            drawAllCard();
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
        }
        for(int i=0; i<4;i++){
            player[i].sort();
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
                    int c = Integer.parseInt(idcard[i]);
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
                            int c = Integer.parseInt(idcard[i]);
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
}
