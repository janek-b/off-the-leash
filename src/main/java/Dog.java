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
    return false;
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

}
