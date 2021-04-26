package sample;

import java.time.LocalDate;
import java.time.LocalTime;

public class Exercise {
    private double distance, caloriesBurned, duration;
    private int steps;
    private LocalDate date;
    private LocalTime time;
    private exerciseType type;

    public enum exerciseType {}

    public Exercise(double distance, double caloriesBurned, double duration, int steps, exerciseType type) {
        this.distance = distance;
        this.caloriesBurned = caloriesBurned;
        this.duration = duration;
        this.steps = steps;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(int day, int month, int year) {
        this.date = LocalDate.of(year, month, day);
    }


    public LocalTime getTime() {
        return time;
    }

    public void setTime(int hour, int minute) throws IllegalArgumentException {
        if ((hour < 24 && minute < 60) && (hour >= 0 && minute >= 0))
            this.time = LocalTime.of(hour, minute);
        else
            throw new IllegalArgumentException("Argument out of range");
    }

    public exerciseType getType() {
        return type;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Exercise{" +
                "type=" + type +
                ", distance=" + distance +
                ", caloriesBurned=" + caloriesBurned +
                ", duration=" + duration +
                ", steps=" + steps +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}