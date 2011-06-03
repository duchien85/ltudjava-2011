
import java.security.SecureRandom;
import java.util.ArrayList;



public class Main implements Runnable{
	private int currentstep;
	private int currentturn,firstturn,roundcount;
	private human player1;
	private computer com1,com2,com3;
	private computer[] com; 
	private giaodien gdien;
	
	private ArrayList<card> fourCard;
	private ArrayList<Integer> threeCard;
	private boolean is2bichplayed,duocChonCo;
	private int[] SignalFlag; //0:card 12:control
	
	private int delay=500;
	public Main(String st){
		player1=new human();
		player1.setName(st);
		com1=new computer();
		com2=new computer();
		com3=new computer();
		com=new computer[]{com1,com2,com3};
		SignalFlag=new int[2];
		SignalFlag[0]=-1;		
		SignalFlag[1]=0;
		fourCard=new ArrayList<card>();
		threeCard=new ArrayList<Integer>();
		gdien=new giaodien(this);
		Thread xuly=new Thread(this);
		xuly.start();
	}
	
	public player getPlayer(int index) {
		switch (index) {
		case 1:return player1;
		case 2: case 3: case 4: return com[index-2];
		default: return null;
		}
	}
	public ArrayList<card> getFourCard(){
		return fourCard;
	}
	public void updateDelay(int time) {
		delay=time;
	}
	public int[] getSignal() {
		return SignalFlag;
	}
	public void divideCard(){
		int[] ddau=new int[53];
		for (int i=1;i<53;i++)
			ddau[i]=0;
		SecureRandom numGenerate=new SecureRandom();
		int tam=numGenerate.nextInt(53);
		for (int i=1;i<53;i++)
		{
			while ((tam==0)||(ddau[tam]==1)){
				tam=numGenerate.nextInt(53);
			}			
			ddau[tam]=1;
			card c=new card(tam);
			switch (i%4) {
				case 1:
					player1.receiveCard(c);
					gdien.drawAllCard();
					try {
						Thread.sleep(delay/10);
					}
					catch (Exception e) {
					}
					break;
				case 2:
					com1.receiveCard(c);
					gdien.drawAllCard();
					try {
						Thread.sleep(delay/10);
					}
					catch (Exception e) {
					}
					break;
				case 3:
					com2.receiveCard(c);
					gdien.drawAllCard();
					try {
						Thread.sleep(delay/10);
					}
					catch (Exception e) {
					}
					break;
				case 0:
					com3.receiveCard(c);
					gdien.drawAllCard();
					try {
						Thread.sleep(delay/10);
					}
					catch (Exception e) {
					}
			}
		}		
	}
	
	public void notice(String st){
		gdien.notice(st);
	}
		
	
	public card check4cardwin(){		
		card max=fourCard.get(0);		
		for (int i=1;i<=3;i++){
			if (fourCard.get(i).greaterThan(max)) max=fourCard.get(i);			
		}
		return max;		
	}
	
	
	public void shootTheMoon(){
		if (player1.checkShootTheMoon()){
			com1.addScore(26);
			com2.addScore(26);
			com3.addScore(26);
		}
		else
			for (int i=0;i<=2;i++){
				if (com[i].checkShootTheMoon()){
					player1.addScore(26);
					for (int j=0;j<=2;j++)
						if (j!=i) com[j].addScore(26);
				}
			}
	}
	
	public boolean check100score(){
		if (player1.getScore()>=20)
			return true;
		else{
			for (int i=0;i<=2;i++)
				if (com[i].getScore()>=20) return true;
			return false;
		}		
	}
	
	public int getMinScore(){
		int min=player1.getScore();
		int winplayer=1;
		for (int i=0;i<=2;i++)
			if (com[i].getScore()<min){
				min=com[i].getScore();
				winplayer=i+2;
			}
		return winplayer;
	}
	
	public void processEndRound(){
		shootTheMoon();
		currentstep=3;
		gdien.notice("Player1: "+player1.getScore()+"   Comuter1: "+com1.getScore()+
				"   Computer2: "+com2.getScore()+"  Computer3:  "+com3.getScore());
		if (check100score()==false){
			gdien.showbutton("New Round");
			gdien.drawAllCard();
		}
		else
		{
			gdien.showbutton("New Game");
			if (getMinScore()==1)
				gdien.notice("You WIN !!!");
			else
				gdien.notice("You LOSE !!!");
			player1.resetScore();
			com1.resetScore();
			com2.resetScore();
			com3.resetScore();
		}
		
	}
	
	public void checkEnd4Card(){
		if (fourCard.size()==4){
			card winCard=check4cardwin();
			if (player1.isContainCard(winCard.getID())){						
				player1.add4scorecard(fourCard);
				firstturn=1;
				currentturn=firstturn;
			}
			else{
				for (int i=0;i<=2;i++){
					if (com[i].isContainCard(winCard.getID())){
						com[i].add4scorecard(fourCard);
						firstturn=i+2;
						currentturn=firstturn;
						break;
					}
				}
			}
			gdien.drawAllCard();
			try {
				Thread.sleep(delay);
			}
			catch (Exception e) {
			}
			roundcount++;
			if (roundcount==13){
				processEndRound();
			}
		}
	}
	
	public void nextturn(){
		currentturn=currentturn==4?1:currentturn+1;		
	}
	
