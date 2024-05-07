import { test, expect } from "@playwright/test";

/**
 * Helper function to go to the page before each test
 */
test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
  const newEmailNumber: number = Math.random() * 324;
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill(`test_new_user_${newEmailNumber}@brown.edu`);
  await page.getByPlaceholder("ilovecooking").fill("ilovecooking");
  await page.getByLabel("Login").click();

  //account creation
  await page.getByPlaceholder("Nim Telson").fill(`test_${newEmailNumber}`);
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
  await page.getByRole("link", { name: "Home" }).click();
});

test("test that a meal plan generates and saves", async ({ page }) => {
  await page.getByRole("button", { name: "Tu" }).click();
  await page.getByRole("button", { name: "Generate" }).click();
  await page.getByRole("button", { name: "View Recipe" }).click();
  const stepsText = await page.locator(".steps").innerText();
  await page.getByRole("button", { name: "X" }).click();
  await page.getByRole("button", { name: "Save" }).click();
  await page.getByRole("button", { name: "20", exact: true }).click();
  await page.getByRole("link", { name: "Calendar" }).click();
  await page.getByRole("button", { name: "20", exact: true }).click();
  await page.getByRole("button", { name: "View Recipe" }).click();
  expect(await page.locator(".steps").innerText()).toEqual(stepsText);
});
