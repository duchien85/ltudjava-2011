/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author kydrenw
 */
public class Server implements Runnable{

    private ServerThread clients[] = new ServerThread[50];
    public ServerSocket server = null;
    private Thread thread = null;
    private int clientCount = 0;
    public boolean isConnect ;
    GameControl gameControl;
    private InetAddress ipAddress;
    public Server(int port, final GameControl gControl) {
        try {
            System.out.println("Binding to port " + port + ", please wait  ...");
            ipAddress =InetAddress.getLocalHost();
            //server = new ServerSocket(port);
            server = new ServerSocket(port,0, ipAddress);
            System.out.println("Server started: " + server);
            gameControl = gControl;
            start();
            isConnect = true;
        } catch (IOException ioe) {
            isConnect= false;
            System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());

        }
    }

    public boolean IsExchanged(){
        if (clientCount==3){
           return (clients[0].getEndExchange()&&clients[1].getEndExchange()&&clients[2].getEndExchange()); 
        }
        return false;
    }

    public ArrayList<Integer> getCardExchange(int index){
        return clients[index].getCardChange();
    }
    public void run() {
        while (thread != null) {
            try {
                System.out.println("Waiting for a client ...");
                addThread(server.accept());
            } catch (IOException ioe) {
                System.out.println("Server accept error: " + ioe);
                stop();
            }
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        if (thread != null) {
            thread.stop();
            thread = null;
        }
    }

    private int findClient(int ID) {
        for (int i = 0; i < getClientCount(); i++) {
            if (getClients()[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }

    public synchronized void SendToAllClient(String input) {
        for (int i = 0; i < clientCount; i++) {
            if (input.equals(".bye")) {
                getClients()[i].send(".bye");
                removeAt(i);
            } else {
                getClients()[i].send(input);
            }
        }
    }

    public synchronized void SendToClient(int index, String input) {
        if (input.equals(".bye")) {
            getClients()[index].send(".bye");
            removeAt(index);
        } else {
            getClients()[index].send(input);
        }

    }

    public synchronized void removeAt(int index) {
        int pos = index;
        if (pos >= 0) {
            ServerThread toTerminate = getClients()[pos];
            System.out.println("Removing client thread " + index + " at " + pos);
            if (pos < getClientCount() - 1) {
                for (int i = pos + 1; i < getClientCount(); i++) {
                    getClients()[i - 1] = getClients()[i];
                }
            }
            setClientCount(getClientCount() - 1);
            try {
                toTerminate.close();
            } catch (IOException ioe) {
                System.out.println("Error closing thread: " + ioe);
            }
            toTerminate.stop();
        }
    }

    public synchronized void handle(int ID, String input) {
        System.out.println("Client send : " + input);
        if (input.equals(".bye")) {
            getClients()[findClient(ID)].send(".bye");
            remove(ID);
        }
        else if(input.equals("test")) {
            getClients()[findClient(ID)].send(".bye");
            remove(ID);
        }
        else {
            gameControl.HaveMessageFromClient(input);          
        }
    }

    public synchronized void remove(int ID) {
        int pos = findClient(ID);
        if (pos >= 0) {
            ServerThread toTerminate = getClients()[pos];
            System.out.println("Removing client thread " + ID + " at " + pos);
            if (pos < getClientCount() - 1) {
                for (int i = pos + 1; i < getClientCount(); i++) {
                    getClients()[i - 1] = getClients()[i];
                }
            }
            clientCount --;
            try {
                toTerminate.close();
            } catch (IOException ioe) {
                System.out.println("Error closing thread: " + ioe);
            }
            toTerminate.stop();
        }
    }

    private void addThread(Socket socket) {
        if (getClientCount() < getClients().length) {
            System.out.println("Client accepted: " + socket);
            getClients()[getClientCount()] = new ServerThread(this, socket);
            try {
                getClients()[getClientCount()].open();
                getClients()[getClientCount()].start();
                clientCount ++;
            } catch (IOException ioe) {
                System.out.println("Error opening thread: " + ioe);
            }
        } else {
            System.out.println("Client refused: maximum " + getClients().length + " reached.");
        }
    }

    /**
     * @return the clientCount
     */
    public int getClientCount() {
        return clientCount;
    }

    /**
     * @param clientCount the clientCount to set
     */
    public void setClientCount(int clientCount) {
        this.clientCount = clientCount;
    }

    /*
    public static void main(String args[]) {
    Server server = null;

    server = new Server(8999);
    }
*/
    /**
     * @return the clients
     */
    public ServerThread[] getClients() {
        return clients;
    }

    /**
     * @param clients the clients to set
     */
    public void setClients(ServerThread[] clients) {
        this.clients = clients;
    }

    /**
     * @return the ipAddress
     */
    public InetAddress getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }


}
