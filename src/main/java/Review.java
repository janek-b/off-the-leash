import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Review {
  private int id;
  private int userId;
  private int parkId;
  private String title;
  private String content;

  public Review(int userId, int parkId, String title, String content) {
    this.userId = userId;
    this.parkId = parkId;
    this.title = title;
    this.content = content;
  }

  public int getId() {
    return this.id;
  }

  public int getUserId() {
    return this.userId;
  }

  public int getParkId() {
    return this.parkId;
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  @Override
  public boolean equals(Object otherReview) {
    if (!(otherReview instanceof Review)) {
      return false;
    } else {
      Review newReview = (Review) otherReview;
      return this.getTitle().equals(newReview.getTitle()) &&
             this.getContent().equals(newReview.getContent()) &&
             this.getUserId() == newReview.getUserId() &&
             this.getParkId() == newReview.getParkId() &&
             this.getId() == newReview.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO reviews (userId, parkId, title, content) VALUES (:userId, :parkId, :title, :content);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("userId", this.userId)
        .addParameter("parkId", this.parkId)
        .addParameter("title", this.title)
        .addParameter("content", this.content)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Review> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews;";
      return con.createQuery(sql)
        .executeAndFetch(Review.class);
    }
  }

  public static Review find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Review.class);
    }
  }

}
