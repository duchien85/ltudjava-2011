
public class human extends player{

	public card playACard(int index){
		playedcard[index]=true;
		return handcard[index];
	}
	
	public boolean checkAvableRank(card c){
		for (int i=1;i<=13;i++){			
			if ((playedcard[i]==false)&&(c.checkSameRank(handcard[i])))
				return true;			
		}
		return false;
	}
	
}
