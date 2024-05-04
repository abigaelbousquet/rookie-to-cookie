import { parseMealPlanWithoutLikes } from "../../src/RecipeUtils/ParseMealPlan";
import { test, expect } from "vitest";
import { parseRecipe } from "../../src/RecipeUtils/ParseRecipe";
/**
 * Helper function to go to the page before each test;
 */
test("parseMealPlan function", () => {
  const mealplanParsed = parseMealPlanWithoutLikes(mockMealPlanJson.Mealplan);
  const mockMealPlan = mockMealPlanJson.Mealplan;
  const mealPlanExpected = [
    { day: "monday", recipeExists: false },
    {
      day: "tuesday",
      recipeExists: true,
      recipe: parseRecipe(mockMealPlan.tuesday),
      liked: 0,
    },
    { day: "wednesday", recipeExists: false },
    {
      day: "thursday",
      recipeExists: true,
      recipe: parseRecipe(mockMealPlan.thursday),
      liked: 0,
    },
    { day: "friday", recipeExists: false },
    {
      day: "saturday",
      recipeExists: true,
      recipe: parseRecipe(mockMealPlan.saturday),
      liked: 0,
    },
  ];

  expect(JSON.stringify(mealplanParsed) == JSON.stringify(mealPlanExpected));
});

const mockMealPlanJson = {
  Mealplan: {
    saturday: {
      analyzedInstructions: [
        {
          name: "",
          steps: [
            {
              number: 1,
              step: "Heat 5 1/2 quart French oven over medium high heat and add cooking oil.",
            },
            {
              number: 2,
              step: "Add scallions, garlic and ginger and sautee for 1-2 minutes, stirring constantly.",
            },
            {
              number: 3,
              step: "Pour in vegetable broth and water. Stir. Turn up heat and bring to a boil. Reduce heat to medium low.",
            },
            {
              number: 4,
              step: "Add carrots, mushrooms, nappa cabbage, soy sauce and crushed red pepper flakes. Stir and simmer until carrots are starting to become tender, about 5 minutes. Stir in bok choy and cook until tender about 5 more minutes.In a separate sauce pan, cook noodles according to the package directions.",
            },
            {
              number: 5,
              step: "Drain and toss with sesame oil and set aside.Season soup with salt and pepper to taste. Divide noodles among bowls and top with the hot soup.",
            },
            {
              number: 6,
              step: "Garnish with scallions, snow peas and a wedge of lemon (see notes). Enjoy!",
            },
          ],
        },
      ],
      creditsText: "Jelly Toast Blog",
      cuisines: ["Asian"],
      dairyFree: true,
      diets: ["dairy free", "lacto ovo vegetarian", "vegan"],
      extendedIngredients: [
        {
          measures: { us: { amount: 1.0, unitLong: "small head" } },
          meta: [],
          name: "bok choy",
        },
        {
          measures: { us: { amount: 0.5, unitLong: "heads" } },
          meta: ["shredded"],
          name: "nappa cabbage",
        },
        {
          measures: { us: { amount: 2.0, unitLong: "mediums" } },
          meta: ["sliced"],
          name: "carrots",
        },
        {
          measures: { us: { amount: 1.0, unitLong: "Tbsp" } },
          meta: ["neutral", "flavored"],
          name: "cooking oil",
        },
        {
          measures: { us: { amount: 0.25, unitLong: "teaspoons" } },
          meta: ["red", "crushed"],
          name: "pepper",
        },
        {
          measures: { us: { amount: 1.0, unitLong: "inch" } },
          meta: ["fresh", "minced"],
          name: "ginger",
        },
        {
          measures: { us: { amount: 2.0, unitLong: "cloves" } },
          meta: ["minced"],
          name: "garlic",
        },
        {
          measures: { us: { amount: 8.0, unitLong: "ounces" } },
          meta: ["sliced"],
          name: "mushrooms",
        },
        {
          measures: { us: { amount: 8.0, unitLong: "ounces" } },
          meta: ["sliced"],
          name: "mushrooms",
        },
        {
          measures: { us: { amount: 6.0, unitLong: "servings" } },
          meta: ["black"],
          name: "salt and pepper",
        },
        {
          measures: { us: { amount: 1.0, unitLong: "bunch" } },
          meta: ["chopped"],
          name: "scallions",
        },
        {
          measures: { us: { amount: 2.0, unitLong: "teaspoons" } },
          meta: [],
          name: "sesame oil",
        },
        {
          measures: { us: { amount: 9.5, unitLong: "ounces" } },
          meta: [],
          name: "soba noodles",
        },
        {
          measures: { us: { amount: 0.25, unitLong: "cups" } },
          meta: [],
          name: "soy sauce",
        },
        {
          measures: { us: { amount: 6.0, unitLong: "cups" } },
          meta: [],
          name: "vegetable stock",
        },
        {
          measures: { us: { amount: 2.0, unitLong: "cups" } },
          meta: [],
          name: "water",
        },
      ],
      glutenFree: false,
      id: 503396,
      image: "https://img.spoonacular.com/recipes/503396-312x231.jpg",
      readyInMinutes: 40,
      servings: 6,
      spoonacularScore: 78.27741241455078,
      title: "asian vegetable noodle soup {video}",
      vegan: true,
      vegetarian: true,
    },
    thursday: {
      analyzedInstructions: [
        {
          name: "",
          steps: [
            {
              number: 1,
              step: "Melt butter in a large heavy-bottomed pot over medium-high heat.",
            },
            {
              number: 2,
              step: "Add the scallions, garlic, and curry powder to the pan.",
            },
            {
              number: 3,
              step: "Saute until fragrant and scallions are tender, about 4 minutes.",
            },
            {
              number: 4,
              step: "Add in the tomato paste and lentils. Cook, stirring constantly, for 1-2 minutes.",
            },
            {
              number: 5,
              step: "Add in the tomatoes and salt along with enough water to cover the lentils by inch. Increase heat to high and bring to a boil. Then reduce heat to low and simmer until lentils are tender, about 30-40 minutes.",
            },
            {
              number: 6,
              step: "Remove from the heat.Meanwhile, heat a small skillet over medium heat.",
            },
            {
              number: 7,
              step: "Add the coconut, pistachios, and mustard seeds to the pan and cook, stirring constantly, until the coconut is golden and the mustard seeds are toasted, about 3 minutes. Season to taste with salt.Divide the lentils evenly among 6 bowls. Top with the coconut-mustard seed mixture as well as a dollop of yogurt.",
            },
            { number: 8, step: "Serve with a crusty loaf of bread or naan." },
          ],
        },
      ],
      creditsText: "Joanne Eats Well with Others",
      cuisines: [],
      dairyFree: false,
      diets: ["gluten free", "lacto ovo vegetarian"],
      extendedIngredients: [
        {
          measures: { us: { amount: 1.5, unitLong: "Tbsps" } },
          meta: ["black"],
          name: "brown mustard seeds",
        },
        {
          measures: { us: { amount: 28.0, unitLong: "ounces" } },
          meta: ["diced", "crushed", "canned"],
          name: "canned tomatoes",
        },
        {
          measures: { us: { amount: 1.75, unitLong: "teaspoons" } },
          meta: [],
          name: "coarse salt",
        },
        {
          measures: { us: { amount: 1.0, unitLong: "Tbsp" } },
          meta: [],
          name: "madras curry powder",
        },
        {
          measures: { us: { amount: 2.0, unitLong: "cups" } },
          meta: [],
          name: "green lentils",
        },
        {
          measures: { us: { amount: 2.0, unitLong: "" } },
          meta: ["minced"],
          name: "garlic cloves",
        },
        {
          measures: { us: { amount: 0.25, unitLong: "cups" } },
          meta: [],
          name: "pistachios",
        },
        {
          measures: { us: { amount: 6.0, unitLong: "servings" } },
          meta: ["plain", "for serving"],
          name: "yogurt",
        },
        {
          measures: { us: { amount: 1.0, unitLong: "bunch" } },
          meta: ["trimmed", "thinly sliced"],
          name: "scallions",
        },
        {
          measures: { us: { amount: 1.0, unitLong: "Tbsp" } },
          meta: [],
          name: "tomato paste",
        },
        {
          measures: { us: { amount: 3.0, unitLong: "Tbsps" } },
          meta: ["unsalted"],
          name: "butter",
        },
        {
          measures: { us: { amount: 1.0, unitLong: "cup" } },
          meta: ["unsweetened"],
          name: "coconut flakes",
        },
        {
          measures: { us: { amount: 1.0, unitLong: "cup" } },
          meta: ["unsweetened"],
          name: "coconut flakes",
        },
      ],
      glutenFree: true,
      id: 900271,
      image: "https://img.spoonacular.com/recipes/900271-312x231.jpg",
      readyInMinutes: 45,
      servings: 6,
      spoonacularScore: 74.5794677734375,
      title: "Spiced Braised Lentils with Tomatoes and Toasted Coconut",
      vegan: false,
      vegetarian: true,
    },
    tuesday: {
      analyzedInstructions: [
        {
          name: "",
          steps: [
            {
              number: 1,
              step: "Heat a large stock pot over medium heat and add olive oil and butter. Once melted, stir in the onion with a pinch of salt and cook until softened, about 5 minutes.",
            },
            {
              number: 2,
              step: "Add the potatoes and enough stock to cover the potatoes - starting with 4 cups and using more if needed. Bring the mixture to a simmer. Cover and cook until the potatoes are tender and falling apart, about 20 to 30 minutes. Turn off the heat.Carefully add the mixture to a blender. Squeeze out the roasted garlic cloves and add them to the blender too. Puree the soup until totally creamy and smooth.",
            },
            {
              number: 3,
              step: "Pour the mixture back into the pot and heat over low heat.",
            },
            {
              number: 4,
              step: "Add the cream, salt and pepper. Taste and season additionally with salt and pepper if needed.",
            },
            {
              number: 5,
              step: "Serve the soup and drizzle the chili oil on top.",
            },
            {
              number: 6,
              step: "Add the brussels as garnish and eat! crispy brussels",
            },
            {
              number: 7,
              step: "While the potatoes are simmering, heat a pot over medium heat and add the vegetable oil. Attach a candy thermometer if you have one - you want the temperature to be around 325 to 350 degrees - no higher!",
            },
            {
              number: 8,
              step: "Add the brussels sprouts leaves a few handfuls at a time and fry until crispy, about 2 to 3 minutes.",
            },
            {
              number: 9,
              step: "Remove with a slotted spoon and place on a paper towel to drain, covering with salt and pepper.",
            },
          ],
        },
      ],
      creditsText: "How Sweet Eats",
      cuisines: [],
      dairyFree: false,
      diets: ["gluten free", "lacto ovo vegetarian"],
      extendedIngredients: [
        {
          measures: { us: { amount: 0.5, unitLong: "pounds" } },
          meta: [],
          name: "brussels sprouts",
        },
        {
          measures: { us: { amount: 4.0, unitLong: "servings" } },
          meta: ["crispy"],
          name: "brussels",
        },
        {
          measures: { us: { amount: 4.0, unitLong: "servings" } },
          meta: ["for serving"],
          name: "chili oil",
        },
        {
          measures: { us: { amount: 0.33333334, unitLong: "cups" } },
          meta: [],
          name: "cream",
        },
        {
          measures: { us: { amount: 1.0, unitLong: "Tbsp" } },
          meta: [],
          name: "olive oil",
        },
        {
          measures: { us: { amount: 0.25, unitLong: "teaspoons" } },
          meta: [],
          name: "pepper",
        },
        {
          measures: { us: { amount: 2.0, unitLong: "" } },
          meta: [],
          name: "bulbs roasted garlic",
        },
        {
          measures: { us: { amount: 3.0, unitLong: "pounds" } },
          meta: ["peeled", "chopped"],
          name: "russet potatoes",
        },
        {
          measures: { us: { amount: 0.25, unitLong: "teaspoons" } },
          meta: [],
          name: "salt",
        },
        {
          measures: { us: { amount: 4.0, unitLong: "servings" } },
          meta: ["to taste"],
          name: "salt and pepper",
        },
        {
          measures: { us: { amount: 1.0, unitLong: "" } },
          meta: ["diced", "sweet"],
          name: "onion",
        },
        {
          measures: { us: { amount: 1.0, unitLong: "Tbsp" } },
          meta: ["unsalted"],
          name: "butter",
        },
        {
          measures: { us: { amount: 4.0, unitLong: "cups" } },
          meta: [],
          name: "vegetable oil",
        },
        {
          measures: { us: { amount: 4.0, unitLong: "cups" } },
          meta: ["low-sodium"],
          name: "vegetable stock",
        },
      ],
      glutenFree: true,
      id: 625692,
      image: "https://img.spoonacular.com/recipes/625692-312x231.jpg",
      readyInMinutes: 60,
      servings: 4,
      spoonacularScore: 85.25627136230469,
      title: "Creamy Roasted Garlic Potato Soup with Crispy Brussels",
      vegan: false,
      vegetarian: true,
    },
  },
  response_type: "success",
};
