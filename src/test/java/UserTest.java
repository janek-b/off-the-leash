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

  @Test
  public void User_objectInstantiatesCorrectly_True() {
    User newUser = new User("Willow");
    assertEquals(true, newUser instanceof User);
  }

  @Test
  public void User_getNameRetrievesName_True() {
    User newUser = new User("Willow");
    assertEquals("Willow", newUser.getName());
  }

  @Test
  public void equals_returnsTrueIfUserNameIsSame() {
    User newUser = new User("Willow");
    User anotherUser = new User("Willow");
    assertTrue(newUser.equals(anotherUser));
  }

  @Test
  public void save_savesUserInfotoDatabase() {
    User newUser = new User("Willow");
    newUser.save();
    User otherUser = User.all().get(0);
    assertEquals(newUser.getId(), otherUser.getId());
  }

  @Test
  public void find_returnsAnimalWithSameId_secondAnimal() {
    User user = new User("Fred");
    user.save();
    User user1 = new User("Frank");
    user1.save();
    assertEquals(user1, User.find(user1.getId()));
  }

}
