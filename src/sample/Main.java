package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resources/views/userGroupView.fxml"));
//        "resources/groupsListCellView.fxml"
//        "resources/views/groupsView.fxml"
        primaryStage.setTitle("Health Tracker");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMinWidth(800);
//        primaryStage.setScene(new Scene(root, 700, 430));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
