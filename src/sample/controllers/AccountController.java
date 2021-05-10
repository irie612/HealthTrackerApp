package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import sample.Main;

import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class AccountController {

    @FXML
    private Label name;

    @FXML
    private Label username;

    @FXML
    private Label email;

    @FXML
    private Label DoB;

    @FXML
    private Label height;

    @FXML
    private Label weight;

    @FXML
    private Label BMI;

    @FXML
    private Button editBtn;


    public void switchToEditAccount(MouseEvent event) throws IOException {
        Main.switchView("../resources/views/EditAccount.fxml", event, getClass());
    }


    public void switchToDashboard(MouseEvent event) throws IOException {
//        Main.switchView("/views/healthtracker.fxml", event, getClass());
    }


    public void switchToMeal(MouseEvent event) throws IOException {
        Main.switchView("../resources/views/mealView.fxml", event, getClass());
    }

    public void switchToGroup(MouseEvent event) throws IOException {
        Main.switchView("../resources/views/UserGroup.fxml", event, getClass());
    }

    @FXML
    public void initialize() {

        if (Main.currentUser != null) {
            name.setText(Main.currentUser.getName());
            username.setText(Main.currentUser.getUsername());
            email.setText(Main.currentUser.getEmail());

            String usrHeight = String.valueOf(Main.currentUser.getHeight());
            height.setText(usrHeight + "m");

            String usrWeight = String.valueOf(Main.currentUser.getWeight());
            weight.setText(usrWeight + "kg");

            String strDate = Main.currentUser.getDoB().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            DoB.setText(strDate);

            String usrBMI = String.valueOf(Main.currentUser.getBMI());

            if (Main.currentUser.getBMI() >= 25.0) {
                BMI.setText(usrBMI + " - Overweight \nLower calorie goal");
            } else if (Main.currentUser.getBMI() <= 18.5) {
                BMI.setText(usrBMI + " -  Underweight \nIncrease calorie goal");
            } else {
                BMI.setText(usrBMI + " - Healthy Weight \nContinue with calorie goal");
            }
        }

    }


    public void navAccountBtnOnClick(MouseEvent event) throws IOException {
        Main.switchView("../resources/views/Account.fxml", event, getClass());
    }

    public void navGroupsBtnOnClick(MouseEvent event) throws IOException {
        Main.switchView("../resources/views/groupsView.fxml", event, getClass());
    }

    public void navExerciseBtnOnClick(MouseEvent event) {
    }

    public void navMealBtnOnClick(MouseEvent event) throws IOException {
        Main.switchView("../resources/views/mealView.fxml", event, getClass());
    }

    public void navDashboardBtnOnClick(MouseEvent event) {

    }

    public void logout(MouseEvent event) throws IOException {
        Main.currentUser = null;
        Main.switchView("../resources/views/loginView.fxml", event, getClass());
    }
}
