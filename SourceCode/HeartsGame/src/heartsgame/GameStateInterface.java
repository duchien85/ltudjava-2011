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
public class GameStateInterface extends GameState {

    GameStateInterface(final GameControl gameControl, final GUI gui) {
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


        // them button Java
        JButton btnJava = new JButton("Java");
        btnJava.setBounds(330, 190, 150, 30);
        btnJava.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UpdateInterface(UpdateInterface.interfacejava(), gui);
                gameControl.SwitchState(GameDef.GAME_MENU);
            }
        });

        // them button Java
        JButton btnLinux = new JButton("Linux");
        btnLinux.setBounds(330, 220, 150, 30);
        btnLinux.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UpdateInterface(UpdateInterface.interfacelinux(), gui);
                gameControl.SwitchState(GameDef.GAME_MENU);
            }
        });

        // them button Java
        JButton btnWin = new JButton("Windows");
        btnWin.setBounds(330, 250, 150, 30);
        btnWin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            new UpdateInterface(UpdateInterface.interfaceWin(), gui);
                gameControl.SwitchState(GameDef.GAME_MENU);
            }
        });

        gui.container.add(btnJava,0);
        gui.container.add(btnLinux,0);
        gui.container.add(btnWin,0);
        gui.repaint();
    }
}
