/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.awt.event.*;
import java.io.*;
import javax.swing.JButton;

/**
 *
 * @author kydrenw
 */
public class GameStateWait extends GameState {

    public GameStateWait(final GameControl gameControl, final GUI gui) {
        this.gameControl= gameControl;
        this.gui = gui;
    }

    @Override
    public void Update() {
        if (gameControl.getType() == GameDef.IS_SERVER) {
            if (gameControl.getServer().getClientCount() == 3) {
                gameControl.setViTri(0);
                gameControl.getServer().SendToClient(0, "start1");
                gameControl.getServer().SendToClient(1, "start2");
                gameControl.getServer().SendToClient(2, "start3");
                gameControl.SwitchState(GameDef.GAME_PLAY);
            }

        } else if (gameControl.getType() == GameDef.IS_CLIENT) {
            
        }
    }

    @Override
    public void Enter(){
        gui.container.removeAll();

        // them button New Game
        JButton btnWait = new JButton("Waiting ... ");
        btnWait.setBounds(330, 200, 150, 20);
        btnWait.setEnabled(false);
        btnWait.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //gameControl.SwitchState(GameDef.GAME_WAIT);
            }
        });
        gui.container.add(btnWait);
        gui.repaint();
    }
    @Override
    public void HaveMessage(String msg){
        if(gameControl.getType() == GameDef.IS_CLIENT && msg.startsWith("start")){
            int vitri = Integer.parseInt(msg.split("start")[1]);
            gameControl.setViTri(vitri);
            gameControl.SwitchState(GameDef.GAME_PLAY);
        }
    }
}
