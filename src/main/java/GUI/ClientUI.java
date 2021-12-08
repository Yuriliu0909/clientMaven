package GUI;

import Client.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientUI extends Application {
    private HBox hbox = new HBox();
    private  TextField name = new TextField();
    private TextArea textArea = new TextArea();

    @Override
    public void start(Stage primaryStage) throws Exception {

        // TODO Auto-generated method stub
        primaryStage.setTitle("client");


        Scene scene = new Scene(hbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        name.setPromptText("Enter your username for the group chat: ");
        name.setPrefColumnCount(10);
        String username = name.getText();
        GridPane.setConstraints(name, 0, 0);


        //Defining the Submit button
        Button start = new Button("add client");
        GridPane.setConstraints(start, 1, 0);
        start.setOnAction(value ->  {
            System.out.println("client request");
            try {
              connect(username);
//                Socket socket = new Socket("localhost", 8080);
//              Client client = new Client(socket, "yuri");
//                Client client = new Client(new Socket("localhost", 8080),username);
//                client.listenForMessage();
//                client.sendMessage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        hbox.getChildren().addAll(name,start,textArea);
    }

    public void connect(String username) throws IOException {
        System.out.println(username);
        Socket socket = new Socket("localhost", 8080);
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
