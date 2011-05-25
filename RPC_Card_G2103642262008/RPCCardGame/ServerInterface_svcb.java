//Genereated by NSA JRPCGEN v 1.0 for Netbula RPC
//Written by Nuwan Sanjeewa Abeysiriwardana 
//University of Colombo School of Computing 
//Sri Lanka



import netbula.ORPC.*;

abstract public class ServerInterface_svcb extends Svc implements ServerInterface{
public ServerInterface_svcb(int prog, int ver) {
super(prog, ver);
}

public ServerInterface_svcb() {
super(ServerInterface._def_pno, ServerInterface._def_vno);
}
abstract public int EnterGame(String in_arg) ;

abstract public String getName(int in_arg) ;

abstract public String getCardPriority() ;

abstract public String givrMeCards(int in_arg) ;

abstract public int giveMyRealPoints(int in_arg) ;

abstract public int putCard(String in_arg) ;

abstract public String getCards(int in_arg) ;

abstract public int whosChance() ;

abstract public int roundOver() ;

abstract public String getLastPut() ;

abstract public int howManyEntered() ;

abstract public int canstartNextEvent(int in_arg) ;

abstract public String getPutType() ;

abstract public int isGameOver() ;

abstract public void needToLeave(int in_arg) ;

abstract public int LeavedPlayerID() ;

abstract public int Leave() ;

abstract public String getWinnerOrder() ;

public XDT proc_call (int proc, XDR inXDR) throws XDRError {
switch(proc) {
case 0: return new XDTvoid();
case 2:
int _out_arg2;
try{
XDTString _in_arg = new XDTString();
_in_arg.xdr(inXDR);
_out_arg2=this.EnterGame(_in_arg.value);
} catch (XDRError e) {
throw e;
}
return new XDTint(_out_arg2);
case 3:
String _out_arg3;
try{
XDTint _in_arg = new XDTint();
_in_arg.xdr(inXDR);
_out_arg3=this.getName(_in_arg.value);
} catch (XDRError e) {
throw e;
}
return new XDTString(_out_arg3);
case 4:
String _out_arg4;
_out_arg4=this.getCardPriority();
return new XDTString(_out_arg4);
case 5:
String _out_arg5;
try{
XDTint _in_arg = new XDTint();
_in_arg.xdr(inXDR);
_out_arg5=this.givrMeCards(_in_arg.value);
} catch (XDRError e) {
throw e;
}
return new XDTString(_out_arg5);
case 6:
int _out_arg6;
try{
XDTint _in_arg = new XDTint();
_in_arg.xdr(inXDR);
_out_arg6=this.giveMyRealPoints(_in_arg.value);
} catch (XDRError e) {
throw e;
}
return new XDTint(_out_arg6);
case 7:
int _out_arg7;
try{
XDTString _in_arg = new XDTString();
_in_arg.xdr(inXDR);
_out_arg7=this.putCard(_in_arg.value);
} catch (XDRError e) {
throw e;
}
return new XDTint(_out_arg7);
case 8:
String _out_arg8;
try{
XDTint _in_arg = new XDTint();
_in_arg.xdr(inXDR);
_out_arg8=this.getCards(_in_arg.value);
} catch (XDRError e) {
throw e;
}
return new XDTString(_out_arg8);
case 9:
int _out_arg9;
_out_arg9=this.whosChance();
return new XDTint(_out_arg9);
case 10:
int _out_arg10;
_out_arg10=this.roundOver();
return new XDTint(_out_arg10);
case 11:
String _out_arg11;
_out_arg11=this.getLastPut();
return new XDTString(_out_arg11);
case 12:
int _out_arg12;
_out_arg12=this.howManyEntered();
return new XDTint(_out_arg12);
case 13:
int _out_arg13;
try{
XDTint _in_arg = new XDTint();
_in_arg.xdr(inXDR);
_out_arg13=this.canstartNextEvent(_in_arg.value);
} catch (XDRError e) {
throw e;
}
return new XDTint(_out_arg13);
case 14:
String _out_arg14;
_out_arg14=this.getPutType();
return new XDTString(_out_arg14);
case 15:
int _out_arg15;
_out_arg15=this.isGameOver();
return new XDTint(_out_arg15);
case 16:
try{
XDTint _in_arg = new XDTint();
_in_arg.xdr(inXDR);
this.needToLeave(_in_arg.value);
} catch (XDRError e) {
throw e;
}
return new XDTvoid();
case 17:
int _out_arg17;
_out_arg17=this.LeavedPlayerID();
return new XDTint(_out_arg17);
case 18:
int _out_arg18;
_out_arg18=this.Leave();
return new XDTint(_out_arg18);
case 19:
String _out_arg19;
_out_arg19=this.getWinnerOrder();
return new XDTString(_out_arg19);
default: return null;
}
	}
	}
