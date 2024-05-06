import { test, expect } from "@playwright/test";

/**
 * Helper function to go to the page before each test
 */
test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
});

test("test that signin with no credentials creates an alert", async ({
  page,
}) => {
  await page.getByLabel("Login").click();

  page.on("dialog", async (dialog) => {
    expect(dialog.type()).toBe("alert");
    expect(dialog.message()).toBe("Please enter your email and password");
    // Dismiss the alert
    await dialog.accept();
  });
});

test("test that a new account with a short password create an alert", async ({
  page,
}) => {
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  const newEmailNumber: number = Math.random() * 512;
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill(`test_new_user_${newEmailNumber}@brown.edu`);
  await page.getByPlaceholder("ilovecooking").click();
  await page.getByPlaceholder("ilovecooking").fill("123");
  await page.getByLabel("Login").click();

  page.on("dialog", async (dialog) => {
    expect(dialog.type()).toBe("alert");
    expect(dialog.message()).toBe(
      "Password should be at least 6 characters long"
    );
    // Dismiss the alert
    await dialog.accept();
  });
});

test("test that an account with an incorrect password creates an alert", async ({
  page,
}) => {
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill("faizah_test@brown.edu");
  await page.getByPlaceholder("ilovecooking").click();
  await page.getByPlaceholder("ilovecooking").fill("incorrectPassword");
  await page.getByLabel("Login").click();
  page.on("dialog", async (dialog) => {
    expect(dialog.type()).toBe("alert");
    expect(dialog.message()).toBe(
      "Incorrect password for email given. Please try again."
    );
    // Dismiss the alert
    await dialog.accept();
  });
});

test("test that a new account with no name creates an alert", async ({
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
  await page.getByRole("button", { name: "Create Account" }).click();

  page.on("dialog", async (dialog) => {
    expect(dialog.type()).toBe("alert");
    expect(dialog.message()).toBe(
      "Please enter name, experience, and family size."
    );
    // Dismiss the alert
    await dialog.accept();
  });
});

test("test that an edit to an account without changes results in an alert", async ({
  page,
}) => {
  await page.getByPlaceholder("josiah_carberry@brown.edu").click();
  await page
    .getByPlaceholder("josiah_carberry@brown.edu")
    .fill("faizah_test@brown.edu");
  await page.getByPlaceholder("ilovecooking").click();
  await page.getByPlaceholder("ilovecooking").fill("ilovecooking");
  await page.getByLabel("Login").click();

  await page.getByRole("link", { name: "Profile" }).click();
  await page.getByLabel("update-account-button").click();
  await page.getByLabel("update-button").click();
  // Wait for the alert
  try {
    const dialog = await page.waitForEvent("dialog", { timeout: 10000 });
    // Check the alert message
    expect(dialog.type()).toBe("alert");
    expect(dialog.message()).toBe(
      "Successfully updated account. Sign out then in again to see changes."
    );

    // Dismiss the alert
    await dialog.dismiss();
  } catch (error) {
    console.error("Error waiting for dialog:", error);
    // Handle timeout error
  }
});
