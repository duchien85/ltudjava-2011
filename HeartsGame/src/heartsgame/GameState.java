/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package heartsgame;

/**
 *
 * @author kydrenw
 */
public class GameState {
    public boolean isEnter;
    GameControl gameControl;
    GUI gui;

    public GameState(){

    }

    public GameState(final GameControl gControl,final GUI g){
        this.gameControl = gControl;
        this.gui = g;
    }
   
    void Draw() {
        
    }

    void Enter() {
        isEnter = false;
    }

    void Update() {

    }

    void HaveMessage(String msg) {

    }
}
