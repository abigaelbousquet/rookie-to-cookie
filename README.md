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

All of the API endpoints in Server.java have handler classes in EndpointHandlers. These handler classes all implement the Route interface to be compatible with the server.

The handlers that interact with firebase and/or recipe sources take an object implementing the StoreInterface and/or RecipeSource interfaces respectively so that the handlers can work with mocked or real sources depending on caller needs.

The RecipeData package contains all of the classes related to Recipes and sources of Recipes.

- The Datsource sub-package contains the RecipeSource interface, which both MockedRecipeSource and SpoonacularRecipeSource implement. This package also contains the SearchResult class which describes a result object which wraps the List of Recipes obtained by calling a RecipeSource's query method.
- The Recipe sub-package contains all classes describing the pieces of a whole Recipe object, which RecipeSource.query returns a List of.
- The MealPlan class describes a meal plan object, which associates Recipe objects with different days.

The RecommenderAlgorithm package contains all classes related to meal plan generation.

- The KDTree sub-package contains the classes involved in creating a KDTree of candidate Recipes for the "personalized" meal plan generation algorithm. A RecipeRecommendationKDTree contains RecipeNodes, and uses DistanceRecipePairs with the DistanceRecipePairComparator (which implements the Comparator interface) to find nearest neighbors of a given Recipe in the tree.
- The MealPlanGeneratorUtilities contains the helper classes involved in meal plan generation, and exists primarily for readability of the MealPlanGenerator class.
- The MealPlanGenerator contains the 2 different algorithms for meal plan generation, and uses classes in the two above sub-packages to do so. The Mode class outlines an ENUM for mode choice used in this class, as well as the RecipeVolumeException, which is a custom exception thrown when not enough quality recipes for a given set of constraints can be gathered from the RecipeSource associated with a MealPlanGenerator object.

The storage package contains the StorageInterface interface and FirebaseUtilities (real) and MockedFirebase (mocked) which are classes both implementing the StorageInterface to interact with Firebase and data storage.

The UserData package contains the Profile and ProfileUtilities classes, which organize data on user profiles stored in Firebase.

## Specific Choices

Discuss any specific data structures you used, why you created it, and other high level explanations.
Runtime/ space optimizations you made (if applicable).

### Front-End

**TODO:** FILL IN

### Back-End

We chose to use classes rather than records for the Recipe object and its sub-classes because Firebase was having difficulties storing records. This was less clean for parsing/deserializing Spoonacular API responses into Recipes, but ultimately was what was necessary for integration with Firebase.

We chose to use the specific types of RecipeRecommendationKDTrees (with RecipeNodes) in the algorithm generation because this data structure gave us a defined algorithm for determining k-nearest-neighbors to a particular recipe which was helpful for comparing candidate Recipes to a user's liked and/or disliked Recipes in the "personalized" meal plan generation algorithm.

We chose to gather Recipes for a user's liked and disliked Recipes from Firebase rather than additional calls to the Spoonacular API to preserve our limited available requests for meal plan generation only.

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
