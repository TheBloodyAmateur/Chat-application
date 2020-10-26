/*
    This is the program which acts as the host!
    In this case the program listens on port 50000.
*/
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Host extends Thread
{
    //This list is used to store all clients in it.
    public static ArrayList<ClientHandler> clientList = new ArrayList<>();

    public static void main (String[] args) throws IOException
    {
        ServerSocket serversocket = new ServerSocket(50000);

        while(true)
        {
            Socket socket;

            try
            {
                socket = serversocket.accept();

                //The input and output variables are used to read and send messages from the user
                DataInputStream inputClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputClient = new DataOutputStream(socket.getOutputStream());

                //The name of the client is send to the host and stored in the variable nameClient
                String nameClient = inputClient.readUTF();

                //The ClientHandler class is used to get the user input and broadcast it
                ClientHandler client = new ClientHandler(socket,inputClient,outputClient,nameClient);
                clientList.add(client);
                Thread thread = new Thread(client);
                thread.start();
            }
            catch (ThreadDeath i)
            {
                System.out.print("Error: " + i);
            }
        }
    }
}