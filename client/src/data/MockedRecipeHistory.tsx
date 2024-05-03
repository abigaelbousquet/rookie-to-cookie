import Recipe from "../components/RecipeCard/Recipe";
import RecipeHistory from "../components/RecipeCard/RecipeHistory";
export const emptyMealPlan = [
  { day: "sunday", recipeExists: false },
  { day: "monday", recipeExists: false },
  { day: "tuesday", recipeExists: false },
  { day: "wednesday", recipeExists: false },
  { day: "thursday", recipeExists: false },
  { day: "friday", recipeExists: false },
  { day: "saturday", recipeExists: false },
];
const dish1: Recipe = {
  name: "Chicken Tikka Masala",
  cuisine: "Indian",
  instructions: ["Step 1", "Step 2", "Step 3"],
  time: 60,
  servings: "2",
  ingredients: ["Chicken", "Curry"],
  image: "https://placeholder.com/312x231",
  credit: "Unknown",
  id: "1",
};
const dish2: Recipe = {
  name: "Sushi",
  cuisine: "Japanese",
  instructions: ["Step 1", "Step 2", "Step 3"],
  time: 45,
  servings: "2",
  ingredients: ["Seaweed", "Rice"],
  image: "https://placeholder.com/312x231",
  credit: "Unknown",
  id: "2",
};
const dish3: Recipe = {
  name: "Beef Bourguignon",
  cuisine: "French",
  instructions: ["Step 1", "Step 2", "Step 3"],
  time: 120,
  servings: "2",
  ingredients: ["Beef"],
  image: "https://placeholder.com/312x231",
  credit: "Unknown",
  id: "3",
};
const dish4: Recipe = {
  name: "Pad Thai",
  cuisine: "Thai",
  servings: "2",
  instructions: ["Step 1", "Step 2", "Step 3"],
  time: 40,
  ingredients: ["Noodles", "Sauce"],
  image: "https://placeholder.com/312x231",
  credit: "Unknown",
  id: "4",
};
const dish5: Recipe = {
  name: "Tacos al Pastor",
  cuisine: "Mexican",
  servings: "2",
  instructions: ["Step 1", "Step 2", "Step 3"],
  time: 35,
  ingredients: ["Shells", "Filling", "Dip"],
  image: "https://placeholder.com/312x231",
  credit: "Unknown",
  id: "5",
};
const dish6: Recipe = {
  name: "Moussaka",
  cuisine: "Greek",
  instructions: ["Step 1", "Step 2", "Step 3"],
  time: 90,
  servings: "2",
  ingredients: ["Cheese"],
  image: "https://placeholder.com/312x231",
  credit: "Unknown",
  id: "6",
};
const dish7: Recipe = {
  name: "Spaghetti Bolognese",
  cuisine: "Italian",
  instructions: ["Step 1", "Step 2", "Step 3"],
  time: 55,
  servings: "2",
  ingredients: ["Pasta"],
  image: "https://placeholder.com/312x231",
  credit: "Unknown",
  id: "7",
};
const dish8: Recipe = {
  name: "Paella",
  cuisine: "Spanish",
  instructions: ["Step 1", "Step 2", "Step 3"],
  time: 75,
  servings: "2",
  ingredients: [],
  image: "https://placeholder.com/312x231",
  credit: "Unknown",
  id: "8",
};
const dish9: Recipe = {
  name: "Hamburger",
  cuisine: "American",
  instructions: ["Step 1", "Step 2", "Step 3"],
  time: 30,
  servings: "2",
  ingredients: ["Beef"],
  image: "https://placeholder.com/312x231",
  credit: "Unknown",
  id: "9",
};
const dish10: Recipe = {
  name: "Ramen",
  cuisine: "Japanese",
  instructions: ["Step 1", "Step 2", "Step 3"],
  time: 50,
  servings: "2",
  ingredients: ["Noodles"],
  image: "https://placeholder.com/312x231",
  credit: "Unknown",
  id: "10",
};

/**
 * Mocked history items
 */
const h1: RecipeHistory = {
  recipe: dish1,
  month: "May",
  day: 1,
  year: 2024,
};
const h2: RecipeHistory = {
  recipe: dish2,
  month: "May",
  day: 2,
  year: 2024,
};
const h3: RecipeHistory = {
  recipe: dish3,
  month: "May",
  day: 3,
  year: 2024,
};
const h4: RecipeHistory = {
  recipe: dish4,
  month: "May",
  day: 4,
  year: 2024,
};
const h5: RecipeHistory = {
  recipe: dish4,
  month: "May",
  day: 15,
  year: 2024,
};
const h6: RecipeHistory = {
  recipe: dish4,
  month: "May",
  day: 18,
  year: 2024,
};
const h7: RecipeHistory = {
  recipe: dish4,
  month: "May",
  day: 19,
  year: 2024,
};
const h8: RecipeHistory = {
  recipe: dish4,
  month: "May",
  day: 22,
  year: 2024,
};
const h9: RecipeHistory = {
  recipe: dish4,
  month: "May",
  day: 27,
  year: 2024,
};
const h10: RecipeHistory = {
  recipe: dish4,
  month: "May",
  day: 28,
  year: 2024,
};

export const mockRecipeList = () => {
  return [
    dish1,
    dish2,
    dish3,
    dish4,
    dish5,
    dish6,
    dish7,
    dish8,
    dish9,
    dish10,
  ];
};
export const mockRecipeHistory = () => {
  return [h1, h2, h3, h4, h5, h6, h7, h8, h9, h10];
};

export const mockEmptyRecipeList = () => {
  return [];
};
