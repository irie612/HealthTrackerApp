package Meal;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MealTrackerController implements Initializable {
ObservableList list= FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
    }

    @FXML private ChoiceBox mealType;

    @FXML private TextArea myMeal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Choicebox meal type
        mealType.getItems().addAll("Breakfast", "Lunch", "Dinner", "Snack", "Drink");

        // my meal
        myMeal.setText("MY MEAL : " );
    }


}
