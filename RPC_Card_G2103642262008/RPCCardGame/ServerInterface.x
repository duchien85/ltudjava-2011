program ServerInterface
{
	version ServerInterface
	{
		int EnterGame(String) = 2;

		String getName(int) = 3;

		String getCardPriority(void) = 4;
		
		String givrMeCards(int) = 5;
		
		int giveMyRealPoints(int) = 6;

		int putCard(String) = 7;
		
		String getCards(int) = 8;

		int whosChance(void) =9;

		int roundOver(void) = 10;

		String getLastPut(void) = 11;

		int howManyEntered(void) = 12;

		int canstartNextEvent(int) = 13;

		String getPutType(void)  = 14;

		int isGameOver(void) = 15;
	
		void needToLeave(int) = 16;

		int LeavedPlayerID(void) = 17;

		int Leave(void) = 18;

		String getWinnerOrder(void) = 19;	
		
	
	} = 1;
} = 123456;