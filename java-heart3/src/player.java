import java.util.ArrayList;



abstract public class player {
	protected String name;
	protected card[] handcard;
	protected Boolean[] playedcard;
	protected ArrayList<card> scorecard;
	protected int score;

	public player(){
		name="";			
		score=0;		
		handcard=new card[14];
		playedcard=new Boolean[14];
		scorecard=new ArrayList<card>();		
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setScore(int score){
		this.score=score;
	}
	
	public int getScore(){
		return score;
	}
	
	public void addScore(int score){
		this.score+=score;
	}
	
	public boolean checkAvableCard(int index){
		return !(playedcard[index]);
	}
	
	public void receiveCard(card c){		
		for (int i=1;i<=13;i++)
			if (handcard[i]==null){
				handcard[i]=c;	
				break;
			}		
	}
	
	public void receiveCard(int index,card c){
		handcard[index]=c;
	}
	
	public card getHandCard(int index){
		if (index<14)
			return handcard[index];
		return null;		
	}
	
	public card pullACard(int index){
		card tam=handcard[index];
		handcard[index]=null;
		return tam;
	}
	
	public void sortcard(){
		card[] handcard2=new card[14];
		card[] setcard=new card[14];
		int tro1=0,tro2;
		for (int currentRank=1;currentRank<=4;currentRank++){
			if (currentRank==4) currentRank=0;
			tro2=0;
			//lay set
			for (int i=1;i<=13;i++){
				if (handcard[i].getRankOfSet()==currentRank){
					tro2++;
					setcard[tro2]=handcard[i];
				}
			}
			//sap xep set do
			for (int i=1;i<tro2;i++)
				for (int j=i;j<=tro2;j++){
					if (setcard[j].getID()<setcard[i].getID()){
						card cardtam=setcard[i];
						setcard[i]=setcard[j];
						setcard[j]=cardtam;
					}
				}
			
			//dua vao handcard2
			for (int i=1;i<=tro2;i++){
				tro1++;
				handcard2[tro1]=setcard[i];
			}
			if (currentRank==0) currentRank=4;
		}
		handcard=handcard2;
	}
	
	public boolean isContainCard(int cardID){
		for (int i=1;i<=13;i++){
			if (handcard[i].getID()==cardID) return true;
		}
		return false;
	}
	
	public void add4scorecard(ArrayList<card> fourcard){
		for (int i=0;i<=3;i++){
			scorecard.add(fourcard.get(i));
			if (fourcard.get(i).checkCo()) addScore(1);
			if (fourcard.get(i).getID()==41) addScore(13);
		}
		fourcard.clear();
	}
	
	public void newRound(){
		for (int i=1;i<=13;i++){
			handcard[i]=null;
			playedcard[i]=false;			
		}
		scorecard.clear();
	}
	
	public void resetScore(){		
		score=0;
	}
	
	public boolean checkShootTheMoon(){
		int demCo=0;
		for (int i=0;i<scorecard.size();i++){
			if (scorecard.get(i).checkCo()) demCo++;
		}
		if (demCo==13){
			for (int i=0;i<scorecard.size();i++)
				if (scorecard.get(i).getID()==41){
					addScore(-26);
					return true;
				}
			return false;
		}
		else
			return false;
	}
}
