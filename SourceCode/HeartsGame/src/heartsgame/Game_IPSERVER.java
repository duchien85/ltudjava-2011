/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package heartsgame;

import java.awt.event.*;


import java.io.*;
import javax.swing.*;

/**
 *
 * @author BACDAIBAN
 */
public class Game_IPSERVER extends GameState {

   Game_IPSERVER(final GameControl gameControl, final GUI gui) {
        this.gameControl = gameControl;
        this.gui = gui;
    }

    @Override
    public void Enter()
    {
        gui.container.removeAll();

        JLabel bg= new JLabel(new ImageIcon("52card\\bg.jpg"));
        bg.setBounds(gui.container.getX(), gui.container.getX(),gui.container.getWidth(), gui.container.getHeight());
        gui.container.add(bg);      

         // them button Back to Menu
        JButton btnBack = new JButton("Back to Menu");
        btnBack.setBounds(350, 300, 150, 20);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameControl.SwitchState(GameDef.GAME_MENU);
            }
        });
        gui.container.add(btnBack,0);
        gui.repaint();

        Connect();
    }

    private void Connect(){
        gameControl.IsServer(GameDef.DEFAULT_PORT);
        gameControl.SwitchState(GameDef.GAME_WAIT);
    }
}
