package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.User;
import sample.UserDatabase;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class EditAccountController {

    @FXML
    private TextField editName;

    @FXML
    private Label username;

    @FXML
    private TextField editEmail;

    @FXML
    private TextField editDoB;

    @FXML
    private TextField editHeight;

    @FXML
    private TextField editWeight;

    @FXML
    private Label BMI;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtn;

    private DateTimeFormatter formatter;

    private UserDatabase userDatabase;

    public void initialize() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            userDatabase = new UserDatabase("src/sample/data/users.csv");
            userDatabase.loadElements();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Main.currentUser != null) {
            editName.setText(Main.currentUser.getName());
            username.setText(Main.currentUser.getUsername());
            editEmail.setText(Main.currentUser.getEmail());

            String strDate = Main.currentUser.getDoB().format(formatter);
            editDoB.setText(strDate);

            String usrHeight = String.valueOf(Main.currentUser.getHeight());
            editHeight.setText(usrHeight);

            String usrWeight = String.valueOf(Main.currentUser.getWeight());
            editWeight.setText(usrWeight);

            String usrBMI = String.valueOf(Main.currentUser.getBMI());
            BMI.setText(usrBMI);
        }

    }

    public void returnToAccountPage(MouseEvent event) throws IOException {
        Main.switchView("../resources/views/Account.fxml", event, getClass());
    }

    public void saveDetails(MouseEvent save) throws IOException {
        String editedName = editName.getText();

        String editedEmail = editEmail.getText();

        LocalDate editedDoB;

        editedDoB = LocalDate.from(formatter.parse(editDoB.getText()));

        double editedHeight = Double.parseDouble(editHeight.getText());
        double editedWeight = Double.parseDouble(editWeight.getText());

        User newDetails = new User(editedName, Main.currentUser.getUsername(), editedEmail, editedHeight, editedWeight, editedDoB);
        userDatabase.update(Main.currentUser, newDetails);
        Main.currentUser = newDetails;
        Main.switchView("../resources/views/Account.fxml", save, getClass());

    }

}
