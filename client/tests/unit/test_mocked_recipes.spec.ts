import { test, expect } from "@playwright/test";

/**
 * Helper function to go to the page before each test;
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

test("weekview display test", async ({ page }) => {});
