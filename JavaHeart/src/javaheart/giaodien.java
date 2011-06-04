package javaheart;

import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class giaodien extends JFrame {

    final private int xresolusion = 800;
    final private int yresolusion = 600;
    final private int cardwidth = 100;
    final private int cardheight = 135;
    private JLabel[] p1, p2, p3, p4, gamecard;
    private Player[] player;
    private ArrayList<card> fourcard;
    private myMouseListener mlisten;
    private Container mainPanel;
    private JLabel note;
    private JButton mybutton;
    private JLabel[] score;
    private Boolean showComCard = false;
    private JSlider slider;

    public giaodien(final gameEngine ge) {
        super("Java Heart game !");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        mlisten = new myMouseListener(ge.getSignal());
        score = new JLabel[4];
        this.player = new Player[4];
        this.player[0] = ge.getPlayer(0);
        this.player[1] = ge.getPlayer(1);
        this.player[2] = ge.getPlayer(2);
        this.player[3] = ge.getPlayer(3);
        this.fourcard = ge.getFourCard();
        setVisible(true);
        setSize(800, 600);

        mainPanel = getContentPane();
        mainPanel.setLayout(null);
        //tao thanh menu
        JMenu menuFile = new JMenu("Menu");
        JMenuItem itemSave = new JMenuItem("Reset");
        itemSave.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                ge.getSignal()[1] = 1;
            }
        });
        menuFile.add(itemSave);
        JMenuBar menubar = new JMenuBar();
        menubar.setLayout(new BorderLayout());
        menubar.add(menuFile, BorderLayout.WEST);
        menubar.setBounds(0, 0, 800, 30);
        menubar.setBorder(new EtchedBorder(EtchedBorder.RAISED));

        JCheckBox checkB = new JCheckBox("Show Computer Cards");
        checkB.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                showComCard = e.getStateChange() == 1 ? true : false;
                drawAllCard();
            }
        });
        menubar.add(checkB, BorderLayout.CENTER);
        slider = new JSlider(50, 1000, 500);
        slider.setBounds(5, 5, 500, 30);
        slider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                ge.updateDelay(slider.getValue());
            }
        });
        menubar.add(slider, BorderLayout.EAST);
        /*JButton testbutton=new JButton("test");
        testbutton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        clear4play();

        }
        });
        menubar.add(testbutton);*/
        mainPanel.add(menubar);

        //tao nut, diem o giua
        Container ultilcontain = new Container();
        ultilcontain.setLayout(null);
        mybutton = new JButton();
        mybutton.setName("0");
        mybutton.setBounds(350, 330, 100, 20);
        mybutton.addMouseListener(mlisten);
        ultilcontain.add(mybutton);
        //add them tuy thix
        Font f = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 20);
        for (int i = 0; i <= 3; i++) {
            score[i] = new JLabel();
            score[i].setFont(f);
            ultilcontain.add(score[i]);
        }
        score[0].setBounds(120, 470, 100, 30);
        score[1].setBounds(10, 40, 100, 30);
        score[2].setBounds(580, 7, 100, 30);
        score[3].setBounds(690, 460, 100, 30);
        ultilcontain.setBounds(0, 30, 800, 540);
        mainPanel.add(ultilcontain);


        //tao note o cuoi
        note = new JLabel("This is the help !!!");
        JPanel pn = new JPanel();
        pn.add(note);
        pn.setBackground(Color.lightGray);
        pn.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        pn.setBounds(0, 538, 800, 30);
        mainPanel.add(pn);
        //drawCards(null,1	);
        validate();
        //mainPanel.remove(1);
    }

    public void notice(String st) {
        note.setText(st);
    }

    public void hideButton() {
        mybutton.setVisible(false);
    }

    public void showbutton(String st) {
        mybutton.setText(st);
        mybutton.setVisible(true);
    }

    public void updateScore() {
        score[0].setText("Score: " + String.valueOf(player[0].getScore()));
        score[1].setText("Score: " + String.valueOf(player[1].getScore()));
        score[2].setText("Score: " + String.valueOf(player[2].getScore()));
        score[3].setText("Score: " + String.valueOf(player[3].getScore()));
    }
    //load du lieu tu 1 Player ra 13 label

    private JLabel[] loadCard(Player _player, Boolean showcard) {
        JLabel[] kq = new JLabel[14];
        ImageIcon im;
        String path;
        for (int i = 1; i < 14; i++) {
            if (_player.getHandCard(i) != null) {
                if ((showcard) || (_player instanceof Human)) {
                    path = "52card\\" + _player.getHandCard(i).getID() + ".jpg";
                } else {
                    path = "52card\\0-2.jpg";
                }
                im = new ImageIcon(path);
                kq[i] = new JLabel(im);
                if (_player instanceof Human) {
                    kq[i].setName(String.valueOf(i));
                    kq[i].addMouseListener(mlisten);
                }
				//if (!_player.checkAvableCard(i))
				//	kq[i].setVisible(false);
            }
        }
        return kq;
    }

    private JLabel[] loadCard(ArrayList<card> fourcard) {
        JLabel[] kq = new JLabel[4];
        ImageIcon im;
        String path;
        for (int i = 0; i < fourcard.size(); i++) {
            path = "52card\\" + fourcard.get(i).getID() + ".jpg";
            im = new ImageIcon(path);
            kq[i] = new JLabel(im);
        }
        return kq;
    }

    //ve 13 la bai o 1 trong 4 vi tri
    private void drawCards(JLabel jls[], int position) {
        final int khoangcach = 20;
        switch (position) {
            case 1:
                int xtoado = (xresolusion - khoangcach * 12 - cardwidth) / 2;
                for (int i = 1; i < 14; i++) {
                    if (jls[i] != null) {
                        jls[i].setBounds(xtoado, 398, 100, 135);
                        xtoado += khoangcach;
                        mainPanel.add(jls[i], 3);
                    }
                }
                break;
            case 3:
                int xtoado1 = (xresolusion - khoangcach * 12 - cardwidth) / 2;
                for (int i = 1; i < 14; i++) {
                    if (jls[i] != null) {
                        jls[i].setBounds(xtoado1, 35, 100, 135);
                        xtoado1 += khoangcach;
                        mainPanel.add(jls[i], 3);
                    }
                }
                break;
            case 2:
                int ytoado = (yresolusion - khoangcach * 12 - cardheight) / 3;
                for (int i = 1; i < 14; i++) {
                    if (jls[i] != null) {
                        jls[i].setBounds(7, ytoado + 30, 100, 135);
                        ytoado += khoangcach;
                        mainPanel.add(jls[i], 3);
                    }
                }
                break;
            case 4:
                int ytoado1 = (yresolusion - khoangcach * 12 - cardheight) / 3;
                for (int i = 1; i < 14; i++) {
                    if (jls[i] != null) {
                        jls[i].setBounds(xresolusion - cardwidth - 15, ytoado1 + 30, 100, 135);
                        ytoado1 += khoangcach;
                        mainPanel.add(jls[i], 3);
                    }
                }
                break;
            case 5:
                int xtoado2 = (xresolusion - khoangcach * fourcard.size() - cardwidth) / 2;
                for (int i = 0; i < fourcard.size(); i++) {
                    jls[i].setBounds(xtoado2, 220, 100, 135);
                    xtoado2 += khoangcach;
                    mainPanel.add(jls[i], 3);
                }
                break;
        }
    }

    private void clear4play() {
        while (mainPanel.getComponentCount() > 3) {
            mainPanel.remove(3);
        }
    }

    public void drawAllCard() {
        clear4play();
        p1 = loadCard(player[0], true);
        p2 = loadCard(player[1], true);
        p3 = loadCard(player[2], true);
        p4 = loadCard(player[3], true);
        gamecard = loadCard(fourcard);
        drawCards(p1, 1);
        drawCards(p2, 2);
        drawCards(p3, 3);
        drawCards(p4, 4);
        drawCards(gamecard, 5);
        mainPanel.repaint();
        updateScore();
    }

    public void sethightlight(int humanCardIndex) {
        p1[humanCardIndex].setEnabled(false);
    }

    public void setnomal(int humanCardIndex) {
        p1[humanCardIndex].setEnabled(true);
    }
}
