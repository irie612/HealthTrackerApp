package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sample.*;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
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
    private ListView<UserGroup> groupsListView;

    private ObservableList<UserGroup> groupObservableList;

    public static final String JOINED_GROUP_MSG = "Joined group successfully";

    public static final String JOIN_GROUP_FAILED_MSG = "Join group failed";

    public static final String GROUP_NOT_FOUND_MSG = "Group not found";

    private UserGroupDatabase userGroupDatabase;

    private MemberDatabase memberDatabase;

    private User currentUser;

    private UserGroup createdGroup;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = Main.currentUser;

        try {
            userGroupDatabase = new UserGroupDatabase("src/sample/data/userGroups.csv");
            userGroupDatabase.loadElements();

            memberDatabase = new MemberDatabase("src/sample/data/member.csv");

            memberDatabase.loadElements();


        } catch (IOException e) {
            e.printStackTrace();
        }

        groupObservableList = FXCollections.observableArrayList();


        for (UserGroupMember member : memberDatabase.getAllByUserName(currentUser.getUsername())) {
            groupObservableList.add(userGroupDatabase.getByGroupName(member.getGroupName()));
        }


        groupsListView.setPlaceholder(new Label("You are currently not in any groups"));
        groupsListView.setItems(groupObservableList);
        groupsListView.setCellFactory(groupsListView -> new GroupsListViewCell());

    }

    public void joinGroupOnClick(ActionEvent event) {
        String code = groupCodeField.getText();

        if (!code.isEmpty()) {

            System.out.println(code);
            joinGroupMessageLabel.setText("join");

            try {
                boolean joined = joinGroup(currentUser, code);

                if (joined) {
                    System.out.println(joined);
                    Main.userGroup = userGroupDatabase.getByGroupCode(code);
                    Main.switchView("../resources/views/userGroupView.fxml", event, getClass());
                } else {
                    joinGroupMessageLabel.setText(JOIN_GROUP_FAILED_MSG);
                    joinGroupMessageLabel.setTextFill(Color.valueOf("#d9534f"));
                    joinGroupMessageLabel.setVisible(true);

                }
            } catch (Exception e) {
                if (e.getMessage().equals("Group with code not found")) { //TODO make constant

                    joinGroupMessageLabel.setText(GROUP_NOT_FOUND_MSG);
                    joinGroupMessageLabel.setTextFill(Color.valueOf("#f0ad4e"));
                    joinGroupMessageLabel.setVisible(true);

                } else {
                    e.printStackTrace();
                }
            }

            //check code
//            joinGroupMessageLabel.setText(JOINED_GROUP_MSG);
//            joinGroupMessageLabel.setTextFill(Color.valueOf("#5cb85c"));
//            joinGroupMessageLabel.setVisible(true);
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

    public boolean joinGroup(User user, String code) {
        UserGroup group;

        if ((group = userGroupDatabase.getByGroupCode(code)) != null
                && group.getCapacity() < UserGroup.DEFAULT_CAPACITY) {


            for (UserGroupMember member : memberDatabase.getAllByGroup(group.getGroupName())) {
                if (member.getMemberUsername().equals(user.getUsername())) {
                    return false;
                }
            }

            try {
                memberDatabase.insert(new UserGroupMember(group.getGroupName(), user.getUsername(), 0));
                userGroupDatabase.update(userGroupDatabase.getByGroupCode(code),
                        new UserGroup(group.getGroupName(), group.getAdmin(), group.getCapacity() + 1, group.getCode()));
            } catch (NoSuchElementException | IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    public void createBtnOnClick(ActionEvent event) throws IOException {
        String groupName = groupNameField.getText();
        String admin = currentUser.getUsername();

        boolean created = createGroup(groupName, admin, 1);

        if (created) {
            Main.switchView("../resources/views/userGroupView.fxml", event, getClass());
        }
    }
    public boolean createGroup(String groupName, String admin, int capacity) {
        String generatedCode = generateCode(10);

        System.out.println(generatedCode);
        UserGroup newGroup = new UserGroup(groupName, admin, capacity, generatedCode);
        try {
            if (!userGroupDatabase.contains(newGroup)) {
                userGroupDatabase.insert(newGroup);
                UserGroupMember newMember = new UserGroupMember(groupName, admin, 0);
                memberDatabase.insert(newMember);
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
        Main.switchView("../resources/views/groupsView.fxml", mouseEvent, getClass());
    }

    public void navMealBtnOnClick(MouseEvent mouseEvent) throws IOException {
        Main.switchView("../resources/views/mealView.fxml", mouseEvent, getClass());
    }

    public void navAccountBtnOnClick(MouseEvent mouseEvent) throws IOException {
        Main.switchView("../resources/views/Account.fxml", mouseEvent, getClass());
    }
}