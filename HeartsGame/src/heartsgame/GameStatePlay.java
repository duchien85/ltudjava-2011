/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package heartsgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author kydrenw
 */
public class GameStatePlay extends GameState{
    private JLabel[] score;
    private JLabel[][] playercard;
    private JLabel[] gamecard;
    private JButton btnExchange;
    private JLabel note;
    private Player[] player;
    private ArrayList<card> fourCard;
    private ArrayList<Integer> threeCard;
    private int cardClicked = -1;
    private int currentTurn = -1;
    private int playState = -1;

    private int startReceiveCard = -1;
    private boolean change;
    
    GameStatePlay(){
        isEnter = false;

        player = new Human[4];
        player[0] = new Human("Player1");
        player[1] = new Human("Player2");
        player[2] = new Human("Player3");
        player[3] = new Human("Player4");

        fourCard = new ArrayList<card>();
        threeCard = new ArrayList<Integer>();
    }
    @Override
    public void Draw(final GameControl gameControl, final GUI gui){
        if (isEnter == false) {
            gui.container.removeAll();
            isEnter = true;
            
            // button Enchange
            btnExchange = new JButton("Exchange");
            btnExchange.setBounds(350, 330, 100, 20);
            btnExchange.setEnabled(false);
            gui.add(btnExchange);

            // hien diem so
            Font f = new Font(Font.SANS_SERIF,Font.TRUETYPE_FONT,20);
            score = new JLabel[4];
            for (int i=0; i <=3 ; i++){
                score[i] = new JLabel();
                score[i].setFont(f);;
                gui.add(score[i]);
            }
            score[0].setBounds(120, 470, 100, 30);
            score[1].setBounds(10, 40, 100, 30);
            score[2].setBounds(580, 7, 100, 30);
            score[3].setBounds(690, 500, 100, 30);

            // tao note o duoi
            note = new JLabel("This is the help !!!");
            JPanel pn = new JPanel();
            pn.add(note);
            pn.setBackground(Color.LIGHT_GRAY);
            pn.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            pn.setBounds(0, 538, GameDef.WIDTH, 30);
            gui.add(pn);

            gui.repaint();
        }
        else
        {
            if (playState == GameDef.GAME_PLAY_START){
                drawAllCard(gui);
            }else if (playState == GameDef.GAME_PLAY_EXCHANGE){
                if (change ==true){
                   drawAllCard(gui);
                   change = false;
                }

                
            }else if (playState == GameDef.GAME_PLAY_PLAYING){
                drawAllCard(gui);
            }else if (playState == GameDef.GAME_PLAY_END){
                drawAllCard(gui);
            }                       
        }

    }

    @Override
    void Update(final GameControl gameControl,final GUI gui) {
        if (gameControl.getType() == GameDef.IS_SERVER){
            UpdateServer(gameControl,gui);
        }
        else if (gameControl.getType() == GameDef.IS_CLIENT){
            UpdateClient(gameControl,gui);
        }
    }

    @Override
    void Enter(){
        isEnter = false;
        currentTurn = 0;
        playState = GameDef.GAME_PLAY_START;
        change =false;
    }

    void UpdateServer(final GameControl gameControl,final GUI gui){
        if(playState == GameDef.GAME_PLAY_START){
            System.out.println("In Game START !!! ");
            divideCard(gameControl, gui);
            change = true;
            SendDataToClient(gameControl);
            System.out.println("Switch to Game Exchange !!! ");
            playState = GameDef.GAME_PLAY_EXCHANGE;
        }else if(playState == GameDef.GAME_PLAY_EXCHANGE){
            //System.out.println("In Game Exchange !!! ");
            if (change == true){
                SendDataToClient(gameControl);
            }

            ReceiveExchange(gui);
            
        }else if(playState == GameDef.GAME_PLAY_PLAYING){
            //System.out.println("In Game PLAYING !!! ");
            
        }else if(playState == GameDef.GAME_PLAY_END){
            //System.out.println("In Game END !!! ");

        }
    }

