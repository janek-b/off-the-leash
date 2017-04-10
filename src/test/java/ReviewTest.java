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

}
