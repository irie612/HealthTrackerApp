package sample;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExerciseGoal extends Goal {

    private LocalTime exerciseToDo;

    public void setExerciseToDo(int h, int m, int s) {
        this.exerciseToDo = LocalTime.of(h, m, s);
    }

    public LocalTime getExerciseToDo() {
        return this.exerciseToDo;
    }

    public ExerciseGoal(Goal.goalType t, LocalTime time, LocalDate sd, LocalDate ed, String username) {
        super(t, sd, ed, username);
        this.exerciseToDo = time;
    }

    public int convertIntoMinutes(LocalTime time) {
        int x = (time.getHour() * 60) + time.getMinute() + (time.getSecond() / 60);
        return x;
    }


    @Override
    public float calculateProgress(int currentData) {
        float time = convertIntoMinutes(exerciseToDo);
        return (currentData / time) * 100;
    }

    @Override
    public String toString() {
        return "Goal Type : " + Goal.goalType.EXERCISE + super.toString() + " exercise to do : " + exerciseToDo;
    }
}
