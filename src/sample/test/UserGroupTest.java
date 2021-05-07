package sample.test;

import org.junit.Test;
import sample.UserGroup;
import sample.Users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class UserGroupTest {

    Users user = new Users("abc", "abc@gmail.com", 1.80, 60);
    UserGroup userGroup = new UserGroup("xyz", user.getUsername(), 30, "qwertyuiop");

    @Test
    public void getGroupNameTest(){

        assertEquals("xyz", userGroup.getGroupName());
    }

    @Test
    public void setGroupNameTest(){

        userGroup.setGroupName("zyx");
        assertEquals("zyx", userGroup.getGroupName());
    }

    @Test
    public void getAdminTest(){

        assertEquals("abc", userGroup.getAdmin());
    }

    @Test
    public void setAdminTest(){

        userGroup.setAdmin("cba");
        assertEquals("cba", userGroup.getAdmin());
    }

    @Test
    public void getCapacityTest(){

        assertEquals(30, userGroup.getCapacity());
    }

    @Test
    public void setCapacityTest(){

        userGroup.setCapacity(40);
        assertEquals(40, userGroup.getCapacity());
    }

    @Test
    public void getCodeTest(){

        assertEquals("qwertyuiop", userGroup.getCode());
    }

    @Test
    public void setCodeTest(){

        userGroup.setCode("poiuytrewq");
        assertEquals("poiuytrewq", userGroup.getCode());
    }

    @Test
    public void updateLeaderBoardTest(){

        userGroup.update(userGroup.getAdmin(), 100);
        assertEquals((long) userGroup.getLeaderBoard().get(userGroup.getAdmin()), 100);
    }
}
