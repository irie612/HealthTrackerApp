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

    @FXML private Button switchExercise;

    @FXML private Button switchGroup;

    @FXML private Button switchAccount;

    //@FXML private TextArea myMeal;

    public void switchToDashboard(ActionEvent event) throws IOException{
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("resources/views/sample.fxml"));
        System.out.println("got fxml file");
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Choicebox meal type
        mealType.getItems().addAll("Breakfast", "Lunch", "Dinner", "Snack", "Drink");

        // my meal
        //myMeal.setText("MY MEAL : " );
    }


}
