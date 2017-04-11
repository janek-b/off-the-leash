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
    Dog dog1 = new Dog()
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
    user.checkOut();
    assertEquals(null, user.getCheckedIn());
  }

  @Test
  public void getReviews_returnsAllReviewsForAUser() {
    User user = new User("Fred");
    user.save();
    Park testPark = new Park("park", "park-location", "medium", true, true);
    testPark.save();
    Review testReview1 = new Review(user.getId(), testPark.getId(), "review title1", "review text1");
    testReview1.save();
    Review testReview2 = new Review(user.getId(), testPark.getId(), "review title2", "review text2");
    testReview2.save();
    Review testReview3 = new Review(1, 1, "review title3", "review text3");
    testReview3.save();
    Review[] reviews = new Review[] {testReview1, testReview2};
    assertTrue(user.getReviews().containsAll(Arrays.asList(reviews)));
    assertFalse(user.getReviews().contains(testReview3));
  }

  @Test
  public void getFavoriteParks_returnsListOfParksAUserHasUpvoted() {
    User user = new User("Fred");
    user.save();
    Park testPark = new Park("park1", "park-location1", "medium1", true, true);
    testPark.save();
    Park testPark2 = new Park("park2", "park-location2", "medium2", true, true);
    testPark2.save();
    Park testPark3 = new Park("park3", "park-location3", "medium3", true, true);
    testPark3.save();
    testPark.upVote(user);
    testPark2.upVote(user);
    Park[] parks = new Park[] {testPark, testPark2};
    assertTrue(user.getFavoriteParks().containsAll(Arrays.asList(parks)));
    assertFalse(user.getFavoriteParks().contains(testPark3));
  }

  @Test
  public void getLeastFavoriteParks_returnsListOfParksAUserHasUpvoted() {
    User user = new User("Fred");
    user.save();
    Park testPark = new Park("park1", "park-location1", "medium1", true, true);
    testPark.save();
    Park testPark2 = new Park("park2", "park-location2", "medium2", true, true);
    testPark2.save();
    Park testPark3 = new Park("park3", "park-location3", "medium3", true, true);
    testPark3.save();
    testPark.downVote(user);
    testPark2.downVote(user);
    Park[] parks = new Park[] {testPark, testPark2};
    assertTrue(user.getLeastFavoriteParks().containsAll(Arrays.asList(parks)));
    assertFalse(user.getLeastFavoriteParks().contains(testPark3));
  }

}
