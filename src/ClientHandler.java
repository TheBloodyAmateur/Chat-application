import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        //When ever a client connects the previous chat is loaded
        if(Host.chatProtocol != null)
        {
            try
            {
                output.writeUTF(Host.chatProtocol.toString());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        //If a client wants to cut the connection to the server he must type "over"
        while (!line.equals("over"))
        {
            try
            {
                line = input.readUTF();
                Host.chatProtocol.append(line + "\n");
                System.out.println(line);
                sendMessageAllClients(line);
                //The messages are stored in a .txt file
                saveMessage(Host.chatProtocol.toString());
            }
            catch(IOException e)
            {
                System.out.println("Error: " + e);
            }
        }
    }

    public void sendMessageAllClients(String message) throws IOException
    {
        PrintWriter toclient;

        //The message is send to all the clients in the network.
        for(int i = 0; i < Host.clientList.size();i++)
        {
            toclient = new PrintWriter(Host.clientList.get(i).socket.getOutputStream(),true);
            toclient.println(line);
        }

    }

    public static void saveMessage(String message) throws FileNotFoundException
    {
        PrintWriter writer = new PrintWriter(new FileOutputStream("chat.txt",false));
        writer.print(message);
        writer.close();
    }
}