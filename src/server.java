import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Tyler Brown, Ryan MacRae
 * CPSC 3600
 * Programming Assignment 4
 * February 22, 2017
 */
public class server {
    public static void main(String args[]) throws Exception {
        BufferedReader portReader = new BufferedReader(new InputStreamReader(System.in));
        String portValue;
        int portNumber = -1;

        //ask for port number
        while(true) {
            System.out.print("Please specify the port number: ");
            portValue = portReader.readLine();
            try {
                portNumber = Integer.parseInt(portValue);
            } catch (NumberFormatException e) {
                System.out.println("That is an invalid port.");
            }
            if (portNumber > -1)
                break;
        }

        System.out.print("Debugger: Port number " + portNumber + " was accepted!\n");

        //initialize the server
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);

        }
        catch (UnknownHostException e) {}
        catch (SocketException e) {}
        catch (IOException e) {}

        //wait for a client connection
        while (true) {
            //accepting the connection and create a new socket
            Socket theConnection = serverSocket.accept();

            // Prepare an object for reading from the input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(theConnection.getInputStream()));

            // Prepare an object for writing to the output stream
            PrintWriter out = new PrintWriter(theConnection.getOutputStream(), true);

            // Read data from the input stream
            String fromClient = in.readLine();

            // Print the data on the screen
            System.out.println("received from client: " + fromClient);

            //parse up the line
            String[] inFromClient = fromClient.split(" ");

            // Write the data on the output stream
            File f = new File(inFromClient[1]);
            if (inFromClient[0].equals("GET")) {
                if (f.exists()) {
                    BufferedReader inFromFile = new BufferedReader(new FileReader(f));
                    String sentence;
                    out.println("HTTP/1.0 200 OK\r\n");
                    while ((sentence = inFromFile.readLine()) != null) {
                        out.println(sentence);

                    }
                } else {
                    out.println("HTTP/1.0 404 Not Found\r\n\r\n File Not Found");
                }
            }
            else if (inFromClient[0].equals("PUT")) {
                //This is where put should go, but unsure how it is set up
            }

            //close connection
            theConnection.close();
            in.close();
            out.close();


        }

    }
}
