package pl.polsl.server;

import java.io.*;
import java.net.Socket;

public class SingleService implements Closeable
{
    private Socket socket;

    private BufferedReader input;

    private PrintWriter output;

    public SingleService(Socket socket) throws IOException
    {
        this.socket = socket;
        output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void realize()
    {
        try
        {
            while (true)
            {
                String str = input.readLine();
                output.println("Server ansers: " + str);
                if(str.toUpperCase().equals("QUIT"))
                    break;
                System.out.println("Client sent: " + str);
            }
            System.out.println("Closing...");
        }catch(IOException e)
        {
            System.err.println(e.getMessage());
        }finally
        {
            try
            {
                socket.close();
            }catch(IOException e)
            {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void close() throws IOException
    {
        if(socket !=null)
        {
            socket.close();
        }
    }
}
