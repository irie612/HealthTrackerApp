package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import sample.Main;
import sample.UserGroup;

import java.io.IOException;

public class GroupsListViewCell extends ListCell<UserGroup> {

    @FXML
    private Label cellGroupName;

    @FXML
    private Label cellGroupCapacity;

    @FXML
    public HBox hbox;

    private FXMLLoader fxmlLoader;

    private UserGroup userGroup;

    public GroupsListViewCell() {

        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../resources/views/groupsListCellView.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(UserGroup userGroup, boolean isEmpty) {
        super.updateItem(userGroup, isEmpty);

        this.userGroup = userGroup;

        if (isEmpty || userGroup == null) {

            setText(null);
            setGraphic(null);

        } else {

            cellGroupName.setText(userGroup.getGroupName());
            cellGroupCapacity.setText(String.valueOf(userGroup.getCapacity()));

            setText(null);
            setGraphic(hbox);
        }

    }


    public void cellOnClick(MouseEvent event) throws IOException {
        Main.userGroup = this.userGroup;
        GroupsController.switchToUserGroup(event, getClass());
    }
}
