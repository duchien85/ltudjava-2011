//Genereated by NSA JRPCGEN v 1.0 for Netbula RPC
//Written by Nuwan Sanjeewa Abeysiriwardana 
//University of Colombo School of Computing 
//Sri Lanka



import netbula.ORPC.*;

public class ServerInterface_cln extends ClientGeneric implements ServerInterface{

/**
Construct an RPC client that is not bound to any server
*/
public ServerInterface_cln() throws rpc_err { };

/**
Construct an RPC client object connected to a server
on the specified host with the specified protocol

@param host server hostname, or URL of the RPC proxy if http is used
@param proto protocol, can be "tcp", "udp" or "http"
*/
public ServerInterface_cln(String host, String proto) throws rpc_err {
super(host, ServerInterface._def_pno, ServerInterface._def_vno, proto);
}
/**
Construct an RPC client object connected to a server
on the specified host with the specified protocol and port number

@param host server hostname
@param proto protocol, can be "tcp" or "udp"
@param port server's fixed port number
*/
public ServerInterface_cln(String host, String proto, int port) throws rpc_err {
super(host, proto, port, ServerInterface._def_pno, ServerInterface._def_vno);
}
/**
Construct an RPC client object connected to a server
on the specified host with the specified program number,version and protocol

@param host server hostname, or URL of RPC proxy
@param proto protocol, can be "tcp", "udp" or "http"
@param prog server's program number
@param ver server's version number
*/
public ServerInterface_cln(String host,  int prog, int ver, String proto) throws rpc_err {
super(host, prog, ver, proto);
}
public int EnterGame(String in_arg) throws rpc_err {
XDTString _in_arg = new XDTString();
_in_arg.value = in_arg;
XDTint _out_arg = new XDTint();
getClient().call(ServerInterface._EnterGame_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public String getName(int in_arg) throws rpc_err {
XDTint _in_arg = new XDTint();
_in_arg.value = in_arg;
XDTString _out_arg = new XDTString();
getClient().call(ServerInterface._getName_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public String getCardPriority() throws rpc_err {
XDTvoid _in_arg = new XDTvoid();
XDTString _out_arg = new XDTString();
getClient().call(ServerInterface._getCardPriority_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public String givrMeCards(int in_arg) throws rpc_err {
XDTint _in_arg = new XDTint();
_in_arg.value = in_arg;
XDTString _out_arg = new XDTString();
getClient().call(ServerInterface._givrMeCards_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public int giveMyRealPoints(int in_arg) throws rpc_err {
XDTint _in_arg = new XDTint();
_in_arg.value = in_arg;
XDTint _out_arg = new XDTint();
getClient().call(ServerInterface._giveMyRealPoints_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public int putCard(String in_arg) throws rpc_err {
XDTString _in_arg = new XDTString();
_in_arg.value = in_arg;
XDTint _out_arg = new XDTint();
getClient().call(ServerInterface._putCard_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public String getCards(int in_arg) throws rpc_err {
XDTint _in_arg = new XDTint();
_in_arg.value = in_arg;
XDTString _out_arg = new XDTString();
getClient().call(ServerInterface._getCards_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public int whosChance() throws rpc_err {
XDTvoid _in_arg = new XDTvoid();
XDTint _out_arg = new XDTint();
getClient().call(ServerInterface._whosChance_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public int roundOver() throws rpc_err {
XDTvoid _in_arg = new XDTvoid();
XDTint _out_arg = new XDTint();
getClient().call(ServerInterface._roundOver_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public String getLastPut() throws rpc_err {
XDTvoid _in_arg = new XDTvoid();
XDTString _out_arg = new XDTString();
getClient().call(ServerInterface._getLastPut_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public int howManyEntered() throws rpc_err {
XDTvoid _in_arg = new XDTvoid();
XDTint _out_arg = new XDTint();
getClient().call(ServerInterface._howManyEntered_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public int canstartNextEvent(int in_arg) throws rpc_err {
XDTint _in_arg = new XDTint();
_in_arg.value = in_arg;
XDTint _out_arg = new XDTint();
getClient().call(ServerInterface._canstartNextEvent_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public String getPutType() throws rpc_err {
XDTvoid _in_arg = new XDTvoid();
XDTString _out_arg = new XDTString();
getClient().call(ServerInterface._getPutType_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public int isGameOver() throws rpc_err {
XDTvoid _in_arg = new XDTvoid();
XDTint _out_arg = new XDTint();
getClient().call(ServerInterface._isGameOver_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public void needToLeave(int in_arg) throws rpc_err {
XDTint _in_arg = new XDTint();
_in_arg.value = in_arg;
XDTvoid _out_arg = new XDTvoid();
getClient().call(ServerInterface._needToLeave_proc, _in_arg, _out_arg);
}

public int LeavedPlayerID() throws rpc_err {
XDTvoid _in_arg = new XDTvoid();
XDTint _out_arg = new XDTint();
getClient().call(ServerInterface._LeavedPlayerID_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public int Leave() throws rpc_err {
XDTvoid _in_arg = new XDTvoid();
XDTint _out_arg = new XDTint();
getClient().call(ServerInterface._Leave_proc, _in_arg, _out_arg);
return _out_arg.value;
}

public String getWinnerOrder() throws rpc_err {
XDTvoid _in_arg = new XDTvoid();
XDTString _out_arg = new XDTString();
getClient().call(ServerInterface._getWinnerOrder_proc, _in_arg, _out_arg);
return _out_arg.value;
}

}
