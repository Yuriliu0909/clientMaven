package Client;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private TextArea textArea;
    private String input;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;

    public Client(Socket socket, String username,TextArea textArea, String input) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            this.textArea = textArea;
            this.input = input;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
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
            //Send request to the server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(this.input);
            //Receive the response from the server
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader input = new BufferedReader(inputStreamReader);
            textArea.appendText("Server response:\n");
    }

    public void sendMessage() {
        try {
            this.bufferedWriter.write(this.username);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);

            while(this.socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                this.bufferedWriter.write(this.username + " " + messageToSend);
                this.bufferedWriter.newLine();
                this.bufferedWriter.close();
            }
        } catch (IOException var3) {
            this.closeEverything(this.socket, this.bufferedReader, this.bufferedWriter);
        }

    }

    public void listenForMessage() {
        (new Thread(new Runnable() {
            public void run() {
                while(Client.this.socket.isConnected()) {
                    try {
                        String msgFromGroupChat = Client.this.bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException var3) {
                        Client.this.closeEverything(Client.this.socket, Client.this.bufferedReader, Client.this.bufferedWriter);
                    }
                }

            }
        })).start();
    }


    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
