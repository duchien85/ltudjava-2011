/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.io.*;
import java.net.*;
import java.util.Vector;

/**/
// Class : Client
// Quan ly nhan va truyen du lieu toi server
// @author kydrenw
/**/
public class Client implements Runnable {

    private Socket socket = null;
    private Thread thread = null;
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;
    private GameControl gameControl ;
    public Client(String serverName, int serverPort,final GameControl gControl) {
        System.out.println("Establishing connection. Please wait ...");
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            gameControl = gControl;
            start();
        } catch (UnknownHostException uhe) {
            System.out.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }
    }

    // chay tien trinh
    public void run() {
        while (thread != null) {
            try {
                handle(streamIn.readUTF());
                //GetMessage();
            } catch (IOException ioe) {
                System.out.println("Error: " + ioe.getMessage());
                stop();
            }
        }
    }

    // gui du lieu den server
    public void SendToServer(String msg){
        try{
            streamOut.writeUTF(msg);
            streamOut.flush();
        } catch (IOException ioe) {
            System.out.println("Sending error: " + ioe.getMessage());
            stop();        
        }
    }

    // xu ly khi co tin hieu tu server gui den
    public void handle(String msg) {
        if (msg.equals(".bye")) {
            System.out.println("Good bye. Press RETURN to exit ...");
            stop();
        } else {
            //if (msg!=null)
            //    message.add(msg);
            gameControl.HaveMessageFromServer(msg);
            //System.out.println(msg);
        }
    }

    // khoi tao cho du lieu nhan va du lieu gui di
    // streamIn  : du lieu nhan
    // streamOut : du lieu gui di
    public void start() throws IOException {
        streamIn = new DataInputStream(socket.getInputStream());
        streamOut = new DataOutputStream(socket.getOutputStream());
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    // dung tien trinh
    public void stop() {
        if (thread != null) {
            thread.stop();
            thread = null;
        }
        try {
            if (getStreamIn() != null) {
                getStreamIn().close();
            }
            if (streamOut != null) {
                streamOut.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ioe) {
            System.out.println("Error closing ...");
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

/*
    public static void main(String args[]) {
        Client client = null;

        client = new Client("127.0.0.1", 8999);

    }
 
 */
}
