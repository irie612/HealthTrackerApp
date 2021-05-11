package sample;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExerciseDatabase extends Database<Exercise>{


    public ExerciseDatabase(String url) throws IOException {
        super(url);
    }

    public ExerciseDatabase(String url, String delimiter) throws IOException {
        super(url, delimiter);
    }

    @Override
    public void insert(Exercise exercise) throws IOException {

        BufferedWriter fw = new BufferedWriter(new FileWriter(url, true));
        data.add(exercise);

        String row = exercise.getDistance() + delimiter + exercise.getDuration()
                + delimiter + exercise.getType() + delimiter +
                exercise.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + delimiter +
                exercise.getTime().format(DateTimeFormatter.ofPattern("HH:mm")) + delimiter +
                exercise.getOther() + delimiter +
                exercise.getUsername();

        System.out.println(row);
        fw.write(row);
        fw.newLine();
        fw.close();
    }

    @Override
    public void delete(Exercise exercise) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void writeAllData() throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void update(Exercise e, Exercise e2) throws IOException {
        throw new UnsupportedEncodingException();
    }


    @Override
    public void loadElements() throws IOException {

        BufferedReader fileReader = new BufferedReader(new FileReader(url));
        String line;
        while ((line = fileReader.readLine()) != null) {
            String[] tokens = line.split(delimiter);

            double distance = Double.parseDouble(tokens[0].trim());

            double duration = Double.parseDouble(tokens[1].trim());

            Exercise.exerciseType exerciseType = Exercise.exerciseType.valueOf(tokens[2].trim());

            String date = tokens[3].trim();
            LocalDate d = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            String time = tokens[4].trim();
            LocalTime t = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));

            String username = tokens[6].trim();

            if(exerciseType.equals(Exercise.exerciseType.OTHER)){
                String other = tokens[5].trim();
                data.add(new Exercise(distance, duration, exerciseType, other, d, t, username));
            }
            else{
                data.add(new Exercise(distance, duration, exerciseType, d, t, username));
            }
        }
        fileReader.close();
    }

    public ArrayList<Exercise> getAllByUserNameAndDate(String username, LocalDate date) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        for (Exercise e : data) {
            if (e.getUsername().equals(username) && e.getDate().equals(date)) {
                exercises.add(e);
            }
        }
        return exercises;
    }
}