    void ReceiveExchange(final GUI gui){

        if (cardClicked != -1) { // click vao 1 la bai
            System.out.println(cardClicked);
            if (threeCard.contains(cardClicked)) {
                for (int i = 0; i < threeCard.size(); i++) {
                    if (threeCard.get(i) == cardClicked) {
                        threeCard.remove(i);
                        break;
                    }
                }
                setnormal(cardClicked);
            } else if (threeCard.size() != 3) {
                threeCard.add(cardClicked);
                sethightlight(cardClicked);
            }
            cardClicked = -1;
            System.out.println(cardClicked);
            //drawAllCard(gui);
        }

        if (threeCard.size()==3){
                enableExchange();
            }else{
                disableExchange();
        }
    }
    void SendDataToClient(final GameControl gameControl){
        // gui du lieu cho cac client
            String data = "";
            for (int i =0; i<4;i++){
                data +="card";
                for (int j=1;j<=14;j++){
                    if (this.player[i].getHandCard(j) != null) {
                        data += "c";
                        data += this.player[i].getHandCard(j).getID();
                    }
                }
            }
            gameControl.getServer().SendToAllClient(data);
    }
    void UpdateClient(final GameControl gameControl,final GUI gui){
        if(playState == GameDef.GAME_PLAY_START){
            String mess = gameControl.getClient().GetMessage();
            if (mess.startsWith("card")) {
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
                        drawAllCard(gui);
                    }
                    d++;
                }
                // ve cac quan bai ra
                //drawAllCard(gui);
                change = true;
                System.out.println("Switch to Game Exchange !!! ");
                playState = GameDef.GAME_PLAY_EXCHANGE;
            }
        }else if(playState == GameDef.GAME_PLAY_EXCHANGE){
            //System.out.println("In Game Exchange !!! ");
            ReceiveExchange(gui);
        }else if(playState == GameDef.GAME_PLAY_PLAYING){
            //System.out.println("In Game PLAYING !!! ");

        }else if(playState == GameDef.GAME_PLAY_END){
            //System.out.println("In Game END !!! ");

        }
    }
    public void note(String st){
        note.setText(st);
    }

    public void enableExchange(){
        btnExchange.setEnabled(true);
    }

    public void disableExchange(){
        btnExchange.setEnabled(false);
    }

    public void updateScore(){
        for(int i=0; i<4;i++){
             score[i].setText("Score: " + String.valueOf(player[i].getScore()));
        }
    }

    private JLabel[]loadCard(Player _player, boolean showcard){
        JLabel[]kq = new JLabel[13];
        ImageIcon im;
        String path;
        for (int i=0; i< 13;i++){
            if (_player.getHandCard(i+1)!=null){
                if(showcard == true){
                    path = "52card\\"+ _player.getHandCard(i+1).getID()+".jpg";
                }else {
                    path = "52card\\0-2.jpg";
                }
                im = new ImageIcon(path);
                kq[i] = new JLabel(im);
                if (showcard){
                    kq[i].setName(String.valueOf(i));
                    kq[i].addMouseListener(new MouseListener() {
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

    private JLabel[]loadCard(ArrayList<card> fourcard){
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

    private void drawCards(final GUI gui,JLabel jls[], int position){
        final int khoangcach = 20;
        int x;
        int y;
        switch(position){
            case 1:
                x = (GameDef.WIDTH - khoangcach*12 - GameDef.CARD_WIDTH)/2;
                for (int i = 0; i < 13; i++) {
                    if (jls[i] != null) {
                        jls[i].setBounds(x, 398, 100, 135);
                        x += khoangcach;
                        gui.add(jls[i], 3);
                    }
                }
                break;
            case 3:
                x = (GameDef.WIDTH - khoangcach * 12 - GameDef.CARD_WIDTH) / 2;
                for (int i = 0; i < 13; i++) {
                    if (jls[i] != null) {
                        jls[i].setBounds(x, 35, 100, 135);
                        x += khoangcach;
                        gui.add(jls[i], 3);
                    }
                }
                break;
            case 2:
                y = (GameDef.HEIGHT - khoangcach * 12 - GameDef.CARD_HEIGHT) / 3;
                for (int i = 0; i < 13; i++) {
                    if (jls[i] != null) {
                        jls[i].setBounds(7, y + 30, 100, 135);
                        y += khoangcach;
                        gui.add(jls[i], 3);
                    }
                }
                break;
            case 4:
                y = (GameDef.HEIGHT - khoangcach * 12 - GameDef.CARD_HEIGHT) / 3;
                for (int i = 0; i < 13; i++) {
                    if (jls[i] != null) {
                        jls[i].setBounds(GameDef.WIDTH - GameDef.CARD_WIDTH - 15, y + 30, 100, 135);
                        y += khoangcach;
                        gui.add(jls[i], 3);
                    }
                }
                break;
            case 5:
                x = (GameDef.WIDTH - khoangcach * fourCard.size() - GameDef.CARD_WIDTH) / 2;
                for (int i = 0; i < fourCard.size(); i++) {
                    jls[i].setBounds(x, 220, 100, 135);
                    x += khoangcach;
                    gui.add(jls[i], 3);
                }
                break;

        }
        gui.repaint();
    }

    private void clear4play(final GUI gui){
        while(gui.getComponentCount()>2){
            gui.remove(2);
        }
    }

    private void drawAllCard(final GUI gui){
        clear4play(gui);
        playercard = new JLabel[4][];
        playercard[0] = loadCard(player[0],true);
        drawCards(gui,playercard[0],1);
        for (int i=1; i<4; i++){
            playercard[i] = loadCard(player[i],false);
            drawCards(gui,playercard[i],i+1);
        }
        gamecard = loadCard(fourCard);
        drawCards(gui,gamecard,5);
        updateScore();
    }

    // chia bai
    public void divideCard(final GameControl gameControl,final GUI gui) {
        // khoi tao mang cac quan bai gia tri ban dau la 0
        int[] ddau = new int[53];
        for (int i = 1; i < 53; i++) {
            ddau[i] = 0;
        }
        // tao ngau nhien mang tu 1-53
        SecureRandom numGenerate = new SecureRandom();
        int tam = numGenerate.nextInt(53);
        for (int i = 1; i < 53; i++) {
            while ((tam == 0) || (ddau[tam] == 1)) {
                tam = numGenerate.nextInt(53);
            }
            // danh dau la da co
            ddau[tam] = 1;

            // chia bai cho  nguoi choi
            card c = new card(tam);
            int p = (i - 1) % 4;
            try {
                player[p].receiveCard(c);
            } catch (Exception e) {
            }
            drawAllCard(gui);
            try {
                gameControl.getThread().sleep(10);
            } catch (Exception e) {
            }
        } 
    }

    private void setnormal(int cardClicked) {
        playercard[0][cardClicked].setEnabled(true);
    }

    private void sethightlight(int cardClicked) {
        playercard[0][cardClicked].setEnabled(false);
    }
}
