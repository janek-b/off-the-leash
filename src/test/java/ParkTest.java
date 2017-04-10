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
    Park testPark = new Park("park", "park-location", "medium", true, true);
    assertTrue(testPark instanceof Park);
  }

  @Test
  public void getName_returnsParkNameCorrectly() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    assertEquals("park", testPark.getName());
  }

  @Test
  public void getLocation_returnsParkLocationCorrectly() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    assertEquals("park-location", testPark.getLocation());
  }

  @Test
  public void getSize_returnsParkSizeCorrectly() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    assertEquals("medium", testPark.getSize());
  }

  @Test
  public void isFenced_returnsTrueIfParkIsFenced() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    assertTrue(testPark.isFenced());
  }

  @Test
  public void hasSmallDogsArea_returnsTrueIfParkHasSeperateAreaForSmallDogs() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    assertTrue(testPark.hasSmallDogsArea());
  }

  @Test
  public void equals_comparesParksBasedOnAllProperties() {
    Park testPark1 = new Park("park", "park-location", "medium", true, true);
    Park testPark2 = new Park("park", "park-location", "medium", true, true);
    assertTrue(testPark1.equals(testPark2));
  }

  @Test
  public void save_savesParkToTheDB() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    assertTrue(Park.all().get(0).equals(testPark));
  }

  @Test
  public void all_returnsAllParksFromTheDB() {
    Park testPark1 = new Park("park", "park-location", "medium", true, true);
    testPark1.save();
    Park testPark2 = new Park("park", "park-location", "medium", true, true);
    testPark2.save();
    assertTrue(Park.all().get(0).equals(testPark1));
    assertTrue(Park.all().get(1).equals(testPark2));
  }

  @Test
  public void save_assignsAnId() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    assertEquals(testPark.getId(), Park.all().get(0).getId());
  }

  @Test
  public void getId_returnsAnObjectsId() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    assertTrue(testPark.getId() > 0);
  }

  @Test
  public void find_returnsParkWithMatchingId() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    Park testPark2 = new Park("park", "park-location", "medium", true, true);
    testPark2.save();
    assertTrue(Park.find(testPark2.getId()).equals(testPark2));
  }

  @Test
  public void update_updatesParkPropertiesInDB() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    testPark.update("park1", "park-locations", "large", false, false);
    assertEquals("park1", Park.find(testPark.getId()).getName());
    assertEquals("park1", testPark.getName());
    assertEquals("park-locations", Park.find(testPark.getId()).getLocation());
    assertEquals("large", Park.find(testPark.getId()).getSize());
    assertEquals(false, Park.find(testPark.getId()).isFenced());
    assertEquals(false, Park.find(testPark.getId()).hasSmallDogsArea());
  }

  @Test
  public void delete_deletesTheObjectFromTheDB() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    testPark.delete();
    assertEquals(null, Park.find(testPark.getId()));
  }

  @Test
  public void upVote_increasesUpVoteCount() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    User testUser = new User("testUser");
    testUser.save();
    assertEquals(0, testPark.getUpVotes());
    testPark.upVote(testUser);
    assertEquals(1, testPark.getUpVotes());
    testPark.upVote(testUser);
    assertEquals(0, testPark.getUpVotes());
    testPark.upVote(testUser);
    assertEquals(1, testPark.getUpVotes());
    testPark.downVote(testUser);
    assertEquals(0, testPark.getUpVotes());
    assertEquals(1, testPark.getDownVotes());
  }

  @Test
  public void downVote_increasesUpVoteCount() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    User testUser = new User("testUser");
    testUser.save();
    assertEquals(0, testPark.getDownVotes());
    testPark.downVote(testUser);
    assertEquals(1, testPark.getDownVotes());
    testPark.downVote(testUser);
    assertEquals(0, testPark.getDownVotes());
    testPark.downVote(testUser);
    assertEquals(1, testPark.getDownVotes());
    testPark.upVote(testUser);
    assertEquals(0, testPark.getDownVotes());
    assertEquals(1, testPark.getUpVotes());
  }

}
