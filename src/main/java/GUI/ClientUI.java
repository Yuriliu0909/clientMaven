package GUI;

import Client.Client;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
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
import java.net.Socket;


public class ClientUI extends Application {
    private HBox hbox = new HBox();
    private VBox vbox = new VBox();
    private  TextField name = new TextField();
    private  TextField password = new TextField();
    private  TextField serverip = new TextField();
    private  TextField serverport = new TextField();
    private TextArea result = new TextArea();
    private TextField chatfield = new TextField();
    private int portNo;
    private String ipaddress;
    private String username;
    private String pwd;
    private String chattext;
    private String input;


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

        Button chat = new Button("start chat");
        GridPane.setConstraints(start, 1, 0);

        vbox.getChildren().addAll(name,password,serverip,serverport,start,chatfield,chat);
        vbox.setSpacing(50);
        VBox.setMargin(name, new Insets(10, 10, 10, 10));
        VBox.setMargin(password, new Insets(10, 10, 10, 10));
        VBox.setMargin(serverip, new Insets(10, 10, 10, 10));
        VBox.setMargin(serverport, new Insets(10, 10, 10, 10));
        VBox.setMargin(chatfield, new Insets(5, 5, 5, 5));
        vbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(vbox, result);

        start.setOnAction(value ->  {
            readInputValues();
            System.out.println("client request");
            service1.start();
        });


        chat.setOnAction(value ->  {
            readInputValues();
            System.out.println("start chat");
            service2.start();
        });
    }

    Service service1 = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    Socket socket = new Socket(ipaddress, portNo);
                    Client client = new Client(socket, username, result,input);
                    client.listenForMessage();
                    return null;
                }
            };
        }
    };


    Service service2 = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    Socket socket = new Socket(ipaddress, portNo);
                    Client client = new Client(socket, username, result,input);
                    client.sendMessage();
                    return null;
                }
            };
        }
    };

    private void readInputValues() {
        username = name.getText();
        pwd=password.getText();
        ipaddress = serverip.getText();
        portNo = getIntFromTextField(serverport);
        input = chatfield.getText();
    }

    public static int getIntFromTextField(TextField serverport) {
        String port = serverport.getText();
        return Integer.parseInt(port);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
