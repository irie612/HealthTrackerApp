package sample;

import java.io.*;
import java.util.NoSuchElementException;

public class UserGroupDatabase extends Database<UserGroup> {

    public UserGroupDatabase(String url) throws IOException{

        super(url);
    }

    public UserGroupDatabase(String url, String delimiter) throws IOException{

        super(url, delimiter);
    }

    @Override
    public void insert(UserGroup userGroup) throws IOException {

        fw = new BufferedWriter(new FileWriter(url, true));
        data.add(userGroup);

        String row = userGroup.getGroupName() + delimiter + userGroup.getAdmin() + delimiter + userGroup.getCapacity() +
                delimiter + userGroup.getCode();

        fw.write(row);
        fw.newLine();
        fw.close();
    }

    @Override
    public void update(UserGroup oldValue, UserGroup newValue) throws IOException {
        if (data.contains(oldValue)) {
            data.remove(oldValue);
            data.add(newValue);
            writeAllData();
        }

    }

    @Override
    public void delete(UserGroup userGroup) throws IOException {
        data.remove(userGroup);
        clearFile();
        writeAllData();
    }

    @Override
    public void writeAllData() throws IOException {
        fw = new BufferedWriter(new FileWriter(url));

        for (UserGroup userGroup : data) {
            String row = userGroup.getGroupName() + delimiter + userGroup.getAdmin() + delimiter + userGroup.getCapacity() +
                    delimiter + userGroup.getCode();

            fw.write(row);
            fw.newLine();
        }
        fw.close();
    }

    public UserGroup getByGroupCode(String code) {
        for (UserGroup ug : data) {
            if (ug.code.equals(code)) {
                return ug;
            }
        }
        throw new NoSuchElementException("Group with code not found");
    }


    public UserGroup getByGroupName(String groupName) {
        for (UserGroup ug : data) {
            if (ug.getGroupName().equals(groupName)) {
                return ug;
            }
        }
        throw new NoSuchElementException("Group with the name " + groupName + " not found");
    }

    @Override
    public void loadElements() throws IOException {
        fileReader = new BufferedReader(new FileReader(url));
        String line;

        while ((line = fileReader.readLine()) != null) {

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


    //TODO getGroupByName

}
