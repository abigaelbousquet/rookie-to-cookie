import { test, expect } from '@playwright/test';

/**
 * Helper function to go to the page before each test
 */
test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
  await page.getByPlaceholder('josiah_carberry@brown.edu').click();
  await page.getByPlaceholder('josiah_carberry@brown.edu').fill('faizah_test@brown.edu');
  await page.getByPlaceholder('ilovecooking').click();
  await page.getByPlaceholder('ilovecooking').fill('ilovecooking');
  await page.getByLabel('Login').click();
});

test('test that generation with no presets works with both food minimize and personalize algorithms', async ({ page }) => {
  await page.getByRole('button', { name: 'Tu' }).click();
  await page.getByRole('button', { name: 'Generate' }).click();
  await expect(page.locator('#root')).toContainText('Blueberry Peach Chicken SaladCuisine: UnknownTime: 10Instructions: ...View Recipe');
  await page.getByText('Prioritize User Taste').click();
  await page.getByRole('button', { name: 'W', exact: true }).click();
  await page.getByRole('button', { name: 'Generate' }).click();
  await page.locator('div').filter({ hasText: 'Welcome to Rookie to Cookie!' }).nth(3).click();
  await expect(page.locator('#root')).toContainText('Welcome to Rookie to Cookie!Our meal planning web application is designed to seamlessly integrate cooking into your daily routine. Whether you\'re a beginner starting from scratch, eager to explore new recipes, or aiming to minimize your weekly food waste, we\'ve got you covered.Get started by selecting from the following options. We\'ll consider intolerances on your profile in addition to what you enter here.Select the days of the week you would like to plan for:MTuWThFSaSuSelect the type of cuisine that you would like to see:Select option(s)Select the type of algorithm that you would like to use:Minimize Food WastePrioritize User TasteSelect any food intolerances:Select option(s)Specify the number of servings you require:Specify any ingredients to exclude:Type something and press enter...Specify max cooking time (min):GenerateMondayTuesdayWednesdayThursdayFridaySaturdaySundayIndian Sticky ChickenCuisine: IndianTime: 15Instructions: ...View RecipeGrilled Lamb Chops with LadolemonoCuisine: UnknownTime: 20Instructions: ...View Recipe Save');
  await page.locator('path').nth(4).click();
});