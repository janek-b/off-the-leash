import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Dog {
  private int id;
  private int userId;
  private String name;
  private String gender;
  private boolean altered;
  private String breed;


  public Dog(String name, String gender, String breed, boolean altered, int userId) {
    this.name = name;
    this.gender = gender;
    this.breed = breed;
    this.altered = altered;
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public String getGender() {
    return gender;
  }

  public String getBreed() {
    return breed;
  }

  public boolean altered(){
    return altered;
  }

  public int getUserId() {
    return userId;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherDog){
    if (!(otherDog instanceof Dog)) {
      return false;
    } else {
      Dog newDog = (Dog) otherDog;
      return this.getName().equals(newDog.getName()) &&
      this.getGender().equals(newDog.getGender()) &&
      this.getBreed().equals(newDog.getBreed()) &&
      // this.altered().equals(newDog.altered()) &&
      this.getUserId() == (newDog.getUserId());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO dogs (userId, name, gender, breed, altered) VALUES (:userId, :name, :gender, :breed, :altered);";
      this.id = (int) con.createQuery(sql, true)
          .addParameter("userId", this.userId)
          .addParameter("name", this.name)
          .addParameter("gender", this.gender)
          .addParameter("breed", this.breed)
          .addParameter("altered", this.altered)
          .executeUpdate()
          .getKey();
    }
  }

  public static List<Dog> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM dogs;";
      return con.createQuery(sql).executeAndFetch(Dog.class);
    }
  }

  public static Dog find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM dogs WHERE id = :id;";
      Dog dog = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Dog.class);
      return dog;
    }
  }

  public void update(String name, String gender,  String breed, boolean altered, int userId){
    this.userId = userId;
    this.name = name;
    this.gender = gender;
    this.breed = breed;
    this.altered = altered;

    try(Connection con = DB.sql2o.open()) {
      String update = "UPDATE dogs SET (name, gender, breed, altered, userId) = (:name, :gender, :breed, :altered, :userId) WHERE id = :id;";
      con.createQuery(update)
         .addParameter("name", name)
         .addParameter("gender", gender)
         .addParameter("breed", breed)
         .addParameter("altered", altered)
         .addParameter("userId", userId)
         .addParameter("id", this.id)
         .executeUpdate();
      // String name = "UPDATE dogs SET name = :name WHERE id = :id;";
      // String gender ="UPDATE dogs SET gender = :gender WHERE id = :id;";
      // String breed = "UPDATE dogs SET breed = :breed WHERE id = :id;";
      // boolean altered ="UPDATE dogs SET altered = :altered WHERE id = :id;";
      // int userId = "UPDATE dogs SET userId = :userId WHERE id = :id";
      // con.createQuery(name)
      //   .addParameter("name", name)
      //   .addParameter("id", id)
      //   .executeUpdate();
      // con.createQuery(gender)
      //   .addParameter("gender", gender)
      //   .addParameter("id", id)
      //   .executeUpdate();
      // con.createQuery(breed)
      //   .addParameter("breed", breed)
      //   .addParameter("id", id)
      //   .executeUpdate();
      // con.createQuery(altered)
      //   .addParameter("altered", altered)
      //   .addParameter("id", id)
      //   .executeUpdate();
      // con.createQuery(userId)
      //   .addParameter("userId", userId)
      //   .addParameter("id", id)
      //   .executeUpdate();
    }
  }

}
