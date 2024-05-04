import { profileProps } from "../components/Login/AccountCreation";
import { User } from "../components/Pages/Profile";
import { getLoginCookie, removeLoginCookie } from "./cookie";

/**
 * This file is responsible for making queries to the back end
 */
const HOST = "http://localhost:3232"; //Server
export interface mealPlanProps {
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

/**
 * Saves meal plans to firebase
 * @param dateOfMonday first day of the week which we want to save to
 * @returns success/failure response
 */
export async function saveMealPlan(dateOfMonday: string) {
  return await queryAPI("save-mealplan", {
    uid: getLoginCookie() || "",
    dateOfMonday: dateOfMonday,
  });
}

/**
 * This function is used in the calendar view to view saved mealplans
 * @param dateOfMonday first day of the week which we want to get meal plan of
 * @returns mealplan json
 */
export async function getMealPlan(dateOfMonday: string) {
  return await queryAPI("get-mealplan", {
    uid: getLoginCookie() || "",
    dateOfMonday: dateOfMonday,
  });
}

/**
 * This function is used to tell the back end to query spoonacular, perform a recommendation algorithm and return recipes
 * @param props generation criteria
 * @returns mealplan json
 */
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

/**
 * This method gets profile data from firestore
 * @returns User data json
 */
export async function getUser(): Promise<User> {
  return await queryAPI("get-user", {
    uid: getLoginCookie() || "",
  });
}

/**
 * Adds a user profile to firestore
 * @param props user profile data
 * @returns success/failure response
 */
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

/**
 * Adds recipe to dislikes in firestore
 * @param recipeID recipe to add dislike
 * @returns success/failure response
 */
export async function addDislike(recipeID: string) {
  return await queryAPI("add-disliked-recipe", {
    uid: getLoginCookie() || "",
    recipeId: recipeID,
  });
}

/**
 * Adds recipe to likes in firestore
 * @param recipeID recipe to add like
 * @returns success/failure response
 */
export async function addLike(recipeID: string) {
  return await queryAPI("add-liked-recipe", {
    uid: getLoginCookie() || "",
    recipeId: recipeID,
  });
}

/**
 * Removes recipe from likes in firestore
 * @param recipeID recipe to remove like
 * @returns success/failure response
 */
export async function removeLike(recipeID: string) {
  return await queryAPI("clear-liked-recipes", {
    uid: getLoginCookie() || "",
    recipeId: recipeID,
  });
}

/**
 * Removes recipe from dislikes in firestore
 * @param recipeID recipe to remove dislike
 * @returns success/failure response
 */
export async function removeDislike(recipeID: string) {
  return await queryAPI("clear-disliked-recipes", {
    uid: getLoginCookie() || "",
    recipeId: recipeID,
  });
}

/**
 * Queries backend for firestored list of liked recipes
 * @returns liked recipes list json
 */
export async function getLikes() {
  return await queryAPI("get-liked-recipes", {
    uid: getLoginCookie() || "",
  });
}

/**
 * Queries backend for firestored list of disliked recipes
 * @returns disliked recipes list json
 */
export async function getDislikes() {
  return await queryAPI("get-disliked-recipes", {
    uid: getLoginCookie() || "",
  });
}
