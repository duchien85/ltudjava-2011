package heartsgame;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameStatePlaySingle extends GameStatePlay  {

    public static class ThreeCard {
        private int c1;
        private int c2;
        private int c3;

        public ThreeCard() {
            c1 = c2 = c3 = -1;
        }

        public ThreeCard(int playARandomCard, int playARandomCard0, int playARandomCard1) {
            c1 = playARandomCard;
            c2 = playARandomCard0;
            c3 = playARandomCard1;
        }

        /**
         * @return the c1
         */
        public int getC1() {
            return c1;
        }

        /**
         * @param c1 the c1 to set
         */
        public void setC1(int c1) {
            this.c1 = c1;
        }

        /**
         * @return the c2
         */
        public int getC2() {
            return c2;
        }

        /**
         * @param c2 the c2 to set
         */
        public void setC2(int c2) {
            this.c2 = c2;
        }

        /**
         * @return the c3
         */
        public int getC3() {
            return c3;
        }

        /**
         * @param c3 the c3 to set
         */
        public void setC3(int c3) {
            this.c3 = c3;
        }
    }
    
    private ArrayList<Integer> threeCard;
//    private int[] SignalFlag; 
    private int delay = 100;
    private ArrayList<ThreeCard> lstThreeCards;

    public GameStatePlaySingle(GameControl gControl, GUI g){
        
        
        player = new Player[4];
        player[0] = new Player("You");
        player[1] = new Player("Player 2");
        player[2] = new Player("Player 3");
        player[3] = new Player("Player 4");
        fourCard = new ArrayList<Integer>();
        this.gameControl = gControl;
        this.gui = g;
        
    }

    
    @Override
    public void Update(){
        if(playState == GameDef.GAME_PLAY_START){            
            divideCard();
            System.out.println("Switch to Game Exchange !!! ");
            if (stateCount % 4 != 3)
                playState = GameDef.GAME_PLAY_EXCHANGE;
            else {
                endExchange = true;
                playState = GameDef.GAME_PLAY_PLAYING;
            }
        }

        else if(playState == GameDef.GAME_PLAY_EXCHANGE){
            // cho nguoi choi doi bai
            if (!endExchange)
                ReceiveExchange();
           
        } else if (playState == GameDef.GAME_PLAY_PLAYING) {
            if (cardClicked != -1) {
                if (currentTurn == 0)
                    ReceiveCardPlay();
                cardClicked = -1;
            }
            checkEnd4Card();
        }

        else if(playState == GameDef.GAME_PLAY_END){
            //System.out.println("In Game END !!! ");

        }
    }
  
    @Override
  public void ReceiveCardPlay() {
        super.ReceiveCardPlay(); 
        checkEnd4Card();
        computerPlay();
    }
    
    private boolean checkEnd4Card() {
        if (fourCard.size() == 4) {
            System.out.println("Checking end 4 card ...");
            int winCard = check4cardwin();
            for (int i = 0; i < 4; i++) {
                if (fourCard.get(i) == winCard) {
                    firstturn = (firstturn + i) % 4;
                    player[firstturn].add4scorecard(fourCard);
                    updateScore();
                    currentTurn = firstturn;
                    if (firstturn == 0) {
                        this.notice("Wait for you play ...");
                    } else {
                        this.notice("Wait for player " + (firstturn + 1) + " play ...");
                    }
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            }catch (Exception ex){}
            roundcount++;
            fourCard.clear();
            drawAllCard();
            if (roundcount == 13) {
                processEndRound();
                btnCommand.setVisible(true);
                btnCommand.setEnabled(true);
                btnCommand.setText("Exchange");
                stateCount++;
            }
            
            return true;
        }
        return false;
    }


 

    public void computerPlay() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
           
        }
        while ((currentTurn != 0) && (playState == GameDef.GAME_PLAY_PLAYING)) {
            if (currentTurn == firstturn) {
                if (roundcount != 0) {
                    System.out.println("Player[" + currentTurn + "] dsBai: " + player[currentTurn].getListCard());
                    fourCard.add(player[currentTurn].playfirst(duocChonCo));
                } else {
                    fourCard.add(player[currentTurn].play2chuon());
                }
            } else {
                fourCard.add(player[currentTurn].playfollow(fourCard.get(0)));
                
                // Neu nguoi choi di dau tien di quan ko phai la quan Co, ma nhung nguoi sau do lai di quan co (do bai nguoi do da het nuoc)
                if (Card.getType(fourCard.get(0)) != GameDef.CHAT_CO
                        && Card.getType(fourCard.get(fourCard.size() - 1)) == GameDef.CHAT_CO) {
                    duocChonCo = true;
                    this.notice("Heart broken!!!!!");
                    System.out.println("Heart broken!!!!!");
                }
            }
            drawAllCard();
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
            }
           if (!checkEnd4Card())
               nextturn();
           System.out.println("Current turn: " + currentTurn + "; Player[" + currentTurn +"] dsBai = " + player[currentTurn].getListCard().size());
           if (player[currentTurn].getListCard().isEmpty())
               break;
           
        }
    }
    
      
    @Override
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

    @Override
    // Hoan doi bai
    protected void DoExchange() {
        if ((stateCount % 4) != 3) { // 
            threeCard = player[0].getThreeCard();
            
            // Luu mot mang cacs phan tu ThreeCard (1 ThreeCard se chua 3 la bai) cua computer1, com2, com3
            // Index 0: player[0]
            // Index 1: player[1]
            // Index 2: player[2]
            // Index 2: player[3]            
            lstThreeCards = new ArrayList<ThreeCard>();
            lstThreeCards.add(player[0].playThreeCard());
            for (int i = 1; i < 4; ++i) {
                // Tao doi tuong chua 3 la bai 
                ThreeCard _3cards = new ThreeCard(player[i].playARandomCard(), player[i].playARandomCard(), player[i].playARandomCard());
                lstThreeCards.add(_3cards);
            }

            switch (roundcount % 4) {
                // Vong 1:
                case 0:
                    
                    //  player[0] doi bai voi player[1]
                    player[0].receiveCard(lstThreeCards.get(1).getC1());
                    player[0].receiveCard(lstThreeCards.get(1).getC2());
                    player[0].receiveCard(lstThreeCards.get(1).getC3());
                    player[1].receiveCard(lstThreeCards.get(0).getC1());
                    player[1].receiveCard(lstThreeCards.get(0).getC2());
                    player[1].receiveCard(lstThreeCards.get(0).getC3());
                    
                     // player[2] doi bai voi player[3]
                    player[2].receiveCard(lstThreeCards.get(3).getC1());
                    player[2].receiveCard(lstThreeCards.get(3).getC2());
                    player[2].receiveCard(lstThreeCards.get(3).getC3());
                    player[3].receiveCard(lstThreeCards.get(2).getC1());
                    player[3].receiveCard(lstThreeCards.get(2).getC2());
                    player[3].receiveCard(lstThreeCards.get(2).getC3());
                    break;

                case 1:
                      //  player[0] doi bai voi player[3]
                    player[0].receiveCard(lstThreeCards.get(3).getC1());
                    player[0].receiveCard(lstThreeCards.get(3).getC2());
                    player[0].receiveCard(lstThreeCards.get(3).getC3());
                    player[3].receiveCard(lstThreeCards.get(0).getC1());
                    player[3].receiveCard(lstThreeCards.get(0).getC2());
                    player[3].receiveCard(lstThreeCards.get(0).getC3());
                    
                     // player[1] doi bai voi player[2]
                    player[1].receiveCard(lstThreeCards.get(2).getC1());
                    player[1].receiveCard(lstThreeCards.get(2).getC2());
                    player[1].receiveCard(lstThreeCards.get(2).getC3());
                    player[2].receiveCard(lstThreeCards.get(1).getC1());
                    player[2].receiveCard(lstThreeCards.get(1).getC2());
                    player[2].receiveCard(lstThreeCards.get(1).getC3());
                    break;

                case 2:
                      //  player[0] doi bai voi player[2]
                    player[0].receiveCard(lstThreeCards.get(2).getC1());
                    player[0].receiveCard(lstThreeCards.get(2).getC2());
                    player[0].receiveCard(lstThreeCards.get(2).getC3());
                    player[2].receiveCard(lstThreeCards.get(0).getC1());
                    player[2].receiveCard(lstThreeCards.get(0).getC2());
                    player[2].receiveCard(lstThreeCards.get(0).getC3());
                    
                     // player[1] doi bai voi player[3]
                    player[1].receiveCard(lstThreeCards.get(3).getC1());
                    player[1].receiveCard(lstThreeCards.get(3).getC2());
                    player[1].receiveCard(lstThreeCards.get(3).getC3());
                    player[3].receiveCard(lstThreeCards.get(1).getC1());
                    player[3].receiveCard(lstThreeCards.get(1).getC2());
                    player[3].receiveCard(lstThreeCards.get(1).getC3());
                    break;

                case 3:
                    // Khong doi
                    break;
            }

            threeCard.clear();
            player[0].sort();
            for (int i = 0; i <= 2; i++) {
                player[i + 1].sort();
            }

        }
        FindFirstPlay();
        if (currentTurn != 0) {
            // Computer di 2 chuon
            fourCard.add(player[currentTurn].play2chuon());
            drawAllCard();
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
            }
            bChuaDi2chuon = false;
            nextturn();

        }
        drawAllCard();
        btnCommand.setVisible(false);
        playState = GameDef.GAME_PLAY_PLAYING;
        computerPlay();
    }


    private void divideCard() {
        // khoi tao mang cac quan bai gia tri ban dau la 0
        
        int[] ddau = new int[53];
        for (int i = 1; i < 53; i++) {
            ddau[i] = 0;
        }
        // tao ngau nhien mang tu 1-53
        SecureRandom numGenerate = new SecureRandom();
        int tam = numGenerate.nextInt(53);
        int p = 0;
        btnCommand.setVisible(false);
        for (int i = 1; i < 53; i++) {
            while ((tam == 0) || (ddau[tam] == 1)) {
                tam = numGenerate.nextInt(53);
            }
            // danh dau la da co
            ddau[tam] = 1;

            // chia bai cho  nguoi choi
            
            try {
                player[p].receiveCard(tam);
                p = (p + 1) % 4;
            } catch (Exception e) {
            }
            drawAllCard();
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
        }
        for (int i = 0; i < 4; i++) {
            player[i].sort();
        }
        drawAllCard();
        btnCommand.setVisible(true);

        System.out.println("Finished dicide card !!! ");
    }



    public void newRound() {
        for(int i = 0; i < 4; ++i)
            player[i].newRound();
        divideCard();
        for(int i = 0; i < 4; ++i)
            player[i].sort();
//        is2bichplayed = false;
        duocChonCo = false;
//        currentstep = 1;
        roundcount = 0;
        showbutton("Exchange");
        drawAllCard();
    }

    public void newGame() {
        for (int i = 0 ; i < 4; ++i)
            player[i].resetScore();;
        fourCard.clear();
        newRound();
    }

}
