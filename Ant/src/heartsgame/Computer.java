package heartsgame;

import java.security.SecureRandom;
import java.util.ArrayList;

public class Computer extends Player{
    @Override
	public int playARandomCard(){
		SecureRandom numGenerate=new SecureRandom();
		int random=0;
		while (random==0){
			random=numGenerate.nextInt(getListCard().size());
		}		
		return playACard(getListCard().get(random));
	}

	
    @Override
	public int playfirst(boolean duocChonCo) {
        SecureRandom numGenerate = new SecureRandom();
        int random = 0;

        while ((getListCard().get(random) == 41) || (Card.getType(getListCard().get(random)) == GameDef.CHAT_CO && (!duocChonCo))) {
            random = numGenerate.nextInt(getListCard().size());
        }
        return playACard(getListCard().get(random));
    }

	
    @Override
	public int playfollow(int id) {
        ArrayList<Integer> samerank = new ArrayList<Integer>();
        for (int i = 0; i < getListCard().size(); ++i)
        {
            if (Card.dongChat(id, getListCard().get(i)))
                samerank.add(i);
        }
        if (!samerank.isEmpty()) {
            SecureRandom numGenerate = new SecureRandom();
            int random = 0;
            random = numGenerate.nextInt(samerank.size());            
            return playACard(samerank.get(random));
        } else {
            return playfirst(true);
        }

    }


	
}
