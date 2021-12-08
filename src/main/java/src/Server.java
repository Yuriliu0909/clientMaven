package src;

import Client.Client;
import Client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{
            //server will be running infinitely until closed
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(Client.clientHandler);
                thread.start();
            }
        }catch(IOException e){
            closeServerSocket();
        }
    }

    public void closeServerSocket(){
        try{
            if(serverSocket != null) {
                serverSocket.close();
            }
    }catch(IOException e){
            e.printStackTrace();
        }
}

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
