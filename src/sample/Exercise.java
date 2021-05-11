package sample;

import java.time.LocalDate;
import java.time.LocalTime;

public class Exercise {
    private double distance, duration;
    private LocalDate date;
    private LocalTime time;
    private exerciseType type;
    private String other;

    public enum exerciseType {WALKING, RUNNING, CYCLING, SWIMMING, OTHER}

    public Exercise(double distance, double duration, exerciseType type, String other, LocalDate date, LocalTime time) {
        this.distance = distance;
        this.duration = duration;
        this.date = date;
        this.time = time;
        this.type = type;
        this.other = other;
    }

    public Exercise(double distance, double duration, exerciseType type, LocalDate date, LocalTime time) {
        this.distance = distance;
        this.duration = duration;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
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

    public void setType(exerciseType type){
        this.type = type;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public java.lang.String toString() {
        return "Exercise{" +
                "type=" + type +
                ", distance=" + distance +
                ", duration=" + duration +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}