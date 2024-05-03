import { test, expect } from '@playwright/test';

/**
 * Helper function to go to the page before each test
 */
test.beforeEach(async ({ page }) => {
  await page.goto('http://localhost:8000/');
  await page.getByPlaceholder('josiah_carberry@brown.edu').click();
  await page.getByPlaceholder('josiah_carberry@brown.edu').fill('test_new@brown.edu');
  await page.getByPlaceholder('ilovecooking').click();
  await page.getByPlaceholder('ilovecooking').fill('ilovecooking');
  await page.getByLabel('Login').click();
});

test('test that a saved mealplan is in the calender', async ({ page }) => {

  await page.getByRole('button', { name: 'Tu' }).click();
  await page.getByRole('button', { name: 'Generate' }).click();
  await page.getByRole('button', { name: 'View Recipe' }).click();
  const stepsText = await page.locator('.steps').innerText();
  
  await page.getByRole('button', { name: 'X' }).click();
  await page.getByRole('button', { name: 'Save' }).click();
  await page.getByRole('button', { name: '20', exact: true }).click();
  await page.getByRole('link', { name: 'Calendar' }).click();
  await page.getByRole('button', { name: '20', exact: true }).click();
  await page.getByRole('button', { name: 'View Recipe' }).click();

});