package sample;

import java.time.LocalDate;
import java.util.Date;

public abstract class Goals {
    private LocalDate startDate;
    private LocalDate endDate;
    private goalType type;

    public enum goalType {WEIGHT, EXERCISE, CALORIES}

    public void setStartDate(int y, int m, int d){
        startDate = LocalDate.of(y, m, d);
    }

    public void setEndDate(int y, int m , int d){
        endDate = LocalDate.of(y, m, d);
    }

    public LocalDate getStartDate(){
        return this.startDate;
    }

    public LocalDate getEndDate(){
        return this.endDate;
    }

    public goalType getType(){
        return type;
    }

    Goals(goalType t, LocalDate sDate, LocalDate eDate){
        this.startDate = sDate;
        this.endDate = eDate;
        this.type = t;
    }

    public abstract float calculateProgress(int currentData);

    @Override
    public String toString() {
        return  "Goal type : "+ type +" start : " + startDate + " ends : " +endDate;
    }
}
