/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.security.SecureRandom;
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
    protected ArrayList<card> fourCard;
    protected int cardClicked = -1;
    protected int currentTurn = -1;
    protected int firstturn = -1;
    protected int playState = -1;
    protected boolean endExchange = false;
    protected boolean have2chuon = false;
    protected boolean duocChonCo = false;
    protected int roundcount = 0;
    JButton btnRemoveGUI;
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
        gui.container.add(btnCommand);

        // hien diem so
        Font f = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 20);
        score = new JLabel[4];
        for (int i = 0; i <= 3; i++) {
            score[i] = new JLabel();
            score[i].setFont(f);
            gui.container.add(score[i]);
        }
        score[0].setBounds(120, 470, 100, 30);
        score[1].setBounds(10, 40, 100, 30);
        score[2].setBounds(580, 7, 100, 30);
        score[3].setBounds(690, 500, 100, 30);

        // tao notice o duoi
        note = new JLabel("This is the help !!!");
        JPanel pn = new JPanel();
        pn.add(note);
        pn.setBackground(Color.LIGHT_GRAY);
        pn.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        pn.setBounds(0, 538, GameDef.WIDTH, 30);
        gui.container.add(pn);
        gui.repaint();
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
            enableExchange();
        } else {
            disableButton();
        }
    }

    protected void nextturn() {
        currentTurn = (currentTurn + 1) % 4;
    }

    protected void ReceiveCardPlay() {
    }

    protected void ChangeCard(String mess) {
        // reset lai cac quan bai
        for (int n = 0; n < 4; n++) {
            player[n].newRound();
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
            for (int m = 1; m < mycard[thutu].length; m++) {
                int id = Integer.parseInt(mycard[thutu][m]);
                card c = new card(id);
                player[j].receiveCard(c);
                drawAllCard();
            }
            d++;
        }
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
        for (int i = 0; i < 4; i++) {
            score[i].setText("Score: " + String.valueOf(player[i].getScore()));
        }
    }

    private ArrayList<JLabel> loadCard(Player _player, boolean showcard) {
        ArrayList<JLabel> kq = new ArrayList<JLabel>();
        ImageIcon im;
        String path;
        for (int i = 0; i < _player.getHandcard().size(); i++) {
            if (_player.getHandCard(i) != null) {
                if (showcard == true) {
                    path = "52card\\" + _player.getHandCard(i).getID() + ".jpg";
                } else {
                    path = "52card\\0-2.jpg";
                }
                im = new ImageIcon(path);
                kq.add(new JLabel(im));
                if (showcard) {
                    kq.get(i).setName(String.valueOf(i));
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
        }
        return kq;
    }

    private ArrayList<JLabel> loadCard(ArrayList<card> fourcard) {
        ArrayList<JLabel> kq = new ArrayList<JLabel>();
        ImageIcon im;
        String path;
        for (int i = 0; i < fourcard.size(); i++) {
            path = "52card\\" + fourcard.get(i).getID() + ".jpg";
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
                            gui.container.add(jls.get(i),2);
                        }
                    }
                    break;
                case 3:
                    x = (GameDef.WIDTH - khoangcach * 12 - GameDef.CARD_WIDTH) / 2;
                    for (int i = 0; i < jls.size(); i++) {
                        if (jls.get(i) != null) {
                            jls.get(i).setBounds(x, 35, 100, 135);
                            x += khoangcach;
                            gui.container.add(jls.get(i),2);
                        }
                    }
                    break;
                case 2:
                    y = (GameDef.HEIGHT - khoangcach * 12 - GameDef.CARD_HEIGHT) / 3;
                    for (int i = 0; i < jls.size(); i++) {
                        if (jls.get(i) != null) {
                            jls.get(i).setBounds(7, y + 30, 100, 135);
                            y += khoangcach;
                            gui.container.add(jls.get(i),2);
                        }
                    }
                    break;
                case 4:
                    y = (GameDef.HEIGHT - khoangcach * 12 - GameDef.CARD_HEIGHT) / 3;
                    for (int i = 0; i < jls.size(); i++) {
                        if (jls.get(i) != null) {
                            jls.get(i).setBounds(GameDef.WIDTH - GameDef.CARD_WIDTH - 15, y + 30, 100, 135);
                            y += khoangcach;
                            gui.container.add(jls.get(i),2);
                        }
                    }
                    break;
                case 5:
                    x = (GameDef.WIDTH - khoangcach * fourCard.size() - GameDef.CARD_WIDTH) / 2;
                    for (int i = 0; i < jls.size(); i++) {
                        jls.get(i).setBounds(x, 220, 100, 135);
                        x += khoangcach;
                        gui.container.add(jls.get(i),2);
                    }
                    break;

            }
            gui.Paint(position);
        }
    }

    protected void clear4play() {
        while (gui.getComponentCount() > 2) {
            gui.remove(2);
        }
        //gui.container.removeAll();
    }

    protected void DrawUpdateCard(int index){
        switch(index){
            case 1:
                playercard0= new ArrayList<JLabel>();
                playercard0 = loadCard(player[0], true);
                drawCards(playercard0, 1);
                break;
            case 2:
                playercard1= new ArrayList<JLabel>();
                playercard1 = loadCard(player[1], false);
                drawCards(playercard1, 2);
                break;
            case 3:
                playercard2= new ArrayList<JLabel>();
                playercard2 = loadCard(player[2], false);
                drawCards(playercard2, 3);
                break;
            case 4:
                playercard3 = new ArrayList<JLabel>();
                playercard3 = loadCard(player[3], false);
                drawCards(playercard3, 4);
                break;
            case 5:
                game4card = new ArrayList<JLabel>();
                game4card = loadCard(fourCard);
                drawCards(game4card, 5);
                break;
        }
    }
    public void drawAllCard() {
        clear4play();
        for (int i=1;i<=5;i++){
            DrawUpdateCard(i);
        }
        updateScore();
    }

    protected void setnormal(int cardClicked) {
        playercard0.get(cardClicked).setEnabled(true);
    }

    protected void sethightlight(int cardClicked) {
        playercard0.get(cardClicked).setEnabled(false);
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
        }
    }

    protected void FindFirstPlay() {
        for (int i = 0; i < 4; i++) {
            if (player[i].isContainCard(2)) {
                firstturn = i;
                currentTurn = firstturn;
                break;
            }
        }
        if(firstturn==0)
            have2chuon = true;
        else
            have2chuon= false;
        System.out.println("Player " + (firstturn + 1) + " play first !!!");
    }

    protected void checkEnd4Card() {
        if (fourCard.size() == 4) {
            card winCard = check4cardwin();
            for (int i = 0; i < 4; i++) {
                if (player[i].isContainCard(winCard.getID())) {
                    player[i].add4scorecard(fourCard);
                    firstturn = i;
                    currentTurn = firstturn;
                    break;
                }
            }

            drawAllCard();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            roundcount++;
            fourCard.clear();
            drawAllCard();
            if (roundcount == 13) {
                processEndRound();
            }
        }
    }

    protected void processEndRound() {
        shootTheMoon();
        playState = GameDef.GAME_PLAY_START;

        this.notice("You : " + player[0].getScore() + "   Player 2 : " + player[1].getScore()
                + "  Player 3 : " + player[2].getScore() + "  Player 4 :  " + player[3].getScore());

        if (check100score() == false) {
            showbutton("New Round");
            //drawAllCard();
        } else {
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

    protected card check4cardwin() {
        card max = fourCard.get(0);
        for (int i = 0; i < fourCard.size(); i++) {
            if (fourCard.get(i).greaterThan(max)) {
                max = fourCard.get(i);
            }
        }
        return max;
    }
}
