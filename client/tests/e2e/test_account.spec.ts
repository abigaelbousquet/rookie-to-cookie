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
  await page.getByLabel("integer-input").fill("6");
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
  await page
    .locator("div")
    .filter({
      hasText:
        /^Faizah TestNovice ChefDiet:Intolerances:Cooking for 1Update Account$/,
    })
    .locator("div")
    .first()
    .click({
      button: "right",
    });
  await page.getByRole("heading", { name: "Rookie To Cookie" }).click();
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
  await expect(page.locator("#root")).toContainText(
    "Faizah TestNovice ChefDiet:Intolerances:Cooking for 1Update Account"
  );
});

test("test a liked recipe is in the liked recipe list on a profile", async ({
  page,
}) => {
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  const newEmailNumber: number = Math.random() * 512;
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill(`test_new_user_${newEmailNumber}@brown.edu`);
  await page.getByPlaceholder("ilovecooking").click();
  await page.getByPlaceholder("ilovecooking").fill("ilovecooking");
  await page.getByLabel("Login").click();
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
  await page.getByLabel("integer-input").fill("6");
  await page.getByRole("button", { name: "Create Account" }).click();
  await page.getByRole("button", { name: "Tu" }).click();
  await page.getByRole("button", { name: "Generate" }).click();
  await page.getByRole("button", { name: "View Recipe" }).click();
  const title = await page.getByLabel("recipe-title").innerText();
  await page.getByRole("button", { name: "X" }).click();
  await page.getByRole("button", { name: "Save" }).click();
  await page.getByRole("button", { name: "20", exact: true }).click();
  await page.getByLabel("like-button-container").click();
  //give it time to load
  await page.getByRole("link", { name: "About" }).click();
  await page.getByRole("link", { name: "Profile" }).click();
  await page
    .getByLabel("liked-recipes")
    .getByRole("button", { name: "View Recipe" })
    .click();
  expect(await page.getByLabel("recipe-title").innerText()).toEqual(title);
});

test("test creating and updating an account", async ({ page }) => {
  const newEmailNumber: number = Math.random() * 512;
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill(`marissa_test_${newEmailNumber}@brown.edu`);
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
  await page.getByLabel("integer-input").click();
  await page.getByLabel("integer-input").fill("6");
  await page.getByRole("button", { name: "Create Account" }).click();
  await page.getByRole("link", { name: "Profile" }).click();
  await expect(page.locator("#root")).toContainText(
    "Testing testBeginner ChefDiet:PescetarianIntolerances:NutCooking for 6Update Account"
  );
  await page
    .locator("div")
    .filter({ hasText: /^Liked Recipes:$/ })
    .click();
  await page.getByLabel("update-account-button").click();
  await expect(page.locator("#account-header")).toContainText("Update Account");
  await page.locator("span:nth-child(3)").first().click();
  await page.locator(".css-1hwfws3").first().click();
  await page.getByText("Vegetarian", { exact: true }).click();
  await page
    .locator("div")
    .filter({ hasText: /^Select\.\.\.$/ })
    .nth(3)
    .click();
  await page.getByText("Shellfish", { exact: true }).click();
  await page.getByLabel("integer-input").click();
  await page.getByLabel("integer-input").fill("4");
  await page.getByLabel("update-button").click();
  await page.getByRole("link", { name: "About" }).click();
  await page.getByRole("link", { name: "Profile" }).click();
  await expect(page.locator("#root")).toContainText(
    "Testing testExperienced ChefDiet:VegetarianIntolerances:ShellfishCooking for 4Update Account"
  );
});
