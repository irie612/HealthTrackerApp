package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginBtnOnClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("")) {

        } else if (password.equals("")) {

        } else {

        }
        System.out.println(username + ", " + password);
    }

    @FXML
    private void exitBtnOnClick() {
        System.exit(0);
    }

}
