package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import sample.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AchievementListViewCell extends ListCell<Achievement> {

    @FXML
    private Label cellAchievementName;

    @FXML
    private Label cellAchievementEndDate;

    @FXML
    private Label cellAchievementPoints;

    @FXML
    private ImageView tickImg;


    private static AchievementDatabase achievementDatabase;

    private static MemberDatabase memberDatabase;

    private Achievement achievement;

    private boolean completed;

    @FXML
    public HBox hbox;

    private FXMLLoader fxmlLoader;


    public AchievementListViewCell() {

        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../resources/views/achievementListCellView.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.load();

            achievementDatabase = new AchievementDatabase("src/sample/data/achievement.csv");
            achievementDatabase.loadElements();

            memberDatabase = new MemberDatabase("src/sample/data/member.csv");
            memberDatabase.loadElements();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Achievement achievement, boolean isEmpty) {
        try {
            memberDatabase.writeAllData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.achievement = achievement;

        if (isEmpty || achievement == null) {

            setText(null);
            setGraphic(null);

        } else {

            if (achievementDatabase.contains(achievement)) {
                completed = true;
                tickImg.setImage(new Image("sample/resources/images/check_circle_blue_24dp.png"));
            } else {
                completed = false;
            }

            cellAchievementName.setText(achievement.getName());
            cellAchievementEndDate.setText(achievement.getDeadline().format(DateTimeFormatter.ofPattern("dd/MM/yy")).toString());
            cellAchievementPoints.setText("+" + achievement.getScore());

            setText(null);
            setGraphic(hbox);
        }

    }

    @FXML
    private void completeAchievement() throws IOException {
        updateItem(achievement, false);
        if (!completed) {

            tickImg.setImage(new Image("sample/resources/images/check_circle_blue_24dp.png"));
            Achievement temp = achievement;
//            temp.setUserName(Main.currentUser.getUsername());
            if (!achievementDatabase.contains(temp)) {

                achievementDatabase.insert(temp);
                UserGroupMember member = null;
                ArrayList<UserGroupMember> groupMembers = memberDatabase.getAllByGroup(achievement.getUserGroupName());
                for (UserGroupMember groupMember : groupMembers) {
                    if (groupMember.getMemberUsername().equals(Main.currentUser.getUsername())) {
                        member = groupMember;
                    }
                }

                if (member != null) {
                    int oldScore = member.getMemberScore();
                    int newScore = achievement.getScore();
                    int score = oldScore + newScore;
                    System.out.println("old score: " + member.getMemberScore());
                    System.out.println("new score: " + achievement.getScore());
                    System.out.println("combined score: " + score);
                    memberDatabase.update(member, new UserGroupMember(member.getGroupName(), member.getMemberUsername(),
                            score));
                }
            }
        }


    }

}
