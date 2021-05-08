package sample;

import java.io.*;

public class UserDatabase extends Database<Users> {

    public UserDatabase(String url) throws IOException{

        super(url);
    }

    public UserDatabase(String url, String delimiter) throws IOException{

        super(url, delimiter);
    }

    @Override
    public void insert(Users users) throws IOException {

        fw = new BufferedWriter(new FileWriter(url, true));
        data.add(users);

        String row = users.getUsername() + delimiter + users.getEmail() + delimiter + users.getWeight() + delimiter +
                users.getHeight();
        fw.write(row);
        fw.newLine();
        fw.close();
    }

    @Override
    public void update(Users users, Users t2) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void delete(Users users) throws IOException {
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
            String username = tokens[0].trim();
            String email = tokens[1].trim();
            String w = tokens[2].trim();
            String h = tokens[3].trim();

            double weight = Double.parseDouble(w);
            double height = Double.parseDouble(h);

            data.add(new Users(username, email, height, weight));
        }
        fileReader.close();
    }
}
