/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package heartsgame;

import java.util.ArrayList;

/**
 *
 * @author kydrenw
 */
public class GameStatePlayClient extends GameStatePlay {

    public GameStatePlayClient(GameControl gControl, GUI g) {
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
           
        }else if(playState == GameDef.GAME_PLAY_EXCHANGE){
            //System.out.println("In Game Exchange !!! ");
            ReceiveExchange();
        }else if(playState == GameDef.GAME_PLAY_PLAYING){
            //System.out.println("In Game PLAYING !!! ");

        }else if(playState == GameDef.GAME_PLAY_END){
            //System.out.println("In Game END !!! ");

        }
    }

    @Override
    public void HaveMessage(String msg) {
        if (msg.startsWith("card")){
            if(playState == GameDef.GAME_PLAY_START){
                // chuyen trang thai
                System.out.println("Switch to Game Exchange !!! ");
                playState = GameDef.GAME_PLAY_EXCHANGE;
            }
            else if(playState == GameDef.GAME_PLAY_EXCHANGE){
                System.out.println("Switch to Game Playing !!! ");
                playState = GameDef.GAME_PLAY_PLAYING;
            }
            ChangeCard(msg);
        }            
    }
}
