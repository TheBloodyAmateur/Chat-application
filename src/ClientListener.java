import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientListener extends Thread
{
    Socket socket;
    String line = "";

    public ClientListener(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true)
        {
            try
            {
                line = input.readLine();
                System.out.println(line + "\n");
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}