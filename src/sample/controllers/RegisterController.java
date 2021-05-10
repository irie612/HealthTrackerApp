package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import sample.LoginDatabase;
import sample.Main;
import sample.User;
import sample.UserDatabase;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterController implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPwdField;
    @FXML
    private Label userRegister;
    @FXML
    private Label isUNameUsed;

    private LoginDatabase log;

    private UserDatabase users;

    String regex = "^(.+)@(.+)$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            log = new LoginDatabase("src/sample/data/account.csv");
            log.loadElements();

            users = new UserDatabase("src/sample/data/users.csv");
            users.loadElements();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void registerBtnOnClick(ActionEvent event) throws IOException {

        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPwdField.getText();

        boolean isPwdConfirmed = password.equals(confirmPassword);
        boolean isUsernameEmpty = username.isEmpty();
        boolean isEmailEmpty = email.isEmpty();
        boolean isPasswordEmpty = password.isEmpty();
        boolean isConfirmPasswordEmpty = confirmPassword.isEmpty();
        boolean isUserNameUsed = users.isUserNameUsed(username);

        // check emailField is empty
        if(isEmailEmpty){
            emailField.setEffect(new DropShadow(5, Color.RED));
        }
        else{
            emailField.setEffect(null);
        }

        // email validation

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){

            emailField.setEffect(new DropShadow(5, Color.RED));
        }
        else{

            emailField.setEffect(new DropShadow(5, Color.GREEN));
        }

        // check userField is empty
        if (isUsernameEmpty) {
            usernameField.setEffect(new DropShadow(5, Color.RED));

        }
        else {
            usernameField.setEffect(null);
        }

        if (isUserNameUsed) {
            usernameField.setEffect(new DropShadow(5, Color.RED));
            isUNameUsed.setText("This username is already taken");
        }

        // check passwordField is empty
        if(isPasswordEmpty){
            passwordField.setEffect(new DropShadow(5, Color.RED));
        }
        else{
            usernameField.setEffect(null);
        }

        // check confirmPwdField is empty
        if(isConfirmPasswordEmpty){
            confirmPwdField.setEffect(new DropShadow(5,Color.RED));
        }
        else{
            confirmPwdField.setEffect(null);
        }

        // check if passwordFiled and confirmPwdField match
        if(!isPwdConfirmed){

            passwordField.setEffect(new DropShadow(5, Color.RED));
            confirmPwdField.setEffect(new DropShadow(5, Color.RED));
        }
        else{

            passwordField.setEffect(new DropShadow(5, Color.GREEN));
            confirmPwdField.setEffect(new DropShadow(5, Color.GREEN));
        }

        // save to database
        if (isEmailEmpty || isUsernameEmpty || isPasswordEmpty || isConfirmPasswordEmpty || !matcher.matches() || !isPwdConfirmed
                || isUserNameUsed) {
            userRegister.setText("Information missing or Invalid information");
        } else {
            userRegister.setText("user has been registered successfully");

            log.insert(new User(username, password));
            User newUser = new User("", username, email, 0, 0, LocalDate.of(1901, 1, 1));
            users.insert(newUser);
            Main.currentUser = newUser;
            Main.switchView("../resources/views/EditAccount.fxml", event, getClass());
        }
    }

   @FXML
    public void backToLoginOnClick(ActionEvent event) throws IOException {
       Main.switchView("../resources/views/loginview.fxml", event, getClass());
    }

    @FXML
    private void exitBtnOnClick() {
        System.exit(0);
    }

}
