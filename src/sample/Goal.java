package sample;

import java.time.LocalDate;

public abstract class Goal {
    private LocalDate startDate;
    private LocalDate endDate;
    private goalType goalType;
    private String userName;

    public enum goalType {WEIGHT, EXERCISE, CALORIES}

    public void setStartDate(int y, int m, int d) {
        startDate = LocalDate.of(y, m, d);
    }

    public void setEndDate(int y, int m, int d) {
        endDate = LocalDate.of(y, m, d);
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public goalType getGoalType() {
        return goalType;
    }

    public String getUserName() {
        return userName;
    }

    Goal(goalType t, LocalDate sDate, LocalDate eDate, String userName) {
        this.startDate = sDate;
        this.endDate = eDate;
        this.goalType = t;
        this.userName = userName;
    }

    public abstract float calculateProgress(int currentData);

    @Override
    public String toString() {
        return "Goal type : " + goalType + " start : " + startDate + " ends : " + endDate;
    }
}
