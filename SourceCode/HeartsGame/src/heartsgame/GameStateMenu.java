/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author kydrenw
 */
public class GameStateMenu extends GameState {

    JLabel ng;

    GameStateMenu(final GameControl gameControl, final GUI gui) {
        this.gameControl = gameControl;
        this.gui = gui;
    }

    @Override
    public void Enter() {
        gui.container.removeAll();
        URL path = getClass().getResource("52card/bg.jpg");
        JLabel bg = new JLabel(new ImageIcon(path));
        bg.setBounds(gui.container.getX(), gui.container.getX(), gui.container.getWidth(), gui.container.getHeight());
        gui.container.add(bg);
        /*
        ng = new JLabel(new ImageIcon("52card\\newgame.png"));
        ng.setBounds(200, 200,150, 20);
        ng.addMouseListener(new MouseListener() {

        public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
        }

        public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
        }

        public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
        }

        public void mouseEntered(MouseEvent e) {
        GameStateMenu.this.ng.setIcon(new ImageIcon("52card\\newgame_over.png"));
        }

        public void mouseExited(MouseEvent e) {
        GameStateMenu.this.ng.setIcon(new ImageIcon("52card\\newgame.png"));
        }
        });
        gui.container.add(ng,0);
         */
        // them button New Game
        JButton btnNewGame = new JButton("Create Game");
        btnNewGame.setBounds(330, 200, 150, 30);
        btnNewGame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gameControl.SwitchState(GameDef.GAME_IPSERVER);
            }
        });
        gui.container.add(btnNewGame, 0);

        // them button Connect to Game
        JButton btnConnect = new JButton("Join to Game");
        btnConnect.setBounds(330, 230, 150, 30);
        btnConnect.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {;
                    gameControl.SwitchState(GameDef.GAME_IPCLIENT);
            }
        });
        gui.container.add(btnConnect, 0);

        // them button Single player

        JButton btnSinglePlayer = new JButton("VS Computer");

        btnSinglePlayer.setBounds(330, 260, 150, 30);
        btnSinglePlayer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gameControl.SwitchState(GameDef.GAME_WAIT);
                gameControl.SinglePlay();
            }
        });

        gui.container.add(btnSinglePlayer, 0);

        // them button About
        JButton btnAbout = new JButton("About");
        btnAbout.setBounds(330, 290, 150, 30);
        btnAbout.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gameControl.SwitchState(GameDef.GAME_ABOUT);
            }
        });
        gui.container.add(btnAbout, 0);

        // them button About
        JButton btnInterface = new JButton("Interface");
        btnInterface.setBounds(330, 290, 150, 30);
        btnInterface.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gameControl.SwitchState(GameDef.GAME_INTERFACE);
            }
        });
        gui.container.add(btnInterface, 0);

        gui.repaint();
    }

    /*
    private void FindServer() throws IOException {
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            System.out.println(ipAddress);
            String hostAdd = ipAddress.getHostAddress().toString();
            String[] hn = hostAdd.split("\\.");

            String iptest = hn[0] + "." + hn[1] + "."+ hn[2];
            
            for (int i = 0; i < 255; i++) {
                System.out.println("Checking " + i);
                Client ctest = new Client(iptest + i, GameDef.DEFAULT_PORT, gameControl);
                if (ctest.isConnected == true) {
                    ctest.stop();
                    System.out.println("Server IP : " + iptest + i);
                    break;
                }
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(GameStateMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     */
}
