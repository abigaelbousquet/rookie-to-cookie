import { test, expect } from "@playwright/test";

/**
 * Helper function to go to the page before each test
 */
test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
});

test("test create a new account", async ({ page }) => {
  await page.goto("http://localhost:8000/");
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  const newEmailNumber: number = Math.random() * 512;
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill(`test_new_user_${newEmailNumber}@brown.edu`);
  await page.getByPlaceholder("ilovecooking").click();
  await page.getByPlaceholder("ilovecooking").fill("ilovecooking");
  await page.getByLabel("Login").click();
  await page.getByPlaceholder("Nim Telson").click();
  await page.getByPlaceholder("Nim Telson").fill("Testing test");
  await page.locator("span:nth-child(2)").first().click();
  await page.locator(".css-1hwfws3").first().click();
  await page.getByText("Pescetarian", { exact: true }).click();
  await page
    .locator("div")
    .filter({ hasText: /^Select\.\.\.$/ })
    .nth(3)
    .click();
  await page.getByText("Nut", { exact: true }).click();
  await page.getByPlaceholder("1").click();
  await page.getByPlaceholder("1").fill("6");
  await page.getByRole("button", { name: "Create Account" }).click();
  await page.getByRole("link", { name: "Profile" }).click();
  await expect(page.locator("#root")).toContainText(
    "Testing testBeginner ChefDiet:PescetarianIntolerances:NutCooking for 6Update Account"
  );
  await page
    .locator("div")
    .filter({ hasText: /^Liked Recipes:$/ })
    .click();
  await expect(page.locator("#root")).toContainText(
    "Liked Recipes:Disliked Recipes:"
  );
});

test("test signing out retains information", async ({ page }) => {
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill("faizah_test@brown.edu");
  await page.getByPlaceholder("ilovecooking").click();
  await page.getByPlaceholder("ilovecooking").fill("ilovecooking");
  await page.getByLabel("Login").click();
  await page.getByRole("link", { name: "Profile" }).click();
  await expect(page.locator("h1")).toContainText("Faizah Test");
  await page.getByLabel("Sign Out").click();
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill("faizah_test@brown.edu");
  await page.getByPlaceholder("ilovecooking").click();
  await page.getByPlaceholder("ilovecooking").fill("ilovecooking");
  await page.getByLabel("Login").click();
  await expect(page.locator("h1")).toContainText("Faizah Test");
});

test("test a liked recipe is in the liked recipe list on a profile", async ({
  page,
}) => {
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill("faizah_test@brown.edu");
  await page.getByPlaceholder("ilovecooking").click();
  await page.getByPlaceholder("ilovecooking").fill("ilovecooking");
  await page.getByLabel("Login").click();
  await page.getByRole("link", { name: "Profile" }).click();
  await expect(page.locator("#root")).toContainText(
    "Rookie To CookieHomeCalendarAboutProfileFaizah TestBeginner ChefDiet:PrimalIntolerances:Tree nut, EggCooking for 6Update AccountLiked Recipes:Disliked Recipes:North African Chickpea Soup (Leblebi)Cuisine: AfricanTime: 45Instructions: ...View RecipeEasy Vegetarian PhoCuisine: VietnameseTime: 45Instructions: ...View RecipeSign Out"
  );
});
//TODO: fix!!
test("test creating and updating an account", async ({ page }) => {
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill("marissa_test@brown.edu");
  await page.getByPlaceholder("ilovecooking").click();
  await page.getByPlaceholder("ilovecooking").fill("ilovecooking");
  await page.getByLabel("Login").click();
  await page.getByPlaceholder("Nim Telson").click();
  await page.getByPlaceholder("Nim Telson").fill("Testing test");
  await page.locator("span:nth-child(2)").first().click();
  await page.locator(".css-1hwfws3").first().click();
  await page.getByText("Pescetarian", { exact: true }).click();
  await page
    .locator("div")
    .filter({ hasText: /^Select\.\.\.$/ })
    .nth(3)
    .click();
  await page.getByText("Nut", { exact: true }).click();
  await page.getByPlaceholder("1").click();
  await page.getByPlaceholder("1").fill("6");
  await page.getByRole("button", { name: "Create Account" }).click();
  await page.getByRole("link", { name: "Profile" }).click();
  await expect(page.locator("#root")).toContainText(
    "Testing testBeginner ChefDiet:PescetarianIntolerances:NutCooking for 6Update Account"
  );
  await page
    .locator("div")
    .filter({ hasText: /^Liked Recipes:$/ })
    .click();
  await page.getByRole("button", { name: "Update Account" }).click();
  await expect(page.locator("#account-header")).toContainText("Update Account");
  await page.locator("span:nth-child(3)").first().click();
});
