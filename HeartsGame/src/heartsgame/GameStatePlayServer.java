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
        isEnter = false;

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
            System.out.println("In Game START !!! ");
            divideCard();
            change = true;
            SendDataCardToClient();
            System.out.println("Switch to Game Exchange !!! ");
            playState = GameDef.GAME_PLAY_EXCHANGE;
        }else if(playState == GameDef.GAME_PLAY_EXCHANGE){
            //System.out.println("In Game Exchange !!! ");

            ReceiveExchange();
            if(CheckExchange()){
                System.out.println("Switch to Game Playing !!! ");
                playState = GameDef.GAME_PLAY_PLAYING;
            }
        }else if(playState == GameDef.GAME_PLAY_PLAYING){
            //System.out.println("In Game PLAYING !!! ");


        }else if(playState == GameDef.GAME_PLAY_END){
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
                //player[i].sortcard();
            }

            SendDataCardToClient();
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
        for (int i = 1; i < 53; i++) {
            while ((tam == 0) || (ddau[tam] == 1)) {
                tam = numGenerate.nextInt(53);
            }
            // danh dau la da co
            ddau[tam] = 1;

            // chia bai cho  nguoi choi
            card c = new card(tam);
            int p = (i - 1) % 4;
            try {
                player[p].receiveCard(c);
            } catch (Exception e) {
            }
            drawAllCard();
            try {
                gameControl.getThread().sleep(10);
            } catch (Exception e) {
            }
        }
    }
    @Override
    public void HaveMessage(String msg) {
       
    }
}
