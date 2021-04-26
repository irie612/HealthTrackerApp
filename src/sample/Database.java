package sample;

import sample.Users;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;

public class Database {

    ArrayList<Users> users;
    private String fileName;
    private File file;

    public Database(String fileName){

        file = new File(fileName);
        this.fileName = fileName;
        users = new ArrayList<>();
    }

    public void addUser(String username, String password){

        Users u = new Users(username, password);
        users.add(u);
    }

    public ArrayList<Users> getUsers(){
        return users;
    }

    public void save() throws IOException{

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for(Users user : users){

                String[] values = {user.getUsername(), user.getPassword()};
                String line = String.join(";",values);
                bw.append(line);
                bw.append("\n");
            }
            bw.flush();
        }
    }

    public void load() throws IOException{

        users.clear();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){

            String s;
            while((s = br.readLine()) !=null){

                String[] values = s.split(";");
                String userName = values[0];
                String password = values[1];
                addUser(userName, password);

            }
        }
    }

    public static void main(String[] args) throws IOException {
        Database database = new Database("users.csv");
//        database.addUser("joshua", "12345");
//        database.save();

        database.load();
        for(Users u : database.getUsers()){
            System.out.println(u);
        }

    }


}
