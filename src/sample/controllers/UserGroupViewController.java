package sample.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class UserGroupViewController implements Initializable {

    @FXML
    public TableView<UserGroupMember> leaderBoardTable;
    @FXML
    public Label groupCodeLabel;
    @FXML
    public Label copyResponseLabel;
    @FXML
    public ImageView copyIcon;

    @FXML
    public Label groupNameLabel;

    @FXML
    public Label capacityLabel;

    @FXML
    public ListView<Achievement> achievementListView;

    private String groupCode;

    private UserGroup currentGroup;

    private User currentUser;

    private MemberDatabase memberDatabase;

    private UserGroupDatabase userGroupDatabase;

    private ObservableList<Achievement> achievementObservableList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currentGroup = Main.userGroup;
        currentUser = Main.currentUser;

        groupCode = currentGroup.getCode();
        groupCodeLabel.setText(groupCode);
        groupNameLabel.setText(currentGroup.getGroupName());
        capacityLabel.setText(String.valueOf(currentGroup.getCapacity()));

        try {
            memberDatabase = new MemberDatabase("src/sample/data/member.csv");
            memberDatabase.loadElements();

            userGroupDatabase = new UserGroupDatabase("src/sample/data/userGroups.csv");
            userGroupDatabase.loadElements();

        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeLeaderboardTable();

        achievementObservableList = FXCollections.observableArrayList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        achievementObservableList.add(new Achievement("Walk 2.5 miles", 3,
                currentUser.getUsername(), LocalDate.parse("21/05/21", formatter), currentGroup.getGroupName()));
        achievementObservableList.add(new Achievement("Walk 5 miles", 5,
                currentUser.getUsername(), LocalDate.parse("21/05/21", formatter), currentGroup.getGroupName()));
        achievementObservableList.add(new Achievement("Walk 7 miles", 10,
                currentUser.getUsername(), LocalDate.parse("21/05/21", formatter), currentGroup.getGroupName()));

        achievementListView.setPlaceholder(new Label("No group objectives available. Come back later"));
        achievementListView.setItems(achievementObservableList);
        achievementListView.setCellFactory(achievementListView -> new AchievementListViewCell());

    }

    public void switchToDashboard(MouseEvent mouseEvent) {
    }

    public void leaveGroupOnClick(ActionEvent actionEvent) throws IOException {
        for (UserGroupMember member : memberDatabase.getAllByGroup(currentGroup.getGroupName())) {
            if (member.getMemberUsername().equals(currentUser.getUsername())) {
                memberDatabase.delete(member);
                userGroupDatabase.update(currentGroup, new UserGroup(currentGroup.getGroupName(),
                        currentGroup.getAdmin(), currentGroup.getCapacity() - 1,
                        currentGroup.getCode()));
                currentGroup = userGroupDatabase.getByGroupName(currentGroup.getGroupName());
                if (currentGroup.getCapacity() == 0) {
                    userGroupDatabase.delete(currentGroup);
                }

                Main.switchView("../resources/views/groupsView.fxml", actionEvent, getClass());
            }
        }


    }

    public void initializeLeaderboardTable() {
        leaderBoardTable.columnResizePolicyProperty().set(CONSTRAINED_RESIZE_POLICY);
        leaderBoardTable.setPlaceholder(new Label("No group members"));

        TableColumn rankColumn = new TableColumn("Pos");
//        rankColumn.setMinWidth(20);
//        rankColumn.prefWidthProperty().bind(leaderBoardTable.widthProperty().divide(4));
        rankColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserGroupMember, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UserGroupMember, String> p) {
                return new ReadOnlyObjectWrapper(leaderBoardTable.getItems().indexOf(p.getValue()) + 1 + "");
            }
        });

        rankColumn.resizableProperty().setValue(true);
        leaderBoardTable.getColumns().add(rankColumn);

        TableColumn<UserGroupMember, String> userColumn = new TableColumn<>("User");
//        userColumn.prefWidthProperty().bind(leaderBoardTable.widthProperty().divide(2));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("memberUsername"));
        userColumn.resizableProperty().setValue(true);
        leaderBoardTable.getColumns().add(userColumn);

        TableColumn<UserGroupMember, Integer> scoreColumn = new TableColumn<>("Score");
//        scoreColumn.prefWidthProperty().bind(leaderBoardTable.widthProperty().divide(4));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("memberScore"));
        scoreColumn.resizableProperty().setValue(true);
        leaderBoardTable.getColumns().add(scoreColumn);
        ArrayList<UserGroupMember> members = memberDatabase.getAllByGroup(currentGroup.getGroupName());
        members.sort(new Comparator<UserGroupMember>() {
            @Override
            public int compare(UserGroupMember o1, UserGroupMember o2) {
                return o2.getMemberScore() - o1.getMemberScore();
            }
        });
        leaderBoardTable.getItems().addAll(members);

    }


    public void copyIconOnClick(MouseEvent mouseEvent) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(groupCode);
        clipboard.setContent(content);
        System.out.println("Copied");

        copyIcon.setEffect(new Glow(0.4));
        copyResponseLabel.setVisible(true);
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            public void run() {
                copyResponseLabel.setVisible(false);
                copyIcon.setEffect(null);
            }
        };

        timer.schedule(task, 2500); //wait 2 and half seconds
    }

    public void navGroupsBtnOnClick(MouseEvent mouseEvent) throws IOException {
        Main.switchView("../resources/views/groupsView.fxml", mouseEvent, getClass());
    }

    public void navMealBtnOnClick(MouseEvent mouseEvent) throws IOException {
        Main.switchView("../resources/views/mealView.fxml", mouseEvent, getClass());
    }
}
