package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MemberDatabase extends Database<UserGroupMember> {

    public MemberDatabase (String url) throws IOException {

        super(url);
    }

    public MemberDatabase(String url, String delimiter) throws IOException{

        super(url, delimiter);
    }

    @Override
    public void insert(UserGroupMember userGroupMember) throws IOException {

        fw = new BufferedWriter(new FileWriter(url, true));
        data.add(userGroupMember);

        String row = userGroupMember.getGroupName() + delimiter + userGroupMember.getMemberUsername() + delimiter +
                userGroupMember.getMemberScore();

        fw.write(row);
        fw.newLine();
        fw.close();
    }

    @Override
    public void update(UserGroupMember userGroupMember, UserGroupMember t2) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void delete(UserGroupMember userGroupMember) throws IOException {
        data.remove(userGroupMember);
        clearFile();
        writeAllData();

    }

    @Override
    public void writeAllData() throws IOException {
        fw = new BufferedWriter(new FileWriter(url));

        for (UserGroupMember member : data) {
            String row = member.getGroupName() + delimiter + member.getMemberUsername() + delimiter + member.getMemberScore();

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
            String groupName = tokens[0].trim();
            String member = tokens[1].trim();
            String s = tokens[2].trim();

            int score = Integer.parseInt(s);
            data.add(new UserGroupMember(groupName, member, score));
        }
        fileReader.close();

    }

    public UserGroupMember getByUserName(String userName) {
        for (UserGroupMember member : data) {
            if (member.getMemberUsername().equals(userName)) {
                return member;
            }
        }
        throw new NoSuchElementException("Member not found");
    }

    public ArrayList<UserGroupMember> getAllByGroup(String groupName) {
        ArrayList<UserGroupMember> groupMembers = new ArrayList<>();

        for (UserGroupMember member : data) {
            if (member.getGroupName().equals(groupName)) {
                groupMembers.add(member);
            }
        }
        if (!groupMembers.isEmpty())
            return groupMembers;
        else
            throw new NoSuchElementException("Members not found");
    }

    public ArrayList<UserGroupMember> getAllByUserName(String userName) {
        ArrayList<UserGroupMember> groupMembers = new ArrayList<>();

        for (UserGroupMember member : data) {
            if (member.getMemberUsername().equals(userName)) {
                groupMembers.add(member);
            }
        }
//        if(!groupMembers.isEmpty())
        return groupMembers;
    }
}
