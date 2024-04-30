import { parseRecipe } from "../components/RecipeCard/ParseRecipe";

describe("parseRecipe function", () => {
  it("should parse recipe data correctly", () => {
    // Sample recipe data
    const recipeData = {
      id: 716245,
      creditsText: "Afrolems",
      title: "Avocado Egg Salad",
      image: "https://img.spoonacular.com/recipes/716245-312x231.jpg",
      servings: 1,
      readyInMinutes: 45,
      spoonacularScore: 19.2667179107666,
      cuisines: [],
      diets: ["dairy free", "lacto ovo vegetarian"],
      dairyFree: true,
      glutenFree: false,
      vegan: false,
      vegetarian: true,
      extendedIngredients: [
        {
          measures: {
            us: {
              amount: 0.25,
              unitLong: "teaspoons",
            },
          },
          meta: ["black"],
          name: "pepper",
        },
        // Additional ingredient data omitted for brevity
      ],
      analyzedInstructions: [
        {
          name: "",
          steps: [
            {
              number: 1,
              equipment: [
                {
                  name: "bowl",
                },
              ],
              ingredients: [
                {
                  name: "avocado",
                },
                // Additional ingredient data omitted for brevity
              ],
              step: "Boil your eggs and immerse in water to cool.Peel your avocado and mash in a bowl.Squirt your lemon over the avocado.Peel the eggs, chop and mix with the avocado.",
            },
            // Additional instruction data omitted for brevity
          ],
        },
        // Additional instruction data omitted for brevity
      ],
    };

    // Expected parsed recipe object
    const expectedRecipe = {
      name: "Avocado Egg Salad",
      cuisine: "Unknown", // Assuming cuisines array is empty
      instructions: [
        "Boil your eggs and immerse in water to cool.Peel your avocado and mash in a bowl.Squirt your lemon over the avocado.Peel the eggs, chop and mix with the avocado.",
        // Additional instructions omitted for brevity
      ],
      time: 45,
      liked: 0,
      ingredients: ["pepper" /* Additional ingredients omitted for brevity */],
      image: "https://img.spoonacular.com/recipes/716245-312x231.jpg",
      credit: "Afrolems",
    };

    // Call parseRecipe function with recipeData
    const parsedRecipe = parseRecipe(recipeData);

    // Assert that parsedRecipe matches expectedRecipe
    expect(parsedRecipe).toEqual(expectedRecipe);
  });
});
