package sample.test;

import org.junit.Test;
import sample.Meal;
import sample.MealDatabase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MealDatabaseTest {

    private MealDatabase mealDB;
    private File file;
    private static final String data = "1,Cereal,BREAKFAST,300.0,01/04/2021,17:14\n" +
            "2,Water,DRINK,0.0,01/04/2021,17:14\n" +
            "3,Orange Juice,DRINK,99.0,01/04/2021,17:14";

    public MealDatabaseTest() {
        try {
            file = File.createTempFile("mealDBTest", ".csv");
            createTestData(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createTestData(File f) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(data);
        bw.close();
    }

    @Test
    public void loadTest() {
        try {
            mealDB = new MealDatabase(file.getPath());
            mealDB.loadElements();
            assertEquals("Cereal", mealDB.get(0).getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertTest() {
        try {
            mealDB = new MealDatabase(file.getPath());
            mealDB.loadElements();
            Meal m = new Meal(4, "Ham Sandwich", Meal.MealType.LUNCH, 255.6);
            mealDB.insert(m);
            assertEquals(m.getName(), mealDB.get(3).getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getByIdTest() {

        try {
            mealDB = new MealDatabase(file.getPath());
            mealDB.loadElements();
            assertEquals("Water", mealDB.getMealByID(2).getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
