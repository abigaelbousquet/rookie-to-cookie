import Cookies from "js-cookie";

/**
 * Cookie handling done here
 * @param uid unique user id
 */
export function addLoginCookie(uid: string): void {
  Cookies.set("uid", uid);
}

export function removeLoginCookie(): void {
  Cookies.remove("uid");
}

export function getLoginCookie(): string | undefined {
  return Cookies.get("uid");
}
