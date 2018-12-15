package pl.polsl.server;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import pl.polsl.model.*;

import java.io.IOException;
import java.net.Socket;

/**
 * Server TCP providing Morse Translation services
 *
 * @author Piotr Musioł
 * @version 1.0
 */
public class Main
{

    /**
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        try (ServerTCP server = new ServerTCP())
        {
            System.out.println("Server started");

            Coder coder = new Coder();
            Decoder decoder = new Decoder();
            boolean connection = true;

            while (true)
            {
                Socket socket = server.serverSocket.accept();
                try (SingleService singleService = new SingleService(socket))
                {
                    singleService.realize(coder, decoder, connection);
                } catch (IOException e)
                {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
