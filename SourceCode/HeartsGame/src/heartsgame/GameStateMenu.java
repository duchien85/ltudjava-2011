/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author kydrenw
 */
public class GameStateMenu extends GameState {

    JLabel ng;

    GameStateMenu(final GameControl gameControl, final GUI gui){
        this.gameControl=gameControl;
        this.gui = gui;
    }

    @Override
    public void Enter(){
        gui.container.removeAll();
        JLabel bg= new JLabel(new ImageIcon("52card\\bg.jpg"));
        bg.setBounds(gui.container.getX(), gui.container.getX(),gui.container.getWidth(), gui.container.getHeight());
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
        JButton btnNewGame = new JButton("New Game");
        btnNewGame.setBounds(330, 200, 150, 20);
        btnNewGame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                 gameControl.SwitchState(GameDef.GAME_IPSERVER);
            }
        });
        gui.container.add(btnNewGame,0);

        // them button Connect to Game
        JButton btnConnect = new JButton("Connect to Game");
        btnConnect.setBounds(330, 230, 150, 20);
        btnConnect.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gameControl.SwitchState(GameDef.GAME_IPCLIENT);
            }
        });
        gui.container.add(btnConnect,0);

         // them button Single player
        JButton btnSinglePlayer = new JButton("Single Player");
        btnSinglePlayer.setBounds(330, 260, 150, 20);
        btnSinglePlayer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gameControl.SwitchState(GameDef.GAME_WAIT);
                gameControl.SinglePlay();
            }
        });
        gui.container.add(btnSinglePlayer);
        
        // them button About
        JButton btnAbout = new JButton("About");
        btnAbout.setBounds(330, 290, 150, 20);
        btnAbout.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gameControl.SwitchState(GameDef.GAME_ABOUT);
            }
        });
        gui.container.add(btnAbout,0);
        gui.repaint();
    }
}
