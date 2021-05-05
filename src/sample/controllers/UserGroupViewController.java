package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class UserGroupViewController implements Initializable {

    @FXML
    public TableView leaderBoardTable;
    @FXML
    public Label groupCodeLabel;
    @FXML
    public Label copyResponseLabel;
    @FXML
    public ImageView copyIcon;

    private String groupCode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        groupCode = "JDHDGT";
        groupCodeLabel.setText(groupCode);


        initializeLeaderboardTable();
    }

    public void switchToDashboard(MouseEvent mouseEvent) {
    }

    public void leaveGroupOnClick(ActionEvent actionEvent) {
    }

    public void initializeLeaderboardTable() {
        leaderBoardTable.columnResizePolicyProperty().set(CONSTRAINED_RESIZE_POLICY);
        leaderBoardTable.setPlaceholder(new Label("No group members"));

        TableColumn rankColumn = new TableColumn("Pos");
//        rankColumn.setMinWidth(20);
//        rankColumn.prefWidthProperty().bind(leaderBoardTable.widthProperty().divide(4));
//        mealTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        rankColumn.resizableProperty().setValue(true);
        leaderBoardTable.getColumns().add(rankColumn);

        TableColumn userColumn = new TableColumn("User");
//        userColumn.prefWidthProperty().bind(leaderBoardTable.widthProperty().divide(2));
//        mealTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        userColumn.resizableProperty().setValue(true);
        leaderBoardTable.getColumns().add(userColumn);

        TableColumn scoreColumn = new TableColumn("Score");
//        scoreColumn.prefWidthProperty().bind(leaderBoardTable.widthProperty().divide(4));
//        mealTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        scoreColumn.resizableProperty().setValue(true);
        leaderBoardTable.getColumns().add(scoreColumn);
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
}