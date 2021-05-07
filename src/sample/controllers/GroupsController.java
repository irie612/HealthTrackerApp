package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupsController implements Initializable {

    @FXML
    public Button joinBtn;
    @FXML
    public Button createBtn;
    @FXML
    public TextField groupNameField;
    @FXML
    public TextField groupCodeField;
    @FXML
    public Label joinGroupMessageLabel;
    @FXML
    private ListView<String> groupsList;

    public static final String JOINED_GROUP_MSG = "Joined group successfully";

    public static final String JOIN_GROUP_FAILED_MSG = "Join group failed";

    public static final String GROUP_NOT_FOUND_MSG = "Group not found";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupsList.setPlaceholder(new Label("You are currently not in any groups"));
    }

    public void joinGroupOnClick(ActionEvent event) {
        String code = groupCodeField.getText();

        if (!code.isEmpty()) {

            System.out.println(code);

            //check code
            joinGroupMessageLabel.setText(JOINED_GROUP_MSG);
            joinGroupMessageLabel.setTextFill(Color.valueOf("#5cb85c"));
            joinGroupMessageLabel.setVisible(true);
//
//            joinGroupMessageLabel.setText(GROUP_NOT_FOUND_MSG);
//            joinGroupMessageLabel.setTextFill(Color.valueOf("#f0ad4e"));
//            joinGroupMessageLabel.setVisible(true);

//            joinGroupMessageLabel.setText(JOIN_GROUP_FAILED_MSG);
//            joinGroupMessageLabel.setTextFill(Color.valueOf("#d9534f"));
//            joinGroupMessageLabel.setVisible(true);

            //error colour - #d9534f
            //success colour - #5cb85c
            //group not found - #f0ad4e
        } else {
            joinGroupMessageLabel.setVisible(false);
        }

    }
}
