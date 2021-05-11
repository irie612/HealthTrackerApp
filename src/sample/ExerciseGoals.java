package sample;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExerciseGoals extends Goals {

    private LocalTime exerciseToDo;
    private String type;

    public void setExerciseToDo(int h, int m, int s) {
        this.exerciseToDo = LocalTime.of(h, m, s);
    }

    public LocalTime getExerciseToDo() {
        return this.exerciseToDo;
    }

    public ExerciseGoals(Goals.goalType t, String type, LocalTime time, LocalDate sd, LocalDate ed) {
        super(t, sd, ed);
        this.exerciseToDo = time;
        this.type = type;
    }

    public int convertIntoMinutes(LocalTime time) {
        return (time.getHour() * 60) + time.getMinute() + (time.getSecond() / 60);
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
        return "Goal Type : " + Goals.goalType.EXERCISE + super.toString() + " exercise to do : " + exerciseToDo;
    }
}
