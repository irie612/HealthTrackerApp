package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {


    public static UserGroup userGroup;
    public static User currentUser;
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 650;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent registerRoot = FXMLLoader.load(getClass().getResource("resources/views/RegisterView.fxml"));
        Scene registerScene = new Scene(registerRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        primaryStage.setScene(registerScene);
        primaryStage.getIcons().add(new Image("sample/resources/images/baseline_fitness_center_white_24dp.png"));
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    public static void switchView(String view, Event event, Class c) throws IOException {
        Parent parent = FXMLLoader.load(c.getResource(view));
        Scene scene = new Scene(parent, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}