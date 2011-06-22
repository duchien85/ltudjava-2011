/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package heartsgame;

import java.awt.*;
import javax.swing.*;

/**/
// Class: GUI
// the hien giao dien game
// @author kydrenw
/**/
public class GUI extends JFrame{
    // khung chua cac component cua giao dien
    public Container container;

    // khoi tao giao dien
    public GUI(final GameControl aThis) {
        // khung nhin
        setBounds(300, 100, 800, 600);
        // nut exit
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // khong cho thay doi man hinh
        setResizable(false);
        // cho phep hien thi
        setVisible(true);
        // khung chua chinh la khung man hinh
        container = getContentPane();
        container.setLayout(null);
    }

    private GUI() {
        setBounds(300, 200, 800, 600);
    }

    public void Paint(int index) {
        switch(index){
            case 1: // ve lai goc duoi
                this.repaint(150, 400, 500, 200);
                break;
            case 2: // ve lai goc ben phai
                this.repaint(650, 0, 150, 600);
                break;
            case 3: // ve lai goc tren
                this.repaint(150, 0, 500, 200);
                break;
            case 4: // ve lai goc trai
                this.repaint(0, 0, 150, 570);
                break;
            case 5: // ve lai phan chinh giua
                this.repaint(150, 200, 500, 200);
                break;
        }
    }
}
