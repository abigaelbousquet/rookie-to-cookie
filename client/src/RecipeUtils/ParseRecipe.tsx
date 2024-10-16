import { useState } from "react";
import Recipe from "../components/RecipeCard/Recipe";

export function parseRecipe(recipeData: any): Recipe {
  const name: string = recipeData.title;
  const cuisine: string =
    recipeData.cuisines.length > 0 ? recipeData.cuisines[0] : "Unknown";
  const instructions: string[] = recipeData.analyzedInstructions.flatMap(
    (instruction) => instruction.steps.map((step) => step.step)
  );
  const id: string = recipeData.id;
  const time: number = recipeData.readyInMinutes;
  const servings: string = recipeData.servings;
  const ingredients: string[] = recipeData.extendedIngredients.map(
    (ingredient) => {
      let ingredientName = ingredient.name;
      if (ingredient.meta && ingredient.meta.length > 0) {
        ingredientName = `${ingredient.meta[0]} ${ingredient.name}`;
      }

      let measurement: string = ingredient.measures.us.amount;
      if (ingredient.measures.us.unitLong.length > 0) {
        measurement = `${measurement} ${ingredient.measures.us.unitLong}`;
      }

      let fullIngredientInfo = `${ingredientName} (${measurement})`;
      return fullIngredientInfo;
    }
  );
  const image: string = recipeData.image || "https://placeholder.com/312x231"; // Placeholder URL
  const credit: string = recipeData.creditsText || "Unknown"; // Default credit

  return {
    name,
    cuisine,
    instructions,
    time,
    ingredients,
    image,
    credit,
    id,
    servings,
  };
}

export function getRecipes(jsonData: any): Recipe[] {
  return jsonData.results.map((result) => parseRecipe(result));
}
