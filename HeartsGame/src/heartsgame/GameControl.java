/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.util.ArrayList;

/**
 *
 * @author kydrenw
 */
public class GameControl implements Runnable {

    private int nState;
    private GameState[] gameState;
    private int currentState;
    private GUI gui;
    private Server server;
    private Client client;
    private int type;
    private Thread thread;
    private String playerID;
    private int viTri;

    public static void main(String[] args) {
        GameControl gameControl = new GameControl();
    }


    public GameControl() {
        gui = new GUI(this);
        nState = 4;
        gameState = new GameState[nState];
        gameState[GameDef.GAME_MENU] = new GameStateMenu(this,gui);
        gameState[GameDef.GAME_ABOUT] = new GameStateAbout(this,gui);
        gameState[GameDef.GAME_WAIT] = new GameStateWait(this,gui);
        //gameState[GameDef.GAME_PLAY] = new GameStatePlay(this,gui);

        currentState = GameDef.GAME_MENU;

        type = -1;
        server = null;
        client = null;

        thread = new Thread(this);
        thread.start();

    }

    public void run() {
        while (true) {
            gameState[currentState].Update();
             try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            gameState[currentState].Draw();
             try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    void SwitchState(int state) {
        currentState = state;
        gameState[currentState].Enter();
    }

    void IsClient() {
        gameState[GameDef.GAME_PLAY] = new GameStatePlayClient(this,gui);
        setType(GameDef.IS_CLIENT);
        setClient(new Client("localhost", 8999,this));
    }

    void IsServer() {
        gameState[GameDef.GAME_PLAY] = new GameStatePlayServer(this,gui);
        setType(GameDef.IS_SERVER);
        setServer(new Server(8999,this));
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


    void HaveMessageFromServer(String msg){
        System.out.println("Server send : " + msg);
        gameState[currentState].HaveMessage(msg);
    }

    void HaveMessageFromClient(String msg){
        System.out.println("Client send : " + msg);
        gameState[currentState].HaveMessage(msg);
    }
}