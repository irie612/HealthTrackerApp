package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.LoginDatabase;
import sample.UserDatabase;
import sample.Users;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterController {

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

    String regex = "^(.+)@(.+)$";



    @FXML
    private void registerBtnOnClick() throws IOException {

        LoginDatabase log;
        UserDatabase users;
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPwdField.getText();

        boolean isPwdConfirmed = password.equals(confirmPassword);
        boolean isUsernameEmpty = username.isEmpty();
        boolean isEmailEmpty = email.isEmpty();
        boolean isPasswordEmpty = password.isEmpty();
        boolean isConfirmPasswordEmpty = confirmPassword.isEmpty();

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
        if(isEmailEmpty || isUsernameEmpty || isPasswordEmpty|| isConfirmPasswordEmpty || !matcher.matches() || !isPwdConfirmed){
            userRegister.setText("Information missing or Invalid information");
        }
        else{
            userRegister.setText("user has been registered successfully");

            log = new LoginDatabase("src/sample/data/account.csv", ",");
            users = new UserDatabase("src/sample/data/users.csv", ",");

            log.insert(new Users(username, password));
            users.insert(new Users(username, email, 0, 0));
        }
    }

   @FXML
    public void backToLoginOnClick(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../resources/views/loginview.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void exitBtnOnClick() {
        System.exit(0);
    }
}
