package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Exercise;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExerciseController implements Initializable {

    public Label exerciseTotalLabel;
    public Label exerciseGoalLabel;
    public TableView exerciseTable;
    public Button prevDatesBtn;
    public Button leftDateBtn;
    public Button middleDateBtn;
    public Button rightDateBtn;
    public Button nextDatesBtn;

    private LocalDate historyDate;
    private DateTimeFormatter dateFormat;
    private DateTimeFormatter timeFormat;

    private ArrayList<Exercise> exerciseList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        historyDate = LocalDate.now();
        dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    }

    public void switchToDashboard(MouseEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/DashboardView.fxml"));
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }
    public void switchToMeal(MouseEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("../resources/views/MealView.fxml"));
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

    public void prevDatesOnClick(ActionEvent actionEvent) {
    }

    public void nextDatesOnClick(ActionEvent actionEvent) {
    }


}
