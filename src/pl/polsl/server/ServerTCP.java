package pl.polsl.server;

import java.io.*;
import java.net.ServerSocket;


public class ServerTCP implements Closeable
{
    final private int PORT = 9090;

    ServerSocket serverSocket;

    ServerTCP() throws IOException
    {
        serverSocket = new ServerSocket(PORT);
    }

    @Override
    public void close() throws IOException
    {
        if(serverSocket != null)
            serverSocket.close();
    }
}
