package sample.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    public void switchToMeal(MouseEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/MealView.fxml"));
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }

    public void switchToExercise(MouseEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/ExerciseView.fxml"));
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }

    public void switchToGroup(MouseEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/UserGroupView.fxml"));
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }

    public void switchToAccount(MouseEvent event) throws IOException {
        //TODO add file path for account view
        /*Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/.fxml"));
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();*/
    }
}
