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

public class ReviewTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void review_instantiatesCorrectly() {
    Review testReview = new Review(1, 1, "review title", "review text");
    assertTrue(testReview instanceof Review);
  }

  @Test
  public void getUserId_returnsReviewUserIdCorrectly() {
    Review testReview = new Review(1, 1, "review title", "review text");
    assertEquals(1, testReview.getUserId());
  }

  @Test
  public void getParkId_returnsReviewParkIdCorrectly() {
    Review testReview = new Review(1, 1, "review title", "review text");
    assertEquals(1, testReview.getParkId());
  }

  @Test
  public void getTitle_returnsReviewTitleCorrectly() {
    Review testReview = new Review(1, 1, "review title", "review text");
    assertEquals("review title", testReview.getTitle());
  }

  @Test
  public void getContent_returnsReviewContentCorrectly() {
    Review testReview = new Review(1, 1, "review title", "review text");
    assertEquals("review text", testReview.getContent());
  }

  @Test
  public void equals_comparesReviewsBasedOnAllProperties() {
    Review testReview1 = new Review(1, 1, "review title", "review text");
    Review testReview2 = new Review(1, 1, "review title", "review text");
    assertTrue(testReview1.equals(testReview2));
  }

  @Test
  public void save_savesReviewToTheDB() {
    Review testReview = new Review(1, 1, "review title", "review text");
    testReview.save();
    assertTrue(Review.all().get(0).equals(testReview));
  }

  @Test
  public void all_returnsAllReviewsFromTheDB() {
    Review testReview1 = new Review(1, 1, "review title", "review text");
    testReview1.save();
    Review testReview2 = new Review(1, 1, "review title", "review text");
    testReview2.save();
    assertTrue(Review.all().get(0).equals(testReview1));
    assertTrue(Review.all().get(1).equals(testReview2));
  }

  @Test
  public void save_assignsAnId() {
    Review testReview = new Review(1, 1, "review title", "review text");
    testReview.save();
    assertEquals(testReview.getId(), Review.all().get(0).getId());
  }

  @Test
  public void getId_returnsAnObjectsId() {
    Review testReview = new Review(1, 1, "review title", "review text");
    testReview.save();
    assertTrue(testReview.getId() > 0);
  }

  @Test
  public void find_returnsReviewWithMatchingId() {
    Review testReview = new Review(1, 1, "review title", "review text");
    testReview.save();
    Review testReview2 = new Review(1, 1, "review title", "review text");
    testReview2.save();
    assertTrue(Review.find(testReview2.getId()).equals(testReview2));
  }

  @Test
  public void update_updatesReviewPropertiesInDB() {
    Review testReview = new Review(1, 1, "review title", "review text");
    testReview.save();
    testReview.update("good review title", "very good review text");
    assertEquals("good review title", Review.find(testReview.getId()).getTitle());
    assertEquals("very good review text", Review.find(testReview.getId()).getContent());
  }

  @Test
  public void delete_deletesTheObjectFromTheDB() {
    Review testReview = new Review(1, 1, "review title", "review text");
    testReview.save();
    testReview.delete();
    assertEquals(null, Review.find(testReview.getId()));
  }

}
