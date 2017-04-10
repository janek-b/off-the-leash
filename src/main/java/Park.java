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
    this.fenced = fenced;
    this.small = small;
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

  public boolean isFenced() {
    return this.fenced;
  }

  public boolean hasSmallDogsArea() {
    return this.small;
  }

  public int getId() {
    return this.id;
  }

  @Override
  public boolean equals(Object otherPark) {
    if (!(otherPark instanceof Park)) {
      return false;
    } else {
      Park newPark = (Park) otherPark;
      return this.getName().equals(newPark.getName()) &&
             this.getLocation().equals(newPark.getLocation()) &&
             this.getSize().equals(newPark.getSize()) &&
             this.isFenced() == newPark.isFenced() &&
             this.hasSmallDogsArea() == newPark.hasSmallDogsArea() &&
             this.getId() == newPark.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO parks (name, location, size, fenced, small) VALUES (:name, :location, :size, :fenced, :small);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("location", this.location)
        .addParameter("size", this.size)
        .addParameter("fenced", this.fenced)
        .addParameter("small", this.small)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Park> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM parks;";
      return con.createQuery(sql)
        .executeAndFetch(Park.class);
    }
  }

  public static Park find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM parks WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Park.class);
    }
  }

  public void update(String name, String location, String size, boolean fenced, boolean small) {
    this.name = name;
    this.location = location;
    this.size = size;
    this.fenced = fenced;
    this.small = small;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE parks SET (name, location, size, fenced, small) = (:name, :location, :size, :fenced, :small) WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("location", this.location)
        .addParameter("size", this.size)
        .addParameter("fenced", this.fenced)
        .addParameter("small", this.small)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM parks WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

}
