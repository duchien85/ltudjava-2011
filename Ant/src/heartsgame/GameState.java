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
    protected GameControl gameControl;
    protected GUI gui;

    public GameState(){

    }

    public GameState(final GameControl gControl,final GUI g){
        this.gameControl = gControl;
        this.gui = g;
    }


    void Enter() {
        
    }

    void Update() {

    }

    void HaveMessage(String msg) {

    }
}
