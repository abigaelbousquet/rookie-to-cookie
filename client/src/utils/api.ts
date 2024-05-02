import { profileProps } from "../components/Login/AccountCreation";
import { User } from "../components/Pages/Profile";
import Recipe from "../components/RecipeCard/Recipe";
import { getLoginCookie, removeLoginCookie } from "./cookie";

const HOST = "http://localhost:3232";
interface mealPlanProps {
  diet: string;
  intolerances: string;
  requestedServings: string;
  daysToPlan: string;
  cuisine: string;
  maxReadyTime: string;
  mode: string;
}

async function queryAPI(
  endpoint: string,
  query_params: Record<string, string>
) {
  // query_params is a dictionary of key-value pairs that gets added to the URL as query parameters
  // e.g. { foo: "bar", hell: "o" } becomes "?foo=bar&hell=o"
  const paramsString = new URLSearchParams(query_params).toString();
  const url = `${HOST}/${endpoint}?${paramsString}`;
  console.log(url); // temporary
  const response = await fetch(url);
  if (!response.ok) {
    console.error(response.status, response.statusText);
  }
  return response.json();
}
export async function saveMealPlan(dateOfMonday: string) {
  //TODO: POST the meal plan with a secure key (we can make it up)
  return await queryAPI("save-mealplan", {
    uid: getLoginCookie() || "",
    dateOfMonday: dateOfMonday,
  });
}
export async function getMealPlan(dateOfMonday: string) {
  return await queryAPI("get-mealplan", {
    uid: getLoginCookie() || "",
    dateOfMonday: dateOfMonday,
  });
}

export async function generateMealPlan(props: mealPlanProps) {
  console.log("generating with: " + props);
  return await queryAPI("generate-mealplan", {
    uid: getLoginCookie() || "",
    diet: props.diet,
    intolerances: props.intolerances,
    daysOfWeek: props.daysToPlan,
    cuisine: props.cuisine,
    servings: props.requestedServings,
    max_time: props.maxReadyTime,
    mode: props.mode,
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
    recipeId: recipeID,
  });
}
export async function addUser(props: profileProps) {
  return await queryAPI("add-user", {
    uid: getLoginCookie() || "",
    name: props.name,
    exp: props.exp,
    fam_size: props.fam_size,
    diet: props.diet.toString(),
    intolerances: props.intolerances.toString(),
  });
}
export async function addDislike(recipeID: string) {
  return await queryAPI("add-disliked-recipe", {
    uid: getLoginCookie() || "",
    recipeId: recipeID,
  });
}

export async function addLike(recipeID: string) {
  return await queryAPI("add-liked-recipe", {
    uid: getLoginCookie() || "",
    recipeId: recipeID,
  });
}
export async function removeLike(recipeID: string) {
  return await queryAPI("clear-liked-recipes", {
    uid: getLoginCookie() || "",
    recipeId: recipeID,
  });
}
export async function removeDislike(recipeID: string) {
  return await queryAPI("clear-disliked-recipes", {
    uid: getLoginCookie() || "",
    recipeId: recipeID,
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

export async function clearUser(): Promise<User> {
  const uid = getLoginCookie();
  removeLoginCookie();
  return await queryAPI("clear-user", {
    uid: uid || "",
  });
}
