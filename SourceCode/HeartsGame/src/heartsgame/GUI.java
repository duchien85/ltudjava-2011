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

    public void Paint(int index) {
        switch(index){
            case 1:
                this.repaint(150, 400, 500, 600);
                break;
            case 2:
                this.repaint(650, 0, 150, 600);
                break;
            case 3:
                this.repaint(150, 0, 500, 200);
                break;
            case 4:
                this.repaint(0, 0, 150, 570);
                break;
            case 5:
                this.repaint(150, 200, 500, 200);
                break;
        }
    }
}
