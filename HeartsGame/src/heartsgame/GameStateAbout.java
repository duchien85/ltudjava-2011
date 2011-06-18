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
public class GameStateAbout extends GameState {

    GameStateAbout(final GameControl gameControl, final GUI gui) {
        isEnter = false;
        this.gameControl = gameControl;
        this.gui = gui;
    }

  
    public void Draw() {
        if (isEnter == false) {
            isEnter = true;
            gui.container.removeAll();

            JLabel lblAbout = new JLabel("Game Hearts version 1.0");
            lblAbout.setBounds(330,200,150,20);
            gui.container.add(lblAbout);
            
            // them button Back to Menu
            JButton btnAbout = new JButton("Back to Menu");
            btnAbout.setBounds(330, 260, 150, 20);
            btnAbout.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    gameControl.SwitchState(GameDef.GAME_MENU);
                }
            });
            gui.container.add(btnAbout);
            gui.repaint();
        }
    }
}
