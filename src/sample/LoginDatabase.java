package sample;

import java.io.*;

public class LoginDatabase extends Database<Users> {

    public LoginDatabase(String url) throws IOException{

        super(url);
    }

    public LoginDatabase(String url, String delimiter) throws IOException{

        super(url, delimiter);
    }

    @Override
    public void insert(Users users) throws IOException {

        fw = new BufferedWriter(new FileWriter(url, true));
        data.add(users);

        String row = users.getUsername() + delimiter + users.getPassword();
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
            String userName = tokens[0].trim();
            String password = tokens[1].trim();

            data.add(new Users(userName, password));

        }
        fileReader.close();
    }

    public boolean containUser(Users u){
        for(Users users: data){
            if(users.getPassword().equals(u.getPassword())){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        LoginDatabase log = new LoginDatabase("account.txt", ";");
        log.loadElements();
        Users user = new Users("vail", "1234");
        System.out.println(log.containUser(user));
    }

}
