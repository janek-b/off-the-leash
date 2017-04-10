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

}
