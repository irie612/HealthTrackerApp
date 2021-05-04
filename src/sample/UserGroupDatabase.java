package sample;

import java.io.*;

public class UserGroupDatabase extends Database<UserGroup> {

    public UserGroupDatabase(String url) throws IOException{

        super(url);
    }

    public UserGroupDatabase(String url, String delimiter) throws IOException{

        super(url, delimiter);
    }

    @Override
    public void insert(UserGroup userGroup) throws IOException {

        fw = new BufferedWriter(new FileWriter(url));
        data.add(userGroup);

        String row = userGroup.getGroupName() + delimiter + userGroup.getAdmin() + delimiter + userGroup.getCapacity() +
                 delimiter + userGroup.getCode();

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
            String groupName = tokens[0].trim();
            String admin = tokens[1].trim();
            String c = tokens[2].trim();
            String code = tokens[3].trim();

            int capacity = Integer.parseInt(c);

            data.add(new UserGroup(groupName, admin, capacity, code));
        }
        fileReader.close();
    }
}
