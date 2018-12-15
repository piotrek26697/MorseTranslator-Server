package pl.polsl.server;

import java.io.*;
import java.net.ServerSocket;
import java.util.Properties;

/**
 * Class object represent the TCP server
 * @author Piotr Musioł
 * @version 1.0
 */
public class ServerTCP implements Closeable
{
    /**
     * port
     */
    private int port = 9090;

    /**
     * server's socket
     */
    ServerSocket serverSocket;

    ServerTCP() throws IOException
    {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(".properties"))
        {
            properties.load(input);
            port = Integer.parseInt(properties.getProperty("port"));
        } catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
        serverSocket = new ServerSocket(port);
    }

    /**
     *Closes stream and releases any system resources
     * @throws IOException if an I/O error occur
     */
    @Override
    public void close() throws IOException
    {
        Properties properties = new Properties();
        properties.setProperty("port", Integer.toString(port));
        try (FileOutputStream output = new FileOutputStream(".properties"))
        {
            properties.store(output, "Server config");
        } catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
        if (serverSocket != null)
            serverSocket.close();
    }
}
