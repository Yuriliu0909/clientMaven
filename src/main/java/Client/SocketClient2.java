package Client;

import javafx.scene.control.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient2 extends Thread {
    private TextArea textArea;
    private String serverAddress = "127.0.01";
    private static final int PORT = 8585;
    private String input;


    public SocketClient2(TextArea textArea, String input) {
       this.textArea = textArea;
       this.input = input;

    }

    @Override
    public void run() {
        try {
            connect();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void connect() throws IOException{
        try (Socket clientSocket = new Socket(serverAddress, PORT)) {
            //Send request to the server
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            writer.println(this.input);
            //Receive the response from the server
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader input = new BufferedReader(inputStreamReader);
            textArea.appendText("Server response:\n");
        }
    }
}
