package sample;

import java.time.LocalDate;

public class CaloriesGoal extends Goal {

    private int calToBurn;

    public CaloriesGoal(goalType t, LocalDate sd, LocalDate ed, int c, String userName) {
        super(t, sd, ed, userName);
        calToBurn = c;
    }

    public void setCalToBurn(int c) {
        calToBurn = c;
    }

    public int getCalToBurn() {
        return calToBurn;
    }

    @Override
    public float calculateProgress(int currentData) {

        return ((float) currentData / (float) calToBurn) * 100;
    }


    @Override
    public String toString() {
        return "Goal Type : " + goalType.CALORIES + super.toString() + "Calories to burn : " + calToBurn;
    }


}
