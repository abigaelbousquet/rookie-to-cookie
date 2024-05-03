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

test('test the calender contains a previously generated mealplan', async ({ page }) => {

  await page.getByRole('link', { name: 'Calendar' }).click();
  await page.getByRole('button', { name: '21', exact: true }).click();
  await page.locator('.popup-plan-content').click();
  await expect(page.locator('#root')).toContainText('MondayTuesdayWednesdayThursdayFridaySaturdaySundayNorth African Chickpea Soup (Leblebi)Cuisine: AfricanTime: 45Instructions: ...View RecipeX');
  await page.locator('.popup-plan-overlay').click();
});


test('test that viewing a recipe works correctly', async ({ page }) => {

  await page.getByRole('link', { name: 'Calendar' }).click();
  await page.getByRole('button', { name: '21', exact: true }).click();
  await page.locator('path').click();
  await page.getByRole('button', { name: 'View Recipe' }).click();
  await expect(page.locator('#root')).toContainText('North African Chickpea Soup (Leblebi)XSource: foodandspice.comCuisine: AfricanTime: 45Ingredients: dried dried chickpeas, olive oil, minced garlic, smoked smoked paprika, ground cumin, chili powder, dried oregano, vegetable stock, to taste sea salt, fresh pepper, fresh lemon juice, with a little water harissa, red roasted red peppers, pitted kalamata olives, cut into strips sun dried tomatoes, sliced green onions, fresh fresh parsley, olive oil, black pepper1. Rinse the chickpeas and soak in several inches of water for 8 hours or overnight.2. Drain and rinse, then transfer to a medium saucepan. Cover with fresh water and bring to a boil. Reduce the heat to medium-low, cover, and simmer until the chickpeas are tender, about 1 hour.3. Drain and set aside.4. Heat the olive oil in the same saucepan over medium heat.5. Add the garlic and saut for 1 minute.6. Add the chickpeas, paprika, cumin, chili powder and oregano. Stir for about 1 minute and then pour in the vegetable stock or water and stir in the salt and pepper. Bring to a boil, reduce the heat to medium, and simmer, covered, for about 20 minutes until the chickpeas are buttery soft. Stir in the lemon juice.7. To serve, scoop some of the chickpeas with their broth into serving bowls, swirl in some harrisa, top with some roasted red peppers, a few olives, sun-dried tomatoes, green onions or fresh herbs, and drizzle in some robust olive oil. Crack some black pepper over the bowls to finish.8. Serve with crusty bread to soak up the liquid, or serve cubes of the bread in bowls and scoop the brothy chickpeas overtop if desired, and proceed with adding toppings of choice.');
  await page.locator('div').filter({ hasText: /^North African Chickpea Soup \(Leblebi\)X$/ }).getByRole('button').click();
});
