// package edu.brown.cs.student.main.server;
//
// import edu.brown.cs.student.main.server.RecipeData.Datasource.DatasourceException;
// import edu.brown.cs.student.main.server.RecipeData.Datasource.MockedRecipeSource;
// import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeDatasource;
// import edu.brown.cs.student.main.server.RecipeData.Datasource.RecipeUtilities;
// import edu.brown.cs.student.main.server.RecipeData.MealPlan;
// import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
// import edu.brown.cs.student.main.server.RecommenderAlgorithm.MealPlanGenerator;
// import edu.brown.cs.student.main.server.RecommenderAlgorithm.Mode;
// import edu.brown.cs.student.main.server.RecommenderAlgorithm.RecipeVolumeException;
// import org.junit.Before;
// import org.junit.Test;
// import org.testng.Assert;
//
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.concurrent.ExecutionException;
//
// public class FuzzTestMealPlanGenerator {
//
//  private MealPlanGenerator mealPlanGenerator;
//  private MealPlanGenerator mealPlanGenerator1;
//
//  private Recipe recipe1;
//  private Recipe recipe2;
//  private Recipe recipe3;
//  private Recipe recipe4;
//  private Recipe recipe5;
//  private List<Recipe> recipeList;
//
//  @Before
//  public void setUp() throws ExecutionException, InterruptedException, IOException {
//    // Mock dependencies
//    this.recipeList = new ArrayList<Recipe>();
//
//    String srecipe1 =
//
// "{\"image\":\"https://img.spoonacular.com/recipes/1086757-312x231.jpg\",\"numSteps\":8,\"numCuisines\":0,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"step\":\"Heat oven to 200C/180C fan/gas\"},{\"number\":2,\"step\":\"Heat 1 tsp of the oil in a pan, brown the chicken, then remove. Bring the stock to the boil in the same pan, add the chicken and cook for 5 mins. Turn off the heat, cover with a lid and leave to stand for 30 mins.\"},{\"number\":3,\"step\":\"Put the bread cubes on a baking tray.\"},{\"number\":4,\"step\":\"Drizzle with the remaining oil, some salt and the Parmesan.\"},{\"number\":5,\"step\":\"Bake for 6 mins until crunchy and golden.\"},{\"number\":6,\"step\":\"Remove and scrape off the tray onto a plate.\"},{\"number\":7,\"step\":\"Remove the chicken from the pan and slice it. Bring the stock to the boil again and add the greens, asparagus and peas. Cook for 1 min, then add the beans and chicken.\"},{\"number\":8,\"step\":\"Heat everything through, then pour into bowls and top with the Parmesan toast to serve.\"}]}],\"glutenFree\":false,\"vegan\":false,\"numIngredients\":9,\"title\":\"Spring vegetable broth with shredded chicken\",\"extendedIngredients\":[{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"Tbsp\"}},\"meta\":[],\"name\":\"olive oil\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"large\"}},\"meta\":[],\"name\":\"chicken breast\"},{\"measures\":{\"us\":{\"amount\":4.226,\"unitLong\":\"cups\"}},\"meta\":[],\"name\":\"chicken stock\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"slice\"}},\"meta\":[\"cut into cubes\"],\"name\":\"sourdough bread\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"Tbsps\"}},\"meta\":[\"grated\"],\"name\":\"parmesan\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"large handfuls\"}},\"meta\":[\"finely sliced\"],\"name\":\"spring greens\"},{\"measures\":{\"us\":{\"amount\":8.0,\"unitLong\":\"\"}},\"meta\":[\"trimmed\",\"halved\",\"cut into chunky pieces\"],\"name\":\"asparagus spears\"},{\"measures\":{\"us\":{\"amount\":2.822,\"unitLong\":\"ounces\"}},\"meta\":[\"fresh\"],\"name\":\"peas\"},{\"measures\":{\"us\":{\"amount\":28.22,\"unitLong\":\"ounces\"}},\"meta\":[\"drained and rinsed\",\"canned\"],\"name\":\"borlotti beans\"}],\"diets\":[],\"cuisines\":[],\"creditsText\":\"BBC Good Food\",\"readyInMinutes\":20,\"dairyFree\":false,\"servings\":4,\"vegetarian\":false,\"spoonacularScore\":82.53782653808594,\"id\":1086757}";
//
//    String srecipe2 =
//
// "{\"image\":\"https://img.spoonacular.com/recipes/476173-312x231.jpg\",\"numSteps\":5,\"numCuisines\":0,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"step\":\"Assemble each sandwiche by layering ¼ cup Gruyere cheese and ½ cup of Monterrey Jack on one slice of bread.\"},{\"number\":2,\"step\":\"Sprinkle with a little fresh thyme, about ¼ teaspoon per sandwich. Top with another slice of bread and gently press down on the sandwich with the palm of your hand.\"},{\"number\":3,\"step\":\"Heat a buttered skillet or griddle over medium-low heat.\"},{\"number\":4,\"step\":\"Place grilled cheese onto skillet or griddle and cook until lightly toasted, about 3 minutes per side.\"},{\"number\":5,\"step\":\"Remove from skillet or griddle and cut in half.\"}]}],\"glutenFree\":false,\"vegan\":false,\"numIngredients\":5,\"title\":\"Grilled Cheese with Thyme\",\"extendedIngredients\":[{\"measures\":{\"us\":{\"amount\":8.0,\"unitLong\":\"slices\"}},\"meta\":[],\"name\":\"bread\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"servings\"}},\"meta\":[],\"name\":\"butter\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"servings\"}},\"meta\":[\"fresh\"],\"name\":\"thyme\"},{\"measures\":{\"us\":{\"amount\":1.0,\"unitLong\":\"cups\"}},\"meta\":[\"grated\"],\"name\":\"gruyere cheese\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"cup\"}},\"meta\":[\"grated\"],\"name\":\"monterrey jack cheese\"}],\"diets\":[],\"cuisines\":[],\"creditsText\":\"Add A Pinch\",\"readyInMinutes\":11,\"dairyFree\":false,\"servings\":4,\"vegetarian\":false,\"spoonacularScore\":75.55024719238281,\"id\":476173}";
//
//    String srecipe3 =
//
// "{\"image\":\"https://img.spoonacular.com/recipes/563828-312x231.jpg\",\"numSteps\":6,\"numCuisines\":0,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"step\":\"Preheat the oven to 400 degrees.\"},{\"number\":2,\"step\":\"Cut the acorn squash in half. Scoop out all of the insides and make sure all seeds are removed.\"},{\"number\":3,\"step\":\"Drizzle lightly with olive oil and massage into squash flesh with fingers.\"},{\"number\":4,\"step\":\"Place the acorn squashes cut-side down on a baking tray. Roast in the oven for 30 minutes or until easily pierced with a fork. Once done, remove from oven and flip over so that the cut side is up to cool. Set the oven to broil.While the acorn squash is roasting, make the bikini bolognese.Five minutes before the acorn squash is done roasting, add the butternut squash noodles onto a baking tray coated with cooking spray. Roast for those last 5 minutes and then add it to the skillet with the bolognese. Stir to combine and spoon half of the mixture into 1 of the acorn squash halves. Set aside.For the other acorn squash, spoon out the flesh of the acorn squash and add it to the remaining bolognese mixture. Stir to combine thoroughly and add back into the acorn squash skin.\"},{\"number\":5,\"step\":\"Sprinkle each acorn squash halve evenly with gruyere cheese and place in the oven to broil for 5 minutes, checking periodically to make sure the cheese is bubbling but does not burn.\"},{\"number\":6,\"step\":\"Remove the acorn squash from the oven and enjoy!\"}]}],\"glutenFree\":true,\"vegan\":false,\"numIngredients\":7,\"title\":\"Butternut Squash Noodle Turkey Bolognese Stuffed Acorn Squash with Melted Gruyere: Two Ways\",\"extendedIngredients\":[{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"\"}},\"meta\":[],\"name\":\"acorn squash\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"cup\"}},\"meta\":[],\"name\":\"butternut squash noodles\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"cup\"}},\"meta\":[\"shaved\"],\"name\":\"gruyere cheese\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"servings\"}},\"meta\":[],\"name\":\"olive oil to drizzle\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"servings\"}},\"meta\":[\"to taste\"],\"name\":\"salt and pepper\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"serving\"}},\"meta\":[],\"name\":\"my bikini bolognese\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"serving\"}},\"meta\":[],\"name\":\"my bikini bolognese\"}],\"diets\":[\"gluten free\",\"primal\"],\"cuisines\":[],\"creditsText\":\"Inspiralized\",\"readyInMinutes\":55,\"dairyFree\":false,\"servings\":4,\"vegetarian\":false,\"spoonacularScore\":93.32958221435547,\"id\":563828}";
//
//    String srecipe4 =
//
// "{\"image\":\"https://img.spoonacular.com/recipes/768013-312x231.jpg\",\"numSteps\":10,\"numCuisines\":3,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"step\":\"Preheat oven to 350F and grease a glass or ceramic pie plate well.In a large skillet over medium-high heat, cook bacon until crisp.\"},{\"number\":2,\"step\":\"Remove with a slotted spoon and let drain on a paper-towel lined plate.\"},{\"number\":3,\"step\":\"Drain all but 1 tbsp of bacon fat from skillet.\"},{\"number\":4,\"step\":\"Cut off root end of endive and slice into 1/2 inch slices.\"},{\"number\":5,\"step\":\"Add endive to skillet over medium-high heat and cook until wilted and soft, about 6 to 7 minutes.\"},{\"number\":6,\"step\":\"Layer endive, bacon and shredded Gruyere in pie plate. In a large bowl, whisk together eggs, cream, garlic, salt and pepper until well-combined.\"},{\"number\":7,\"step\":\"Pour filling over endive, bacon and cheese in pie plate.\"},{\"number\":8,\"step\":\"Bake 30 minutes, or until top is puffy and golden brown and quiche is set and no longer jiggles when shaken.\"},{\"number\":9,\"step\":\"Serves\"},{\"number\":10,\"step\":\"Each serving has a total of 12.8 g of carbs and 10 g of fiber. Total NET CARBS = 2.8 g.\"}]}],\"glutenFree\":true,\"vegan\":false,\"numIngredients\":8,\"title\":\"Bacon, Gruyere and Endive Quiche (Low Carb and Gluten-Free)\",\"extendedIngredients\":[{\"measures\":{\"us\":{\"amount\":5.0,\"unitLong\":\"larges\"}},\"meta\":[],\"name\":\"eggs\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"heads\"}},\"meta\":[],\"name\":\"california endive\"},{\"measures\":{\"us\":{\"amount\":1.0,\"unitLong\":\"clove\"}},\"meta\":[\"pressed\"],\"name\":\"garlic\"},{\"measures\":{\"us\":{\"amount\":1.0,\"unitLong\":\"cup\"}},\"meta\":[\"grated\"],\"name\":\"gruyere cheese\"},{\"measures\":{\"us\":{\"amount\":1.0,\"unitLong\":\"cup\"}},\"meta\":[],\"name\":\"heavy cream\"},{\"measures\":{\"us\":{\"amount\":0.25,\"unitLong\":\"teaspoons\"}},\"meta\":[],\"name\":\"pepper\"},{\"measures\":{\"us\":{\"amount\":0.5,\"unitLong\":\"teaspoons\"}},\"meta\":[],\"name\":\"salt\"},{\"measures\":{\"us\":{\"amount\":8.0,\"unitLong\":\"slices\"}},\"meta\":[\"thick-cut\",\"chopped\"],\"name\":\"bacon\"}],\"diets\":[\"gluten free\",\"primal\",\"ketogenic\"],\"cuisines\":[\"Mediterranean\",\"French\",\"European\"],\"creditsText\":\"All Day I Dream About Food\",\"readyInMinutes\":45,\"dairyFree\":false,\"servings\":6,\"vegetarian\":false,\"spoonacularScore\":72.70922088623047,\"id\":768013}";
//
//    String srecipe5 =
//
// "{\"image\":\"https://img.spoonacular.com/recipes/775657-312x231.jpg\",\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"step\":\"Preheat grill over high heat.\"},{\"number\":2,\"step\":\"Cut 4 sheets of foil about 12 inches long. Divide shrimp, garlic, sausage, corn, and potatoes evenly over the foil sheets.\"},{\"number\":3,\"step\":\"Drizzle with olive oil.\"},{\"number\":4,\"step\":\"Add the Old Bay seasoning and season to taste with salt and pepper. Toss gently to combine. Top each mixture with parsley, lemon and a tablespoon of butter each.Fold the foil packets crosswise over the shrimp boil mixture to completely cover the food.\"},{\"number\":5,\"step\":\"Roll the top and bottom edges to seal them closed.\"},{\"number\":6,\"step\":\"Place foil packets on the grill and cook until just cooked through, about 10-15 minutes.\"},{\"number\":7,\"step\":\"Serve immediately.\"}]}],\"glutenFree\":true,\"vegan\":false,\"title\":\"Grilled Shrimp Foil Packets\",\"extendedIngredients\":[{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"\"}},\"meta\":[\"smoked\",\"thinly sliced\"],\"name\":\"andouille sausages\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"servings\"}},\"meta\":[\"black\",\"freshly ground\"],\"name\":\"pepper\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"Tbsps\"}},\"meta\":[],\"name\":\"butter\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"\"}},\"meta\":[],\"name\":\"ears corn\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"Tbsps\"}},\"meta\":[\"fresh\",\"chopped\"],\"name\":\"parsley leaves\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"cloves\"}},\"meta\":[\"minced\"],\"name\":\"garlic\"},{\"measures\":{\"us\":{\"amount\":4.0,\"unitLong\":\"servings\"}},\"meta\":[],\"name\":\"kosher salt\"},{\"measures\":{\"us\":{\"amount\":1.0,\"unitLong\":\"\"}},\"meta\":[\"sliced into thin wedges\"],\"name\":\"lemon\"},{\"measures\":{\"us\":{\"amount\":1.0,\"unitLong\":\"Tbsp\"}},\"meta\":[],\"name\":\"old bay seasoning\"},{\"measures\":{\"us\":{\"amount\":2.0,\"unitLong\":\"Tbsps\"}},\"meta\":[\"extra-virgin\"],\"name\":\"olive oil\"},{\"measures\":{\"us\":{\"amount\":1.0,\"unitLong\":\"pound\"}},\"meta\":[\"red\",\"chopped\"],\"name\":\"bliss potatoes\"},{\"measures\":{\"us\":{\"amount\":1.5,\"unitLong\":\"pounds\"}},\"meta\":[\"deveined\",\"peeled\"],\"name\":\"shrimp\"}],\"diets\":[\"gluten free\"],\"cuisines\":[],\"creditsText\":\"Delish\",\"readyInMinutes\":25,\"dairyFree\":false,\"servings\":4,\"vegetarian\":false,\"spoonacularScore\":82.22171020507812,\"id\":775657}";
//
//    this.recipe1 = RecipeUtilities.deserializeRecipe(srecipe1);
//    this.recipe2 = RecipeUtilities.deserializeRecipe(srecipe2);
//    this.recipe3 = RecipeUtilities.deserializeRecipe(srecipe3);
//    this.recipe4 = RecipeUtilities.deserializeRecipe(srecipe4);
//    this.recipe5 = RecipeUtilities.deserializeRecipe(srecipe5);
//
//    recipeList.add(this.recipe1);
//    recipeList.add(this.recipe2);
//    recipeList.add(this.recipe3);
//    recipeList.add(this.recipe4);
//    recipeList.add(this.recipe5);
//    RecipeDatasource mockedDatasource = new MockedRecipeSource(recipeList);
//    Mode mode = Mode.MINIMIZE_FOOD_WASTE;
//    String daysOfWeek = "monday,tuesday,wednesday";
//    int servings = 2;
//    String cuisine = "Italian";
//    String excludeCuisine = null;
//    String diet = "Vegetarian";
//    String intolerances = null;
//    int maxReadyTime = 30;
//    String uid = "test_uid";
//
//    // Initialize MealPlanGenerator
//    this.mealPlanGenerator =
//        new MealPlanGenerator(
//            mockedDatasource,
//            mode,
//            daysOfWeek,
//            servings,
//            cuisine,
//            excludeCuisine,
//            diet,
//            intolerances,
//            maxReadyTime,
//            null,
//            uid,
//            null);
//
//    RecipeDatasource mockedDatasource1 = new MockedRecipeSource(recipeList);
//    Mode mode1 = Mode.MINIMIZE_FOOD_WASTE;
//    String daysOfWeek1 = "saturday,tuesday";
//    int servings1 = 2;
//    String cuisine1 = "Indian";
//    String excludeCuisine1 = null;
//    String diet1 = "Vegan";
//    String intolerances1 = null;
//    int maxReadyTime1 = 60;
//    String uid1 = "test_uid_faizah_1";
//    // Initialize MealPlanGenerator
//    this.mealPlanGenerator1 =
//            new MealPlanGenerator(
//                    mockedDatasource1,
//                    mode1,
//                    daysOfWeek1,
//                    servings1,
//                    cuisine1,
//                    excludeCuisine1,
//                    diet1,
//                    intolerances1,
//                    maxReadyTime1,
//                    null,
//                    uid1,
//                    null);
//  }
//
//  @Test
//  public void testSimpleGeneratePlan() throws DatasourceException, RecipeVolumeException {
//    // Test generatePlan method
//    MealPlan mealPlan = this.mealPlanGenerator.generatePlan();
//    Assert.assertNotNull(mealPlan.getTuesday());
//    Assert.assertNotNull(mealPlan.getMonday());
//    Assert.assertNotNull(mealPlan.getWednesday());
//    Assert.assertNull(mealPlan.getThursday());
//    Assert.assertNull(mealPlan.getSunday());
//    Assert.assertNull(mealPlan.getFriday());
//    Assert.assertNull(mealPlan.getSaturday());
//  }
// }
