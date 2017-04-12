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
    User newUser = new User("Willie", "will123");
    assertEquals(true, newUser instanceof User);
  }

  @Test
  public void User_getNameRetrievesName_True() {
    User newUser = new User("Willie", "will123");
    newUser.save();
    newUser.update("Willow");
    assertEquals("Willow", newUser.getName());
  }

  @Test
  public void equals_returnsTrueIfUserNameIsSame() {
    User newUser = new User("Willie", "will123");
    User anotherUser = new User("Willie", "will123");
    assertTrue(newUser.equals(anotherUser));
  }

  @Test
  public void save_savesUserInfotoDatabase() {
    User newUser = new User("Willie", "will123");
    newUser.save();
    User otherUser = User.all().get(0);
    assertEquals(newUser.getId(), otherUser.getId());
  }

  @Test
  public void find_returnsUserWithSameId_secondUser() {
    User user = new User("Willie", "will123");
    user.save();
    User user1 = new User("franklin", "frankay");
    user1.save();
    System.out.println(user.getId());
    System.out.println(user1.getId());
    assertTrue(User.find(user1.getId()).equals(user1));

  }

  @Test
  public void update_updatesUser_true() {
    User user = new User("Willie", "will123");
    user.save();
    user.update("Freddie");
    assertEquals("Freddie", user.getName());
  }

  @Test
  public void delete_deletesUser() {
    User user = new User("Willie", "will123");
    user.save();
    Dog dog = new Dog("Rufus", "Male", "greyhound", false, user.getId());
    dog.save();
    Dog newDog = new Dog("Rufus", "Male", "greyhound", false, user.getId());
    newDog.save();
    user.delete();
    assertEquals(null, Dog.find(dog.getId()));
    assertEquals(null, Dog.find(newDog.getId()));
    assertEquals(null, User.find(user.getId()));
  }

  @Test
  public void checkIn_addsEntryToCheckinJoinTable() {
    User user = new User("Willie", "will123");
    user.save();
    Park testPark = new Park("park", "Portland, OR", "medium", true, true);
    testPark.save();
    user.checkIn(testPark);
    assertEquals(testPark, user.getCheckedIn());
  }

  @Test
  public void checkOut_updatesEntryInCheckinJoinTable() {
    User user = new User("Willie", "will123");
    user.save();
    Park testPark = new Park("park", "Portland, OR", "medium", true, true);
    testPark.save();
    user.checkIn(testPark);
    assertEquals(testPark, user.getCheckedIn());
    user.checkOut();
    assertEquals(null, user.getCheckedIn());
  }

  @Test
  public void getReviews_returnsAllReviewsForAUser() {
    User user = new User("Willie", "will123");
    user.save();
    Park testPark = new Park("park", "Portland, OR", "medium", true, true);
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
    User user = new User("Willie", "will123");
    user.save();
    Park testPark = new Park("park1", "Portland, OR1", "medium1", true, true);
    testPark.save();
    Park testPark2 = new Park("park2", "Portland, OR2", "medium2", true, true);
    testPark2.save();
    Park testPark3 = new Park("park3", "Portland, OR3", "medium3", true, true);
    testPark3.save();
    testPark.upVote(user);
    testPark2.upVote(user);
    Park[] parks = new Park[] {testPark, testPark2};
    assertTrue(user.getFavoriteParks().containsAll(Arrays.asList(parks)));
    assertFalse(user.getFavoriteParks().contains(testPark3));
  }

  @Test
  public void getLeastFavoriteParks_returnsListOfParksAUserHasUpvoted() {
    User user = new User("Willie", "will123");
    user.save();
    Park testPark = new Park("park1", "Portland, OR1", "medium1", true, true);
    testPark.save();
    Park testPark2 = new Park("park2", "Portland, OR2", "medium2", true, true);
    testPark2.save();
    Park testPark3 = new Park("park3", "Portland, OR3", "medium3", true, true);
    testPark3.save();
    testPark.downVote(user);
    testPark2.downVote(user);
    Park[] parks = new Park[] {testPark, testPark2};
    assertTrue(user.getLeastFavoriteParks().containsAll(Arrays.asList(parks)));
    assertFalse(user.getLeastFavoriteParks().contains(testPark3));
  }

  @Test
  public void getAllDogs_RetrievesDogsAssociatedWithUser() {
    User user = new User ("Willie", "will123");
    user.save();
    Dog dog = new Dog("Rufus", "Male", "greyhound", false, user.getId());
    dog.save();
    assertTrue(user.getAllDogs().contains(dog));
  }

  @Test
  public void getUsernameAndGetPassword_retrievesUsernameAssociatedWithUser() {
    User user = new User("Willie", "will123");
    user.save();
    assertEquals("Willie", user.getUsername());
    assertEquals("will123", user.getPassword());
  }

  @Test
  public void getAdmin_retrievesUsernameAssociatedWithUser() {
    User user = new User("Willie", "will123");
    user.save();
    assertFalse(user.getAdmin());
  }

  @Test
  public void findByUserName_retrievesTheUserByTheirUserName() {
    User user = new User("Willie", "will123");
    user.save();
    assertEquals(user, User.findByUserName("Willie"));
  }

  @Test
  public void makeAdmin_changesUserAdminValueToTrue() {
    User user = new User("Willie", "will123");
    user.save();
    user.makeAdmin();
    assertTrue(user.getAdmin());
  }

}
