package sample;

import java.time.LocalDate;
import java.util.Objects;

public class Achievement {

    String name;
    int score;
    String userName;
    LocalDate deadline;
    String userGroupName;

    public Achievement(String name, int score, String userName, LocalDate deadline){

        this.name = name;
        this.score = score;
        this.userName = userName;
        this.deadline = deadline;
    }

    public Achievement(String name, int score, String userName, LocalDate deadline, String userGroupName) {

        this.name = name;
        this.score = score;
        this.userName = userName;
        this.deadline = deadline;
        this.userGroupName = userGroupName;
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

    public String getUserGroupName() {
        return userGroupName;
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

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Achievement that = (Achievement) o;
        return score == that.score &&
                Objects.equals(name, that.name) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(deadline, that.deadline) &&
                Objects.equals(userGroupName, that.userGroupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score, userName, deadline, userGroupName);
    }
}
