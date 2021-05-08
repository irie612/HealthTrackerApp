package sample;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroupMember member = (UserGroupMember) o;
        return memberScore == member.memberScore &&
                Objects.equals(groupName, member.groupName) &&
                Objects.equals(memberUsername, member.memberUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName, memberUsername, memberScore);
    }
}
