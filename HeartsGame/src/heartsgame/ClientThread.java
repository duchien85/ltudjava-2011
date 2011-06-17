/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author kydrenw
 */
public class ClientThread extends Thread {

    private Socket socket = null;
    private Client client = null;
    private DataInputStream streamIn = null;

    public ClientThread(Client _client, Socket _socket) {
        client = _client;
        socket = _socket;
        open();
        start();
    }

    public void open() {
        try {
            setStreamIn(new DataInputStream(socket.getInputStream()));
        } catch (IOException ioe) {
            System.out.println("Error getting input stream: " + ioe);
            client.stop();
        }
    }

    public void close() {
        try {
            if (getStreamIn() != null) {
                getStreamIn().close();
            }
        } catch (IOException ioe) {
            System.out.println("Error closing input stream: " + ioe);
        }
    }

    public void run() {
        while (true) {
            try {
                client.handle(GetMessage());
            } catch (IOException ioe) {
                System.out.println("Listening error: " + ioe.getMessage());
                client.stop();
            }
        }
    }

    /**
     * @return the streamIn
     */
    public DataInputStream getStreamIn() {
        return streamIn;
    }

    /**
     * @param streamIn the streamIn to set
     */
    public void setStreamIn(DataInputStream streamIn) {
        this.streamIn = streamIn;
    }

    public String GetMessage() throws IOException{
        return streamIn.readUTF();
    }
}
