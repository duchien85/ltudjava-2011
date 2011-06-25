/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.awt.Color;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;


/**
 *
     * @author kydrenw
     */
public class GameStateAbout extends GameState {
    
    Clip clip = null;

    GameStateAbout(final GameControl gameControl, final GUI gui) {
        this.gameControl = gameControl;
        this.gui = gui;
    }

  
    @Override
    public void Enter(){
        gui.container.removeAll();
        URL path = getClass().getResource("52card/Broken heart.png");
        JLabel bg= new JLabel(new ImageIcon(path));
        bg.setBounds(gui.container.getX(), gui.container.getX(),gui.container.getWidth(), gui.container.getHeight());
        gui.container.add(bg);
        
        URL soundLocation = getClass().getResource("sound/H2O.wav");
        try {
            clip = AudioSystem.getClip();

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundLocation);
            clip.open(inputStream);
            clip.loop(20);
            clip.start();
        } catch (Exception ex) {
        }
        gui.container.add(bg);

        JLabel lblAbout = new JLabel("Game Hearts version 1.0");
        lblAbout.setBounds(330, 80, 150, 20);
        lblAbout.setForeground(Color.white);
        gui.container.add(lblAbout,0);

        // them button Back to Menu
        JButton btnAbout = new JButton("Back to Menu");
        btnAbout.setBounds(330, 100, 150, 30);
        btnAbout.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (clip != null)
                    clip.stop();
                gameControl.SwitchState(GameDef.GAME_MENU);
            }
        });
        gui.container.add(btnAbout,0);    
        gui.repaint();
    }
}
