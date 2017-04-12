import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

import com.google.gson.Gson;

public class App {
  public static void main(String[] args) {
    // Use this setting only for development
    externalStaticFileLocation(String.format("%s/src/main/resources/public", System.getProperty("user.dir")));

    // staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    Gson gson = new Gson();

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("MAPS_KEY", System.getenv("MAPS_KEY"));
      model.put("coordinates", gson.toJson(Park.getAllCoordinates()));
      model.put("user", request.session().attribute("user"));
      model.put("parks", Park.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/admin", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", request.session().attribute("user"));
      model.put("users", User.all());
      model.put("parks", Park.all());
      model.put("template", "templates/admin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/admin", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.queryParams("userSelected")));
      user.makeAdmin();
      response.redirect("/admin");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/login", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String username = request.queryParams("username");
      String password = request.queryParams("password");
      User user;
      try {
        user = User.findByUserName(username);
      } catch(IllegalArgumentException exception) {
        user = new User(username, password);
        user.save();
      }
      if (user.getPassword().equals(password)) {
        request.session().attribute("user", user);
        request.session().removeAttribute("wrongPass");
      } else {
        request.session().attribute("wrongPass", "wrongPass");
      }
      response.redirect(request.headers("Referer"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/logout", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      request.session().removeAttribute("user");
      response.redirect(request.headers("Referer"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/parks", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("MAPS_KEY", System.getenv("MAPS_KEY"));
      model.put("coordinates", gson.toJson(Park.getAllCoordinates()));
      model.put("user", request.session().attribute("user"));
      model.put("parks", Park.all());
      model.put("template", "templates/parks.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/parks/sort", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("MAPS_KEY", System.getenv("MAPS_KEY"));
      model.put("coordinates", gson.toJson(Park.getAllCoordinates()));
      model.put("user", request.session().attribute("user"));
      String sort = request.queryParams("sort");
      if (sort.equals("rating")) {
        // model.put("parks", Park.allByRating());
      } else if(sort.equals("alpha")) {
        model.put("parks", Park.all());
      }
      model.put("template", "templates/parks.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/parks/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String location = request.queryParams("location");
      String sizeSelected = request.queryParams("sizeSelected");
      boolean fenced = request.queryParams("fenceSelected").equals("true");
      boolean small = request.queryParams("smallSelected").equals("true");
      Park newPark = new Park(name, location, sizeSelected, fenced, small);
      newPark.save();
      response.redirect("/admin");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/parks/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Park park = Park.find(Integer.parseInt(request.params(":id")));
      model.put("MAPS_KEY", System.getenv("MAPS_KEY"));
      model.put("coordinates", gson.toJson(Park.getAllCoordinates()));
      model.put("park", park);
      model.put("user", request.session().attribute("user"));
      model.put("template", "templates/park.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/parks/:id/upvote", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Park park = Park.find(Integer.parseInt(request.params(":id")));
      User user = request.session().attribute("user");
      park.upVote(user);
      response.redirect(request.headers("Referer"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/parks/:id/downvote", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Park park = Park.find(Integer.parseInt(request.params(":id")));
      User user = request.session().attribute("user");
      park.downVote(user);
      response.redirect(request.headers("Referer"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/parks/:id/checkin", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Park park = Park.find(Integer.parseInt(request.params(":id")));
      User user = request.session().attribute("user");
      user.checkIn(park);
      model.put("user", request.session().attribute("user"));
      response.redirect(request.headers("Referer"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/parks/:id/checkout", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Park park = Park.find(Integer.parseInt(request.params(":id")));
      User user = request.session().attribute("user");
      user.checkOut();
      model.put("user", request.session().attribute("user"));
      response.redirect(request.headers("Referer"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = request.session().attribute("user");
      User profile = User.find(Integer.parseInt(request.params(":id")));
      if (profile.getCheckedIn() != null) {
        model.put("coordinates", gson.toJson(profile.getCheckedIn().getCoordinates()));
      }
      model.put("MAPS_KEY", System.getenv("MAPS_KEY"));
      model.put("user", user);
      model.put("profile", profile);
      if (profile.equals(user)) {
        model.put("template", "templates/user.vtl");
      } else {
        model.put("template", "templates/profile.vtl");
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
