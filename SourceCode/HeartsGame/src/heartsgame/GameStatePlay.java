/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author kydrenw
 */
public class GameStatePlay extends GameState {

    public JLabel[] score;
    protected ArrayList<JLabel> playercard0;
    protected ArrayList<JLabel> playercard1;
    protected ArrayList<JLabel> playercard2;
    protected ArrayList<JLabel> playercard3;
    protected ArrayList<JLabel> game4card;
    protected JButton btnCommand;
    protected JLabel note;
    protected Player[] player;
    protected ArrayList<Integer> fourCard;
    protected int cardClicked = -1;
    protected int currentTurn = -1;
    protected int firstturn = -1;
    protected int playState = -1;
    protected boolean endExchange = false;
    protected boolean bChuaDi2chuon = false;
    protected boolean duocChonCo = false;
    protected int roundcount = 0;
    protected int numGame = 1;

    public GameStatePlay() {
    }

    @Override
    public void Update() {
    }

    @Override
    public void Enter() {
        currentTurn = 0;
        playState = GameDef.GAME_PLAY_START;

        gui.container.removeAll();

        // button Enchange
        btnCommand = new JButton("Exchange");
        btnCommand.setBounds(350, 330, 100, 20);
        btnCommand.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (GameStatePlay.this.playState == GameDef.GAME_PLAY_EXCHANGE) {
                    GameStatePlay.this.AceptExchange();
                } else if (GameStatePlay.this.playState == GameDef.GAME_PLAY_PLAYING) {
                }
            }
        });
        gui.container.add(btnCommand, 0);

        // tao notice o duoi
        note = new JLabel("This is the help !!!");
        JPanel pn = new JPanel();
        pn.add(note);
        pn.setBackground(Color.LIGHT_GRAY);
        pn.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        pn.setBounds(0, 538, GameDef.WIDTH, 30);
        gui.container.add(pn, 1);

        // hien diem so
        Font f = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 20);
        score = new JLabel[4];
        for (int i = 0; i <= 3; i++) {
            score[i] = new JLabel();
            score[i].setFont(f);
            gui.container.add(score[i], i + 2);
        }

        score[3].setBounds(12, 50, 100, 30);
        score[2].setBounds(350, 6, 100, 30);
        score[1].setBounds(700, 500, 100, 30);
        score[0].setBounds(350, 360, 100, 30);
        
