package heartsgame;


import java.security.SecureRandom;
import java.util.ArrayList;

public class GameStatePlaySingle extends GameStatePlay  {
    
    private ArrayList<Integer> threeCard;
//    private int[] SignalFlag; 
    private int delay = 100;

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
            btnCommand.setEnabled(false);
            divideCard();
//            SendDataCardToClient();
            System.out.println("Switch to Game Exchange !!! ");
            playState = GameDef.GAME_PLAY_EXCHANGE;
        }

        else if(playState == GameDef.GAME_PLAY_EXCHANGE){
            // cho nguoi choi doi bai
            if (!endExchange)
                ReceiveExchange();
           
        } else if (playState == GameDef.GAME_PLAY_PLAYING) {
            if (currentTurn == 0 && cardClicked != -1) {
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
            roundcount++;
            fourCard.clear();
            drawAllCard();
            if (roundcount == 13) {
                processEndRound();
                btnCommand.setVisible(true);
                btnCommand.setEnabled(true);
                btnCommand.setText("Exchange");
            }
            
            return true;
        }
        return false;
    }


 

    public void computerPlay() {
        while ((currentTurn != 0) && (playState == GameDef.GAME_PLAY_PLAYING)) {
            if (currentTurn == firstturn) {
                fourCard.add(player[currentTurn].playfirst(duocChonCo));
            } else {
                fourCard.add(player[currentTurn].playfollow(fourCard.get(0)));
                
                // Neu nguoi choi di dau tien di quan ko phai la quan Co, ma nhung nguoi sau do lai di quan co (do bai nguoi do da het nuoc)
                if (Card.getType(fourCard.get(0)) != GameDef.CHAT_CO
                        && Card.getType(fourCard.get(fourCard.size() - 1)) == GameDef.CHAT_CO) {
                    duocChonCo = true;
                }
            }
            drawAllCard();
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
            }
           if (!checkEnd4Card())
               nextturn();
           
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
            enableExchange();
        } else {
            disableButton();
        }
    }

    @Override
    // Hoan doi bai
    protected void DoExchange() {

        threeCard = player[0].getThreeCard();
        int card1, card2;
        for (int i = 0; i <= 2; i++) {
            card1 = player[0].playACard(threeCard.get(i));
            card2 = player[i + 1].playARandomCard();
            player[0].receiveCard(card2);
            player[i + 1].receiveCard(card1);
        }
        threeCard.clear();
        player[0].sort();
        for (int i = 0; i <= 2; i++) {
            player[i + 1].sort();
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

//    public static void main(String[] args) {
////        GameStatePlaySingle gameE = new GameStatePlaySingle("hehe");
////        gameE.newRound();
//    }
}
