package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public Client() {
        String serverAddress = "localhost";
        int port = 9090;
        {
            try {
                //request server
                Socket clientSocket = new Socket(serverAddress, port);
                //Send a request to the server
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                printWriter.println("Berry");
                //store data into buffer
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //read string from buffer
                String answer = input.readLine();
                System.out.println(answer);
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Runs the client.
     */
    public static void main(String[] args) throws IOException {
        new Client();
    }
}
