import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class UserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  public void User_objectInstantiatesCorrectly_True() {
    User newUser = new User("Willow");
    assertEquals(true, newUser instanceof User);
  }

  public void User_getNameRetrievesName_True() {
    User newUser = new User("Willow");
    assertEquals("Willow", newUser.getName());
  }

  public void equals_returnsTrueIfUserNameIsSame() {
    User newUser = new User("Willow");
    User anotherUser = new User("Willow");
    assertTrue(newUser.equals(anotherUser));
  }

  public void save_savesUserInfotoDatabase() {
    User newUser = new User("Willow");
    newUser.save();
    User otherUser = User.all().get(0);
    assertEquals(newUser.getId(), otherUser.getId());
  }


}
