package sample;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Users {

    // TODO: methods to add joinUserGroup, addExercise

    private String username;
    private String password;
    private String email;
    private double height;
    private double weight;
    private ArrayList<Meal> mealDiary;
    private ArrayList<Goals> userGoals;


    Users(String uname, String pwd) {
        this.username = uname;
        this.password = pwd;
    }

    Users(String uname, String email, double height, double weight) {

        this.username = uname;
        this.email = email;
        this.height = height;
        this.weight = weight;

    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public double getHeight() {
        return height;
    }

    public String getEmail() {
        return email;
    }

    public double getWeight() {
        return weight;
    }

    public ArrayList<Meal> getMealDiary() {
        return mealDiary;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addMeal(Meal meal) {
        mealDiary.add(meal);
    }

    public void addCalorieGoal(Goals.goalType goalType, LocalDate startDate, LocalDate endDate, int cal) {

        CaloriesGoals c = new CaloriesGoals(goalType, startDate, endDate, cal);
        userGoals.add(c);
    }

    public void addExerciseGoal(Goals.goalType goalType, LocalTime exerciseDone, LocalDate startDate, LocalDate endDate) {

        ExerciseGoals e = new ExerciseGoals(goalType, exerciseDone, startDate, endDate);
        userGoals.add(e);
    }

    public void addWeightGoal(Goals.goalType goalType, LocalDate startDate, LocalDate endDate, int weight) {

        WeightGoals w = new WeightGoals(goalType, startDate, endDate, weight);
        userGoals.add(w);
    }

    public String toString() {
        return "username : " + username + " email : " + email + " height : " + height + " weight : " + weight;
    }

}
