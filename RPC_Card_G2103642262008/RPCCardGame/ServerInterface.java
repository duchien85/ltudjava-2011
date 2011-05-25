//Genereated by NSA JRPCGEN v 1.0 for Netbula RPC
//Written by Nuwan Sanjeewa Abeysiriwardana 
//University of Colombo School of Computing 
//Sri Lanka



public interface ServerInterface {

public static final int _def_pno = 123456;
public static final int _def_vno = 1;



public static final int _EnterGame_proc =2;

public int EnterGame(String in_arg) throws netbula.ORPC.rpc_err;

public static final int _getName_proc =3;

public String getName(int in_arg) throws netbula.ORPC.rpc_err;

public static final int _getCardPriority_proc =4;

public String getCardPriority() throws netbula.ORPC.rpc_err;

public static final int _givrMeCards_proc =5;

public String givrMeCards(int in_arg) throws netbula.ORPC.rpc_err;

public static final int _giveMyRealPoints_proc =6;

public int giveMyRealPoints(int in_arg) throws netbula.ORPC.rpc_err;

public static final int _putCard_proc =7;

public int putCard(String in_arg) throws netbula.ORPC.rpc_err;

public static final int _getCards_proc =8;

public String getCards(int in_arg) throws netbula.ORPC.rpc_err;

public static final int _whosChance_proc =9;

public int whosChance() throws netbula.ORPC.rpc_err;

public static final int _roundOver_proc =10;

public int roundOver() throws netbula.ORPC.rpc_err;

public static final int _getLastPut_proc =11;

public String getLastPut() throws netbula.ORPC.rpc_err;

public static final int _howManyEntered_proc =12;

public int howManyEntered() throws netbula.ORPC.rpc_err;

public static final int _canstartNextEvent_proc =13;

public int canstartNextEvent(int in_arg) throws netbula.ORPC.rpc_err;

public static final int _getPutType_proc =14;

public String getPutType() throws netbula.ORPC.rpc_err;

public static final int _isGameOver_proc =15;

public int isGameOver() throws netbula.ORPC.rpc_err;

public static final int _needToLeave_proc =16;

public void needToLeave(int in_arg) throws netbula.ORPC.rpc_err;

public static final int _LeavedPlayerID_proc =17;

public int LeavedPlayerID() throws netbula.ORPC.rpc_err;

public static final int _Leave_proc =18;

public int Leave() throws netbula.ORPC.rpc_err;

public static final int _getWinnerOrder_proc =19;

public String getWinnerOrder() throws netbula.ORPC.rpc_err;

}