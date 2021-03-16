package sample;

import java.time.LocalDate;
import java.util.Date;

public abstract class Goals {
    private LocalDate startDate;
    private LocalDate endDate;
    private goalType type;

    public enum goalType {WEIGHT, EXERCISE, CALORIES}

    public void setStartDate(){
        startDate = LocalDate.now();
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

    Goals(LocalDate sDate, LocalDate eDate){
        this.startDate = sDate;
        this.endDate = eDate;
    }

    public abstract float calculateProgress(int currentData);

    @Override
    public String toString() {
        return  " start : " + startDate + " ends : " +endDate;
    }
}
