
import { profileProps } from "../components/Login/AccountCreation";
import Recipe from "../components/RecipeCard/Recipe";
import { getLoginCookie } from "./cookie";

const HOST = "http://localhost:3232";
interface mealPlanProps {
  diet: string;
  intolerances: string[];
  requestedServings: string;
  daysToPlan: string[];
  cuisine: string[];
  maxReadyTime: string;
}

interface User {
  name: string;
  experienceLevel: string;
  familySize: number;
  diet: string;
  intolerances: string[];
  likedRecipes: Recipe[];
  dislikedRecipes: Recipe[];
}
async function queryAPI(
  endpoint: string,
  query_params: Record<string, string>
) {
  // query_params is a dictionary of key-value pairs that gets added to the URL as query parameters
  // e.g. { foo: "bar", hell: "o" } becomes "?foo=bar&hell=o"
  const paramsString = new URLSearchParams(query_params).toString();
  const url = `${HOST}/${endpoint}?${paramsString}`;
  const response = await fetch(url);
  if (!response.ok) {
    console.error(response.status, response.statusText);
  }
  return response.json();
}
export async function saveMealPlan(dateOfSunday: string) {
  //TODO: POST the meal plan with a secure key (we can make it up)
  return await queryAPI("save-meal-plan", {
    uid: getLoginCookie() || "",
    dateOfSunday: dateOfSunday,
  });
}
export async function getMealPlan(dateOfSunday: string) {
  return await queryAPI("get-meal-plan", {
    uid: getLoginCookie() || "",
    dateOfSunday: dateOfSunday,
  });
}

export async function generateMealPlan(props: mealPlanProps) {
  console.log("generating with: " + props);
  return await queryAPI("generate-meal-plan", {
    uid: getLoginCookie() || "",
    diet: props.diet.toString(),
    intolerances: props.intolerances.toString(),
    daysToPlan: props.daysToPlan.toString(),
    cuisine: props.cuisine.toString(),
    requestedServings: props.requestedServings,
    maxReadyTime: props.maxReadyTime,
  });
}
export async function getUser(): Promise<User> {
  return await queryAPI("get-user", {
    uid: getLoginCookie() || "",
  });
}

export async function getRecipe(recipeID: string) {
  return await queryAPI("get-recipe", {
    uid: getLoginCookie() || "",
    recipeID: recipeID,
  });
}
export async function addUser(props: profileProps) {
  const response = await queryAPI("add-user", {
    uid: getLoginCookie() || "",
    name: props.name,
    exp: props.exp,
    diet: props.diet.toString(),
    intolerances: props.intolerances.toString(),
  });
}
export async function addDislike(recipeID: string) {
  return await queryAPI("add-disliked-recipes", {
    uid: getLoginCookie() || "",
    recipeID: recipeID,
  });
}

export async function addLike(recipeID: string) {
  return await queryAPI("add-liked-recipes", {
    uid: getLoginCookie() || "",
    recipeID: recipeID,
  });
}

export async function getLikes() {
  return await queryAPI("get-liked-recipes", {
    uid: getLoginCookie() || "",
  });
}

export async function getDislikes() {
  return await queryAPI("get-disliked-recipes", {
    uid: getLoginCookie() || "",
  });
}
