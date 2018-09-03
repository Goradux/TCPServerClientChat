import java.io.*;
import java.net.*;

/**
 *
 * @author Albert Asratyan
 */

public class TCPServer {
    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String sentence;

        // QOL line, if the code is run on two different machines
        System.out.println("Your local IP address is " + InetAddress.getLocalHost().getHostAddress());

        // Creating a socket
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket connectionSocket = serverSocket.accept();
        System.out.println("Client has connected. You can start chatting!");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            // Checks if the client has sent anything
            if (inFromClient.ready() == true) {
                clientSentence = inFromClient.readLine();
                System.out.println("CLIENT: " + clientSentence);
                // Close the socket, if client has said so
                if (clientSentence.equals("quit")) {
                   connectionSocket.close();
                   serverSocket.close();
                   break;
                }
            }

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            // Checks if the user has sent anything
            if (inFromUser.ready() == true) {
                sentence = inFromUser.readLine();
                outToClient.writeBytes(sentence + '\n');
                // Close the socket, if the user has said so
                if (sentence.equals("quit")) {
                    connectionSocket.close();
                    serverSocket.close();
                    break;
                }
            }

            // Slow down the while loop to prevent overloading processor
            Thread.sleep(50);
        }
    }
}
