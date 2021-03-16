package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;


public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginBtnOnClick() {
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
        System.out.println(username + ", " + password);
    }

    @FXML
    private void exitBtnOnClick() {
        System.exit(0);
    }

}
