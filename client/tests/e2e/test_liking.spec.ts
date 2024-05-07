import { test, expect } from "@playwright/test";

/**
 * Helper function to create an account before each test to ensure that
 * the liking interaction works properly without interference from
 * other actions
 */
test.beforeEach(async ({ page }) => {
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
});

test("test alert on liking recipe before saving", async ({ page }) => {
  await page.getByRole("button", { name: "Tu" }).click();
  await page.getByRole("button", { name: "Generate" }).click();
  await page.getByLabel("like-button-container").click();
  page.on("dialog", async (dialog) => {
    expect(dialog.type()).toBe("alert");
    expect(dialog.message()).toBe(
      "You must save this meal plan below in order to add a recipe to your likes!"
    );
    // Dismiss the alert
    await dialog.accept();
  });
});

//save a meal plan, like the recipe, and check that it shows up on profile/calendar page
test("test that liking a saved meal plan is displayed on the profile", async ({
  page,
}) => {
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
