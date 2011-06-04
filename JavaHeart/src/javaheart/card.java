package javaheart;


public class card {
	private int ID;
	private int rankofset;
	private int set;
	
	public card(int id){
		if ((id>0)&&(id<53)) ID=id;
		else ID=0;
		set=ID/4+1;
		rankofset=ID%4;
	}
	
	public int getID(){
		return ID;
	}
	
	public int getRankOfSet(){
		return rankofset;
	}
	
	public boolean checkCo(){
		return (rankofset==0);
	}
	
	public boolean checkSameRank(card othercard){
		return (othercard.rankofset==rankofset);
	}
	
	public boolean greaterThan(card othercaCard){
		return ((set>othercaCard.set)&&(rankofset==othercaCard.rankofset));
	}
	
}
