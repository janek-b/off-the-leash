import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

import com.google.maps.model.GeocodingResult;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.gson.Gson;

public class App {
  public static void main(String[] args) {
    // Use this setting only for development
    externalStaticFileLocation(String.format("%s/src/main/resources/public", System.getProperty("user.dir")));

    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    // Gson gson = new Gson();

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      // GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAh2ZaHDDFfIKy1wLOi6bKxJ3X4Na3wtXw");
      // GeocodingResult[] results =  GeocodingApi.geocode(context, "Hillsboro, OR").await();
      // Map<String, Object> coordinates = new HashMap<String, Object>();
      // System.out.println(results[0].geometry.location.lat);
      // System.out.println(results[0].geometry.location.lng);
      // coordinates.put("lat", results[0].geometry.location.lat);
      // coordinates.put("lng", results[0].geometry.location.lng);
      // model.put("coordinates", gson.toJson(coordinates));
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
