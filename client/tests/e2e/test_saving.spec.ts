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

test('test a generated mealplan displays in the calender', async ({ page }) => {
  await page.getByRole('link', { name: 'Calendar' }).click();
  await page.getByRole('button', { name: '›' }).click();
  await page.getByRole('button', { name: '23', exact: true }).click();
  await expect(page.locator('#root')).toContainText('Your Saved Meal PlanMondayTuesdayWednesdayThursdayFridaySaturdaySunday10+ With Cabbage: Egg Roll in a BowlCuisine: ChineseTime: 25Instructions: ...View RecipeEgg Roll SoupCuisine: ChineseTime: 45Instructions: ...View RecipeX');
});