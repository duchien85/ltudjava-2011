/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.awt.Color;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import java.io.*;

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

        URL path = getClass().getResource("52card/bg.jpg");
        JLabel bg= new JLabel(new ImageIcon(path));

        bg.setBounds(gui.container.getX(), gui.container.getX(),gui.container.getWidth(), gui.container.getHeight());
        gui.container.add(bg);

        // them button New Game
        JButton btnWait = new JButton("Waiting other player ...");
        btnWait.setBounds(330, 200, 150, 30);
        btnWait.setEnabled(false);
        btnWait.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //gameControl.SwitchState(GameDef.GAME_WAIT);
            }
        });
        gui.container.add(btnWait,0);

        if (gameControl.getType() == GameDef.IS_SERVER){
            JLabel ip = new JLabel();
            ip.setForeground(Color.white);
            ip.setText("Server name : " + gameControl.getServer().getIpAddress().toString());
            ip.setBounds(280, 250, 250, 30);
            gui.container.add(ip,0);
        }

        // them button Back to Menu
        JButton btnAbout = new JButton("Back to Menu");
        btnAbout.setBounds(330, 280, 150, 30);
        btnAbout.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(gameControl.getType()==GameDef.IS_CLIENT)
                    gameControl.getClient().stop();
                else if(gameControl.getType() == GameDef.IS_SERVER){
                    gameControl.getServer().SendToAllClient("exit");
                    gameControl.getServer().stop();
                }
                gameControl.SwitchState(GameDef.GAME_MENU);
            }
        });
        gui.container.add(btnAbout,0);

        btnAbout.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

                GameSound sound = new GameSound("../HeartsGame/src/heartsgame/sound/shot.wav");
                InputStream stream = new ByteArrayInputStream(sound.getSamples());
                sound.play(stream);
            }

            public void mouseExited(MouseEvent e) {

            }
        });

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
