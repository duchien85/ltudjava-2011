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

        JLabel JL_PortServer = new JLabel("Port:");
        JL_PortServer.setBounds(310, 215, 50, 50);

        final JTextField JTFPortServer = new JTextField();

        JTFPortServer.setBounds(350, 230, 150, 20);

        JButton JBT_Listen = new JButton("Listen");

        gui.container.add(JL_PortServer,0);

        gui.container.add(JTFPortServer,0);

        JBT_Listen.setBounds(400, 260, 100, 20);

        final JOptionPane jop = new JOptionPane("Thông Báo");

        jop.setBounds(300, 300, 300, 300);     

        JBT_Listen.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if(  JTFPortServer.getText() == null || (JTFPortServer.getText() == null ? "" == null : JTFPortServer.getText().equals("")))
                {
                    jop.showMessageDialog(jop, "Vui Lòng Nhập Vào Port");
                }
                else
                {
                    int portserver = Integer.parseInt(JTFPortServer.getText());
                    gameControl.IsServer(portserver);
                    gameControl.SwitchState(GameDef.GAME_WAIT);
                    
                }
            }
        });
        //gui.container.add(jop,0);

        gui.container.add(JBT_Listen,0);

        gui.repaint();

    }


}
