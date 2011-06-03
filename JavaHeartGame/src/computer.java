import java.security.SecureRandom;
import java.util.ArrayList;


public class computer extends player{
	public card pullARandomCard(){
		SecureRandom numGenerate=new SecureRandom();
		int random=0;
		while (random==0){
			random=numGenerate.nextInt(14);
		}		
		return pullACard(random);
	}

	
	public card playfirst(boolean duocChonCo) {
		SecureRandom numGenerate=new SecureRandom();
		int random=0;
		while ((random==0)||(playedcard[random]==true)||(handcard[random].getID()==41)||
				((handcard[random].checkCo())&&(!duocChonCo))){
			random=numGenerate.nextInt(14);
		}
		playedcard[random]=true;
		return handcard[random];
	}

	
	public card playfollow(card c) {
		ArrayList<Integer> samerank=new ArrayList<Integer>();
		for (int i=1;i<=13;i++)
			if ((playedcard[i]==false)&&(handcard[i].checkSameRank(c))){
				samerank.add(i);
			}
		if (samerank.size()!=0){
			SecureRandom numGenerate=new SecureRandom();
			int random=0;			
			random=numGenerate.nextInt(samerank.size());			
			playedcard[samerank.get(random)]=true;
			return handcard[samerank.get(random)];
		}
		else{
			return playfirst(true);
		}
		
	}
	
	public card play2bich(){
		for (int i=1;i<=13;i++)
			if (handcard[i].getID()==2)
			{
				playedcard[i]=true;
				return handcard[i];
			}
		return null;
	}
	
	
}
