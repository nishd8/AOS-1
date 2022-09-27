package Client;

import java.net.*;
import java.io.*;

public class Client
{
    // initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;

    // constructor to put ip address and port
    public Client(String address, int port)
    {
        System.out.println("Connecting to server on port : " + port);
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected to server on port : " + port);


            // sends output to the socket
            out    = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() throws IOException {
            out.close();
            socket.close();

    }
}