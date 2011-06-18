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
public class GameStatePlay extends GameState{
    public JLabel[] score;
    public JLabel[][] playercard;
    public JLabel[] gamecard;
    public JButton btnExchange;
    public JLabel note;
    public Player[] player;
    public ArrayList<card> fourCard;
    public int cardClicked = -1;
    public int currentTurn = -1;
    public int playState = -1;
    public boolean change;
    public boolean endExchange = false;

    GameStatePlay(){

    }

    @Override
    public void Draw(){
        if (isEnter == false) {
            gui.container.removeAll();
            isEnter = true;
            
            // button Enchange
            btnExchange = new JButton("Exchange");
            btnExchange.setBounds(350, 330, 100, 20);
            btnExchange.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    GameStatePlay.this.btnExchange.setText("Wait for other player...");
                    GameStatePlay.this.btnExchange.setBounds(310, 330, 160, 20);
                    GameStatePlay.this.btnExchange.setEnabled(false);
                    GameStatePlay.this.AceptExchange();
                }
            });
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
                drawAllCard();
            }else if (playState == GameDef.GAME_PLAY_EXCHANGE){
                if (change ==true){
                   drawAllCard();
                   change = false;
                }                
            }else if (playState == GameDef.GAME_PLAY_PLAYING){
                btnExchange.setText("Playing...");
                if (change ==true){
                   drawAllCard();
                   change = false;
                }
            }else if (playState == GameDef.GAME_PLAY_END){
                if (change ==true){
                   drawAllCard();
                   change = false;
                }
            }                       
        }

    }

    @Override
    public void Update() {

    }

    @Override
    public void Enter(){
        isEnter = false;
        currentTurn = 0;
        playState = GameDef.GAME_PLAY_START;
        change =false;
    }


    // cho chon 3 la bai de trao doi
    protected void ReceiveExchange(){
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
            //System.out.println(cardClicked);
            //drawAllCard(gui);
        }

        if ( player[0].getThreeCard().size()==3){
                enableExchange();
            }else{
                disableExchange();
        }
    }
    
    protected void ChangeCard(String mess){
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

    protected void note(String st){
        note.setText(st);
    }

    protected void enableExchange(){
        btnExchange.setEnabled(true);
    }

    protected void disableExchange(){
        btnExchange.setEnabled(false);
    }

    protected void updateScore(){
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

    private void drawCards(JLabel jls[], int position){
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

    protected void clear4play(){
        while(gui.getComponentCount()>2){
            gui.remove(2);
        }
    }

    public void drawAllCard(){
        clear4play();
        playercard = new JLabel[4][];
        playercard[0] = loadCard(player[0],true);
        drawCards(playercard[0],1);
        for (int i=1; i<4; i++){
            playercard[i] = loadCard(player[i],false);
            drawCards(playercard[i],i+1);
        }
        gamecard = loadCard(fourCard);
        drawCards(gamecard,5);
        updateScore();
    }  

    private void setnormal(int cardClicked) {
        playercard[0][cardClicked].setEnabled(true);
    }

    private void sethightlight(int cardClicked) {
        playercard[0][cardClicked].setEnabled(false);
    }

    private void AceptExchange() {
        endExchange = true;
        
        if (gameControl.getType()==GameDef.IS_SERVER){

        }else if(gameControl.getType()==GameDef.IS_CLIENT){
            String data ="exchange";
            for(int i=0; i<3;i++){
                data +="c";
                data +=player[0].getThreeCard().get(i);
            }
            gameControl.getClient().SendToServer(data);
        }
    }
    
    // Server Methor
    
}
