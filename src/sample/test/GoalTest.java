package sample.test;

import org.junit.Test;
import sample.CaloriesGoal;
import sample.ExerciseGoal;
import sample.Goal;
import sample.WeightGoal;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class GoalTest {

    private Goal goal;
    private ExerciseGoal goal2;
    private CaloriesGoal goal3;
    private WeightGoal goal4;

    @Test
    public void getStartDateTest() {
        goal = new WeightGoal(Goal.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 70, "Test");
        assertEquals(LocalDate.now(), goal.getStartDate());
    }

    @Test
    public void setStartDateTest() {
        goal = new WeightGoal(Goal.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 70, "Test");
        goal.setStartDate(2021, 7, 26);
        assertEquals(LocalDate.of(2021, 7, 26), goal.getStartDate());
    }

    @Test
    public void getEndDateTest() {
        goal = new CaloriesGoal(Goal.goalType.CALORIES, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 2000, "Test");
        assertEquals(LocalDate.of(2021, 9, 7), goal.getEndDate());
    }

    @Test
    public void setEndDateTest() {
        goal = new CaloriesGoal(Goal.goalType.CALORIES, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 2000, "Test");
        goal.setEndDate(2021, 8, 23);
        assertEquals(LocalDate.of(2021, 8, 23), goal.getEndDate());
    }

    @Test
    public void getTypeTest() {
        goal = new ExerciseGoal(Goal.goalType.EXERCISE, LocalTime.of(1, 30), LocalDate.now()
                , LocalDate.of(2021, 9, 7), "Test");
        assertEquals(Goal.goalType.EXERCISE, goal.getType());
    }

    @Test
    public void getExerciseToDoTest() {
        goal2 = new ExerciseGoal(Goal.goalType.EXERCISE, LocalTime.of(1, 30), LocalDate.now()
                , LocalDate.of(2021, 9, 7), "Test");

        assertEquals(LocalTime.of(1, 30), goal2.getExerciseToDo());
    }

    @Test
    public void setExerciseToDoTest() {
        goal2 = new ExerciseGoal(Goal.goalType.EXERCISE, LocalTime.of(1, 30), LocalDate.now()
                , LocalDate.of(2021, 9, 7), "Test");
        goal2.setExerciseToDo(1, 45, 30);
        assertEquals(LocalTime.of(1, 45, 30), goal2.getExerciseToDo());
    }

    @Test
    public void convertIntoMinTest() {
        goal2 = new ExerciseGoal(Goal.goalType.EXERCISE, LocalTime.of(1, 30), LocalDate.now()
                , LocalDate.of(2021, 9, 7), "Test");
        int x = goal2.convertIntoMinutes(goal2.getExerciseToDo());
        assertEquals(90, x);
    }

    @Test
    public void getCalToBurnTest() {
        goal3 = new CaloriesGoal(Goal.goalType.CALORIES, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 2000, "Test");
        assertEquals(2000, goal3.getCalToBurn());
    }

    @Test
    public void setCalToBurn() {
        goal3 = new CaloriesGoal(Goal.goalType.CALORIES, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 2000, "Test");
        goal3.setCalToBurn(3000);
        assertEquals(3000, goal3.getCalToBurn());
    }

    @Test
    public void getWeightToReachTest() {
        goal4 = new WeightGoal(Goal.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 70, "Test");
        assertEquals(70, goal4.getWeightsToReach());
    }

    @Test
    public void setWeightToReachTest() {
        goal4 = new WeightGoal(Goal.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 70, "Test");
        goal4.setWeightsToReach(90);
        assertEquals(90, goal4.getWeightsToReach());
    }

    @Test
    public void calProgressTest1() {
        goal2 = new ExerciseGoal(Goal.goalType.EXERCISE, LocalTime.of(1, 30), LocalDate.now()
                , LocalDate.of(2021, 9, 7), "Test");
        float y = goal2.calculateProgress(30);
        assertEquals((int) 33.0, (int) y);
    }

    @Test
    public void calProgressTest2() {
        goal3 = new CaloriesGoal(Goal.goalType.CALORIES, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 2000, "Test");
        float z = goal3.calculateProgress(1000);
        assertEquals((int) 50.0, (int) z);
    }

    @Test
    public void calProgressTest3() {
        goal4 = new WeightGoal(Goal.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 80, "Test");
        float x = goal4.calculateProgress(40);
        assertEquals((int) 50.0, (int) x);
    }

    @Test
    public void getUsername() {
        goal4 = new WeightGoal(Goal.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 80, "Test");
        assertEquals("Test", goal4.getUserName());
    }


}
