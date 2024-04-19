package edu.brown.cs.student.main.server.Endpoints;

import edu.brown.cs.student.main.server.RecipeParsing.Recipe.Recipe;
import edu.brown.cs.student.main.server.RecipeParsing.RecipeUtilities;
import edu.brown.cs.student.main.server.Server;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/** Class to serve as a proxy server between the user and the ACS API */
public class SpoonacularHandler implements Route {

    private String apiKey; //will probably need to add this to queries when we recieve access

    /**
     * This handle method requests state and county params from the user and uses those params to send
     * and recieve requests from the ACS API
     *
     * @param request  The request object providing information about the HTTP request
     * @param response The response object providing functionality for modifying the response
     */
    @Override
    public Object handle(Request request, Response response)
            throws IOException, URISyntaxException, InterruptedException {

        // Creates a hashmap to store the results of the request

        Map<String, Object> responseMap = new HashMap<>();
        String cuisine = request.queryParams("cuisine");

        try {
            // Sends a request to the API and receives JSON back
//            String recipeJson = this.sendRequest(cuisine);

            String recipePath = "C:\\Users\\Faizah\\Documents\\Brown Sophomore Year\\CS0320\\term-project-mshaffe3-ddedona-ffnaqvi-abousque\\server\\data\\exampleRecipe.json";

            String recipeJson = new String(Files.readAllBytes(Paths.get(recipePath)));

            // Deserializes JSON into a recipe
            Recipe returnedRecipe = RecipeUtilities.deserializeRecipe(recipeJson);
            Server.currRecipe = returnedRecipe;

            // Adds results to the responseMap
            responseMap.put("result", "success");

            responseMap.put("Recipe Result", returnedRecipe);
            return responseMap;
        } catch (Exception e) {
            response.status(404);

            responseMap.put("result", "error_datasource");
        }
        return responseMap;
    }


    /**
     * Method to send a requests to the Spoonacular API
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    //  @Override
    public String sendRequest(String cuisine)
            throws URISyntaxException, IOException, InterruptedException {

        HttpRequest buildSpoonacularApiRequest =
                HttpRequest.newBuilder()
                        .uri(
                                new URI(
                                        "https://api.spoonacular.com/recipes/complexSearch?cuisine="
                                                + cuisine))
                        .GET()
                        .build();

        // Send that API request then store the response in this variable. Note the generic type.
        HttpResponse<String> sentCensusApiResponse =
                HttpClient.newBuilder()
                        .build()
                        .send(buildSpoonacularApiRequest, HttpResponse.BodyHandlers.ofString());

        return sentCensusApiResponse.body();
    }
}