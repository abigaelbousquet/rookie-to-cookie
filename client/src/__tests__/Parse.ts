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
        {
          measures: {
            us: {
              amount: 2.0,
              unitLong: "",
            },
          },
          meta: [],
          name: "eggs",
        },
        {
          measures: {
            us: {
              amount: 0.5,
              unitLong: "",
            },
          },
          meta: [],
          name: "seasoning cube",
        },
        {
          measures: {
            us: {
              amount: 0.25,
              unitLong: "",
            },
          },
          meta: [],
          name: "lemon",
        },
        {
          measures: {
            us: {
              amount: 1.0,
              unitLong: "",
            },
          },
          meta: [],
          name: "avocado pear",
        },
        {
          measures: {
            us: {
              amount: 1.0,
              unitLong: "",
            },
          },
          meta: [],
          name: "scotch bonnet pepper",
        },
        {
          measures: {
            us: {
              amount: 2.0,
              unitLong: "slices",
            },
          },
          meta: ["whole wheat"],
          name: "bread",
        },
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
                {
                  name: "lemon",
                },
                {
                  name: "water",
                },
                {
                  name: "egg",
                },
              ],
              step: "Boil your eggs and immerse in water to cool.Peel your avocado and mash in a bowl.Squirt your lemon over the avocado.Peel the eggs, chop and mix with the avocado.",
            },
            {
              number: 2,
              equipment: [
                {
                  name: "rolling pin",
                },
              ],
              ingredients: [
                {
                  name: "scotch bonnet chili",
                },
                {
                  name: "black pepper",
                },
                {
                  name: "egg salad",
                },
                {
                  name: "seasoning",
                },
                {
                  name: "avocado",
                },
                {
                  name: "bread",
                },
                {
                  name: "sandwich bread",
                },
                {
                  name: "roll",
                },
              ],
              step: "Mix the black pepper, seasoning and scotch bonnet pepper and set aside.Toast your bread and roll out with a rolling pin, cut the edges off and serve the avocado egg salad on the bread.",
            },
          ],
        },
      ],
    };

    // Expected parsed recipe object
    const expectedRecipe = {
      name: "Avocado Egg Salad",
      cuisine: "Unknown", // Assuming cuisines array is empty
      instructions: [
        "Boil your eggs and immerse in water to cool.Peel your avocado and mash in a bowl.Squirt your lemon over the avocado.Peel the eggs, chop and mix with the avocado.",
        "Mix the black pepper, seasoning and scotch bonnet pepper and set aside.Toast your bread and roll out with a rolling pin, cut the edges off and serve the avocado egg salad on the bread.",
      ],
      time: 45,
      liked: 0,
      ingredients: [
        "black pepper",
        "eggs",
        "seasoning cube",
        "lemon",
        "avocado pear",
        "scotch bonnet pepper",
        "whole wheat bread",
      ],
      image: "https://img.spoonacular.com/recipes/716245-312x231.jpg",
      credit: "Afrolems",
    };

    // Call parseRecipe function with recipeData
    const parsedRecipe = parseRecipe(recipeData);

    // Assert that parsedRecipe matches expectedRecipe
    expect(parsedRecipe).toEqual(expectedRecipe);
  });
});

