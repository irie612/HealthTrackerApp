package sample;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalorieGoalDatabase extends Database<CaloriesGoal> {

    public CalorieGoalDatabase(String url) throws IOException {
        super(url);
    }

    public CalorieGoalDatabase(String url, String delimiter) throws IOException {
        super(url, delimiter);
    }

    @Override
    public void insert(CaloriesGoal caloriesGoal) throws IOException {
        fw = new BufferedWriter(new FileWriter(url, true));
        data.add(caloriesGoal);

        String row = caloriesGoal.getType() + delimiter + caloriesGoal.getStartDate() + delimiter + caloriesGoal.getEndDate() +
                delimiter + caloriesGoal.getUserName();

        fw.write(row);
        fw.newLine();
        fw.close();
    }

    @Override
    public void update(CaloriesGoal oldValue, CaloriesGoal newValue) throws IOException {
        if (data.contains(oldValue)) {
            data.remove(oldValue);
            data.add(newValue);
            writeAllData();
        }
    }

    @Override
    public void delete(CaloriesGoal caloriesGoal) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void writeAllData() throws IOException {
        fw = new BufferedWriter(new FileWriter(url));

        for (CaloriesGoal caloriesGoal : data) {

            String row = caloriesGoal.getType() + delimiter +
                    caloriesGoal.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + delimiter + caloriesGoal.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                    delimiter + caloriesGoal.getCalToBurn() + delimiter + caloriesGoal.getUserName();

            fw.write(row);
            fw.newLine();
        }
        fw.close();
    }

    @Override
    public void loadElements() throws IOException {
        fileReader = new BufferedReader(new FileReader(url));
        String line;
        while ((line = fileReader.readLine()) != null) {
            String[] tokens = line.split(delimiter);

            Goal.goalType type = Goal.goalType.valueOf(tokens[0].trim());

            LocalDate startDate = LocalDate.parse(tokens[1].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            LocalDate endDate = LocalDate.parse(tokens[2].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            int calToBurn = Integer.parseInt(tokens[3].trim());

            String userName = tokens[4].trim();

            data.add(new CaloriesGoal(type, startDate, endDate, calToBurn, userName));

        }
        fileReader.close();
    }

    public CaloriesGoal getByUsername(String username) {
        CaloriesGoal goal = null;

        for (CaloriesGoal c : data) {
            if (c.getUserName().equals(username)) {
                goal = c;
                break;
            }
        }
        return goal;
    }
}
