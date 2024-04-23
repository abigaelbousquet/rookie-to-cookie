import Cookies from "js-cookie";

export function addLoginCookie(uid: string): void {
  // TODO: fill out!
  Cookies.set("uid", uid);
}

export function removeLoginCookie(): void {
  // TODO: fill out!
  Cookies.remove("uid");
}

export function getLoginCookie(): string | undefined {
  // TODO: fill out!
  return Cookies.get("uid");
}
