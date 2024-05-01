import { parseRecipe } from "../RecipeCard/ParseRecipe";
import Recipe from "../RecipeCard/Recipe";

export const dayToRecipe = (day: string, recipeList): Recipe | null => {
  const recipeJson = recipeList[day];
  if (recipeJson === null || recipeJson === undefined) {
    return null;
  }
  console.log(recipeJson);
  const recipe = parseRecipe(recipeJson);
  console.log(recipe);
  return recipe;
};
export const parseMealPlan = (recipeList) => {
  const sunRecipe = dayToRecipe("sunday", recipeList);
  const monRecipe = dayToRecipe("monday", recipeList);
  const tuesRecipe = dayToRecipe("tuesday", recipeList);
  const wedRecipe = dayToRecipe("wednesday", recipeList);
  const thursRecipe = dayToRecipe("thursday", recipeList);
  const friRecipe = dayToRecipe("friday", recipeList);
  const satRecipe = dayToRecipe("saturday", recipeList);
  const newMealPlan = [
    {
      day: "Sunday",
      recipeExists: recipeList["sunday"] !== undefined,
      recipe: sunRecipe,
    },
    {
      day: "Monday",
      recipeExists: recipeList["monday"] !== undefined,
      recipe: monRecipe,
    },
    {
      day: "Tuesday",
      recipeExists: recipeList["tuesday"] !== undefined,
      recipe: tuesRecipe || null,
    },
    {
      day: "Wednesday",
      recipeExists: recipeList["wednesday"] !== undefined,
      recipe: wedRecipe || null,
    },
    {
      day: "Thursday",
      recipeExists: recipeList["thursday"] !== undefined,
      recipe: thursRecipe || null,
    },
    {
      day: "Friday",
      recipeExists: recipeList["friday"] !== undefined,
      recipe: friRecipe || null,
    },
    {
      day: "Saturday",
      recipeExists: recipeList["saturday"] !== undefined,
      recipe: satRecipe || null,
    },
  ];
  return newMealPlan;
};
