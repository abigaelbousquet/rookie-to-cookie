import Recipe from "../components/RecipeCard/Recipe";
import RecipeHistory from "../components/RecipeCard/RecipeHistory";

const dish1: Recipe = {
  name: "Chicken Tikka Masala",
  cuisine: "Indian",
  instructions: "Step 1, 2, 3",
  time: 60,
  liked: true,
};
const dish2: Recipe = {
  name: "Sushi",
  cuisine: "Japanese",
  instructions: "Step 1, 2, 3",
  time: 45,
  liked: false,
};
const dish3: Recipe = {
  name: "Beef Bourguignon",
  cuisine: "French",
  instructions: "Step 1, 2, 3",
  time: 120,
  liked: false,
};
const dish4: Recipe = {
  name: "Pad Thai",
  cuisine: "Thai",
  instructions: "Step 1, 2, 3",
  time: 40,
  liked: false,
};
const dish5: Recipe = {
  name: "Tacos al Pastor",
  cuisine: "Mexican",
  instructions: "Step 1, 2, 3",
  time: 35,
  liked: false,
};
const dish6: Recipe = {
  name: "Moussaka",
  cuisine: "Greek",
  instructions: "Step 1, 2, 3",
  time: 90,
  liked: false,
};
const dish7: Recipe = {
  name: "Spaghetti Bolognese",
  cuisine: "Italian",
  instructions: "Step 1, 2, 3",
  time: 55,
  liked: false,
};
const dish8: Recipe = {
  name: "Paella",
  cuisine: "Spanish",
  instructions: "Step 1, 2, 3",
  time: 75,
  liked: false,
};
const dish9: Recipe = {
  name: "Hamburger",
  cuisine: "American",
  instructions: "Step 1, 2, 3",
  time: 30,
  liked: false,
};
const dish10: Recipe = {
  name: "Ramen",
  cuisine: "Japanese",
  instructions: "Step 1, 2, 3",
  time: 50,
  liked: false,
};

/**
 * Mocked history items
 */
const h1: RecipeHistory = {
  recipe: dish1,
  month: "May",
  day: 2,
  year: 2024,
};
const h2: RecipeHistory = {
  recipe: dish2,
  month: "May",
  day: 4,
  year: 2024,
};
const h3: RecipeHistory = {
  recipe: dish3,
  month: "May",
  day: 6,
  year: 2024,
};
const h4: RecipeHistory = {
  recipe: dish4,
  month: "May",
  day: 11,
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
  day: 27,
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
