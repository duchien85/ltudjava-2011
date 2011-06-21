/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package heartsgame;

import java.util.ArrayList;

/**
 *
 * @author kydrenw
 */
public class GameStatePlayClient extends GameStatePlay {

    public GameStatePlayClient(GameControl gControl, GUI g) {
        player = new Human[4];
        player[0] = new Human("Player1");
        player[1] = new Human("Player2");
        player[2] = new Human("Player3");
        player[3] = new Human("Player4");

        fourCard = new ArrayList<card>();
        this.gameControl = gControl;
        this.gui = g;
    }

    @Override
    public void Update(){
        if(playState == GameDef.GAME_PLAY_START){            
           
        }else if(playState == GameDef.GAME_PLAY_EXCHANGE){
            if (!endExchange)
                ReceiveExchange();
        }else if(playState == GameDef.GAME_PLAY_PLAYING){
            if (currentTurn == 0 && (cardClicked!=-1) ){
                ReceiveCardPlay();
                cardClicked = -1;
            }

        }else if(playState == GameDef.GAME_PLAY_END){
            

        }
    }

    @Override
    public void HaveMessage(String msg) {
        if (msg.startsWith("card")){
            if(playState == GameDef.GAME_PLAY_START){
                // chuyen trang thai
                System.out.println("Switch to Game Exchange !!! ");
                playState = GameDef.GAME_PLAY_EXCHANGE;
            }
            else if(playState == GameDef.GAME_PLAY_EXCHANGE){
                System.out.println("Switch to Game Playing !!! ");
                playState = GameDef.GAME_PLAY_PLAYING;
            }
            ChangeCard(msg);
        }
        else if (msg.startsWith("first")){            
                FindFirstPlay();            
        }
        else if (msg.startsWith("four")){// tin hieu 4 la bai danh ra
            fourCard.clear();
            if (playState==GameDef.GAME_PLAY_PLAYING){
                    if (msg.split("four").length>1){
                        String fCard = msg.split("four")[1];
                        String []idcard = fCard.split("c");
                        for(int i=1; i<idcard.length;i++){
                            card c = new card(Integer.parseInt(idcard[i]));
                            fourCard.add(c);
                        }
                    }
                    //drawAllCard();
                    DrawUpdateCard(5);

                    nextturn(); // qua luot cho nguoi ke tiep
                }
        }
    }

    private void SendDataToServer(){
        String cardData = "play" + gameControl.getViTri() + "play";
        for (int i=0; i<player[0].getHandcard().size();i++){
            cardData += "c";
            cardData += player[0].getHandCard(i).getID();
        }
        gameControl.getClient().SendToServer(cardData);

        String fourData = "four";
        for(int i=0; i<fourCard.size();i++){
            fourData += "c";
            fourData += fourCard.get(i).getID();
        }
        gameControl.getClient().SendToServer(fourData);
    }
    @Override
    public void nextturn(){       
        currentTurn = (currentTurn+1)%4;
        if (fourCard.size()==4){
                    checkEnd4Card();
        }
        System.out.println("Wait for player " + (currentTurn+1)+" play ....");
    }
    
    @Override
    public void ReceiveCardPlay(){
        if(have2chuon){// neu quan 2 chuon chua dj
            if (player[0].getHandCard(cardClicked).getID()==2){
                fourCard.add(player[0].playACard(cardClicked));
                have2chuon = false;
                //drawAllCard();
                DrawUpdateCard(1);
                DrawUpdateCard(5);
                SendDataToServer();
                nextturn();                
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                }     
            }
            else
                this.notice("Ban phai di 2 chuon dau tien !!!");
        }
        else if (fourCard.isEmpty()){ // client di truoc
            if((player[0].getHandCard(cardClicked).checkCo())&&(!duocChonCo)){
                this.notice("Ban khong duoc phep chon quan Co");
            }
            else{
                if (player[0].getHandCard(cardClicked).getID()==41)
                    this.notice("Ban phai di 2 chuon truoc");
                else{
                    fourCard.add(player[0].playACard(cardClicked));
                    //drawAllCard();
                    DrawUpdateCard(1);
                    DrawUpdateCard(5);
                    SendDataToServer();
                    nextturn();
                    try{
                        Thread.sleep(100);
                    }catch(Exception e){

                    }                    
                }
            }
        }
        else {// client di sau
            if((player[0].getHandCard(cardClicked).checkSameRank(fourCard.get(0)))||
            (!player[0].checkAvableRank(fourCard.get(0)))){
                fourCard.add(player[0].playACard(cardClicked));

                if (player[0].getHandCard(cardClicked).checkCo())
                    duocChonCo=true;
                //drawAllCard();
                DrawUpdateCard(1);
                DrawUpdateCard(5);
                SendDataToServer();                
                nextturn();
                
                try {
                    Thread.sleep(100);
                }catch(Exception e) {

                }                
            }
            else
                this.notice("Ban phai di cung chat voi la dau tien");
        }
    }
}
