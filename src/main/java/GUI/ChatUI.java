package GUI;

import Client.Client;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ChatUI extends Application {
    private HBox hbox = new HBox();
    private VBox vbox = new VBox();
    private  TextField name = new TextField();
    private  TextField password = new TextField();
    private  TextField serverip = new TextField();
    private  TextField serverport = new TextField();
    private TextArea textArea = new TextArea();
    private int portNo;
    private String ipaddress;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // TODO Auto-generated method stub
        primaryStage.setTitle("client");

        Scene scene = new Scene(hbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        name.setPromptText("Enter your username: ");
        name.setPrefColumnCount(10);

        password.setPromptText("Enter your password: ");

        serverip.setPromptText("Enter Server IP address: ");

//        InetAddress ipaddress = InetAddress.getByName(serverip.getText());
        serverport.setPromptText("Enter Server port,eg:0808: ");

        GridPane.setConstraints(name, 0, 0);

        //Defining the Submit button
        Button start = new Button("add client");
        GridPane.setConstraints(start, 1, 0);

        vbox.getChildren().addAll(name,password,serverip,serverport,start);
        vbox.setSpacing(50);
        VBox.setMargin(name, new Insets(10, 10, 10, 10));
        VBox.setMargin(password, new Insets(10, 10, 10, 10));
        VBox.setMargin(serverip, new Insets(10, 10, 10, 10));
        VBox.setMargin(serverport, new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(vbox,textArea);


        start.setOnAction(value ->  {
            readInputValues();
            System.out.println("client request");
            service.start();
        });
    }

    Service service = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    Socket socket = new Socket(ipaddress, portNo);
                    Client client = new Client(socket, "yuri");
                    client.sendMessage();
                    client.listenForMessage();
                    return null;
                }
            };
        }
    };

    private void readInputValues() {
        String username = name.getText();
        String pwd=password.getText();
        ipaddress = serverip.getText();
        portNo = getIntFromTextField(serverport);
    }

    public static int getIntFromTextField(TextField serverport) {
        String port = serverport.getText();
        return Integer.parseInt(port);
    }

    public void connect(String username, InetAddress ip,Integer port) throws IOException {
        System.out.println(username);
        Socket socket = new Socket(ip,port);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
        writer.println(username);
        InputStreamReader inputStreamReader= new InputStreamReader(socket.getInputStream());
        BufferedReader input = new BufferedReader(inputStreamReader);
        while(socket.isConnected()){
            String message=input.readLine();
            if(message!=null){
                textArea.appendText(message+"\n");
            }
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
