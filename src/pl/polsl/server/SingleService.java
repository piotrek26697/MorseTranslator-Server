package pl.polsl.server;

import pl.polsl.model.*;

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

    public void realize(Coder coder, Decoder decoder, boolean connection)
    {
        try
        {
            while (connection)
            {
                String dataInput = input.readLine();
                char command = dataInput.toLowerCase().charAt(0);
                if (dataInput.length() >= 2)
                    dataInput = dataInput.substring(2);
                String dataOutput = new String("");

                switch (command)
                {
                    case 'd':
                        try
                        {
                            dataOutput = decoder.decode(dataInput);
                        } catch (DictionaryException e)
                        {
                            dataOutput = e.getMessage();
                        }
                        break;
                    case 'c':
                        try
                        {
                            dataOutput = coder.code(dataInput);
                        } catch (DictionaryException e)
                        {
                            dataOutput = e.getMessage();
                        }
                        break;
                    case 'h':
                        dataOutput = "Zasady translacji kodu Morse'a: krótki sygnał to '*', \n\r" +
                                "długi sygnał to '-'; pojedyncze litery oddzielone są pojedynczą spacją; \n\r" +
                                "wyrazy oddzielone są podwójną spacją.\n\r" +
                                "Komunikacja przy pomocy konsoli: [litera][pojedyncza spacja][tekst do translacji]. Litery:\n\r" +
                                "'d' - dekodowanie sygnału Morse'a\n\r" +
                                "'c' - kodowanie znaków\n\r" +
                                "'q' - wyjście z programu";
                        break;
                    case 'q':
                        connection = false;
                        break;
                    default:
                        dataOutput = "Invalid command, type 'H' for help";
                }
                output.println(dataOutput);
            }
        } catch (IOException e)
        {
            System.err.println(e.getMessage());
        } finally
        {
            try
            {
                socket.close();
            } catch (IOException e)
            {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void close() throws IOException
    {
        if (socket != null)
        {
            socket.close();
        }
    }
}
