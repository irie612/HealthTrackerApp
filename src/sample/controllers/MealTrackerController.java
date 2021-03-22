package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import sample.Main;

public class MealTrackerController implements Initializable {
ObservableList list= FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
    }

    @FXML private ChoiceBox<String> mealType;

    @FXML private Button switchHome;

    @FXML private Button switchExercise;

    @FXML private Button switchGroup;

    @FXML private Button switchAccount;

    //@FXML private TextArea myMeal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Choicebox meal type
        mealType.getItems().addAll("Breakfast", "Lunch", "Dinner", "Snack", "Drink");

        switchHome.setOnAction(event -> Main.window.setScene(Main.dashboardScene));
        switchExercise.setOnAction(event -> Main.window.setScene(Main.exerciseScene));
        switchGroup.setOnAction(event -> Main.window.setScene(Main.groupScene));
        switchAccount.setOnAction(event -> Main.window.setScene(Main.accountScene));


        // my meal
        //myMeal.setText("MY MEAL : " );
    }


}
