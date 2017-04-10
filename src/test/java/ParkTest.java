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

public class ParkTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void park_instantiatesCorrectly() {
    Park testPark = new Park("park", "park-location", "medium", true, false);
    assertTrue(testPark instanceof Park);
  }

  @Test
  public void getName_returnsParkNameCorrectly() {
    Park testPark = new Park("park", "park-location", "medium", true, false);
    assertEquals("park", testPark.getName());
  }

  @Test
  public void getLocation_returnsParkLocationCorrectly() {
    Park testPark = new Park("park", "park-location", "medium", true, false);
    assertEquals("park-location", testPark.getLocation());
  }

  @Test
  public void getSize_returnsParkSizeCorrectly() {
    Park testPark = new Park("park", "park-location", "medium", true, false);
    assertEquals("medium", testPark.getSize());
  }

}
