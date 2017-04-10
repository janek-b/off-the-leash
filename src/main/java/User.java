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
    this.id = id;

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

  // @Override
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
}
