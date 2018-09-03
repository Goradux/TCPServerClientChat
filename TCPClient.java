import java.io.*;
import java.net.*;

/**
 *
 * @author Albert Asratyan
 */

public class TCPClient {
    public static void main(String argv[]) throws Exception {
        String serverSentence;
        String sentence;

        System.out.println("Please input an IP address.");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        try {
            // Connecting to a socket
            Socket clientSocket = new Socket(inFromUser.readLine(), 8080);
            System.out.println("Connection established!");

            while (true) {
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // Check if the server has sent anything
                if (inFromServer.ready() == true) {
                    serverSentence = inFromServer.readLine();
                    System.out.println("SERVER: " + serverSentence);
                    // Close the socket, if the server has said so
                    if (serverSentence.equals("quit")) {
                        clientSocket.close();
                        break;
                    }
                }

                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                // Check if the user has sent anything
                if (inFromUser.ready() == true) {
                    sentence = inFromUser.readLine();
                    outToServer.writeBytes(sentence + '\n');
                    // Close the socket, if the user has said so
                    if (sentence.equals("quit")) {
                        clientSocket.close();
                        break;
                    }
                }

                // Slow down the while loop to prevent overloading processor
                Thread.sleep(50);
            }
        }
        catch (UnknownHostException a){
            System.out.println("Wrong host address. Shutting down.");
            System.exit(0);
        }
    }
}
