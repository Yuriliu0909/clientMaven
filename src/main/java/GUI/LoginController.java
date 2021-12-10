package GUI;

import Client.SocketClient;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_ipaddress;
    @FXML
    private TextField tf_port;
    @FXML
    private TextField input;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField receiverName;

    private SocketClient client;

    private String username;

    @FXML
    public void doLogin(Event event){
        username = tf_username.getText();
        String password = tf_password.getText();
        String ipAddress = tf_ipaddress.getText();
        int portNumber = Integer.parseInt(tf_port.getText());
        try {
            client = new SocketClient(ipAddress, portNumber, textArea);
            client.start();
            //LOGIN:<username>:<password>
            //TODO common method to build protocol messages
            String login= "LOGIN:"+username+":"+password+"\n";
            client.sendMessage(login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sendMsg(Event event) {
        String rcvName = receiverName.getText();
        String msg = input.getText();
        //SEND:<sender>:<receiver>:message
        String cmd = "SEND:"+username+":"+rcvName+":"+msg;
        client.sendMessage(cmd);
    }
}
