package sample.test;

import org.junit.Test;
import sample.Meal;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class MealTest {

    private Meal meal;

    @Test
    public void getIdTest() {
        meal = new Meal(1, "Cereal", Meal.MealType.DINNER, 300, "user1");
        assertEquals(1, meal.getId());
    }

    @Test
    public void getNameTest() {
        meal = new Meal(1, "Cereal", Meal.MealType.DINNER, 300, "user2");
        assertEquals("Cereal", meal.getName());
    }

    @Test
    public void getMealTypeTest() {
        Meal meal = new Meal(1, "Cereal", Meal.MealType.BREAKFAST, 300, "user1");
        assertEquals(Meal.MealType.BREAKFAST, meal.getType());
    }

    @Test
    public void getCaloriesTest() {
        Meal meal = new Meal(1, "Cereal", Meal.MealType.BREAKFAST, 300, "user1");
        assertEquals(300.0, meal.getCalories(), 1.0);
    }

    @Test
    public void getUserNameTest() {
        Meal meal = new Meal(1, "Cereal", Meal.MealType.BREAKFAST, 300, "user1");
        assertEquals("user1", meal.getUserName());
    }

    @Test
    public void setDateTest() {
        Meal meal = new Meal(1, "Cereal", Meal.MealType.BREAKFAST, 300, "user1");
        meal.setDate(20, 3, 2021);
        assertEquals(LocalDate.of(2021, 3, 20), meal.getDate());
    }

    @Test
    public void setTimeTest() {
        Meal meal = new Meal(1, "Cereal", Meal.MealType.BREAKFAST, 300, "user1");
        meal.setTime(21, 7);
        assertEquals(LocalTime.of(21, 7), meal.getTime());
    }

    @Test
    public void timeExceptionTest() {
        Meal meal = new Meal(1, "Cereal", Meal.MealType.BREAKFAST, 300, "user1");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> meal.setTime(25, 7));

        assertEquals("Argument out of range", thrown.getMessage());
    }

}
