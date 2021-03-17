package sample;

import java.time.LocalDate;
import java.util.Date;


public class WeightGoals extends Goals {

    private int weightsToReach;

    public void setWeightsToReach(int w){
        weightsToReach = w;
    }

    public int getWeightsToReach(){
        return this.weightsToReach;
    }

    public WeightGoals(goalType t, LocalDate sd, LocalDate ed, int w) {
        super(t, sd, ed);
        weightsToReach = w;
    }

    @Override
    public float calculateProgress(int currentData) {
        return ( (float) currentData/ (float) weightsToReach) * 100;
    }

    @Override
    public String toString() {
        return "Goal Type : "+ Goals.goalType.WEIGHT + super.toString() + " weight to reach : " + weightsToReach;
    }
}