describe("parseRecipe function", () => {
  it("should parse the second set of recipe data correctly", () => {
    // Sample recipe data
    const recipeDataset = {
      number: 5,
      totalResults: 1619,
      results: [
        {
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
            {
              measures: {
                us: {
                  amount: 2.0,
                  unitLong: "",
                },
              },
              meta: [],
              name: "eggs",
            },
            {
              measures: {
                us: {
                  amount: 0.5,
                  unitLong: "",
                },
              },
              meta: [],
              name: "seasoning cube",
            },
            {
              measures: {
                us: {
                  amount: 0.25,
                  unitLong: "",
                },
              },
              meta: [],
              name: "lemon",
            },
            {
              measures: {
                us: {
                  amount: 1.0,
                  unitLong: "",
                },
              },
              meta: [],
              name: "avocado pear",
            },
            {
              measures: {
                us: {
                  amount: 1.0,
                  unitLong: "",
                },
              },
              meta: [],
              name: "scotch bonnet pepper",
            },
            {
              measures: {
                us: {
                  amount: 2.0,
                  unitLong: "slices",
                },
              },
              meta: ["whole wheat"],
              name: "bread",
            },
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
                    {
                      name: "lemon",
                    },
                    {
                      name: "water",
                    },
                    {
                      name: "egg",
                    },
                  ],
                  step: "Boil your eggs and immerse in water to cool.Peel your avocado and mash in a bowl.Squirt your lemon over the avocado.Peel the eggs, chop and mix with the avocado.",
                },
                {
                  number: 2,
                  equipment: [
                    {
                      name: "rolling pin",
                    },
                  ],
                  ingredients: [
                    {
                      name: "scotch bonnet chili",
                    },
                    {
                      name: "black pepper",
                    },
                    {
                      name: "egg salad",
                    },
                    {
                      name: "seasoning",
                    },
                    {
                      name: "avocado",
                    },
                    {
                      name: "bread",
                    },
                    {
                      name: "sandwich bread",
                    },
                    {
                      name: "roll",
                    },
                  ],
                  step: "Mix the black pepper, seasoning and scotch bonnet pepper and set aside.Toast your bread and roll out with a rolling pin, cut the edges off and serve the avocado egg salad on the bread.",
                },
              ],
            },
          ],
        },
        {
          id: 633953,
          creditsText:
            "Foodista.com – The Cooking Encyclopedia Everyone Can Edit",
          title: "Balsamic Wheat & Chicken Salad",
          image: "https://img.spoonacular.com/recipes/633953-312x231.jpg",
          servings: 4,
          readyInMinutes: 45,
          spoonacularScore: 78.05068969726562,
          cuisines: [],
          diets: ["gluten free", "dairy free"],
          dairyFree: true,
          glutenFree: true,
          vegan: false,
          vegetarian: false,
          extendedIngredients: [
            {
              measures: {
                us: {
                  amount: 2.5,
                  unitLong: "cups",
                },
              },
              meta: ["white", "soft", "cooked"],
              name: "wheat",
            },
            {
              measures: {
                us: {
                  amount: 0.25,
                  unitLong: "cups",
                },
              },
              meta: ["minced"],
              name: "onion",
            },
            {
              measures: {
                us: {
                  amount: 0.25,
                  unitLong: "cups",
                },
              },
              meta: ["diced", "red"],
              name: "bell pepper",
            },
            {
              measures: {
                us: {
                  amount: 1.0,
                  unitLong: "",
                },
              },
              meta: ["boneless", "cubed", "cooked"],
              name: "chicken breast",
            },
            {
              measures: {
                us: {
                  amount: 4.5,
                  unitLong: "Tbsps",
                },
              },
              meta: [],
              name: "balsamic vinegrette salad dressing",
            },
          ],
          analyzedInstructions: [
            {
              name: "",
              steps: [
                {
                  number: 1,
                  equipment: [],
                  ingredients: [
                    {
                      name: "chicken",
                    },
                    {
                      name: "pepper",
                    },
                    {
                      name: "onion",
                    },
                    {
                      name: "wheat",
                    },
                  ],
                  step: "Mix wheat, onion, pepper and chicken together.  Chill  and pour dressing over when ready to serve.",
                },
              ],
            },
          ],
        },
        {
          id: 659677,
          creditsText:
            "Foodista.com – The Cooking Encyclopedia Everyone Can Edit",
          title: "Seared Scallop With Asparagus Cream",
          image: "https://img.spoonacular.com/recipes/659677-312x231.jpg",
          servings: 1,
          readyInMinutes: 45,
          spoonacularScore: 50.03648376464844,
          cuisines: [],
          diets: ["gluten free", "pescatarian"],
          dairyFree: false,
          glutenFree: true,
          vegan: false,
          vegetarian: false,
          extendedIngredients: [
            {
              measures: {
                us: {
                  amount: 1.0,
                  unitLong: "Tbsp",
                },
              },
              meta: [],
              name: "cooking",
            },
            {
              measures: {
                us: {
                  amount: 3.0,
                  unitLong: "",
                },
              },
              meta: [],
              name: "scallops",
            },
            {
              measures: {
                us: {
                  amount: 1.0,
                  unitLong: "",
                },
              },
              meta: ["diced", "seeded", "finely"],
              name: "tomato",
            },
            {
              measures: {
                us: {
                  amount: 1.764,
                  unitLong: "ounces",
                },
              },
              meta: ["chopped"],
              name: "asparagus",
            },
            {
              measures: {
                us: {
                  amount: 0.705,
                  unitLong: "ounces",
                },
              },
              meta: [],
              name: "whipping cream",
            },
            {
              measures: {
                us: {
                  amount: 6.861,
                  unitLong: "fl. ozs",
                },
              },
              meta: [],
              name: "chicken stock",
            },
            {
              measures: {
                us: {
                  amount: 1.715,
                  unitLong: "fl. ozs",
                },
              },
              meta: [],
              name: "white wine",
            },
            {
              measures: {
                us: {
                  amount: 0.5,
                  unitLong: "",
                },
              },
              meta: [],
              name: "salt",
            },
            {
              measures: {
                us: {
                  amount: 1.0,
                  unitLong: "dash",
                },
              },
              meta: ["white"],
              name: "ground pepper",
            },
            {
              measures: {
                us: {
                  amount: 1.0,
                  unitLong: "sheet",
                },
              },
              meta: ["toasted", "(seaweed)"],
              name: "nori",
            },
          ],
          analyzedInstructions: [
            {
              name: "",
              steps: [
                {
                  number: 1,
                  equipment: [
                    {
                      name: "blender",
                    },
                  ],
                  ingredients: [
                    {
                      name: "cream sauce",
                    },
                    {
                      name: "asparagus",
                    },
                    {
                      name: "sauce",
                    },
                  ],
                  step: "Combine asparagus, cream sauce ingredients and bring to boil for 1 hour until sauce is reduced.  Process in a blender and strain.",
                },
                {
                  number: 2,
                  equipment: [],
                  ingredients: [
                    {
                      name: "scallops",
                    },
                    {
                      name: "cooking oil",
                    },
                  ],
                  step: "Heat oil and sear scallops on both sides until golden brown.",
                },
                {
                  number: 3,
                  equipment: [],
                  ingredients: [
                    {
                      name: "cream sauce",
                    },
                    {
                      name: "asparagus",
                    },
                    {
                      name: "scallops",
                    },
                    {
                      name: "tomato",
                    },
                  ],
                  step: "Pour some of the asparagus cream sauce on a serving plate.  Arrange the seared scallops on top and sprinkle diced tomato over.",
                },
                {
                  number: 4,
                  equipment: [],
                  ingredients: [
                    {
                      name: "nori",
                    },
                  ],
                  step: "Garnish with ground nori.",
                },
              ],
            },
            {
              name: "Chefs Notes",
              steps: [
                {
                  number: 1,
                  equipment: [],
                  ingredients: [
                    {
                      name: "nori",
                    },
                  ],
                  step: "Store excess ground nori in a clean airtight container for future use.",
                },
              ],
            },
          ],
        },
      ],
    };

    // Expected parsed recipe object
    const expectedSecond = {
      name: "Balsamic Wheat & Chicken Salad",
      cuisine: "Unknown",
      instructions: [
        "Mix wheat, onion, pepper and chicken together.  Chill  and pour dressing over when ready to serve.",
      ],
      time: 45,
      liked: 0,
      ingredients: [
        "white wheat",
        "minced onion",
        "diced bell pepper",
        "boneless chicken breast",
        "balsamic vinegrette salad dressing",
      ],
      image: "https://img.spoonacular.com/recipes/633953-312x231.jpg",
      credit: "Foodista.com – The Cooking Encyclopedia Everyone Can Edit",
    };

    // Call parseRecipe function with recipeData
    const parsedRecipe = parseRecipe(recipeDataset.results[1]);

    // Assert that parsedRecipe matches expectedRecipe
    expect(parsedRecipe).toEqual(expectedSecond);
  });
});
