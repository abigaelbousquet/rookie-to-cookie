import { test, expect } from "@playwright/test";
import { PlaywrightTestConfig } from "@playwright/test";

const config: PlaywrightTestConfig = {
  use: {
    // Set the browser to launch with headless mode disabled
    headless: false,
    // Add any other configuration options here
  },
};

export default config;

/**
 * Helper function to go to the page before each test
 */
test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
});

test("test that a new account wiht no name creates an alert", async ({
  page,
}) => {
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  const newEmailNumber: number = Math.random() * 512;
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill(`test_new_user_${newEmailNumber}@brown.edu`);
  await page.getByPlaceholder("ilovecooking").click();
  await page.getByPlaceholder("ilovecooking").fill("ilovecooking");
  await page.getByLabel("Login").click();
  await page.getByLabel("Login").click();
  await page.getByRole("button", { name: "Create Account" }).click();

  // Wait for the alert
  page.on("dialog", async (dialog) => {
    // Check the alert message
    expect(dialog.type()).toBe("alert");
    expect(dialog.message()).toBe(
      "Please enter name, experience, and family size."
    );
    // Dismiss the alert
    await dialog.accept();
  });
});
