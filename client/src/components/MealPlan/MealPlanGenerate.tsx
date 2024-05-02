import { getDislikes, getLikes } from "../../utils/api";
import { parseRecipe } from "../RecipeCard/ParseRecipe";
import Recipe from "../RecipeCard/Recipe";

export const dayToRecipe = (day: string, recipeList): Recipe | null => {
  if (recipeList !== undefined) {
    const recipeJson = recipeList[day];
    if (recipeJson === null || recipeJson === undefined) {
      return null;
    }
    console.log(recipeJson);
    const recipe = parseRecipe(recipeJson);
    console.log(recipe);
    return recipe;
  }
  return null;
};
export const getLikeDislike = async (recipeId): Promise<number> => {
  const response = await getLikes();
  const likes: any[] = response["Recipes"];
  const response2 = await getDislikes();
  const dislikes = response2["Recipes"];
  if (
    likes
      .map((recipeData) => {
        recipeData.id;
      })
      .includes(recipeId)
  ) {
    return 1;
  } else if (
    dislikes
      .map((recipeData) => {
        recipeData.id;
      })
      .includes(recipeId)
  ) {
    return 2;
  } else {
    return 0;
  }
};
export const parseMealPlan = async (recipeList) => {
  const sunRecipe = dayToRecipe("sunday", recipeList);
  const monRecipe = dayToRecipe("monday", recipeList);
  const tuesRecipe = dayToRecipe("tuesday", recipeList);
  const wedRecipe = dayToRecipe("wednesday", recipeList);
  const thursRecipe = dayToRecipe("thursday", recipeList);
  const friRecipe = dayToRecipe("friday", recipeList);
  const satRecipe = dayToRecipe("saturday", recipeList);
  const newMealPlan = [
    {
      day: "Monday",
      recipeExists: recipeList["monday"] !== undefined,
      recipe: monRecipe,
      liked: await getLikeDislike(monRecipe !== null ? monRecipe.id : null),
    },
    {
      day: "Tuesday",
      recipeExists: recipeList["tuesday"] !== undefined,
      recipe: tuesRecipe || null,
      liked: await getLikeDislike(tuesRecipe !== null ? tuesRecipe.id : null),
    },
    {
      day: "Wednesday",
      recipeExists: recipeList["wednesday"] !== undefined,
      recipe: wedRecipe || null,
      liked: await getLikeDislike(wedRecipe !== null ? wedRecipe.id : null),
    },
    {
      day: "Thursday",
      recipeExists: recipeList["thursday"] !== undefined,
      recipe: thursRecipe || null,
      liked: await getLikeDislike(thursRecipe !== null ? thursRecipe.id : null),
    },
    {
      day: "Friday",
      recipeExists: recipeList["friday"] !== undefined,
      recipe: friRecipe || null,
      liked: await getLikeDislike(friRecipe !== null ? friRecipe.id : null),
    },
    {
      day: "Saturday",
      recipeExists: recipeList["saturday"] !== undefined,
      recipe: satRecipe || null,
      liked: await getLikeDislike(satRecipe !== null ? satRecipe.id : null),
    },
    {
      day: "Sunday",
      recipeExists: recipeList["sunday"] !== undefined,
      recipe: sunRecipe,
      liked: await getLikeDislike(sunRecipe !== null ? sunRecipe.id : null),
    },
  ];
  return newMealPlan;
};
