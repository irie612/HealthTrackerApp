package sample.controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.LoginDatabase;
import sample.Main;
import sample.User;
import sample.UserDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label canLogIn;
    @FXML
    private VBox welcomeText;

    private UserDatabase userDatabase;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeAnimation();
    }

    @FXML
    public void loginBtnOnClick(ActionEvent event) throws IOException {
        LoginDatabase log;

        String username = usernameField.getText();
        String password = passwordField.getText();

        //check if username field is empty
        if (username.equals("")) {
            usernameField.setEffect(new DropShadow(5, Color.RED));
        } else {
            usernameField.setEffect(null);
        }

        //check if password field is empty
        if (password.equals("")) {
            passwordField.setEffect(new DropShadow(5, Color.RED));
        } else {
            passwordField.setEffect(null);
        }

        log = new LoginDatabase("src/sample/data/account.csv");
        userDatabase = new UserDatabase("src/sample/data/users.csv");


        try {
            log.loadElements();
            userDatabase.loadElements();
            User user = new User(username, password);
            if (log.containUser(user)) {
                if (userDatabase.getUserByUsername(user.getUsername()) != null) {
                    Main.currentUser = userDatabase.getUserByUsername(user.getUsername());
                    Main.switchView("../resources/views/DashboardView.fxml", event, getClass());
                } else
                    canLogIn.setText("System error");

            } else {
                canLogIn.setText("Invalid information given");
            }

        }
        catch (FileNotFoundException e){
            canLogIn.setText("error with database");
        }
    }

    @FXML
    private void exitBtnOnClick() {
        System.exit(0);
    }

    @FXML
    public void newUserOnClick(MouseEvent event) throws IOException {
        Main.switchView("../resources/views/registerView.fxml", event, getClass());
    }

    private void welcomeAnimation() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setFromValue(0.1);
        fadeTransition.setToValue(10);
        fadeTransition.setNode(welcomeText);
        fadeTransition.play();
    }
}
