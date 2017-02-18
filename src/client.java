import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        System.out.println("Please specify the server name: ");
        BufferedReader ser = new BufferedReader(new InputStreamReader(System.in));
        String serv = ser.readLine();
        System.out.println();
        System.out.println("Please specify the port number: ");
        BufferedReader soc = new BufferedReader(new InputStreamReader(System.in));
        Integer sock = Integer.valueOf(soc.readLine());
        System.out.println();
        System.out.println("Please specify the 1 for GET, 2 for PUT");
        BufferedReader choice = new BufferedReader(new InputStreamReader(System.in));
        Integer ch = Integer.valueOf(choice.readLine());
        String action;
        if (ch.equals(1)){
            action = "GET";
        }
        else {
            action = "PUT";
        }
        System.out.println();
        System.out.println("Please specify the file on the remote server that you want to GET/PUT: ");
        BufferedReader fil = new BufferedReader(new InputStreamReader(System.in));
        String file = fil.readLine();
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(serv, sock);
        } catch (UnknownHostException e) {}
        catch (SocketException e){}
        catch (IOException e){}

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println("GET /" + file + " HTTP/1.0\n");
        while (in.readLine() != null) {
            System.out.println(in.readLine());
        }
    }
}
