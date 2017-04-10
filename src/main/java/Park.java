import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Park {
  private int id;
  private String name;
  private String location;
  private String size;
  private boolean fenced;
  private boolean small;

  public Park(String name, String location, String size, boolean fenced, boolean small) {
    this.name = name;
    this.location = location;
    this.size = size;
  }

  public String getName() {
    return this.name;
  }

  public String getLocation() {
    return this.location;
  }

  public String getSize() {
    return this.size;
  }

}
