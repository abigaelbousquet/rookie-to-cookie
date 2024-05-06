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

test("test the calender contains a previously generated mealplan", async ({
  page,
}) => {
  await page.getByRole("link", { name: "Calendar" }).click();
  await page.getByRole("button", { name: "21", exact: true }).click();
  await page.locator(".popup-plan-content").click();
  await expect(page.locator("#root")).toContainText(
    "Rookie To CookieHomeCalendarAboutProfileCooking HistoryClick on a week (orange tile) below to view your meal plan saved to that week. You can like and unlike recipes from this page!«‹May 2024›»1819202122MonTueWedThuFriSatSun29301234567891011121314151617181920212223242526272829303112Your Saved Meal PlanMondayTuesdayWednesdayThursdayFridaySaturdaySundayXSign Out"
  );
  await page.locator(".popup-plan-overlay").click();
});

test("test that viewing a recipe works correctly", async ({ page }) => {
  await page.getByRole("link", { name: "Calendar" }).click();
  await page.getByRole("button", { name: "21", exact: true }).click();
  await page.locator("path").click();
  await page.getByRole("button", { name: "View Recipe" }).click();
  await expect(page.locator("#root")).toContainText(
    "Rookie To CookieHomeCalendarAboutProfileCooking HistoryClick on a week (orange tile) below to view your meal plan saved to that week. You can like and unlike recipes from this page!«‹May 2024›»1819202122MonTueWedThuFriSatSun29301234567891011121314151617181920212223242526272829303112Your Saved Meal PlanMondayTuesdayWednesdayThursdayFridaySaturdaySundayNorth African Chickpea Soup (Leblebi)North African Chickpea Soup (Leblebi)XSource: foodandspice.comCuisine: AfricanTime: 45Servings: 6Ingredients: dried dried chickpeas (3 cups)olive oil (3 Tbsps)minced garlic (2 cloves)smoked smoked paprika (0.5 teaspoons)ground cumin (0.5 teaspoons)chili powder (0.5 teaspoons)dried oregano (0.5 teaspoons)vegetable stock (2 cups)to taste sea salt (1 teaspoon)fresh pepper (1 serving)fresh lemon juice (1 Tbsp)with a little water harissa (1 teaspoon)red roasted red peppers (1 serving)pitted kalamata olives (1 handful)cut into strips sun dried tomatoes (2)sliced green onions (2)fresh fresh parsley (1 serving)olive oil (1 serving)black pepper (1 serving)1. Rinse the chickpeas and soak in several inches of water for 8 hours or overnight.2. Drain and rinse, then transfer to a medium saucepan. Cover with fresh water and bring to a boil. Reduce the heat to medium-low, cover, and simmer until the chickpeas are tender, about 1 hour.3. Drain and set aside.4. Heat the olive oil in the same saucepan over medium heat.5. Add the garlic and saut for 1 minute.6. Add the chickpeas, paprika, cumin, chili powder and oregano. Stir for about 1 minute and then pour in the vegetable stock or water and stir in the salt and pepper. Bring to a boil, reduce the heat to medium, and simmer, covered, for about 20 minutes until the chickpeas are buttery soft. Stir in the lemon juice.7. To serve, scoop some of the chickpeas with their broth into serving bowls, swirl in some harrisa, top with some roasted red peppers, a few olives, sun-dried tomatoes, green onions or fresh herbs, and drizzle in some robust olive oil. Crack some black pepper over the bowls to finish.8. Serve with crusty bread to soak up the liquid, or serve cubes of the bread in bowls and scoop the brothy chickpeas overtop if desired, and proceed with adding toppings of choice.Cuisine: AfricanTime: 45Instructions: ...View RecipeXSign Out"
  );
});

test("test alert appears on weeks already saved for", async ({ page }) => {
  await page.getByRole("button", { name: "Tu" }).click();
  await page.getByRole("button", { name: "Generate" }).click();
  await page.getByRole("button", { name: "Save" }).click();
  // await page.locator(".popup-save-overlay").click();
  // await page.getByRole("button", { name: "21", exact: true }).click();

  await page.click("#root > div > div.App > div > div.save-data-button-container > div.popup-save-overlay > div.popup-save-content > div > div.react-calendar small_cal > div.react-calendar__viewContainer > div > div > div.react-calendar__month-view__weekNumbers > button:nth-child(4)");
  page.on("dialog", async (dialog) => {
    // const dialog = await page.waitForEvent("dialog", { timeout: 10000 });
    // // Check the alert message
    expect(dialog.type()).toBe("alert");
    expect(dialog.message()).toBe("Unable to save. Week already planned for");
    // Dismiss the alert
    await dialog.accept();
  });
});

test("test alert appears if no button selected for generate", async ({
  page,
}) => {
  await page.getByRole("button", { name: "Generate" }).click();
  page.on("dialog", async (dialog) => {
    // Check the alert message
    expect(dialog.type()).toBe("alert");
    expect(dialog.message()).toBe(
      "You must select at least one day of the week to generate a recipe for."
    );
    // Dismiss the alert
    await dialog.accept();
  });
});
