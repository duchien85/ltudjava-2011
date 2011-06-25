/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import javax.swing.JOptionPane;

/**
 *
 * @author kydrenw
 */
public final class GameControl implements Runnable {

    private int nState;
    private GameState[] gameState;
    private int currentState;
    private GUI gui;
    private Server server;
    private Client client;
    private int type;
    public Thread thread;
    private String playerID;
    private int viTri;

    public static void main(String[] args) {
        GameControl gameControl = new GameControl();
    }


    public GameControl() {
        gui = new GUI(this);
        nState = 10;
        gameState = new GameState[nState];
        gameState[GameDef.GAME_MENU] = new GameStateMenu(this,gui);
        gameState[GameDef.GAME_ABOUT] = new GameStateAbout(this,gui);
        gameState[GameDef.GAME_INTERFACE] = new GameStateInterface(this, gui);
        
        gameState[GameDef.GAME_IPCLIENT] = new Game_IPCLIENT(this, gui);
        gameState[GameDef.GAME_IPSERVER] = new Game_IPSERVER(this, gui);

        gameState[GameDef.GAME_WAIT] = new GameStateWait(this,gui);
        //gameState[GameDef.GAME_PLAY] = new GameStatePlay(this,gui);
        
        SwitchState(GameDef.GAME_MENU);

        type = -1;
        server = null;
        client = null;

        thread = new Thread(this);
        thread.start();

    }

    public void run() {
        while (true) {
            if (this.type == GameDef.IS_SERVER){
                if((currentState == GameDef.GAME_PLAY)&& (this.getServer().getClientCount()<3)){
                    final JOptionPane jop = new JOptionPane("Notice");
                    jop.setBounds(300, 300, 300, 300);
                    jop.showMessageDialog(jop, "Not enought 4 player. Will end game ... ");
                    try {
                        this.getServer().SendToAllClient("exit");
                        this.getServer().stop();
                    }catch (Exception ex){}

                    SwitchState(GameDef.GAME_MENU);
                }
            }
            else if (this.type == GameDef.IS_CLIENT){
                if((currentState == GameDef.GAME_PLAY) && (this.getClient().isConnected == false)){
                    //final JOptionPane jop = new JOptionPane("Notice");
                    //jop.setBounds(300, 300, 300, 300);
                    //jop.showMessageDialog(jop, "Server is exited game... ");
                    try{
                        this.getClient().stop();
                    }catch(Exception ex){}
                    SwitchState(GameDef.GAME_MENU);
                }
            }
            gameState[currentState].Update();
             try {
                thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    public void SwitchState(int state) {
        currentState = state;
        gameState[currentState].Enter();
    }

    public void IsClient(String IP, int Port) {
        gameState[GameDef.GAME_PLAY] = new GameStatePlayClient(this,gui);
        setType(GameDef.IS_CLIENT);
        setClient(new Client(IP, Port,this));
    }

    public void IsServer(int portserver) {
        gameState[GameDef.GAME_PLAY] = new GameStatePlayServer(this,gui);
        setType(GameDef.IS_SERVER);
        setServer(new Server(portserver,this));
    }
    
    public void SinglePlay() {
        gameState[GameDef.GAME_PLAY] = new GameStatePlaySingle(this,gui);       
        setType(GameDef.IS_SINGLE);
        SwitchState(GameDef.GAME_PLAY);
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the server
     */
    public Server getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * @return the thread
     */
    public Thread getThread() {
        return thread;
    }

    /**
     * @param thread the thread to set
     */
    public void setThread(Thread thread) {
        this.thread = thread;
    }

    /**
     * @return the playerID
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * @param playerID the playerID to set
     */
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    /**
     * @return the viTri
     */
    public int getViTri() {
        return viTri;
    }

    /**
     * @param viTri the viTri to set
     */
    public void setViTri(int viTri) {
        this.viTri = viTri;
    }


    public void HaveMessageFromServer(String msg){
        System.out.println("Server send : " + msg);
        gameState[currentState].HaveMessage(msg);
    }

    public void HaveMessageFromClient(String msg){
        System.out.println("Client send : " + msg);
        gameState[currentState].HaveMessage(msg);
    }
}
