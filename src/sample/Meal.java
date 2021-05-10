package sample;

import java.time.LocalDate;
import java.time.LocalTime;


public class Meal {


    private long id;
    private String name;
    private MealType type;
    private double calories;
    private LocalDate date;
    private LocalTime time;
    private String userName;

    public enum MealType {BREAKFAST, LUNCH, DINNER, SNACK, DRINK}


    public Meal(long id, String name, MealType type, double calories, String userName) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.calories = calories;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.userName = userName;

    }

    public Meal(long id, String name, MealType type, double calories, String userName, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.calories = calories;
        this.date = date;
        this.time = time;
        this.userName = userName;
    }

    public long getId() {
        return id;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDate(int day, int month, int year) {
        this.date = LocalDate.of(year, month, day);
    }

    public void setTime(int hour, int minute) throws IllegalArgumentException {
        if ((hour < 24 && minute < 60) && (hour >= 0 && minute >= 0))
            this.time = LocalTime.of(hour, minute);
        else
            throw new IllegalArgumentException("Argument out of range");
    }

    public String getUserName() {
        return userName;
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
