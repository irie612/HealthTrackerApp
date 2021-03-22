package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage window;

    public static Scene mealScene, exerciseScene, dashboardScene, loginUser, registerScene, accountScene, groupScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("resources/views/MealTracker.fxml"));
        Scene mealScene = new Scene(root);
        primaryStage.setScene(mealScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
