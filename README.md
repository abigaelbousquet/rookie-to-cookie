# Project Details

### Rookie to Cookie

Rookie to Cookie aims to help people who don’t currently cook as well as people who are seeking to branch out or looking to cook within constraints, by providing specialized, encouraging, and clear meal plans. The plans will take away the overwhelming nature of creating one’s own cooking-101-curricula by creating plans in a timeline that is personalized for the individual. While people can use services like Hello Fresh to simplify cooking processes, these services can be expensive, as well as wasteful, as they have shipping materials and individually packaged ingredients. This program will add a similar level of planning structure, but be more financially accessible, and environmentally friendly to ultimately make the process of meal planning as beginner chefs more efficient and encouraging. We aim to be inclusive of different user priorities by offering 2 algorithmic options for meal plan generation: minimizing food waste across a week's ingredients, or creating a plan tailored to a user's liked and disliked recipe history.

### Team Members

**Back-End Software Engineers:**  
Abigael Bousquet (abousque) - Spoonacular integration, meal plan generator algorithm  
Faizah Naqvi (ffnaqvi) - Firebase integration, API endpoints and handlers

**Front-End Software Engineers:**  
Daniela DeDona (ddedona) - Home page, recipecard and mealplan displays, rendering
Marissa Shaffer (mshaffe3) - Backend querying, Authentication, Account creation, Profile page

### Total Estimated Time for Project:

- abousque - 23 (hours week 1) + 23 (hours week 2) + 25 (hours week 3) = 71 hours total
- ffnaqvi - 13 (hours week 1) + 19 (hours week 2) + 13 (hours week 3) = 45 hours total
- ddedona - 20 (hours week 1) + 22 (hours week 2) + 10 (hours week 3) = 52 hours total
- mshaffe3 - 14 (hours week 1) + 18 (hours week 2) + 25 (hours week 3) = 58 hours

### Repository

Our repository can be found at: https://github.com/cs0320-s24/term-project-mshaffe3-ddedona-ffnaqvi-abousque

Please ask our team for the front-end and back-end .env files, which are not uploaded to git for security purposes.

# Design Choices

## High Level Overview of Design

Explain the relationships between classes/interfaces.

### Front-End

The components package contains all of the major components of the webapp, from the individual SelectionTypes to the top-level Pages, which organize the elements.

- Login uses firebase to create accounts, take in user input, and moderates the auth state to show the login, create account, or master components. Account update is also contained in this package, but found by the user in the profile page.
- MealPlan contains the front end parser of meal plans from the back end, the weekview display, the save and view past meal plan popups.
- Pages contains the Profile, Home, Calendar, and About pages, as well as a Master component, which houses them all, and the navbar, used to move between them.
- RecipeCard has the front end recipe parser, the infoview popup with recipe instructions and ingredients, our recipe interface, recipe card display found on profile likes and meal plans, and the three-state like button used to keep track of user preferences.
- SelectionTypes has the more complicated selection options and input types.

The utils package contains the cookie handling and functions which perform queries to the back end for both spoonacular and firebase data.

The styles package organizes all the css files for various components.

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

- Accessibility: We chose to use checkboxes on the recipes for visual cues and attention tracking, requested by one of our user stories, as well as aria labels and flexboxes in order to make our application as accessible as possible, as well as selecting a color palette which retained good contrast in grayscale.
- Security:
  We chose to require that a user was logged in before accessing our application for a few reasons. First, it protects us from having bots spam our generator and cost us money. Secondly, it allows us to track the login flow more efficiently, rather than having to decide exactly which features are accessible to a non-logged in user, such as having to restrict access to certain algorithms, liking features, history, saving, and profiles. We also just believed it would enhance and make the user experience less confusing to be able to use the webapp to its full extent (requiring a login). However, we did not require a google account, as we have in previous sprints; rather, we let users use their own email so as not to specifically be forcing users to use a certain provider that collects data aside from us.

### Back-End

