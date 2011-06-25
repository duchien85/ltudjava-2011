/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package heartsgame;

import java.awt.Color;
import java.awt.event.*;


import java.io.*;
import java.net.URL;
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

        URL path = getClass().getResource("52card/bg.jpg");
        JLabel bg= new JLabel(new ImageIcon(path));
        
        bg.setBounds(gui.container.getX(), gui.container.getX(),gui.container.getWidth(), gui.container.getHeight());
        gui.container.add(bg);      

         // them button Back to Menu
        JButton btnBack = new JButton("Back to Menu");
        btnBack.setBounds(350, 300, 150, 30);
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
        try{
            Server server = new Server(GameDef.DEFAULT_PORT, gameControl);
            if (server.isConnect == true){
                server.stop();
                gameControl.IsServer(GameDef.DEFAULT_PORT);
                gameControl.SwitchState(GameDef.GAME_WAIT);
            }
            else{
                JLabel note = new JLabel();
                note.setForeground(Color.white);
                note.setText("Cannot create more than 1 game in a computer !!!");
                note.setBounds(280, 250, 300, 30);
                gui.container.add(note,0);
            }

        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        
    }
}
