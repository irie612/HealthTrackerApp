package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Main;
import sample.UserGroup;
import sample.UserGroupDatabase;
import sample.Users;

import java.io.IOException;
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

    private UserGroupDatabase userGroupDatabase;

    private Users currentUser;

    private UserGroup createdGroup;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currentUser = new Users("dhdhgd", "hjdhdhd", 929, 27);
        try {
            userGroupDatabase = new UserGroupDatabase("src/sample/data/userGroups.csv");
            userGroupDatabase.loadElements();


            //usermembers db
            //find user - get list of groups

            //get groups from database and store in list


        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void createBtnOnClick(ActionEvent event) throws IOException {
        String groupName = groupNameField.getText();
        String admin = currentUser.getUsername();

        boolean created = createGroup(groupName, admin, 1);

        if (created) {
            switchToUserGroup(event);
        }
    }

    public void switchToUserGroup(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../resources/views/userGroupView.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public boolean createGroup(String groupName, String admin, int capacity) {
        String generatedCode = generateCode(10);

        System.out.println(generatedCode);
        UserGroup newGroup = new UserGroup(groupName, admin, capacity, generatedCode);
        try {
            if (!userGroupDatabase.contains(newGroup)) {
                userGroupDatabase.insert(newGroup);
                Main.userGroup = newGroup;
            } else
                return false;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }

    static String generateCode(int n) {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public void navGroupsBtnOnClick(MouseEvent mouseEvent) throws IOException {
        Main.switchToGroups(mouseEvent, this.getClass());
    }

    public void navMealBtnOnClick(MouseEvent mouseEvent) throws IOException {
        Main.switchToMeal(mouseEvent, this.getClass());
    }
}
