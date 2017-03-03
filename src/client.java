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


         // Save command line arguments into separate variables.
        String serv = args[0];
        Integer port = Integer.parseInt(args[1]);
        String action = args[2];
        String file = args[3];


        // Create socket for client to connect to server.
        Socket clientSocket = null;

        // Open connection to server using hostname and port given in command line arguments
        try {
            clientSocket = new Socket(serv, port);
        } catch (UnknownHostException e) {}
        catch (SocketException e){}
        catch (IOException e){}

        // Create bufferedreader to read in responses from server through the socket.
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        // Create printwriter to send requests and files to client through the socket.
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Request to send to the server
        out.println(action + " /" + file + " HTTP/1.0\n\r");

        // File to open if a put request is specified.  Will read in the file and send it to the server
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

        // Read in the response from the server
        String redl = in.readLine();
        while (redl != null) {
            System.out.println(redl);
            redl = in.readLine();
        }
        // Close the socket
		clientSocket.close();
    }
}
