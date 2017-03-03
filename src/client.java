import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Ryan MacRae, Tyler Brown
 * February 18, 2017
 * CPSC 3600
 * Programming Assignment 4
 */
public class client {
    public static void main(String[] args) throws IOException {
        String serv = args[0];
        Integer port = Integer.parseInt(args[1]);
        String action = args[2];
        String file = args[3];
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(serv, port);
        } catch (UnknownHostException e) {}
        catch (SocketException e){}
        catch (IOException e){}

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(action + " /" + file + " HTTP/1.0\n\r");
        if(action.equals("PUT")) {
                BufferedReader inFromFile = new BufferedReader(new FileReader("test.txt"));
                String sentence = inFromFile.readLine();
                while (sentence != null) {
                    System.out.println(sentence);
                    out.println(sentence);
                    sentence = inFromFile.readLine();
                }
                out.println("\0");
                out.flush();
            }

        String redl = in.readLine();
        while (redl != null) {
            System.out.println(redl);
            redl = in.readLine();
        }
		clientSocket.close();
    }
}
