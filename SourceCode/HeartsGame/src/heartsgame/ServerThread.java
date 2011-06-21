/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author kydrenw
 */
public class ServerThread extends Thread {

    private Server server = null;
    private Socket socket = null;
    // id : port ket noi
    private int ID = -1;
    // du lieu nhan ve
    private DataInputStream streamIn = null;
    // du lieu gui di
    private DataOutputStream streamOut = null;
    // mang quan bai de doi
    private ArrayList<Integer> cardChange;
    private boolean endExchange = false;
    public ServerThread(Server _server, Socket _socket) {
        super();
        server = _server;
        socket = _socket;
        ID = socket.getPort();
        cardChange = new ArrayList<Integer>();
    }

    // gui du lieu cho client
    public void send(String msg) {
        try {
            streamOut.writeUTF(msg);
            streamOut.flush();
        } catch (IOException ioe) {
            System.out.println(ID + " ERROR sending: " + ioe.getMessage());
            server.remove(ID);
            stop();
        }
    }
    // kiem tra du lieu nhan ve
    public String getMessage() throws IOException{
        String DuLieu = "";
        BufferedReader inFromServer = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        DuLieu = inFromServer.readLine();
        return DuLieu;
    }
    // lay id cua client
    public int getID() {
        return ID;
    }

    public void run() {
        System.out.println("Server Thread " + ID + " running.");
        while (true) {
            try {
                if (getEndExchange()==false){
                    waitExchange(streamIn.readUTF());
                }
                server.handle(ID, streamIn.readUTF());
            } catch (IOException ioe) {
                System.out.println(ID + " ERROR reading: " + ioe.getMessage());
                server.remove(ID);
                stop();
            }
        }
    }
    void waitExchange(String msg){
        if (msg.startsWith("exchange")){
            String[]card = (msg.split("exchange")[1]).split("c");
            for(int i=1; i<card.length;i++){
                getCardChange().add(Integer.parseInt(card[i]));
            }
            setEndExchange(true);
        }
    }
    // khoi tao streamIn va streamOut
    public void open() throws IOException {
        streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    // dong ket noi
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (streamIn != null) {
            streamIn.close();
        }
        if (streamOut != null) {
            streamOut.close();
        }
    }

    /**
     * @return the cardChange
     */
    public ArrayList<Integer> getCardChange() {
        return cardChange;
    }

    /**
     * @param cardChange the cardChange to set
     */
    public void setCardChange(ArrayList<Integer> cardChange) {
        this.cardChange = cardChange;
    }

    /**
     * @return the endExchange
     */
    public boolean getEndExchange() {
        return endExchange;
    }

    /**
     * @param endExchange the endExchange to set
     */
    public void setEndExchange(boolean endExchange) {
        this.endExchange = endExchange;
    }
}
