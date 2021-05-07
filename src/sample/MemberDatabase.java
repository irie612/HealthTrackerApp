package sample;

import java.io.*;

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
    public void loadElements() throws IOException {

        fileReader = new BufferedReader(new FileReader(url));
        String line;

        while((line = fileReader.readLine()) != null){

            String[] tokens = line.split(delimiter);
            String groupName = tokens[0].trim();
            String member = tokens[1].trim();
            String s = tokens[2].trim();

            int score = Integer.parseInt(s);
            data.add(new UserGroupMember(groupName, member, score));
        }
        fileReader.close();

    }
}
