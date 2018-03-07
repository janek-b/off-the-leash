import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/leash_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteReviewQuery = "DELETE FROM reviews *;";
      String deleteDogQuery = "DELETE FROM dogs *;";
      String deleteUserQuery = "DELETE FROM users *;";
      String deleteParkQuery = "DELETE FROM parks *;";
      String deleteCheckinsQuery = "DELETE FROM checkins *;";
      String deleteVotesQuery = "DELETE FROM Votes *;";
      con.createQuery(deleteVotesQuery).executeUpdate();
      con.createQuery(deleteCheckinsQuery).executeUpdate();
      con.createQuery(deleteReviewQuery).executeUpdate();
      con.createQuery(deleteDogQuery).executeUpdate();
      con.createQuery(deleteUserQuery).executeUpdate();
      con.createQuery(deleteParkQuery).executeUpdate();
    }
  }
}
