package sample;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserDatabase extends Database<User> {

    public UserDatabase(String url) throws IOException {

        super(url);
    }

    public UserDatabase(String url, String delimiter) throws IOException {

        super(url, delimiter);
    }

    @Override
    public void insert(User user) throws IOException {

        fw = new BufferedWriter(new FileWriter(url, true));
        data.add(user);

        String row = user.getName() + delimiter + user.getUsername() + delimiter + user.getEmail() + delimiter + user.getWeight() + delimiter +
                user.getHeight() + delimiter + user.getDoB().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        fw.write(row);
        fw.newLine();
        fw.close();
    }

    @Override
    public void update(User oldValue, User newValue) throws IOException {

        if (data.contains(oldValue)) {
            data.remove(oldValue);
            data.add(newValue);
            writeAllData();
        }
    }

    @Override
    public void delete(User user) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void writeAllData() throws IOException {
        fw = new BufferedWriter(new FileWriter(url));

        for (User user : data) {

            String row = user.getName() + delimiter + user.getUsername() + delimiter + user.getEmail() + delimiter + user.getWeight() + delimiter +
                    user.getHeight() + delimiter + user.getDoB().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

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

            String name = tokens[0].trim();
            String username = tokens[1].trim();
            String email = tokens[2].trim();
            String w = tokens[3].trim();
            String h = tokens[4].trim();
            LocalDate dob = LocalDate.parse(tokens[5].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            double weight = Double.parseDouble(w);
            double height = Double.parseDouble(h);

            data.add(new User(name, username, email, height, weight, dob));
        }
        fileReader.close();
    }

    public User getUserByUsername(String username) {
        User foundUser = null;
        for (User user : data) {
            if (user.getUsername().equals(username)) {
                foundUser = user;
            }
        }
        return foundUser;
    }
}
