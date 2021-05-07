package sample.test;

import sample.*;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class UsersTest {


    Users user1 = new Users("abcxyz", "12345");
    Users user2 = new Users("abcxyz", "abc@gmail.com", 180, 70);

    @Test
    public void getUsernameTest(){

        assertEquals("abcxyz", user1.getUsername());
        assertEquals("abcxyz", user2.getUsername());
    }

    @Test
    public void getPasswordTest(){

        assertEquals("12345", user1.getPassword());
    }

    @Test
    public void getEmailTest(){

        assertEquals("abc@gmail.com", user2.getEmail());
    }

    @Test
    public void getHeightTest(){

        assertEquals(180, (int) user2.getHeight());
    }

    @Test
    public void getWeightTest(){

         assertEquals(70, (int) user2.getWeight());
    }

    @Test
    public void setUsernameTest(){

        user1.setUsername("zyxcba");
        assertEquals("zyxcba", user1.getUsername());
    }

    @Test
    public void setPasswordTest(){

        user1.setPassword("54321");
        assertEquals("54321", user1.getPassword());
    }

    @Test
    public void setEmailTest(){

        user2.setEmail("zyxcba@gmail.com");
        assertEquals("zyxcba@gmail.com", user2.getEmail());
    }

    @Test
    public void setHeightTest(){

        user2.setHeight(170);
        assertEquals(170, (int) user2.getHeight());
    }

    @Test
    public void setWeightTest(){

        user2.setWeight(60);
        assertEquals(60, (int) user2.getWeight());
    }

    @Test
    public void addMealTest(){

        Meal meal= new Meal(1000, "mealTest", Meal.MealType.BREAKFAST, 2000.0);
        user2.addMeal(meal);
        ArrayList<Meal> mealDiary = user2.getUsersMeal();
        assertEquals(mealDiary.get(0).getId(), meal.getId());
    }

    @Test
    public void addCalorieGoalTest(){

        CaloriesGoals caloriesGoals = new CaloriesGoals(Goals.goalType.CALORIES, LocalDate.now(),
                LocalDate.of(2021, 9, 21), 2000);
        user2.addCalorieGoal(caloriesGoals);
        ArrayList<Goals> userGoals = user2.getUserGoals();
        assertEquals(LocalDate.now(), userGoals.get(0).getStartDate());
    }

    @Test
    public void addExerciseGoalTest(){

        ExerciseGoals exerciseGoals = new ExerciseGoals(Goals.goalType.EXERCISE, LocalTime.of(1,30), LocalDate.now()
        , LocalDate.of(2021, 9, 21));
        user2.addExerciseGoal(exerciseGoals);
        ArrayList<Goals> userGoals = user2.getUserGoals();
        assertEquals(Goals.goalType.EXERCISE, userGoals.get(0).getType());
    }

    @Test
    public void addWeightGoalTest(){

        WeightGoals weightGoals = new WeightGoals(Goals.goalType.WEIGHT, LocalDate.now(), LocalDate.of(2021,8, 21), 70);
        user2.addWeightGoal(weightGoals);
        ArrayList<Goals> userGoals = user2.getUserGoals();
        assertEquals(Goals.goalType.WEIGHT, userGoals.get(0).getType());
    }
}
