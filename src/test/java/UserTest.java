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

  @Test
  public void update_updatesUser_true() {
    User user = new User("Fred");
    user.save();
    user.update("Freddie");
    assertEquals("Freddie", user.getName());
  }

  @Test
  public void delete_deletesUser() {
    User user = new User("Fred");
    user.save();
    user.delete();
    assertEquals(0, User.all().size());
  }

  @Test
  public void checkIn_addsEntryToCheckinJoinTable() {
    User user = new User("Fred");
    user.save();
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    user.checkIn(testPark);
    assertEquals(testPark, user.getCheckedIn());
  }

  @Test
  public void checkOut_updatesEntryInCheckinJoinTable() {
    User user = new User("Fred");
    user.save();
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    user.checkIn(testPark);
    assertEquals(testPark, user.getCheckedIn());
    user.checkOut(testPark);
    assertEquals(null, user.getCheckedIn());
  }

}
