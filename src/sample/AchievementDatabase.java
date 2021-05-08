package sample;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AchievementDatabase extends Database<Achievement> {

    public AchievementDatabase(String url) throws IOException {

        super(url);
    }

    public AchievementDatabase(String url, String delimiter) throws IOException{

        super(url, delimiter);
    }

    @Override
    public void insert(Achievement achievement) throws IOException {

        fw = new BufferedWriter(new FileWriter(url, true));
        data.add(achievement);

        String row = achievement.getName() + delimiter + achievement.getScore() + delimiter + achievement.getUserName() +
                delimiter + achievement.getDeadline().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + delimiter + achievement.getUserGroupName();

        fw.write(row);
        fw.newLine();
        fw.close();
    }

    @Override
    public void update(Achievement achievement, Achievement t2) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void delete(Achievement achievement) throws IOException {
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

        while ((line = fileReader.readLine()) != null){

            String[] tokens = line.split(delimiter);
            String name = tokens[0].trim();
            String s = tokens[1].trim();
            String userName = tokens[2].trim();
            String d = tokens[3].trim();
            String u = tokens[4].trim();

            int score = Integer.parseInt(s);
            LocalDate date = LocalDate.parse(d, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            data.add(new Achievement(name, score, userName, date, u));
        }
        fileReader.close();
    }
}
