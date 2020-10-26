import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread
{
    Socket socket;
    DataInputStream input;
    DataOutputStream output;
    String name;
    String line = "";

    public ClientHandler(Socket socket, DataInputStream input, DataOutputStream output, String name) throws IOException
    {
        this.socket = socket;
        this.input = input;
        this.output = output;
        this.name = name;
    }

    @Override
    public void run()
    {
        //If a client wants to cut the connection to the server he must type "over"
        while (!line.equals("over"))
        {
            try
            {
                line = input.readUTF();
                System.out.println(line + "\n");
                sendMessageAllClients();
            }
            catch(IOException e)
            {
                System.out.println("Error: " + e);
            }
        }
    }

    public void sendMessageAllClients() throws IOException
    {
        PrintWriter toclient;

        //The message is send to all the clients in the network.
        for(int i = 0; i < Host.clientList.size();i++)
        {
            toclient = new PrintWriter(Host.clientList.get(i).socket.getOutputStream(),true);
            toclient.println(line);
        }

    }
}