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

public class DogTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void dog_instantiatesCorrectly_true() {
    Dog newDog = new Dog("Rufus", "Male", "greyhound", false, 1);
    assertEquals(true, newDog instanceof Dog);
  }

  @Test
  public void dog_testDogGettersCorrectly() {
    Dog newDog = new Dog("Rufus", "Male", "greyhound", false, 1);
    assertEquals("Rufus", newDog.getName());
    assertEquals("Male", newDog.getGender());
    assertEquals("greyhound", newDog.getBreed());
    assertEquals(false, newDog.altered());
    assertEquals(1, newDog.getUserId());
  }

  @Test
  public void equals_returnsTrueIfUserNameIsSame() {
    Dog newDog = new Dog("Rufus", "Male", "greyhound", false, 1);
    Dog dog = new Dog("Rufus", "Male", "greyhound", false, 1);
    assertTrue(newDog.equals(dog));
  }

  @Test
  public void save_addIdAndSaveIntoDatabase() {
    Dog dog = new Dog("Rufus", "Male", "greyhound", false, 1);
    dog.save();
    Dog savedDog = Dog.all().get(0);
    assertEquals(savedDog.getId(), dog.getId());
  }

  @Test
  public void find_returnsObjectWithSameId_int() {
    Dog dog = new Dog("Rufus", "Male", "greyhound", false, 1);
    dog.save();
    Dog newDog = new Dog("Rufus", "Male", "greyhound", false, 1);
    newDog.save();
    assertEquals(dog, Dog.find(dog.getId()));
  }

  @Test
  public void update_updateObject_true() {
    Dog dog = new Dog("Rufus", "Male", "greyhound", false, 1);
    dog.save();
    dog.update("Randy", "Female", "dalmation", true, 2);
    assertEquals("Randy", dog.getName());
    assertEquals("Female", dog.getGender());
    assertEquals("dalmation", dog.getBreed());
    assertEquals(true, dog.altered());
    assertEquals(2, dog.getUserId());
  }


}
