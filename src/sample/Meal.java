package sample;

import java.time.LocalDate;
import java.time.LocalTime;


public class Meal {

    private String name;
    private MealType type;
    private double calories;
    private LocalDate date;
    private LocalTime time;

    public enum MealType {BREAKFAST, LUNCH, DINNER, SNACK, DRINK}

    public Meal(String name, MealType type, double calories) {
        this.name = name;
        this.type = type;
        this.calories = calories;
        this.date = LocalDate.now();
        this.time = LocalTime.now();

    }

    public String getName() {
        return name;
    }

    public MealType getType() {
        return type;
    }

    public double getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setDate(int day, int month, int year) {
        this.date = LocalDate.of(year, month, day);
        //TODO add some error checking
    }

    public void setTime(int hour, int minute) throws IllegalArgumentException {
        if ((hour < 24 && minute < 60) && (hour >= 0 && minute >= 0))
            this.time = LocalTime.of(hour, minute);
        else
            throw new IllegalArgumentException("Argument out of range");
    }

    @Override
    public String toString() {
        return "Meal{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", calories=" + calories +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

}