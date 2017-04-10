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

}
