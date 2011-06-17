/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package heartsgame;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author kydrenw
 */
public class GUI extends JFrame{
    Container container;
    GUI(final GameControl aThis) {
        setBounds(300, 100, 800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        container = getContentPane();
        container.setLayout(null);

    }

    private GUI() {
        setBounds(300, 200, 800, 600);
    }

}
