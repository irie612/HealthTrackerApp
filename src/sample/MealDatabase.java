package sample;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MealDatabase extends Database<Meal> {


    public MealDatabase(String url) throws IOException {
        super(url);
    }

    public MealDatabase(String url, String delimiter) throws IOException {
        super(url, delimiter);
    }

    @Override
    public void insert(Meal meal) throws IOException {

        fw = new BufferedWriter(new FileWriter(url, true));
        data.add(meal);

        String row = meal.getId() + delimiter + meal.getName() + delimiter + meal.getType()
                + delimiter + meal.getCalories() + delimiter +
                meal.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + delimiter +
                meal.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        System.out.println(row);
        fw.write(row);
        fw.newLine();
        fw.close();
    }

    @Override
    public void update(Meal meal, Meal t2) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void delete(Meal meal) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void writeAllData() throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void loadElements() throws IOException {

        fileReader = new BufferedReader(new FileReader(url));
        String line;
        while ((line = fileReader.readLine()) != null) {
            String[] tokens = line.split(delimiter);

//            System.out.println(Arrays.toString(tokens));
            long id = Long.parseLong(tokens[0].trim());
//            System.out.println(id);


            String name = tokens[1].trim();

            Meal.MealType mealType = Meal.MealType.valueOf(tokens[2].trim());

            double cal = Double.parseDouble(tokens[3].trim());

            String date = tokens[4].trim();

            LocalDate d = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            String time = tokens[5].trim();
            LocalTime t = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));

            data.add(new Meal(id, name, mealType, cal, d, t));


//            for (String e : tokens) {
//                System.out.println(e.trim());
//            }

        }
        fileReader.close();
    }

    public Meal getMealByID(long id) throws Exception {
        for (Meal m : data) {
            if (m.getId() == id) {
                return m;
            }
        }
        //throw an exception
        throw new Exception("Element not found");
    }

    //Test harness
    public static void main(String[] args) {

        MealDatabase mealDB = null;

        Meal m = new Meal(1, "Cereal", Meal.MealType.BREAKFAST, 300);
        Meal d = new Meal(2, "Water", Meal.MealType.DRINK, 0);
        Meal d1 = new Meal(3, "Orange Juice", Meal.MealType.DRINK, 99);
        try {
            mealDB = new MealDatabase("src/sample/data/meals.csv");
            mealDB.loadElements();
            System.out.println("ID 1:" + mealDB.getMealByID(1));
            System.out.println("ID 3:" + mealDB.getMealByID(3));
            System.out.println(mealDB.data);
            mealDB.insert(m);
            mealDB.insert(d);
            mealDB.insert(d1);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
