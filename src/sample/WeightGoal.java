package sample;

import java.time.LocalDate;


public class WeightGoal extends Goal {

    private int weightsToReach;

    public void setWeightsToReach(int w) {
        weightsToReach = w;
    }

    public int getWeightsToReach() {
        return this.weightsToReach;
    }

    public WeightGoal(goalType t, LocalDate sd, LocalDate ed, int w, String username) {
        super(t, sd, ed, username);
        weightsToReach = w;
    }

    @Override
    public float calculateProgress(int currentData) {
        return ((float) currentData / (float) weightsToReach) * 100;
    }

    @Override
    public String toString() {
        return "Goal Type : " + Goal.goalType.WEIGHT + super.toString() + " weight to reach : " + weightsToReach;
    }
}
