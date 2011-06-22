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
public class Game_IPCLIENT extends GameState {
    Game_IPCLIENT(final GameControl gameControl, final GUI gui) {
        this.gameControl = gameControl;
        this.gui = gui;
    }

    @Override
    public void Enter(){
        gui.container.removeAll();

        JLabel ip = new JLabel("IP: ");
        ip.setBounds(310, 200, 50, 20);

        JLabel port = new JLabel("Port:");

        port.setBounds(310, 215, 50, 50);

        final JTextField textip = new JTextField();

        textip.setBounds(350, 200, 150, 20);

        final JTextField textport = new JTextField();

        textport.setBounds(350, 230, 150, 20);

        gui.container.add(ip);
        gui.container.add(port);
        gui.container.add(textip);
        gui.container.add(textport);

        JButton btnConnect = new JButton("Connect...");

        btnConnect.setBounds(400, 260, 100, 20);

        final JOptionPane jop = new JOptionPane("Thông Báo");

        jop.setBounds(300, 300, 300, 300);

        btnConnect.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if(textip.getText() == null || (textip.getText() == null ? "" == null : textip.getText().equals("")))
                {
                     jop.showMessageDialog(jop, "Vui Lòng Nhập Địa Chỉ IP");
                     

                }
                else
                {
                    if(textport.getText() == null || (textport.getText() == null ? "" == null : textport.getText().equals("")))
                    {
                        jop.showMessageDialog(jop, "Vui Lòng Nhập Vào Port");
                    }
                    else
                    {
                        final String Ip = textip.getText();

                        final int portclient = Integer.parseInt(textport.getText());
                        gameControl.SwitchState(GameDef.GAME_WAIT);
                        gameControl.IsClient(Ip,portclient);
                    }


                }


                
            }

            }
        );

       gui.container.add(jop);
       gui.container.add(btnConnect);
       gui.repaint();
    }
}
