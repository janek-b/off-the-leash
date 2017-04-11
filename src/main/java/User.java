import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class User implements BasicMethodsInterface {
  private int id;
  private String name;

  public User( String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherUser){
    if (!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getName().equals(newUser.getName());
    }
  }

  @Override
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public static List<User> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users;";
      return con.createQuery(sql).executeAndFetch(User.class);
    }
  }

  public static User find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE id = :id;";
      User user  = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(User.class);
      return user;
    }
  }

  public void update(String name) {
    this.name = name;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET name = :name WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  @Override
  public void delete() {
    List<Dog> dogList = this.getAllDogs();
    for (Dog eachDog : dogList) {
      eachDog.delete();
    }

    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM users WHERE id = :id;";
      con.createQuery(sql)
         .addParameter("id", id)
         .executeUpdate();
    }
  }

  public Park getCheckedIn() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT parks.* FROM parks JOIN checkins ON (parks.id = checkins.parkId) JOIN users ON (checkins.userId = users.id) WHERE users.id = :id AND checkins.checkout IS NULL;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetchFirst(Park.class);
    }
  }

  public void checkIn(Park park) {
    if (this.getCheckedIn() == null) {
      try (Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO checkins (userId, parkId, checkin) VALUES (:userId, :parkId, now());";
        con.createQuery(sql)
        .addParameter("userId", this.id)
        .addParameter("parkId", park.getId())
        .executeUpdate();
      }
    }
  }

  public void checkOut() {
    Park park = this.getCheckedIn();
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE checkins SET checkout = now() WHERE (userId, parkId) = (:userId, :parkId);";
      con.createQuery(sql)
        .addParameter("userId", this.id)
        .addParameter("parkId", park.getId())
        .executeUpdate();
    }
  }

  public List<Review> getReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE userId = :id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Review.class);
    }
  }

  public List<Park> getFavoriteParks() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT parks.* FROM parks JOIN votes ON (parks.id = votes.parkId) WHERE votes.userId = :id AND votes.direction = 'up';";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Park.class);
    }
  }

  public List<Park> getLeastFavoriteParks() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT parks.* FROM parks JOIN votes ON (parks.id = votes.parkId) WHERE votes.userId = :id AND votes.direction = 'down';";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Park.class);
    }
  }

  public List<Dog> getAllDogs() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM dogs WHERE userId = :userId;";
      return con.createQuery(sql)
      .addParameter("userId", this.id)
      .executeAndFetch(Dog.class);
    }
  }
}
