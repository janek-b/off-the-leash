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
      model.put("template", "templates/admin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/admin", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
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


    post("/parks/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/parks/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Park park = Park.find(Integer.parseInt(request.params(":id")));
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
      model.put("user", request.session().attribute("user"));
      response.redirect(request.headers("Referer"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/parks/:id/checkout", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Park park = Park.find(Integer.parseInt(request.params(":id")));
      model.put("user", request.session().attribute("user"));
      response.redirect(request.headers("Referer"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
