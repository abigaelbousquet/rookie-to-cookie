package edu.brown.cs.student.main.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.brown.cs.student.main.server.EndpointHandlers.*;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import edu.brown.cs.student.main.server.storage.MockedFirebase;
import edu.brown.cs.student.main.server.storage.StorageInterface;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;
import spark.Spark;

public class IntegrationTests {

  private Recipe recipe1;
  private Recipe recipe2;
  private Recipe recipe3;
  private Recipe recipe4;
  private Recipe recipe5;
  private List<Recipe> recipeList;

  @BeforeClass
  public static void setup_before_everything() {
    // Set the Spark port number.
    Spark.port(0);

    // Remove the logging spam during tests
    Logger.getLogger("").setLevel(Level.WARNING); // empty name = root logger
  }

  @BeforeEach
  public void setup() throws IOException, ExecutionException, InterruptedException {
    // Re-initialize state, etc. for _every_ test method run

    StorageInterface mock = new MockedFirebase();

    Spark.get("add-liked-recipe", new AddLikedRecipeHandler(mock));
    Spark.get("add-disliked-recipe", new AddDislikedRecipeHandler(mock));
    Spark.get("add-user", new AddUserHandler(mock));
    Spark.get("clear-user", new ClearUserHandler(mock));
    Spark.get("save-mealplan", new SaveMealPlanHandler(mock));
    Spark.get("clear-liked-recipes", new ClearLikedRecipesHandler(mock));
    Spark.get("clear-disliked-recipes", new ClearDislikedRecipesHandler(mock));

    Spark.init();
    Spark.awaitInitialization(); // don't continue until the server is listening
  }

  @AfterEach
  public void teardown() {
    // Gracefully stop Spark listening on both endpoints after each test
    Spark.unmap("add-liked-recipe");
    Spark.unmap("add-disliked-recipe");
    Spark.unmap("add-user");
    Spark.unmap("clear-user");
    Spark.unmap("save-mealplan");
    Spark.unmap("clear-liked-recipes");
    Spark.unmap("clear-disliked-recipes");

    Spark.awaitStop(); // don't proceed until the server is stopped
  }

  /**
   * Helper to start a connection to a specific API endpoint/params
   *
   * @param apiCall the call string, including endpoint
   * @return the connection for the given URL, just after connecting
   * @throws IOException if the connection fails for some reason
   */
  private static HttpURLConnection tryRequest(String apiCall) throws IOException {
    // Configure the connection (but don't actually send the request yet)
    URL requestURL = new URL("http://localhost:" + Spark.port() + "/" + apiCall);
    HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();

    // The default method is "GET", which is what we're using here.
    clientConnection.setRequestMethod("GET");

    clientConnection.connect();
    return clientConnection;
  }

  /**
   * Tests bad request exception through if a state doesn't exist
   *
   * @throws IOException
   */
  @Test
  public void testFullPipeline() throws IOException {
    HttpURLConnection clientConnection =
        tryRequest(
            "generate-mealplan?uid=test&diet=Pescetarian&intolerances=Peanut&daysOfWeek=wednesday&cuisine=&servings=1&max_time=20&mode=personalize");
    // Get an OK response (the *connection* worked, the *API* provides an error response)
    assertEquals(404, clientConnection.getResponseCode());

    clientConnection.disconnect();

    HttpURLConnection clientConnection1 =
        tryRequest("save-mealplan?uid=2YtRmY2yYWO1OKarSPlFqtzdi6T2&dateOfMonday=06%2F10%2F2024");
    // Get an OK response (the *connection* worked, the *API* provides an error response)
    assertEquals(404, clientConnection.getResponseCode());

    clientConnection.disconnect();
  }
}