//       
//        JCheckBox cbBackground = new JCheckBox("Background", true);
//        cbBackground.setBounds(gui.container.getX() + 10, gui.container.getY() + 20,
//                100, 20);
//        
//        URL path2 = getClass().getResource("52card/1169_Nature.jpg");
//        final JLabel bgr = new JLabel(new ImageIcon(path2));
//        bgr.setBounds(gui.container.getX(), gui.container.getX(), gui.container.getWidth(), gui.container.getHeight());
//        gui.container.add(bgr);
//        cbBackground.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                bgr.setIcon(null);
//                //gui.repaint();
//            }
//        });
//        gui.container.add(cbBackground, 0);

        gui.repaint();

        playercard0 = new ArrayList<JLabel>();
        playercard1 = new ArrayList<JLabel>();
        playercard2 = new ArrayList<JLabel>();
        playercard3 = new ArrayList<JLabel>();
    }

    // cho chon 3 la bai de trao doi
    protected void ReceiveExchange() {
        if (cardClicked != -1) { // click vao 1 la bai
            System.out.println("Click to card " + cardClicked);
            if (player[0].getThreeCard().contains(cardClicked)) {
                for (int i = 0; i < player[0].getThreeCard().size(); i++) {
                    if (player[0].getThreeCard().get(i) == cardClicked) {
                        player[0].getThreeCard().remove(i);
                        break;
                    }
                }
                setnormal(cardClicked);
            } else if (player[0].getThreeCard().size() != 3) {
                player[0].getThreeCard().add(cardClicked);
                sethightlight(cardClicked);
            }
            cardClicked = -1;
        }

        if (player[0].getThreeCard().size() == 3) {
            showbutton("Exchange");
            enableExchange();
        } else {
            disableButton();
        }
    }

    protected void nextturn() {
        currentTurn = (currentTurn + 1) % 4;

        System.out.println("Next turn : Wait for player " + (currentTurn + 1) + " play ....");
        if (currentTurn != 0) {
            this.notice("Wait for player " + (currentTurn + 1) + " play ....");
        } else {
            this.notice("Wait to you play ...");
        }
    }

    protected void ReceiveCardPlay() {
        if (player[0].isContainCard(cardClicked)) {
            this.notice("This is a help !!!");
            if (bChuaDi2chuon) {// neu quan 2 chuon chua dj
                if (cardClicked == GameDef.IS2CHUON) {
                    fourCard.add(player[0].playACard(cardClicked));
                    drawAllCard();
                    bChuaDi2chuon = false;

                    if (gameControl.getType() == GameDef.IS_SERVER) {
                        nextturn();
                        SendDataCardToClient();
                    } else if (gameControl.getType() == GameDef.IS_CLIENT) {
                        SendDataCardToServer();
                    } else {
                        nextturn();
                    }
                } else {
                    this.notice("Ban phai di 2 chuon dau tien !!!");
                }
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            }
        else if (fourCard.isEmpty()) { // server di truoc
            if ((Card.getType(cardClicked) == GameDef.CHAT_CO) && (!duocChonCo)) {
                this.notice("Ban khong duoc phep chon quan Co trong nuoc di nay (Heart haven't broken yet)");
            } else {
                if (cardClicked == GameDef.ISQBICH && (!duocChonCo)) {
                    this.notice("Ban khong duoc phep chon quan Q bich trong nuoc di nay (Heart haven't broken yet)");
                } else {
                    fourCard.add(player[0].playACard(cardClicked));
                    drawAllCard();

                    if (gameControl.getType() == GameDef.IS_SERVER) {
                        nextturn();
                        SendDataCardToClient();
                    } else if (gameControl.getType() == GameDef.IS_CLIENT) {
                        SendDataCardToServer();
                    } else // GameDef.IS_SINGLE
                    {
                        nextturn();
                    }

                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }
        } else if (fourCard.size() > 0) {// server di sau

            if (Card.dongChat(cardClicked, fourCard.get(0)) || (!player[0].checkAvableRank(fourCard.get(0)))) {

                fourCard.add(player[0].playACard(cardClicked));

                if (Card.getType(cardClicked) == GameDef.CHAT_CO) {
                    duocChonCo = true;
                    this.notice("Heart broken!!!!!");
                    System.out.println("Heart broken!!!!!");
                }
                drawAllCard();

                if (gameControl.getType() == GameDef.IS_SERVER) {
                    nextturn();
                    SendDataCardToClient();
                } else if (gameControl.getType() == GameDef.IS_CLIENT) {
                    SendDataCardToServer();
                } else {
                    nextturn();
                }
            }
            else
                this.notice("Ban phai di cung nuoc.");

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        } else {
            this.notice("Ban phai di cung chat voi la dau tien");
        }
    }
    }

    protected void ChangeCard(String mess) {
        // reset lai cac quan bai
        for (int n = 0; n < 4; n++) {
            player[n].getListCard().clear();
        }
        // doc du lieu tu goi tin nhan duoc
        String[] pcard = mess.split("card");
        String[][] mycard = new String[4][];
        for (int i = 1; i < pcard.length; i++) {
            mycard[i - 1] = pcard[i].split("c");
        }

        // cap nhat cac quan bai cho 4 nguoi choi
        int d = 0;
        for (int j = 0; j < player.length; j++) {
            int thutu = (gameControl.getViTri() + d) % 4;
            if (mycard[thutu] != null) {
                for (int m = 1; m < mycard[thutu].length; m++) {
                    int id = Integer.parseInt(mycard[thutu][m]);
                    player[j].receiveCard(id);
                }
            }
            d++;
        }
        drawAllCard();
    }

    protected void notice(String st) {
        note.setText(st);
    }

    protected void enableExchange() {
        btnCommand.setEnabled(true);
    }

    protected void disableButton() {
        btnCommand.setEnabled(false);
    }

    protected void updateScore() {
        System.out.println("Updating score...");
        for (int i = 0; i < 4; i++) {
            score[i].setText("Score: " + String.valueOf(player[i].getScore()));
        }
        gui.repaint();
    }

    private ArrayList<JLabel> loadCard(Player _player, boolean showcard) {
        ArrayList<JLabel> kq = new ArrayList<JLabel>();
        ImageIcon im;
        URL path;
        
        for (int i = 0; i < _player.getListCard().size(); i++) {
            if (showcard == true) {
                path = getClass().getResource("52card/" +_player.getIDCardAt(i) + ".jpg");
            } else {
                path = getClass().getResource("52card/0-1.jpg");
            }
            im = new ImageIcon(path);
            kq.add(new JLabel(im));
            if (showcard) {
                kq.get(i).setName(String.valueOf(_player.getIDCardAt(i)));
                kq.get(i).addMouseListener(new MouseListener() {

                    public void mouseClicked(MouseEvent e) {
                    }

                    public void mousePressed(MouseEvent e) {
                        cardClicked = Integer.parseInt(e.getComponent().getName());
                    }

                    public void mouseReleased(MouseEvent e) {
                    }

                    public void mouseEntered(MouseEvent e) {
                    }

                    public void mouseExited(MouseEvent e) {
                    }
                });

            }

        }
        return kq;
    }

    private ArrayList<JLabel> loadCard(ArrayList<Integer> fourcard) {
        ArrayList<JLabel> kq = new ArrayList<JLabel>();
        ImageIcon im;
        URL path;
        for (int i = 0; i < fourcard.size(); i++) {
            path = getClass().getResource("52card/" + fourcard.get(i) + ".jpg");
            im = new ImageIcon(path);
            kq.add(new JLabel(im));
        }
        return kq;
    }

    protected void drawCards(ArrayList<JLabel> jls, int position) {
        final int khoangcach = 20;
        if (!jls.isEmpty()) {
            int x;
            int y;
            switch (position) {
                case 1:
                    x = (GameDef.WIDTH - khoangcach * 12 - GameDef.CARD_WIDTH) / 2;
                    for (int i = 0; i < jls.size(); i++) {
                        if (jls.get(i) != null) {
                            jls.get(i).setBounds(x, 398, 100, 135);
                            x += khoangcach;
                            gui.container.add(jls.get(i), 6);
                        }
                    }
                    break;
                case 2:
                    y = (GameDef.HEIGHT - khoangcach * 12 - GameDef.CARD_HEIGHT) / 3;
                    for (int i = 0; i < jls.size(); i++) {
                        if (jls.get(i) != null) {
                            jls.get(i).setBounds(7, y + 30, 100, 135);
                            y += khoangcach;
                            gui.container.add(jls.get(i), 6);
                        }
                    }
                    break;
                case 3:
                    x = (GameDef.WIDTH - khoangcach * 12 - GameDef.CARD_WIDTH) / 2;
                    for (int i = 0; i < jls.size(); i++) {
                        if (jls.get(i) != null) {
                            jls.get(i).setBounds(x, 35, 100, 135);
                            x += khoangcach;
                            gui.container.add(jls.get(i), 6);
                        }
                    }
                    break;
                case 4:
                    y = (GameDef.HEIGHT - khoangcach * 12 - GameDef.CARD_HEIGHT) / 3;
                    for (int i = 0; i < jls.size(); i++) {
                        if (jls.get(i) != null) {
                            jls.get(i).setBounds(GameDef.WIDTH - GameDef.CARD_WIDTH - 15, y + 30, 100, 135);
                            y += khoangcach;
                            gui.container.add(jls.get(i), 6);
                        }
                    }
                    break;
                case 5:
                    x = (GameDef.WIDTH - khoangcach * fourCard.size() - GameDef.CARD_WIDTH) / 2;
                    for (int i = 0; i < jls.size(); i++) {
                        jls.get(i).setBounds(x, 220, 100, 135);
                        x += khoangcach;
                        gui.container.add(jls.get(i), 6);
                    }
                    break;
            }
        }
    }

    protected void clear4play() {
        while (gui.container.getComponentCount() > 6) {
            gui.container.remove(gui.container.getComponentCount() - 1);
        }
    }

    protected void DrawUpdateCard(int index) {
        switch (index) {
            case 1:
                playercard0 = loadCard(player[0], true);
                drawCards(playercard0, 1);
                break;
            case 2:
                playercard1 = loadCard(player[1], false);
                drawCards(playercard1, 2);
                break;
            case 3:
                playercard2 = loadCard(player[2], false);
                drawCards(playercard2, 3);
                break;
            case 4:
                playercard3 = loadCard(player[3], false);
                drawCards(playercard3, 4);
                break;
            case 5:
                game4card = loadCard(fourCard);
                drawCards(game4card, 5);
                break;
        }
    }

    public void drawAllCard() {
        clear4play();
        for (int i = 0; i <= 5; i++) {
            DrawUpdateCard(i);
        }
        gui.repaint();
    }

    protected void setnormal(int cardClicked) {
        for (int i = 0; i < playercard0.size(); i++) {
            if (playercard0.get(i).getName().equals(String.valueOf(cardClicked))) {
                playercard0.get(i).setEnabled(true);
            }
        }
    }

    protected void sethightlight(int cardClicked) {
        for (int i = 0; i < playercard0.size(); i++) {
            if (playercard0.get(i).getName().equals(String.valueOf(cardClicked))) {
                playercard0.get(i).setEnabled(false);
            }
        }
    }

    private void AceptExchange() {
        endExchange = true;
        this.btnCommand.setText("Wait for other player...");
        this.btnCommand.setBounds(310, 330, 160, 20);
        disableButton();

        if (gameControl.getType() == GameDef.IS_CLIENT) {
            String data = "exchange";
            for (int i = 0; i < 3; i++) {
                data += "c";
                data += player[0].getThreeCard().get(i);



            }
            gameControl.getClient().SendToServer(data);
        } else if (gameControl.getType() == GameDef.IS_SINGLE) {

            DoExchange();

        }
    }

    protected void FindFirstPlay() {
        for (int i = 0; i < 4; i++) {
            if (player[i].isContainCard(GameDef.IS2CHUON)) {
                firstturn = i;
                currentTurn = firstturn;
                break;
            }
        }
        if (firstturn == 0) {
            bChuaDi2chuon = true;
        } else {
            bChuaDi2chuon = false;
        }
        System.out.println("Player " + (firstturn + 1) + " play first !!!");

        if (firstturn == 0) {
            this.notice("You play first !");
        } else {
            this.notice("Player " + (firstturn + 1) + " play first !!!");
        }
    }

    protected void processEndRound() {
        shootTheMoon();
        playState = GameDef.GAME_PLAY_START;

        this.notice("You : " + player[0].getScore() + "   Player 2 : " + player[1].getScore()
                + "  Player 3 : " + player[2].getScore() + "  Player 4 :  " + player[3].getScore());

        if (check100score() == false) {
            showbutton("New Round");
            disableButton();
            newRound();
        } else {
            if (gameControl.getType() != GameDef.IS_CLIENT)
                showbutton("New Game");
            
            if (getMinScore() == 0) {
                notice("You WIN !!!");
            } else {
                notice("You LOSE !!!");
            }

            for (int i = 0; i < 4; i++) {
                player[i].resetScore();
            }
        }
        drawAllCard();
    }

    protected boolean check100score() {
        for (int i = 0; i < 4; i++) {
            if (player[i].getScore() >= 100) {
                return true;
            }
        }
        return false;
    }

    protected int getMinScore() {
        int min = player[0].getScore();
        int winplayer = 0;
        for (int i = 1; i < 4; i++) {
            if (player[i].getScore() < min) {
                min = player[i].getScore();
                winplayer = i;
            }
        }
        return winplayer;
    }

    protected void showbutton(String string) {
        btnCommand.setText(string);
        btnCommand.setEnabled(true);
    }

    protected void shootTheMoon() {
        if (player[0].checkShootTheMoon()) {
            player[1].addScore(26);
            player[2].addScore(26);
            player[3].addScore(26);
        } else {
            for (int i = 1; i < 4; i++) {
                if (player[i].checkShootTheMoon()) {
                    player[0].addScore(26);
                    for (int j = 0; j < 4; j++) {
                        if (j != i) {
                            player[j].addScore(26);
                        }
                    }
                }
            }
        }
    }

    // tra ve id cua quan bai thang trong 4 quan bai danh ra
    protected int check4cardwin() {
        int max = fourCard.get(0);
        for (int i = 1; i < fourCard.size(); i++) {
            if (Card.fisrtThan(fourCard.get(i), max)) {
                max = fourCard.get(i);
            }
        }
        return max;
    }

    // gui du lieu toi client
    protected void SendDataCardToClient() {
        System.out.println("Sending data to client...");
        // gui du lieu cho cac client
        String data = "";
        for (int i = 0; i < 4; i++) {
            data += "card";
            for (int j = 0; j < player[i].getListCard().size(); j++) {
                data += "c";
                data += this.player[i].getIDCardAt(j);
            }
        }
        gameControl.getServer().SendToAllClient(data);

        String fourData = "four";
        for (int i = 0; i < fourCard.size(); i++) {
            fourData += "c";
            fourData += fourCard.get(i);
        }
        gameControl.getServer().SendToAllClient(fourData);
    }

    private void SendDataCardToServer() {
        System.out.println("Sending data to server...");
        String cardData = "play" + gameControl.getViTri() + "play";
        for (int i = 0; i < player[0].getListCard().size(); i++) {
            cardData += "c";
            cardData += player[0].getIDCardAt(i);
        }
        gameControl.getClient().SendToServer(cardData);

        String fourData = "four";
        for (int i = 0; i < fourCard.size(); i++) {
            fourData += "c";
            fourData += fourCard.get(i);
        }
        gameControl.getClient().SendToServer(fourData);
    }

    protected void DoExchange() {
    }

    private void newRound() {
        numGame++;
        for (int i = 0; i < 4; i++) {
            player[i].newRound();
        }
        if ((numGame % 4) == 0) {
            playState = GameDef.GAME_PLAY_PLAYING;
            endExchange = true;
        } else {
            endExchange = false;
        }

        bChuaDi2chuon = false;
        duocChonCo = false;

    }
}
