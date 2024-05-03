import { test, expect } from '@playwright/test';

/**
 * Helper function to go to the page before each test
 */
test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
  await page.getByPlaceholder('josiah_carberry@brown.edu').click();
  await page.getByPlaceholder('josiah_carberry@brown.edu').fill('faizah_test@brown.edu');
  await page.getByPlaceholder('ilovecooking').click();

});

test('test creating account and mealplan', async ({ page }) => {
  await page.getByPlaceholder('ilovecooking').fill('ilovecooking');
  await page.getByLabel('Login').click();
  await page.getByRole('link', { name: 'Profile' }).click();
  await expect(page.locator('h1')).toContainText('Faizah Test');
  await expect(page.locator('#root')).toContainText('Vegan');
  await expect(page.locator('#root')).toContainText('Intolerances:Soy, Peanut');
  await page.getByRole('heading', { name: 'Cooking for' }).click();
  await expect(page.locator('h4')).toContainText('Cooking for 4');
  await page.getByRole('link', { name: 'Home' }).click();
  await page.getByRole('button', { name: 'Tu' }).click();
  await page.locator('.css-1hwfws3').first().click();
  await page.getByText('korean', { exact: true }).click();
  await page.getByText('Minimize Food Waste').click();
  await page.getByRole('spinbutton').nth(1).click();
  await page.getByRole('spinbutton').nth(1).fill('80');
  await page.getByRole('button', { name: 'Generate' }).click();
  await page.getByRole('button', { name: 'View Recipe' }).click();
  await page.getByRole('button', { name: 'X' }).click();
  await expect(page.locator('#root')).toContainText('Tuesday');
  await page.locator('.recipe-card-container').click();
  await expect(page.locator('#root')).toContainText('Korean Spicy BBQ Chicken TacosCuisine: KoreanTime: 45Instructions: ...View Recipe');
  await expect(page.locator('#root')).toContainText('Cuisine: Korean');
  await page.getByRole('link', { name: 'Calendar' }).click();
});

test('test signing out retains information', async ({ page }) => {
  await page.getByPlaceholder('ilovecooking').fill('ilovecooking');
  await page.getByLabel('Login').click();
  await page.getByRole('link', { name: 'Profile' }).click();
  await expect(page.locator('h1')).toContainText('Faizah Test');
  await page.getByLabel('Sign Out').click();
  await page.getByPlaceholder('josiah_carberry@brown.edu').click();
  await page.getByPlaceholder('josiah_carberry@brown.edu').fill('faizah_test@brown.edu');
  await page.getByPlaceholder('ilovecooking').click();
  await page.getByPlaceholder('ilovecooking').fill('ilovecooking');
  await page.getByLabel('Login').click();
  await expect(page.locator('h1')).toContainText('Faizah Test');
});


test('test a liked recipe is in the liked recipe list on a profile', async ({ page }) => {
  await page.getByPlaceholder('ilovecooking').fill('ilovecooking');
  await page.getByLabel('Login').click();
  await page.getByRole('link', { name: 'Profile' }).click();
  await page.getByText('Liked Recipes:North African').click();
  await expect(page.locator('#root')).toContainText('Liked Recipes:North African Chickpea Soup (Leblebi)Cuisine: AfricanTime: 45Instructions: ...View Recipe');
});


test('test entering wrong password show create account page', async ({ page }) => {
 
  await page.getByPlaceholder('ilovecooking').fill('wrongpassword');
  await page.getByLabel('Login').click();
  await expect(page.locator('#account-header')).toContainText('Create Account');
  await page.getByPlaceholder('Nim Telson').click();
});

test('test updating account information', async ({ page }) => {
  await page.getByRole('link', { name: 'Profile' }).click();
  await expect(page.locator('#root')).toContainText('Faizah TestMaster ChefDiet:VeganIntolerances:PeanutCooking for 3Update Account');
  await page.getByRole('button', { name: 'Update Account' }).click();
  await page.getByRole('slider').click();
  await page.locator('span:nth-child(2)').first().click();
  await page.locator('.css-1hwfws3').first().click();
  await page.getByText('Primal', { exact: true }).click();
  await page.locator('div').filter({ hasText: /^Select\.\.\.$/ }).nth(3).click();
  await page.getByText('Tree nut', { exact: true }).click();
  await page.locator('div').filter({ hasText: /^Tree nut$/ }).nth(1).click();
  await page.getByText('Egg', { exact: true }).click();
  await page.getByPlaceholder('2').click();
  await page.getByPlaceholder('2').fill('6');
  await page.getByRole('button', { name: 'Update', exact: true }).click();
  await page.getByPlaceholder('josiah_carberry@brown.edu').click();
  await page.getByPlaceholder('josiah_carberry@brown.edu').fill('faizah_test@brown.edu');
  await page.getByPlaceholder('ilovecooking').click();
  await page.getByPlaceholder('ilovecooking').fill('ilovecooking');
  await page.getByLabel('Login').click();
  await expect(page.locator('#root')).toContainText('Faizah TestBeginner ChefDiet:PrimalIntolerances:Tree nut, EggCooking for 6Update Account');
});