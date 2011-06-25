/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package heartsgame;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author kydrenw
 */
public class GameStatePlayClient extends GameStatePlay {

    public GameStatePlayClient(GameControl gControl, GUI g) {
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
           
        }else if(playState == GameDef.GAME_PLAY_EXCHANGE){
            if (!endExchange){
                this.notice("Select 3 cards to exchange !");
                ReceiveExchange();                
            }
            else
                this.notice("Wait for exchange with other player...");
        }else if(playState == GameDef.GAME_PLAY_PLAYING){
            if (cardClicked!=-1) {
                if (currentTurn ==0)
                    ReceiveCardPlay();
                cardClicked = -1;
            }

        }else if(playState == GameDef.GAME_PLAY_END){
            

        }
    }

    @Override
    public void HaveMessage(String msg) {
        if (msg.startsWith("card")){
            ChangeCard(msg);
            if(playState == GameDef.GAME_PLAY_START){
                // chuyen trang thai
                System.out.println("Switch to Game Exchange !!! ");                
                playState = GameDef.GAME_PLAY_EXCHANGE;
            }
            else if(playState == GameDef.GAME_PLAY_EXCHANGE){
                System.out.println("Switch to Game Playing !!! ");                
                playState = GameDef.GAME_PLAY_PLAYING;
            }            
        }
        else if (msg.startsWith("first")){            
                FindFirstPlay();            
        }
        else if (msg.startsWith("four")){// tin hieu 4 la bai danh ra
            fourCard.clear();
            if (playState == GameDef.GAME_PLAY_PLAYING) {
                if (msg.split("four").length > 1) {
                    String fCard = msg.split("four")[1];
                    String[] idcard = fCard.split("c");
                    for (int i = 1; i < idcard.length; i++) {
                        int id = Integer.parseInt(idcard[i]);
                        fourCard.add(id);
                    }
                }
                nextturn();
                drawAllCard();
            }
        }
        else if (msg.startsWith("turn")){
            int turn = Integer.parseInt(msg.split("turn")[1]);
            if (turn < gameControl.getViTri()){
                firstturn = (turn+4)-gameControl.getViTri();
            }else{
                firstturn= turn - gameControl.getViTri();
            }
            
            currentTurn = firstturn;
            if (currentTurn ==0)
                this.notice("Wait for you play ...");
            else
                this.notice("Wait for player " + (currentTurn+1)+ " play ....");
            roundcount++;
            if (roundcount >1 );
                duocChonCo = true;
            fourCard.clear();
            drawAllCard();
        }
        else if (msg.startsWith("score")){
            String[] pscore= msg.split("score");
            // cap nhat cac quan bai cho 4 nguoi choi
            int d = 0;
            for (int i = 1; i <= 4; i++) {
                int thutu = (gameControl.getViTri() + d) % 4;                
                    int _score = Integer.parseInt(pscore[i]);
                    player[thutu].setScore(_score);                
                d++;
            }
            updateScore();
        }
        else if (msg.startsWith("endround")){
            processEndRound();
        }
        else if (msg.startsWith("exit")){
            final JOptionPane jop = new JOptionPane("Notice");
            jop.setBounds(300, 300, 300, 300);
            jop.showMessageDialog(jop, "Server is exited game... ");
            try{
                gameControl.getClient().stop();
            }catch(Exception ex){}
            gameControl.SwitchState(GameDef.GAME_MENU);
        }
    }
}
