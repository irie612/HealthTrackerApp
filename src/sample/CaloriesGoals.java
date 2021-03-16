package sample;

import java.time.LocalDate;

public class CaloriesGoals extends Goals {

    private int calToBurn;

    CaloriesGoals(goalType t, LocalDate sd, LocalDate ed, int c){
        super(t, sd, ed);
        this.calToBurn = c;
    }

    public void setCalToBurn(int calToBurn) {
        this.calToBurn = calToBurn;
    }

    public int getCalToBurn() {
        return calToBurn;
    }

    @Override
    public String toString() {
        return "Goal Type : "+ goalType.CALORIES + super.toString() + "Calories to burn : "+ calToBurn;
    }

    @Override
    public float calculateProgress(int currentData) {
        return (currentData/calToBurn) * 100;
    }


}
