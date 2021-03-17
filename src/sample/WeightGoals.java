package sample;

import java.time.LocalDate;
import java.util.Date;


public class WeightGoals extends Goals {

    private float weightsToReach;

    public void setWeightsToReach(float w){
        weightsToReach = w;
    }

    public float getWeightsToReach(){
        return this.weightsToReach;
    }

    public WeightGoals(goalType t, LocalDate sd, LocalDate ed, float w) {
        super(t, sd, ed);
        weightsToReach = w;
    }

    @Override
    public float calculateProgress(int currentData) {
        return (currentData/weightsToReach) * 100;
    }

    @Override
    public String toString() {
        return "Goal Type : "+ Goals.goalType.WEIGHT + super.toString() + " weight to reach : " + weightsToReach;
    }
}