	public void getFirstTurn(){
		if (player1.isContainCard(2))
			firstturn=1;
		else
			for (int i=0;i<=2;i++)
				if (com[i].isContainCard(2)){
					firstturn=i+2;
					break;
				}		
	}
	
	public void computerPlay(){
		while ((currentturn!=1)&&(currentstep==2)){			
			if (currentturn==firstturn){
				fourCard.add(com[currentturn-2].playfirst(duocChonCo));
			}
			else{
				fourCard.add(com[currentturn-2].playfollow(fourCard.get(0)));
				if ((fourCard.get(fourCard.size()-1)).checkCo()) duocChonCo=true;
			}
			gdien.drawAllCard();
			try {
				Thread.sleep(delay);
			}
			catch (Exception e) {
			}
			nextturn();
			checkEnd4Card();
		}
	}
	
	public void receiveClick(int cardClicked) {
		if (currentstep==1){ //step1: hoan doi bai
			if (cardClicked!=0){ //click vao 1 la bai
				if (threeCard.contains(cardClicked)){
					for (int i=0;i<threeCard.size();i++)
						if (threeCard.get(i)==cardClicked){
							threeCard.remove(i);
							break;
						}						
					gdien.setnomal(cardClicked);						
				}
				else{
					if (threeCard.size()!=3){
						threeCard.add(cardClicked);
						gdien.sethightlight(cardClicked);
					}
				}				
			}
			else{ //click vao button
				if (threeCard.size()==3){//doi 3 la bai
					card card1,card2;					
					for (int i=0;i<=2;i++){
						card1=player1.pullACard(threeCard.get(i));
						card2=com[i].pullARandomCard();
						player1.receiveCard(threeCard.get(i), card2);
						com[i].receiveCard(card1);
					}
					threeCard.clear();					
					gdien.hideButton();										
					currentstep=2;
					player1.sortcard();
					for (int i=0;i<=2;i++){
						com[i].sortcard();
					}
					
					//init cho step 2
					getFirstTurn();
					currentturn=firstturn;
					if (currentturn!=1){
						fourCard.add(com[currentturn-2].play2bich());
						gdien.drawAllCard();
						try {
							Thread.sleep(delay);
						}
						catch (Exception e) {
						}
						is2bichplayed=true;
						nextturn();
					}					
					computerPlay();
					gdien.drawAllCard();
				}
				else
					gdien.notice("Ban phai chon du 3 la bai !!!");
			}
		}
		else{
			if (currentstep==2){//step 2:play
				if (!is2bichplayed){//human di 2 bich truoc
					if (player1.getHandCard(cardClicked).getID()==2){
						fourCard.add(player1.playACard(cardClicked));
					
						gdien.drawAllCard();
						try {
						Thread.sleep(delay);
						}
						catch (Exception e) {
						}
						is2bichplayed=true;
						nextturn();
					}
					else						
						gdien.notice("Ban phai di 2 bich truoc");
				}
				else{
					if(fourCard.size()==0){//human di dau tien
						if ((player1.getHandCard(cardClicked).checkCo())&&(!duocChonCo)){						
							gdien.notice("Chua co la Co nao ra");
						}
						else{
							if (player1.getHandCard(cardClicked).getID()==41)
								gdien.notice("Ban phai di 2 bich truoc");
							else{
								fourCard.add(player1.playACard(cardClicked));
								gdien.drawAllCard();
								try {
								Thread.sleep(delay);
								}
								catch (Exception e) {
								}
								nextturn();
							}
						}						
					}
					else{//human di theo
						if ((player1.getHandCard(cardClicked).checkSameRank(fourCard.get(0)))||
							(!player1.checkAvableRank(fourCard.get(0)))){
							fourCard.add(player1.playACard(cardClicked));
							if (player1.getHandCard(cardClicked).checkCo()) duocChonCo=true;
							gdien.drawAllCard();
							try {
								Thread.sleep(delay);
							}
							catch (Exception e) {
							}					
							nextturn();
							checkEnd4Card();
						}
						else
							gdien.notice("Ban phai di cung nuoc voi la dau tien");
					}				
				}
				computerPlay();				
			}
			else{//step 3
				newRound();
			}
		}
	}
	
	public void run() {
		while (true) {
			if (SignalFlag[0]!=-1) {
				receiveClick(SignalFlag[0]);
				SignalFlag[0]=-1;
			}
			if (SignalFlag[1]!=0) {
				newGame();
				SignalFlag[1]=0;
			}
			try {
				Thread.sleep(100);
			}
			catch (Exception e) {
			}
		}		
	}
	
	public void newRound(){
		player1.newRound();
		for (int i=0;i<=2;i++){
			com[i].newRound();
		}
		divideCard();	
		player1.sortcard();
		for (int i=0;i<=2;i++){
			com[i].sortcard();
		}
		is2bichplayed=false;
		duocChonCo=false;
		currentstep=1;
		roundcount=0;
		gdien.showbutton("Exchange");
		gdien.drawAllCard();		
	}
	
	public void newGame() {
		player1.resetScore();
		com1.resetScore();
		com2.resetScore();
		com3.resetScore();
		fourCard.clear();
		newRound();
	}
	public static void main(String[] args){
		Main gameE=new Main("hehe");
		gameE.newRound();							
	}

		
}
