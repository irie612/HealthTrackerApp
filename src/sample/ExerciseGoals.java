package sample;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExerciseGoals extends Goals {

    private LocalTime exerciseToDo;

    public void setExerciseToDo(int h, int m, int s){
        this.exerciseToDo = LocalTime.of(h, m, s);
    }

    public LocalTime getExerciseToDo(){
        return this.exerciseToDo;
    }

    ExerciseGoals(Goals.goalType t, LocalTime time, LocalDate sd, LocalDate ed){
        super(sd , ed);
        this.exerciseToDo = time;
        t = Goals.goalType.EXERCISE;
    }

    public float convertIntoMinutes(LocalTime time) {
        float x = (time.getHour() * 60) + time.getMinute() + (time.getSecond()/60);
        return x;
    }


    @Override
    public float calculateProgress(int currentData) {
        float time = convertIntoMinutes(exerciseToDo);
        return (currentData/time) * 100 ;
    }

    @Override
    public String toString() {
        return "Goal Type : "+ Goals.goalType.EXERCISE + super.toString() + " exercise to do : " + exerciseToDo;
    }
}
