import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/off_the_leash_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteReviewQuery = "DELETE FROM reviews *;";
      con.createQuery(deleteReviewQuery).executeUpdate();
      String deleteDogQuery = "DELETE FROM dogs *;";
      con.createQuery(deleteDogQuery).executeUpdate();
      String deleteUserQuery = "DELETE FROM users *;";
      con.createQuery(deleteUserQuery).executeUpdate();
      String deleteParkQuery = "DELETE FROM parks *;";
      con.createQuery(deleteParkQuery).executeUpdate();
    }
  }
}
