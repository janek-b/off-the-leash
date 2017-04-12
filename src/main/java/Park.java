import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.util.Map;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.maps.model.GeocodingResult;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import java.io.IOException;

public class Park implements BasicMethodsInterface {
  private int id;
  private String name;
  private String location;
  private String size;
  private boolean fenced;
  private boolean small;
  private int upVote;
  private int downVote;
  private double lat;
  private double lng;

  Gson gson = new Gson();

  public Park(String name, String location, String size, boolean fenced, boolean small) {
    this.name = name;
    this.location = location;
    this.size = size;
    this.fenced = fenced;
    this.small = small;
    this.upVote = 0;
    this.downVote = 0;
    GeoApiContext context = new GeoApiContext().setApiKey(System.getenv("MAPS_KEY"));
    try {
      GeocodingResult[] results =  GeocodingApi.geocode(context, location).await();
      this.lat = results[0].geometry.location.lat;
      this.lng = results[0].geometry.location.lng;
    } catch(ApiException exception) {
    } catch (InterruptedException exception) {
    } catch (IOException exception) {
    }
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

  public int getUpVotes() {
    return this.upVote;
  }

  public int getDownVotes() {
    return this.downVote;
  }

  public double getLat() {
    return this.lat;
  }

  public double getLng() {
    return this.lng;
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
             this.getUpVotes() == newPark.getUpVotes() &&
             this.getDownVotes() == newPark.getDownVotes() &&
             this.getLat() == newPark.getLat() &&
             this.getLng() == newPark.getLng() &&
             this.getId() == newPark.getId();
    }
  }

  @Override
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO parks (name, location, size, fenced, small, upVote, downVote, lat, lng) VALUES (:name, :location, :size, :fenced, :small, :upVote, :downVote, :lat, :lng);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("location", this.location)
        .addParameter("size", this.size)
        .addParameter("fenced", this.fenced)
        .addParameter("small", this.small)
        .addParameter("upVote", this.upVote)
        .addParameter("downVote", this.downVote)
        .addParameter("lat", this.lat)
        .addParameter("lng", this.lng)
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
    GeoApiContext context = new GeoApiContext().setApiKey(System.getenv("MAPS_KEY"));
    try {
      GeocodingResult[] results =  GeocodingApi.geocode(context, location).await();
      this.lat = results[0].geometry.location.lat;
      this.lng = results[0].geometry.location.lng;
    } catch(ApiException exception) {
    } catch (InterruptedException exception) {
    } catch (IOException exception) {
    }
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE parks SET (name, location, size, fenced, small, lat, lng) = (:name, :location, :size, :fenced, :small, :lat, :lng) WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("location", this.location)
        .addParameter("size", this.size)
        .addParameter("fenced", this.fenced)
        .addParameter("small", this.small)
        .addParameter("lat", this.lat)
        .addParameter("lng", this.lng)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM parks WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void upVote(User user) {
    String direction;
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT direction FROM votes WHERE userId = :userId AND parkId = :parkId;";
      direction = con.createQuery(sql)
        .addParameter("userId", user.getId())
        .addParameter("parkId", this.id)
        .executeAndFetchFirst(String.class);
    }
    if (direction == null) {
      this.upVote ++;
      try (Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO votes (userId, parkId, direction) VALUES (:userId, :parkId, 'up');";
        con.createQuery(sql)
          .addParameter("userId", user.getId())
          .addParameter("parkId", this.id)
          .executeUpdate();
      }
    } else if(direction.equals("up")) {
      this.upVote --;
      try (Connection con = DB.sql2o.open()) {
        String sql = "DELETE FROM votes WHERE (userId, parkId) = (:userId, :parkId);";
        con.createQuery(sql)
          .addParameter("userId", user.getId())
          .addParameter("parkId", this.id)
          .executeUpdate();
      }
    } else if(direction.equals("down")) {
      this.downVote --;
      this.upVote ++;
      try (Connection con = DB.sql2o.open()) {
        String sql = "UPDATE votes SET direction = 'up' WHERE (userId, parkId) = (:userId, :parkId);";
        con.createQuery(sql)
          .addParameter("userId", user.getId())
          .addParameter("parkId", this.id)
          .executeUpdate();
      }
    }
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE parks SET (upVote, downVote) = (:upVote, :downVote) WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("upVote", this.upVote)
      .addParameter("downVote", this.downVote)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void downVote(User user) {
    String direction;
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT direction FROM votes WHERE userId = :userId AND parkId = :parkId;";
      direction = con.createQuery(sql)
        .addParameter("userId", user.getId())
        .addParameter("parkId", this.id)
        .executeAndFetchFirst(String.class);
    }
    if (direction == null) {
      this.downVote ++;
      try (Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO votes (userId, parkId, direction) VALUES (:userId, :parkId, 'down');";
        con.createQuery(sql)
          .addParameter("userId", user.getId())
          .addParameter("parkId", this.id)
          .executeUpdate();
      }
    } else if(direction.equals("down")) {
      this.downVote --;
      try (Connection con = DB.sql2o.open()) {
        String sql = "DELETE FROM votes WHERE (userId, parkId) = (:userId, :parkId);";
        con.createQuery(sql)
          .addParameter("userId", user.getId())
          .addParameter("parkId", this.id)
          .executeUpdate();
      }
    } else if(direction.equals("up")) {
      this.upVote --;
      this.downVote ++;
      try (Connection con = DB.sql2o.open()) {
        String sql = "UPDATE votes SET direction = 'down' WHERE (userId, parkId) = (:userId, :parkId);";
        con.createQuery(sql)
          .addParameter("userId", user.getId())
          .addParameter("parkId", this.id)
          .executeUpdate();
      }
    }
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE parks SET (upVote, downVote) = (:upVote, :downVote) WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("upVote", this.upVote)
      .addParameter("downVote", this.downVote)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public List<Review> getReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE parkId = :id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Review.class);
    }
  }

  public Integer getCurrentVisitors() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT COUNT(users.id) FROM users JOIN checkins ON (users.id = checkins.userId) JOIN parks ON (checkins.parkId = parks.id) WHERE parks.id = :id AND checkins.checkout IS NULL;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeScalar(Integer.class);
    }
  }

  public Map<String, Object> getCoordinates() {
    Map<String, Object> coordinates = new HashMap<String, Object>();
    coordinates.put("lat", this.lat);
    coordinates.put("lng", this.lng);
    return coordinates;
  }

  public static List<Map<String, Object>> getAllCoordinates() {
    List<Map<String, Object>> allCoordinates = new ArrayList<Map<String, Object>>();
    List<Park> parks = Park.all();
    for (Park park: parks) {
      Map<String, Object> coordinates = new HashMap<String, Object>();
      coordinates.put("name", park.getName());
      coordinates.put("id", park.getId());
      coordinates.put("position", park.getCoordinates());
      allCoordinates.add(coordinates);
    }
    return allCoordinates;
  }

  public List<User> getUsersCheckedIn() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT users.* FROM users JOIN checkins ON (users.id = checkins.userId) JOIN parks ON (checkins.parkId = parks.id) WHERE parks.id = :id AND checkins.checkout IS NULL;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(User.class);
    }
  }

}
