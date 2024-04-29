package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
import edu.brown.cs.student.main.server.RecipeData.MealPlan;
import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TestDeserialize {

  /**
   * Tests basic mealPlan deserialization
   * @throws IOException
   */
  @Test
  public void testRecipeDeserialize() throws IOException {
    String grilledShrimp = "{\"image\":\"https://img.spoonacular.com/recipes/775657-312x231.jpg\",\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"step\":\"Preheat grill over high heat.\"},{\"number\":2,\"step\":\"Cut 4 sheets of foil about 12 inches long. Divide shrimp, garlic, sausage, corn, and potatoes evenly over the foil sheets.\"},{\"number\":3,\"step\":\"Drizzle with olive oil.\"},{\"number\":4,\"step\":\"Add the Old Bay seasoning and season to taste with salt and pepper. Toss gently to combine. Top each mixture with parsley, lemon and a tablespoon of butter each.Fold the foil packets crosswise over the shrimp boil mixture to completely cover the food.\"},{\"number\":5,\"step\":\"Roll the top and bottom edges to seal them closed.\"},{\"number\":6,\"step\":\"Place foil packets on the grill and cook until just cooked through, about 10-15 minutes.\"},{\"number\":7,\"step\":\"Serve immediately.\"}]}],\"glutenFree\":true,\"vegan\":false,\"title\":\"Grilled Shrimp Foil Packets\",\"extendedIngredients\":[{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"\"}},\"meta\":[\"smoked\",\"thinly sliced\"],\"name\":\"andouille sausages\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"servings\"}},\"meta\":[\"black\",\"freshly ground\"],\"name\":\"pepper\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"Tbsps\"}},\"meta\":[],\"name\":\"butter\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"\"}},\"meta\":[],\"name\":\"ears corn\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"Tbsps\"}},\"meta\":[\"fresh\",\"chopped\"],\"name\":\"parsley leaves\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"cloves\"}},\"meta\":[\"minced\"],\"name\":\"garlic\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"servings\"}},\"meta\":[],\"name\":\"kosher salt\"},{\"measures\":{\"us\":{\"amount\":1.0,\"unitLong\":\"\"}},\"meta\":[\"sliced into thin wedges\"],\"name\":\"lemon\"},{\"measures\":{\"us\":{\"amount\":1.0,\"unitLong\":\"Tbsp\"}},\"meta\":[],\"name\":\"old bay seasoning\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"Tbsps\"}},\"meta\":[\"extra-virgin\"],\"name\":\"olive oil\"},{\"measures\":{\"us\":{\"amount\":1.0,\"unitLong\":\"pound\"}},\"meta\":[\"red\",\"chopped\"],\"name\":\"bliss potatoes\"},{\"measures\":{\"us\":{\"amount\":1.5,\"unitLong\":\"pounds\"}},\"meta\":[\"deveined\",\"peeled\"],\"name\":\"shrimp\"}],\"diets\":[\"gluten free\"],\"cuisines\":[],\"creditsText\":\"Delish\",\"readyInMinutes\":25,\"dairyFree\":false,\"servings\":4,\"vegetarian\":false,\"spoonacularScore\":82.22171020507812,\"id\":775657}";
    Recipe recipeShrimp = RecipeUtilities.deserializeRecipe(grilledShrimp);
    // This might throw an IOException, but if so JUnit will mark the test as failed.
    Assert.assertEquals("Grilled Shrimp Foil Packets", recipeShrimp.getTitle());
    Assert.assertEquals(775657, recipeShrimp.getId());
    Assert.assertEquals(4, recipeShrimp.getServings());
    Assert.assertEquals(false, recipeShrimp.isVegan());
    Assert.assertEquals(82.22171020507812, recipeShrimp.getSpoonacularScore());
    Assert.assertEquals(25, recipeShrimp.getReadyInMinutes());
  }

  /**
   * Tests meal plan deserialization on null fields
   * @throws IOException
   */
    @Test
    public void testMealplanDeserialize() throws IOException {
        String mealJson = "{\"mealplan-0\": {\"recipes\":[]}, \"sunday\":null,\"monday\":null,\"tuesday\":null,\"wednesday\":null,\"thursday\":null,\"friday\":null,\"saturday\":null}";
        MealPlan plan = RecipeUtilities.deserializePlan("mealplan-0", mealJson);
        // This might throw an IOException, but if so JUnit will mark the test as failed.
      Assert.assertTrue(plan.getRecipes().isEmpty());

    }
}
