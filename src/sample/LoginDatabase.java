package sample;

import java.io.*;

public class LoginDatabase extends Database<User> {

    public LoginDatabase(String url) throws IOException {

        super(url);
    }

    public LoginDatabase(String url, String delimiter) throws IOException {

        super(url, delimiter);
    }

    @Override
    public void insert(User user) throws IOException {

        fw = new BufferedWriter(new FileWriter(url, true));
        data.add(user);

        String row = user.getUsername() + delimiter + user.getPassword();
        fw.write(row);
        fw.newLine();
        fw.close();
    }

    @Override
    public void update(User user, User t2) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void delete(User user) throws IOException {
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
            String userName = tokens[0].trim();
            String password = tokens[1].trim();

            data.add(new User(userName, password));

        }
        fileReader.close();
    }

    public boolean containUser(User u) {
        for (User user : data) {
            if (user.getPassword().equals(u.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        LoginDatabase log = new LoginDatabase("account.txt", ";");
        log.loadElements();
        User user = new User("vail", "1234");
        System.out.println(log.containUser(user));
    }

}
