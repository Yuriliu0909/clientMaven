package GUI;

import Client.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ClientUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // TODO Auto-generated method stub
        primaryStage.setTitle("client");

        HBox hbox = new HBox();
        Scene scene = new Scene(hbox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

        final TextField name = new TextField();
        name.setPromptText("Enter food number.");
        name.setPrefColumnCount(10);
        name.getText();
        GridPane.setConstraints(name, 0, 0);

        //Defining the Submit button
        Button start = new Button("ask");
        GridPane.setConstraints(start, 1, 0);
        start.setOnAction(value ->  {
            System.out.println("client request");
            Client client=new Client();
        });


        hbox.getChildren().addAll(name,start);
    }

    public static void main(String[] args) {

        Application.launch(args);
    }

}
