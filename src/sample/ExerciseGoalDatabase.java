package sample;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExerciseGoalDatabase extends Database<ExerciseGoal>{

    public ExerciseGoalDatabase(String url) throws IOException {
        super(url);
    }

   public ExerciseGoalDatabase(String url, String delimiter) throws IOException {
        super(url, delimiter);
    }

    @Override
    public void insert(ExerciseGoal exerciseGoal) throws IOException {
        fw = new BufferedWriter(new FileWriter(url, true));
        data.add(exerciseGoal);

        String row = exerciseGoal.getGoalType() + delimiter +
                exerciseGoal.getType() + delimiter +
                exerciseGoal.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + delimiter +
                exerciseGoal.getEndDate() .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + delimiter +
                exerciseGoal.getExerciseToDo().format(DateTimeFormatter.ofPattern("HH:mm"))  + delimiter +
                exerciseGoal.getUserName() + delimiter +
                exerciseGoal.isCompleted();

        fw.write(row);
        fw.newLine();
        fw.close();
    }

    @Override
    public void update(ExerciseGoal oldValue, ExerciseGoal newValue) throws IOException {
        if (data.contains(oldValue)) {
            data.remove(oldValue);
            data.add(newValue);
            writeAllData();
        }
    }

    @Override
    public void delete(ExerciseGoal exerciseGoal) throws IOException {
        data.remove(exerciseGoal);
        writeAllData();
    }

    @Override
    public void writeAllData() throws IOException {
        fw = new BufferedWriter(new FileWriter(url));

        for (ExerciseGoal exerciseGoal : data) {

            String row = exerciseGoal.getGoalType() + delimiter +
                    exerciseGoal.getType() + delimiter +
                    exerciseGoal.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + delimiter +
                    exerciseGoal.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + delimiter +
                    exerciseGoal.getExerciseToDo().format(DateTimeFormatter.ofPattern("HH:mm")) + delimiter +
                    exerciseGoal.getUserName() + delimiter +
                    exerciseGoal.isCompleted();

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

            Goal.goalType goalType = Goal.goalType.valueOf(tokens[0].trim());

            String type = tokens[1].trim();

            LocalDate startDate = LocalDate.parse(tokens[2].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            LocalDate endDate = LocalDate.parse(tokens[3].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            LocalTime exerciseToDo = LocalTime.parse(tokens[4].trim(), DateTimeFormatter.ofPattern("HH:mm"));

            String userName = tokens[5].trim();

            boolean isCompleted = Boolean.parseBoolean(tokens[6].trim());

            data.add(new ExerciseGoal(goalType, type, exerciseToDo, startDate, endDate, userName, isCompleted));

        }
        fileReader.close();
    }

    public ArrayList<ExerciseGoal> getByUsername(String username){
        ArrayList<ExerciseGoal> goals = new ArrayList<>();

        for(ExerciseGoal e : data){
            if(e.getUserName().equals(username)) {
                goals.add(e);
            }
        }
        return goals;
    }

    public ExerciseGoal getTopGoalByUsername(String username){
        ExerciseGoal goal = null;
        LocalTime time = LocalTime.of(0, 0, 0);
        for(ExerciseGoal e: data){
            if(e.getUserName().equals(username)) {
                if(e.getExerciseToDo().isAfter(time)){
                    time = e.getExerciseToDo();
                    goal = e;
                }
            }
        }
        return goal;
    }

    public ArrayList<ExerciseGoal> getByUsernameAndCompleteness(String username, boolean isComplete){
        ArrayList<ExerciseGoal> goals = new ArrayList<>();
        for (ExerciseGoal e : data){
            if(e.getUserName().equals(username) && e.isCompleted() == isComplete){
                goals.add(e);
            }
        }
        return goals;
    }
}
