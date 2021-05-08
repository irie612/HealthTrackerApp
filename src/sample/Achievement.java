package sample;

import java.time.LocalDate;

public class Achievement {

    String name;
    int score;
    String userName;
    LocalDate deadline;
    UserGroup userGroup;

    public Achievement(String name, int score, String userName, LocalDate deadline){

        this.name = name;
        this.score = score;
        this.userName = userName;
        this.deadline = deadline;
    }

    public Achievement(String name, int score, String userName, LocalDate deadline, UserGroup userGroup){

        this.name = name;
        this.score = score;
        this.userName = userName;
        this.deadline = deadline;
        this.userGroup = userGroup;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
