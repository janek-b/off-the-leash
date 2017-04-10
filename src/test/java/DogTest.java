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

public class DogTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void dog_instantiatesCorrectly_true() {
    Dog newDog = new Dog("Rufus", "Male", "greyhound", false, 1);
    assertEquals(true, newDog instanceof Dog);
  }


}
