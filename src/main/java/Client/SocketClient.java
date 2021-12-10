package Client;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;

public class SocketClient extends Thread{
    private TextArea textArea;
    private static Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public SocketClient(String serverAddress, int port, TextArea textArea) throws IOException {
        socket = new Socket(serverAddress, port);
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.textArea = textArea;
    }

    @Override
    public void run() {
        messageListener();
    }

    public void messageListener() {
        //Continuously listening for the incoming messages.
        while(socket.isConnected()) {
            try {
                String string = bufferedReader.readLine();
                if(string != null) {
                    System.out.println("Server: "+string);
                    textArea.appendText(string+"\n");
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        //This thread is used to send messages to the server
        //Thread is destroyed by the JVM when the run method completes
        new Thread(() -> printWriter.println(message)).start();
    }

}
