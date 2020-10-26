import java.io.*;
import java.net.Socket;

public class Client
{
    public static void main (String[] args) throws IOException
    {
        //Get the name of the client
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Typ in your name: ");
        String name = scanner.readLine();
        System.out.print("\n\n");

        //The client connects, in this case to the local host at the port 50000
        Socket socket = new Socket("localhost", 50000);

        //The input and output variables are used to read and send messages from the host
        DataInputStream input = new DataInputStream(System.in);
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        //Name of the client is sent to the host
        String line = "";
        output.writeUTF(name);

        //The class ClientListener receives messages from the host program and displays them
        ClientListener listener = new ClientListener(socket);
        Thread thread = new Thread(listener);
        thread.start();

        //If a client wants to cut the connection to the server he must type "over"
        while (!line.equals("Over"))
        {
            try
            {
                line = input.readLine();
                output.writeUTF(name + ": " + line);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
    }
}