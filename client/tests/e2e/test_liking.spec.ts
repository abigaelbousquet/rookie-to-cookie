import { test, expect } from "@playwright/test";

/**
 * Helper function to go to the page before each test
 */
test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill("faizah_test@brown.edu");
  await page.getByPlaceholder("ilovecooking").click();
  await page.getByPlaceholder("ilovecooking").fill("ilovecooking");
  await page.getByLabel("Login").click();
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

//TODO: save a meal plan, like the recipe, and check that it shows up on profile/calendar page
