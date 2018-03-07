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
import java.util.Map;
import java.util.HashMap;

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
    Park testPark1 = new Park("apark", "park-location", "medium", true, true);
    testPark1.save();
    Park testPark2 = new Park("bpark", "park-location", "medium", true, true);
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
    User testUser = new User("franklin", "frankay");
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
    User testUser = new User("franklin", "frankay");
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

  @Test
  public void getReviews_returnsAllReviewsForAPark() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    Review testReview1 = new Review(1, testPark.getId(), "review title1", "review text1");
    testReview1.save();
    Review testReview2 = new Review(1, testPark.getId(), "review title2", "review text2");
    testReview2.save();
    Review testReview3 = new Review(1, 1, "review title3", "review text3");
    testReview3.save();
    Review[] reviews = new Review[] {testReview1, testReview2};
    assertTrue(testPark.getReviews().containsAll(Arrays.asList(reviews)));
    assertFalse(testPark.getReviews().contains(testReview3));
  }

  @Test
  public void getCurrentVisitors_returnsTheCurrentNumberOfCheckedInUsers() {
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    User user1 = new User("franklin", "frankay");
    user1.save();
    User user2 = new User("franklin", "frankay");
    user2.save();
    user1.checkIn(testPark);
    user2.checkIn(testPark);
    assertEquals((Integer) 2, testPark.getCurrentVisitors());
    user1.checkOut();
    assertEquals((Integer) 1, testPark.getCurrentVisitors());
  }

  @Test
  public void getLat_returnsCorrectLatValue() {
    Park testPark = new Park("park", "Portland, OR", "medium", true, true);
    testPark.save();
    assertEquals(45.5230622, testPark.getLat(), 0.000001);
    assertEquals(45.5230622, Park.find(testPark.getId()).getLat(), 0.000001);
    assertEquals(-122.6764816, testPark.getLng(), 0.000001);
    assertEquals(-122.6764816, Park.find(testPark.getId()).getLng(), 0.000001);
  }

  @Test
  public void getCoordinates_returnsCoordinatesForPark() {
    Park testPark = new Park("park", "Portland, OR", "medium", true, true);
    testPark.save();
    Map<String, Object> testCoordinates = new HashMap<String, Object>();
    testCoordinates.put("lat", 45.5230622);
    testCoordinates.put("lng", -122.6764815);
    assertEquals(testCoordinates, testPark.getCoordinates());
  }

  @Test
  public void getAllCoordinates_returnsCoordinatesForAllParks() {
    List<Map<String, Object>> allCoordinates = new ArrayList<Map<String, Object>>();
    Park testPark1 = new Park("park", "Portland, OR", "medium", true, true);
    testPark1.save();
    Park testPark2 = new Park("park", "Hillsboro, OR", "medium", true, true);
    testPark2.save();
    Map<String, Object> location1 = new HashMap<String, Object>();
    location1.put("name", testPark1.getName());
    location1.put("id", testPark1.getId());
    Map<String, Object> testCoordinates1 = new HashMap<String, Object>();
    testCoordinates1.put("lat", 45.5230622);
    testCoordinates1.put("lng", -122.6764815);
    location1.put("position", testCoordinates1);
    allCoordinates.add(location1);
    Map<String, Object> location2 = new HashMap<String, Object>();
    location2.put("name", testPark2.getName());
    location2.put("id", testPark2.getId());
    Map<String, Object> testCoordinates2 = new HashMap<String, Object>();
    testCoordinates2.put("lat", 45.5228939);
    testCoordinates2.put("lng",-122.989827);
    location2.put("position", testCoordinates2);
    allCoordinates.add(location2);
    assertEquals(allCoordinates, Park.getAllCoordinates());
  }

  @Test
  public void getUsersCheckedIn_returnsListOfUsersCheckedInAtAPark() {
    Park testPark1 = new Park("park", "Portland, OR", "medium", true, true);
    testPark1.save();
    Park testPark2 = new Park("second park", "Portland, OR", "medium", false, false);
    testPark2.save();
    User user1 = new User("franklin", "frankay");
    user1.save();
    User user2 = new User("jerry", "jerray");
    user2.save();
    User user3 = new User("jerry", "jerray");
    user3.save();
    user1.checkIn(testPark1);
    user2.checkIn(testPark1);
    user3.checkIn(testPark2);
    User[] usersPark1 = new User[] {user1, user2};
    assertTrue(testPark1.getUsersCheckedIn().containsAll(Arrays.asList(usersPark1)));
    assertFalse(testPark1.getUsersCheckedIn().contains(user3));
    assertTrue(testPark2.getUsersCheckedIn().contains(user3));
  }

}
