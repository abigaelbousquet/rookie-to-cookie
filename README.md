# Project Details

### Rookie to Cookie

Rookie to Cookie aims to help people who don’t currently cook as well as people who are seeking to branch out or looking to cook within constraints, by providing specialized, encouraging, and clear meal plans. The plans will take away the overwhelming nature of creating one’s own cooking-101-curricula by creating plans in a timeline that is personalized for the individual. While people can use services like Hello Fresh to simplify cooking processes, these services can be expensive, as well as wasteful, as they have shipping materials and individually packaged ingredients. This program will add a similar level of planning structure, but be more financially accessible, and environmentally friendly to ultimately make the process of meal planning as beginner chefs more efficient and encouraging. We aim to be inclusive of different user priorities by offering 2 algorithmic options for meal plan generation: minimizing food waste across a week's ingredients, or creating a plan tailored to a user's liked and disliked recipe history.

### Team Members

**Back-End Software Engineers:**  
Abigael Bousquet (abousque) - Spoonacular integration, meal plan generator algorithms  
Faizah Naqvi (ffnaqvi) - Firebase integration, API endpoints and handlers

**Front-End Software Engineers:**  
Daniela DeDona (ddedona) -  
Marissa Shaffer (mshaffe3) -

### Total Estimated Time for Project:

abousque - 23 (hours week 1) + 23 (hours week 2) + 25 (hours week 3) = 71 hours total  
ffnaqvi -  
ddedona -  
mshaffe3 - 14 (hours week 1) +18 (hours week 2) + 30 (hours week 3) = 63 hours

### Repository

Our repository can be found at: https://github.com/cs0320-s24/term-project-mshaffe3-ddedona-ffnaqvi-abousque

Please ask our team for the front-end and back-end .env files, which are not uploaded to git for security purposes.

# Design Choices

## High Level Overview of Design

Explain the relationships between classes/interfaces.

### Front-End

**TODO:** FILL IN

### Back-End

## Specific Choices

Discuss any specific data structures you used, why you created it, and other high level explanations.
Runtime/ space optimizations you made (if applicable).

### Front-End

**TODO:** FILL IN

### Back-End

# Errors/Bugs

**TODO:** FILL IN (Write reproduction steps for all the bugs in your program. If the mentor TA finds an error and knows how to reproduce it, they will be able to leave better feedback. If the mentor TA finds the bug without proper documentation, they will assume you did not test your program properly.)

# Tests

Explain the testing suites that you implemented for your program and how each test ensures that a part of the program works.

### Front-End

**TODO:** FILL IN

### Back-End

# How-To:

## Run Tests:

### Run Back-End Tests

In a bash terminal:

1. cd server
2. mvn package OR mvn test

### Run Front-End Tests

(Start server) In a terminal:

1. cd server
2. mvn package
3. ./run

In a terminal:

1. cd client
2. npm install
3. npx playwright install (!!!)
4. npm test

## Build & Run (For User-Interaction):

### Start Server

In a bash terminal:

1. cd server
2. mvn package
3. ./run

### Start Client

In a terminal:

1. cd client
2. npm install
3. npm start

Then, go to http:/localhost:8000 in a browser window to use Rookie to Cookie!

**What can you do with Rookie to Cookie?**

- Create an account by entering a new email and password, then set up your profile in the popup after hitting the "LOGIN" button
- Check out your profile in the profile tab (find liked and disliked recipes easily here!)
- Edit your profile in the profile tab with the "Update Account" button
- Create a meal plan by going to the Home page, entering criteria, and clicking "Generate" (be patient, it may take a moment to load!)
- Regenerate your meal plan as many times as you would like by clicking "Generate" repeatedly
- Save a meal plan to your account by clicking the "Save" button at the bottom of the Home page after generation and following directions there
- Look at your saved meal plans by heading to the Calendar tab and clicking on the orange tile next to the week of interest
- Like or dislike a recipe once you've cooked it and tried it by finding it in the Calendar tab and then toggling the "heart" button to a thumbs up or thumbs down in the top right corner of the recipe card
- Sign out at any time by clicking the "Sign Out" button at the bottom of any page
- Log back in by entering the same email and password as the account was created with into the Login screen to see all of your protected account data
