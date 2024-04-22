import { profileProps } from "../components/Login/AccountCreation";
import { getLoginCookie } from "./cookie";

const HOST = "http://localhost:3232";

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
export async function addUser(props: profileProps) {
  return await queryAPI("add-user", {
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
  return await queryAPI("get-likes", {
    uid: getLoginCookie() || "",
  });
}

export async function getDislikes() {
  return await queryAPI("get-dislikes", {
    uid: getLoginCookie() || "",
  });
}
