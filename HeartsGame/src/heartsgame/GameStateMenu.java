/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author kydrenw
 */
public class GameStateMenu extends GameState {

    GameStateMenu(){
        isEnter = false;
    }

    @Override
    public void Draw(final GameControl gameControl, final GUI gui) {
        if (isEnter == false) {
            gui.container.removeAll();
            isEnter = true;

            // them button New Game
            JButton btnNewGame = new JButton("New Game");
            btnNewGame.setBounds(330, 200, 150, 20);
            btnNewGame.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    gameControl.IsServer();
                    gameControl.SwitchState(GameDef.GAME_WAIT);
                }
            });
            gui.container.add(btnNewGame);

            // them button Connect to Game
            JButton btnConnect = new JButton("Connect to Game");
            btnConnect.setBounds(330, 230, 150, 20);
            btnConnect.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    gameControl.SwitchState(GameDef.GAME_WAIT);
                    gameControl.IsClient();
                }
            });
            gui.container.add(btnConnect);

            // them button About
            JButton btnAbout = new JButton("About");
            btnAbout.setBounds(330, 260, 150, 20);
            btnAbout.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    gameControl.SwitchState(GameDef.GAME_ABOUT);
                }
            });
            gui.container.add(btnAbout);
            gui.repaint();
        }
    }
}
