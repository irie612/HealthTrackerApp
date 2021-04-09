package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class MealController implements Initializable {

    ObservableList list= FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML private ChoiceBox<String> mealType;

    @FXML private Button switchDashboard;

    @FXML private Button switchExercise;

    @FXML private Button switchGroup;

    @FXML private Button switchAccount;

    //@FXML private TextArea myMeal;

    public void switchScene(ActionEvent event, String filename) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filename));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Choicebox meal type
        mealType.getItems().addAll("Breakfast", "Lunch", "Dinner", "Snack", "Drink");

        switchDashboard.setOnAction(event -> {
            try {
                switchScene(event, "../resources/views/DashboardView.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        switchExercise.setOnAction(event -> {
            try {
                switchScene(event, "../resources/views/ExerciseView.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        switchGroup.setOnAction(event -> {
            try {
                switchScene(event, "../resources/views/GroupView.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        switchAccount.setOnAction(event -> {
            try {
                switchScene(event, "../resources/views/AccountView.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // my meal
        //myMeal.setText("MY MEAL : " );
    }


}
