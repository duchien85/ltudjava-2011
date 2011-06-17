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

    GameStateWait() {
        isEnter = false;
    }

    @Override
    public void Draw(final GameControl gameControl, final GUI gui) {
        if (isEnter == false) {
            gui.container.removeAll();
            isEnter = true;

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
    }

    public void Update(final GameControl gameControl, final GUI gui) {
        if (gameControl.getType() == GameDef.IS_SERVER) {
            if (gameControl.getServer().getClientCount() == 3) {
                gameControl.setViTri(0);
                gameControl.getServer().SendToClient(0, "start1");
                gameControl.getServer().SendToClient(1, "start2");
                gameControl.getServer().SendToClient(2, "start3");
                gameControl.SwitchState(GameDef.GAME_PLAY);
            }

        } else if (gameControl.getType() == GameDef.IS_CLIENT) {
            try {
                String msg = gameControl.getClient().GetMessage();
                if (msg.startsWith("start")){
                    int vitri = Integer.parseInt(msg.split("start")[1]);
                    gameControl.setViTri(vitri);
                    gameControl.SwitchState(GameDef.GAME_PLAY);
                }
            } catch (Exception ex) {
                return;
            }
            ;
        }
    }
}
