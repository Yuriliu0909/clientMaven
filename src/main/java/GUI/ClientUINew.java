package GUI;

import Client.SocketClient;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class ClientUINew extends Application {
    private HBox hbox = new HBox();
    private VBox vbox = new VBox();
    private  TextField name = new TextField();
    private  TextField password = new TextField();
    private  TextField serverip = new TextField();
    private  TextField serverport = new TextField();
    private TextArea result = new TextArea();
    private TextField chatfield = new TextField();
    private TextField receiverName= new TextField();;
    private int portNo;
    private String ipaddress;
    private String username;
    private String pwd;
    private String chattext;
    private String input;
    private String receiver;
    private SocketClient client;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // TODO Auto-generated method stub
        primaryStage.setTitle("client");

        Scene scene = new Scene(hbox, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        name.setPromptText("Enter your username: ");
        name.setPrefColumnCount(10);
        password.setPromptText("Enter your password: ");
        serverip.setPromptText("Enter Server IP address: ");
        serverport.setPromptText("Enter Server port,eg:0808: ");

        GridPane.setConstraints(name, 0, 0);
        Button start = new Button("add client");
        GridPane.setConstraints(start, 1, 0);

        chatfield.setPromptText("Enter words: ");
        receiverName.setPromptText("who send message: ");
        Button chat = new Button("start chat");
        GridPane.setConstraints(start, 1, 0);

        vbox.getChildren().addAll(name,password,serverip,serverport,start,chatfield,receiverName,chat);
        vbox.setSpacing(50);
        VBox.setMargin(name, new Insets(10, 10, 10, 10));
        VBox.setMargin(password, new Insets(10, 10, 10, 10));
        VBox.setMargin(serverip, new Insets(10, 10, 10, 10));
        VBox.setMargin(serverport, new Insets(10, 10, 10, 10));
        VBox.setMargin(chatfield, new Insets(5, 5, 5, 5));
        VBox.setMargin(receiverName, new Insets(5, 5, 5, 5));
        vbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(vbox, result);

        start.setOnAction(value ->  {
            readInputValues();
            doLogin();

        });


        chat.setOnAction(value ->  {
            readInputValues();
            sendMsg();
            System.out.println("start chat");
        });
    }

    //login method
    public void doLogin(){
        try {
            client = new SocketClient(ipaddress,portNo,result);
            client.start();
            //LOGIN:<username>:<password>
            //TODO common method to build protocol messages
            String login= "LOGIN:"+username+":"+pwd+"\n";
            client.sendMessage(login);
            System.out.println("client request");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //send message method
    public void sendMsg() {
        //SEND:<sender>:<receiver>:message
        String msg = "SEND:"+username+":"+receiver+":"+input;
        client.sendMessage(msg);
    }

    private void readInputValues() {
        username = name.getText();
        pwd=password.getText();
        ipaddress = serverip.getText();
        portNo = getIntFromTextField(serverport);
        input = chatfield.getText();
        receiver= receiverName.getText();
        chattext = result.getText();
    }

    public static int getIntFromTextField(TextField serverport) {
        String port = serverport.getText();
        return Integer.parseInt(port);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
