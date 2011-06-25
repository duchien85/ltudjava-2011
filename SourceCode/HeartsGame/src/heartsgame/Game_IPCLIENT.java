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
 * @author BACDAIBAN
 */
public class Game_IPCLIENT extends GameState {

    Game_IPCLIENT(final GameControl gameControl, final GUI gui) {
        this.gameControl = gameControl;
        this.gui = gui;
    }

    @Override
    public void Enter() {
        gui.container.removeAll();

        URL path = getClass().getResource("52card/bg.jpg");
        JLabel bg= new JLabel(new ImageIcon(path));

        bg.setBounds(gui.container.getX(), gui.container.getX(),gui.container.getWidth(), gui.container.getHeight());
        gui.container.add(bg);

        JLabel ip = new JLabel("IP: ");
        ip.setBounds(310, 200, 50, 30);

        final JTextField textip = new JTextField();


        textip.setText("127.0.0.1");
        textip.setBounds(350, 200, 150, 30);

        gui.container.add(ip,0);
        gui.container.add(textip,0);

        JButton btnConnect = new JButton("Connect...");

        btnConnect.setBounds(400, 260, 100, 30);

        final JOptionPane jop = new JOptionPane("Thông Báo");

        jop.setBounds(300, 300, 300, 300);

        btnConnect.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (textip.getText() == null || (textip.getText() == null ? "" == null : textip.getText().equals(""))) {
                    jop.showMessageDialog(jop, "Vui Lòng Nhập Địa Chỉ IP");
                } else {
                    final String Ip = textip.getText();
                    final int portclient = GameDef.DEFAULT_PORT;
                    Client testConnect = new Client(Ip, portclient, gameControl);
                    if (testConnect.isConnected == true){
                        testConnect.stop();
                        gameControl.SwitchState(GameDef.GAME_WAIT);
                        gameControl.IsClient(Ip, portclient);
                    }
                    else{                        
                        jop.showMessageDialog(jop, "Không tìm thấy server !!!");
                    }

                }
            }
        });

        // them button Back to Menu
        JButton btnAbout = new JButton("Back to Menu");
        btnAbout.setBounds(350, 300, 150, 30);
        btnAbout.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gameControl.SwitchState(GameDef.GAME_MENU);
            }
        });
        gui.container.add(btnAbout,0);

        gui.container.add(btnConnect,0);
        gui.repaint();
    }
}
