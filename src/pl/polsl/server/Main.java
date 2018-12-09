package pl.polsl.server;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.io.IOException;
import java.net.Socket;

public class Main
{

    public static void main(String[] args)
    {
        try (ServerTCP server = new ServerTCP())
        {
            System.out.println("Server started");
            while (true)
            {
                Socket socket = server.serverSocket.accept();
                try(SingleService singleService = new SingleService(socket))
                {
                    singleService.realize();
                }catch(IOException e)
                {
                    System.err.println(e.getMessage());
                }
            }
        }catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
