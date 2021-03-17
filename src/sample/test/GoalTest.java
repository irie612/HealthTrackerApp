package sample.test;

import org.junit.Test;
import sample.CaloriesGoals;
import sample.ExerciseGoals;
import sample.Goals;
import sample.WeightGoals;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class GoalTest {

    private Goals goal;
    private ExerciseGoals goal2;
    private CaloriesGoals goal3;
    private WeightGoals goal4;

    @Test
    public void getStartDateTest(){
        goal = new WeightGoals(Goals.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021, 9, 7)
                               , 70);
        assertEquals(LocalDate.now(), goal.getStartDate());
    }

    @Test
    public void setStartDateTest(){
        goal = new WeightGoals(Goals.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 70);
        goal.setStartDate(2021, 7, 26);
        assertEquals(LocalDate.of(2021, 7,26), goal.getStartDate());
    }

    @Test
    public void getEndDateTest(){
        goal = new CaloriesGoals(Goals.goalType.CALORIES, LocalDate.now(), LocalDate.of(2021,9,7)
                                , 2000);
        assertEquals(LocalDate.of(2021, 9, 7), goal.getEndDate());
    }

    @Test
    public void setEndDateTest(){
        goal = new CaloriesGoals(Goals.goalType.CALORIES, LocalDate.now(), LocalDate.of(2021,9,7)
                , 2000);
        goal.setEndDate(2021, 8, 23);
        assertEquals(LocalDate.of(2021, 8, 23), goal.getEndDate());
    }

    @Test
    public void getTypeTest(){
        goal = new ExerciseGoals(Goals.goalType.EXERCISE, LocalTime.of(1,30), LocalDate.now()
                                ,  LocalDate.of(2021, 9, 7));
        assertEquals(Goals.goalType.EXERCISE, goal.getType());
    }

    @Test
    public void getExerciseToDoTest(){
        goal2 = new ExerciseGoals(Goals.goalType.EXERCISE, LocalTime.of(1,30), LocalDate.now()
                ,  LocalDate.of(2021, 9, 7));

        assertEquals(LocalTime.of(1,30), goal2.getExerciseToDo());
    }

    @Test
    public void setExerciseToDoTest(){
        goal2 = new ExerciseGoals(Goals.goalType.EXERCISE, LocalTime.of(1,30), LocalDate.now()
                ,  LocalDate.of(2021, 9, 7));
        goal2.setExerciseToDo(1, 45, 30);
        assertEquals(LocalTime.of(1, 45, 30), goal2.getExerciseToDo());
    }

    @Test
    public void convertIntoMinTest(){
        goal2 = new ExerciseGoals(Goals.goalType.EXERCISE, LocalTime.of(1,30), LocalDate.now()
                ,  LocalDate.of(2021, 9, 7));
        int x = goal2.convertIntoMinutes(goal2.getExerciseToDo());
        assertEquals(90, x);
    }

    @Test
    public void getCalToBurnTest(){
        goal3 = new CaloriesGoals(Goals.goalType.CALORIES, LocalDate.now(), LocalDate.of(2021,9,7)
                , 2000);
        assertEquals(2000, goal3.getCalToBurn());
    }

    @Test
    public void setCalToBurn(){
        goal3 = new CaloriesGoals(Goals.goalType.CALORIES, LocalDate.now(), LocalDate.of(2021,9,7)
                , 2000);
        goal3.setCalToBurn(3000);
        assertEquals(3000, goal3.getCalToBurn());
    }

    @Test
    public void getWeightToReachTest(){
        goal4 = new WeightGoals(Goals.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 70);
        assertEquals(70, goal4.getWeightsToReach());
    }

    @Test
    public void setWeightToReachTest(){
        goal4 = new WeightGoals(Goals.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 70);
        goal4.setWeightsToReach(90);
        assertEquals(90, goal4.getWeightsToReach());
    }

    @Test
    public void calProgressTest1(){
        goal2 = new ExerciseGoals(Goals.goalType.EXERCISE, LocalTime.of(1,30), LocalDate.now()
                ,  LocalDate.of(2021, 9, 7));
        float y = goal2.calculateProgress(30);
        assertEquals((int) 33.0, (int) y);
    }

    @Test
    public void calProgressTest2(){
        goal3 = new CaloriesGoals(Goals.goalType.CALORIES, LocalDate.now(), LocalDate.of(2021,9,7)
                , 2000);
        float z = goal3.calculateProgress(1000);
        assertEquals((int) 50.0, (int) z);
    }

    @Test
    public void calProgressTest3(){
        goal4 = new WeightGoals(Goals.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021, 9, 7)
                , 80);
        float x = goal4.calculateProgress(40);
        assertEquals((int) 50.0, (int) x);
    }



}
