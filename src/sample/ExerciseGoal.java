package sample;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExerciseGoal extends Goal {

    private LocalTime exerciseToDo;
    private String type;
    private boolean completed;

    public void setExerciseToDo(int h, int m, int s) {
        this.exerciseToDo = LocalTime.of(h, m, s);
    }

    public LocalTime getExerciseToDo() {
        return this.exerciseToDo;
    }

    public ExerciseGoal(Goal.goalType t, String type, LocalTime time, LocalDate sd, LocalDate ed, String username, boolean completed) {
        super(t, sd, ed, username);
        this.exerciseToDo = time;
        this.type = type;
        this.completed = completed;
    }

    public int convertIntoMinutes(LocalTime time) {
        int x = (time.getHour() * 60) + time.getMinute() + (time.getSecond() / 60);
        return x;
    }

    public String getType(){
        return type;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
