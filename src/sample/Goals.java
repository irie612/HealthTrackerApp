package sample;

import java.time.LocalDate;

public abstract class Goals {
    private LocalDate startDate;
    private LocalDate endDate;
    private goalType goalType;

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

    Goals(goalType t, LocalDate sDate, LocalDate eDate) {
        this.startDate = sDate;
        this.endDate = eDate;
        this.goalType = t;
    }

    public abstract float calculateProgress(int currentData);

    @Override
    public String toString() {
        return "Goal type : " + goalType + " start : " + startDate + " ends : " + endDate;
    }
}
