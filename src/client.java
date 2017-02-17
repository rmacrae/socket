import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by ryan on 2/10/17.
 */
public class client {
    public static void main(String[] args) throws IOException {
        System.out.println("Please specify the server name: ");
        BufferedReader ser = new BufferedReader(new InputStreamReader(System.in));
        String serv = ser.readLine();
        System.out.println();
        System.out.println("Please specify the port number: ");
        BufferedReader soc = new BufferedReader(new InputStreamReader(System.in));
        Integer sock = soc.read();
        System.out.println();
        System.out.println("Please specify the 1 for GET, 2 for PUT");
        BufferedReader choice = new BufferedReader(new InputStreamReader(System.in));
        Integer ch = choice.read();
        System.out.println();
        System.out.println("Please specify the file on the remote server that you want to ");
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(serv, sock);
        } catch (UnknownHostException e) {}
        catch (SocketException e){}
        catch (IOException e){}

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println("GET /index.html HTTP/1.0\n");
        while (in.readLine() != null) {
            System.out.println(in.readLine());
        }
    }
}
