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

        fw = new BufferedWriter(new FileWriter(url));
        data.add(users);

        String row = users.getUsername() + delimiter + users.getPassword();
        fw.write(row);
        fw.newLine();
        fw.close();
    }

    @Override
    public void load() throws IOException {

        fileReader = new BufferedReader(new FileReader(url));
        String line;

        while((line = fileReader.readLine()) != null){

            String[] tokens = line.split(delimiter);
            String userName = tokens[0].trim();
            String password = tokens[1].trim();

            data.add(new Users(userName, password));

        }
        fileReader.close();
    }
}
