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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.LoginDatabase;
import sample.Users;

import java.io.FileNotFoundException;
import java.io.IOException;


public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label canLogIn;

    @FXML
    public void loginBtnOnClick(ActionEvent event) throws IOException{
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

        log = new LoginDatabase("src/sample/data/account.csv",";");


        try{
            log.loadElements();
            Users user = new Users(username, password);
            if(log.containUser(user)){
                Parent parent = FXMLLoader.load(getClass().getResource("../resources/views/UserGroupView.fxml"));
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
            else{
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
        Parent parent = FXMLLoader.load(getClass().getResource("../resources/views/registerView.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