We chose to use classes rather than records for the Recipe object and its sub-classes because Firebase was having difficulties storing records. This was less clean for parsing/deserializing Spoonacular API responses into Recipes, but ultimately was what was necessary for integration with Firebase.

We chose to use the specific types of RecipeRecommendationKDTrees (with RecipeNodes) in the algorithm generation because this data structure gave us a defined algorithm for determining k-nearest-neighbors to a particular recipe which was helpful for comparing candidate Recipes to a user's liked and/or disliked Recipes in the "personalized" meal plan generation algorithm.

We chose to gather Recipes for a user's liked and disliked Recipes from Firebase rather than additional calls to the Spoonacular API to preserve our limited available requests for meal plan generation only. This led to saving more data in firebase which wasn't very space-efficient, but was more cost-efficient.

# Errors/Bugs

Recipe information (ingredient strings and instruction steps) are sometimes a little wonky, for example:

- Including seemingly multiple or all steps in a single "step 1".
- Funky meta data for an ingredient (e.g., "() cheddar cheese (4 slices)")

These are oddities in the data we get from Spoonacular via their endpoint. We chose to use Spoonacular as a source
of recipes, so these are not things we can fix ourselves persay. However, since interpretability is very important
for the experience of new chefs, we would consider trying to find a more reliable recipe source if we were to
deploy Rookie to Cookie.

# Tests

Explain the testing suites that you implemented for your program and how each test ensures that a part of the program works.

### Front-End

The bulk of front end testing concerned end to end testing, as this was a full stack application, which was testable visually in the mocking stage and the functions unmocked were mostly unable to be unit tested. However, we did use Vitest in order to test our two parsing functions: mealplan parsing and recipe parsing, as these are the most complex queries received from the back end.

e2e:

- test_accout tests functionality for firebase profile data and login flow
- test_generation tests functionality of e2e mealplan generation
- test_saving and test_calendar test the saving process and the calendar displays

unit:

- mealplan parsing tests deserialization of the meal plan JSON from the backend, used in generation and displaying in calendar
- recipe parsing tests the deserialization of the recipe JSONs from the backend, used in likes/dislikes, generation, and calendar!

### Back-End

Please see javadocs for details on what each test specifically asserts.

In brief summary:

- TestDeserialize contains tests checking that Recipe and Mealplan deserialization from json to java Object works as expected.
- TestRecipe unit tests the scaling of a recipe to new numbers of servings.
- TestTDTree contains tests checking the creation of and k-nearest-neighbors searching of RecipeRecommendationKDTrees.
- TestRecipeDatasource tests mocked and real RecipeSources for functionality and expected query results.
- TestRecommenderAlgorithms contains tests verifying expected results of the two meal plan generation algorithms: minimizeFoodWaste and personalized.
- TestGeneratorUtility unit tests the helper methods involved in meal plan generation.
- TestMealPlanGenerator tests the whole of generating a meal plan with MealPlanGenerator objects.
- TestEndpoint tests that all endpoints can be called in our Server.
- IntegrationTests tests the full server pipeline (e.g., generating and saving meal plans).
- FullTestMealPlanGenerator contains fuzz testing of our generate-mealplan endpoint with random inputs, asserting no unexpected exceptions are encountered.

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

In a terminal: e2e

1. cd client
2. npm install
3. npx playwright install (!!!)
4. npm run test:e2e

In a terminal: unit

1. cd client
2. npm install
3. npm i vitest
4. npm run test:unit

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

**Outside Source Credit**

- dropdown selection: https://www.digitalocean.com/community/tutorials/react-react-select
- radio buttons: https://medium.com/@christinaroise/how-to-create-a-reusable-custom-radiobutton-in-react-with-typescript-3ae7fc704e09
- react creatable objects: https://react-select.com/creatable
- like and dislike buttons: https://medium.com/@mpcodes/how-to-create-an-interactive-like-and-dislike-button-component-in-react-c023f1417174
- calendar https://www.npmjs.com/package/react-calendar
- Slider https://www.npmjs.com/package/rc-slider
