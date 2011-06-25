/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

/**
 *
     * @author kydrenw
     */
public class GameStateAbout extends GameState {

    GameStateAbout(final GameControl gameControl, final GUI gui) {
        this.gameControl = gameControl;
        this.gui = gui;
    }

  
    @Override
    public void Enter(){
        gui.container.removeAll();
        URL path = getClass().getResource("52card/bg.jpg");
        JLabel bg= new JLabel(new ImageIcon(path));
        bg.setBounds(gui.container.getX(), gui.container.getX(),gui.container.getWidth(), gui.container.getHeight());
        gui.container.add(bg);

        JLabel lblAbout = new JLabel("Game Hearts version 1.0");
        lblAbout.setBounds(330, 200, 150, 20);
        gui.container.add(lblAbout,0);

        // them button Back to Menu
        JButton btnAbout = new JButton("Back to Menu");
        btnAbout.setBounds(330, 250, 150, 30);
        btnAbout.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gameControl.SwitchState(GameDef.GAME_MENU);
            }
        });
        gui.container.add(btnAbout,0);
        gui.repaint();
    }
}
