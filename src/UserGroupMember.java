public class UserGroupMember {

    private String groupName;
    private String memberUsername;
    private int memberScore;

    public UserGroupMember(String groupName, String memberUsername, int memberScore) {
        this.groupName = groupName;
        this.memberUsername = memberUsername;
        this.memberScore = memberScore;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public int getMemberScore() {
        return memberScore;
    }
}
