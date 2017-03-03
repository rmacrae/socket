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

        //initialize the server with the port specified in the command line arguments
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Integer.parseInt(args[0]));

        } catch (UnknownHostException e) {
        } catch (SocketException e) {
        } catch (IOException e) {
        }


        //wait for a client connection
        while (true) {
            //accept the connection and create a new socket
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
            File f = new File(System.getProperty("user.dir") + inFromClient[1]);
            if (inFromClient[0].contains("GET")) {
                if (f.isFile()) {
                    BufferedReader inFromFile = new BufferedReader(new FileReader(f));
                    String sentence;
                    // return the response if the file is found.
                    out.println("HTTP/1.0 200 OK\r\n");
                    while ((sentence = inFromFile.readLine()) != null) {
                        out.println(sentence);

                    }
                    // Close the bufferedreader
                    inFromFile.close();
                } else {
                    // Send this to client if the file is not found
                    out.println("HTTP/1.0 404 Not Found\r\n");
                }
            }
            // If the client requests to put a file, create the file and read in the file that they send
            // and save it to the file that they specify.
            else if (inFromClient[0].equals("PUT")) {
                File p = new File(System.getProperty("user.dir") + inFromClient[1]);
                BufferedWriter bw = new BufferedWriter(new FileWriter(p.getAbsoluteFile(), true));
                String r = in.readLine();
                while(!r.equals("\0")) {
                    System.out.println(r);
                    bw.write(r + "\n");
                    r = in.readLine();
                }
                bw.flush();
                // close the bufferedwriter and send a confirmation to client that file was created
                bw.close();
                out.println("200 OK File Created");
            }

            // close the connection and input/output
            theConnection.close();
            in.close();
            out.close();
        }

    }
}
