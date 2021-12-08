package Client;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class BroadcastingClient {
    private static DatagramSocket socket = null;

    public static void main(String[] args) {
        try {
            broadcast("Hello", InetAddress.getByName("localhost"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(
            String broadcastMessage, InetAddress address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9090);
        socket.send(packet);
        socket.close();
    }
}
