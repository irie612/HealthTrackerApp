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
    public static boolean isFullScreen;
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 650;


    @Override
    public void start(Stage primaryStage) throws Exception {

//        Parent mealRoot = FXMLLoader.load(getClass().getResource("resources/views/mealView.fxml"));
//        Scene mealScene = new Scene(mealRoot);


//        "resources/views/mealView.fxml

//        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("resources/views/DashboardView.fxml"));
//        dashboardScene = new Scene(dashboardRoot);
//
//        Parent loginRoot = FXMLLoader.load(getClass().getResource("resources/views/LoginView.fxml"));
//        loginScene = new Scene(loginRoot);
//
        Parent registerRoot = FXMLLoader.load(getClass().getResource("resources/views/RegisterView.fxml"));
        Scene registerScene = new Scene(registerRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);

//
//        Parent mealRoot = FXMLLoader.load(getClass().getResource("resources/views/MealView.fxml"));
//        mealScene = new Scene(mealRoot);
//
//        Parent exerciseRoot = FXMLLoader.load(getClass().getResource("resources/views/ExerciseView.fxml"));
//        exerciseScene = new Scene(exerciseRoot);


//        Parent groupRoot = FXMLLoader.load(getClass().getResource("resources/views/userGroupView.fxml"));
//        Scene groupScene = new Scene(groupRoot);

//        Parent groupsRoot = FXMLLoader.load(getClass().getResource("resources/views/groupsView.fxml"));
//        Scene groupsScene = new Scene(groupsRoot);

        primaryStage.setScene(registerScene);
        primaryStage.getIcons().add(new Image("sample/resources/images/baseline_fitness_center_white_24dp.png"));
        primaryStage.setFullScreen(false);
        isFullScreen = false;
        primaryStage.show();
    }


    public static void switchView(String view, Event event, Class c) throws IOException {
        Parent parent = FXMLLoader.load(c.getResource(view));
        Scene scene = new Scene(parent, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


//    public static void switchToGroups(Event event, Class c) throws IOException {
//        Parent parent = FXMLLoader.load(c.getResource("../resources/views/groupsView.fxml"));
//        Scene scene = new Scene(parent);
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(scene);
//        window.show();
//    }
//
//    public static void switchToMeal(Event event, Class c) throws IOException {
//        Parent parent = FXMLLoader.load(c.getResource("../resources/views/mealView.fxml"));
//        Scene scene = new Scene(parent);
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(scene);
//        window.show();
//    }

    public static void main(String[] args) {
        launch(args);
    }
}