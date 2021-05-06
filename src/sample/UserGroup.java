package sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserGroup {

    String groupName;
    String admin;
    int capacity;
    String code;

    HashMap<String, Integer> leaderBoard;

    public UserGroup(String groupName, String admin, int capacity){

        this.groupName = groupName;
        this.admin = admin;
        this.capacity = capacity;
    }

    public UserGroup(String groupName, String admin, int capacity, String code){

        this.groupName = groupName;
        this.admin = admin;
        this.capacity = capacity;
        this.code = code;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getAdmin() {
        return admin;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCode() {
        return code;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HashMap<String, Integer> getLeaderBoard() {
        return leaderBoard;
    }

    public void update(String user, int score){

        leaderBoard = new HashMap<>();
        leaderBoard.put(user, score);
    }

    public void iterator(){

        leaderBoard = getLeaderBoard();
        Iterator iterator = leaderBoard.entrySet().iterator();
        while(iterator.hasNext()){

            Map.Entry m = (Map.Entry) iterator.next();
            System.out.println(m.getKey() + " : " + m.getValue());
        }
    }



    public static void main(String[] args) {
        Users user = new Users("joshua", "joshua@gmail.com", 1.80, 60);
        UserGroup userGroup = new UserGroup("abc", user.getUsername(), 30);
        userGroup.update(user.getUsername(), 100);
        userGroup.iterator();

    }
}
