import { getDislikes, getLikes } from "../../utils/api";
import { parseRecipe } from "../RecipeCard/ParseRecipe";
import Recipe from "../RecipeCard/Recipe";

export interface mealPlanDay {
  day: string;
  recipeExists: boolean;
  recipe: Recipe | null;
  liked: number | null;
}
export const dayToRecipe = (day: string, recipeList): Recipe | null => {
  if (recipeList !== undefined) {
    const recipeJson = recipeList[day];
    if (recipeJson === null || recipeJson === undefined) {
      return null;
    }
    const recipe = parseRecipe(recipeJson);
    return recipe;
  }
  return null;
};
export const getLikeDislike = async (recipeId): Promise<number> => {
  const response = await getLikes();
  const likes: any[] = response["Recipes"];
  const response2 = await getDislikes();
  const dislikes = response2["Recipes"];
  const likesList = likes.map((recipeData) => recipeData["id"]);
  const dislikesList = dislikes.map((recipeData) => recipeData["id"]);
  console.log(recipeId);
  if (likesList.includes(recipeId)) {
    console.log("found in likes");
    return 1;
  } else if (dislikesList.includes(recipeId)) {
    console.log("found in dislikes");
    return 2;
  } else {
    console.log("not found in likes/dislikes");
    return 0;
  }
};
export const parseMealPlanLikes = async (
  recipeList
): Promise<mealPlanDay[]> => {
  const sunRecipe = dayToRecipe("sunday", recipeList);
  const monRecipe = dayToRecipe("monday", recipeList);
  const tuesRecipe = dayToRecipe("tuesday", recipeList);
  const wedRecipe = dayToRecipe("wednesday", recipeList);
  const thursRecipe = dayToRecipe("thursday", recipeList);
  const friRecipe = dayToRecipe("friday", recipeList);
  const satRecipe = dayToRecipe("saturday", recipeList);
  console.log(monRecipe);
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
export const parseMealPlanWithoutLikes = (
  recipeList
): mealPlanDay[] => {
  const sunRecipe = dayToRecipe("sunday", recipeList);
  const monRecipe = dayToRecipe("monday", recipeList);
  const tuesRecipe = dayToRecipe("tuesday", recipeList);
  const wedRecipe = dayToRecipe("wednesday", recipeList);
  const thursRecipe = dayToRecipe("thursday", recipeList);
  const friRecipe = dayToRecipe("friday", recipeList);
  const satRecipe = dayToRecipe("saturday", recipeList);
  console.log(monRecipe);
  const newMealPlan = [
    {
      day: "Monday",
      recipeExists: recipeList["monday"] !== undefined,
      recipe: monRecipe,
      liked: monRecipe !== null ? 0 : null,
    },
    {
      day: "Tuesday",
      recipeExists: recipeList["tuesday"] !== undefined,
      recipe: tuesRecipe || null,
      liked: tuesRecipe !== null ? 0 : null,
    },
    {
      day: "Wednesday",
      recipeExists: recipeList["wednesday"] !== undefined,
      recipe: wedRecipe || null,
      liked: wedRecipe !== null ? 0 : null,
    },
    {
      day: "Thursday",
      recipeExists: recipeList["thursday"] !== undefined,
      recipe: thursRecipe || null,
      liked: thursRecipe !== null ? 0 : null,
    },
    {
      day: "Friday",
      recipeExists: recipeList["friday"] !== undefined,
      recipe: friRecipe || null,
      liked: friRecipe !== null ? 0 : null,
    },
    {
      day: "Saturday",
      recipeExists: recipeList["saturday"] !== undefined,
      recipe: satRecipe || null,
      liked: satRecipe !== null ? 0 : null,
    },
    {
      day: "Sunday",
      recipeExists: recipeList["sunday"] !== undefined,
      recipe: sunRecipe,
      liked: sunRecipe !== null ? 0 : null,
    },
  ];
  return newMealPlan;
};
