import "../../styles/Home.css";
import React, { useState } from "react";
import Select from "react-select";
import MultiSelectInput from "../SelectionTypes/MultiSelectInput";
import IntegerInput from "../SelectionTypes/IntegerInput";
import WeekView from "../MealPlan/WeekView";
import DaysOfTheWeekButtons from "../SelectionTypes/DaysOfTheWeekButtons";
import { generateMealPlan, getUser } from "../../utils/api";
import { cuisineOptions, intoleranceOptions } from "../../data/Spoonacular";
import MealPlanSave from "../MealPlan/MealPlanSave";
import { parseMealPlanWithoutLikes } from "../../RecipeUtils/ParseMealPlan";
import { emptyMealPlan } from "../../data/MockedRecipeHistory";

/**
 * Home page with generation prompts
 * @returns Home/Generate page
 */
const Home: React.FC = () => {
  // State to keep track of selected buttons
  const [selectedButtons, setSelectedButtons] = useState<string[]>([]);
  const [selectedOptionsCuisine, setSelectedOptionsCuisine] = useState<
    { label: string; value: string }[]
  >([]);
  const [selectedOptionsIntolerance, setSelectedOptionsIntolerance] = useState<
    { label: string; value: string }[]
  >([]);
  // State to hold the selected option
  const [selectedAlg, setSelectedAlg] = useState("minimize");
  // State to control the visibility of the popup
  const [showSavePopup, setSavePopup] = useState<boolean>(false);
  const [numberOfPeople, setNumberOfPeople] = useState<number>(1);
  const [maxTime, setMaxTime] = useState<number>(20);
  const [excludedIngredients, setExcludedIngredients] = useState<string[]>([]);
  const [intols, setIntols] = useState<string[]>([]);
  const [currMealPlan, setCurrMealPlan] = useState(emptyMealPlan);
  const [saved, setSaved] = useState<boolean>(false);

  /**
   * Converts the selectedButtons state to a comma-separated string of
   * days deleted in unabbreviated, lowercase form.
   * @returns the conversion of selectedButtons to a corresponding CSV string
   */
  const convertDaysOfWeekToCSVString = () => {
    var csv: string = "";
    selectedButtons.forEach(function (value) {
      var dayUnabbrev: string = "";
      if (value === "M") {
        dayUnabbrev = "monday";
      } else if (value === "Tu") {
        dayUnabbrev = "tuesday";
      } else if (value === "W") {
        dayUnabbrev = "wednesday";
      } else if (value === "Th") {
        dayUnabbrev = "thursday";
      } else if (value === "F") {
        dayUnabbrev = "friday";
      } else if (value === "Sa") {
        dayUnabbrev = "saturday";
      } else {
        dayUnabbrev = "sunday";
      }
      csv = csv + "," + dayUnabbrev;
    });
    // remove first comma
    return csv.substring(1);
  };

  /**
   * Converts a string array to a CSV string.
   *
   * @param param the parameter to convert to a CSV string
   * @returns the CSV string interpretation of param
   */
  const paramToString = (param: string[] | undefined) => {
    if (param === undefined) {
      return "";
    } else if (param.length === 0) {
      return "";
    } else {
      // remove brackets from string and return
      return param.toString().substring(1, param.length - 1);
    }
  };

  /**
   * Calls generate on the backend
   */
  const handleGenerate = async () => {
    setSaved(false);
    const user = await getUser();
    const myUser = user["User"];
    const userData = myUser[0];
    let newIntols: string[] = [];
    let cuisineLabels: string[] = [];
    if (selectedButtons.length === 0) {
      alert(
        "You must select at least one day of the week to generate a recipe for."
      );
    } else {
      if (selectedOptionsCuisine === null) {
        await setSelectedOptionsCuisine([]);
      }
      cuisineLabels = selectedOptionsCuisine
        ? selectedOptionsCuisine.map((val) => val.label)
        : [];

      console.log("cuisineLabels: ", cuisineLabels);

      //if there are no selected intollerances

      if (selectedOptionsIntolerance === null) {
        if (
          //if there are no user intollerances
          userData["intolerances"] !== undefined &&
          userData["intolerances"].length > 0
        ) {
          newIntols = userData["intolerances"].concat(excludedIngredients);
        } else {
          newIntols = excludedIngredients;
        }
      } else if (selectedOptionsIntolerance.length == 0) {
        if (
          //if there are no user intollerances
          userData["intolerances"] !== undefined &&
          userData["intolerances"].length > 0
        ) {
          newIntols = userData["intolerances"].concat(excludedIngredients);
        } else {
          newIntols = excludedIngredients;
        }
      } //if there are selected intollerances
      else {
        newIntols = selectedOptionsIntolerance
          .map((val) => val.label)
          .concat(excludedIngredients);
      }
      await setIntols(newIntols);
      console.log("intols: ", newIntols);
      const props = {
        daysToPlan: convertDaysOfWeekToCSVString(),
        maxReadyTime: maxTime.toString(),
        diet: userData["diet"].toString(),
        intolerances: intols.toString(),
        cuisine: cuisineLabels.toString(),
        requestedServings: numberOfPeople.toString(),
        exp: userData["exp"],
        mode: selectedAlg,
      };
      console.log(props);
      const mealPlanJson = await generateMealPlan(props);
      const responseType = mealPlanJson["response_type"];

      if (responseType === "failure") {
        const errorMessage = mealPlanJson["error"];
        // Check if the error is related to not enough meals generated or strict search criteria
        if (errorMessage.includes("Caller requested")) {
          alert(
            "Not enough meals generated or the search criteria is too strict. Try again, and if possible try broadening your search."
          );
        }
      } else {
        const recipeList = mealPlanJson["Mealplan"];
        const newMealPlan = await parseMealPlanWithoutLikes(recipeList);
        setCurrMealPlan(newMealPlan);
        console.log(currMealPlan);
      }
    }
  };

  // Function to handle button click
  const handleButtonClick = (buttonName: string) => {
    if (selectedButtons.includes(buttonName)) {
      setSelectedButtons(selectedButtons.filter((item) => item !== buttonName));
    } else {
      setSelectedButtons([...selectedButtons, buttonName]);
    }
  };
  // Function to handle changes to the radio button selection
  const handleAlgChange = (event) => {
    if (event.target.value == "minimize_foodwaste") {
      setSelectedAlg("minimize");
    } else {
      setSelectedAlg("personalize");
    }
    console.log("Selected Algorithm:", event.target.value);
  };

  /**
   * Updates excludedIngredients state variable, accounting for possibility
   * of user clearing it.
   *
   * @param selectedToExclude the user-selected ingredients to exclude
   */
  const handleExcludedIngredientsChange = (selectedToExclude) => {
    setExcludedIngredients(selectedToExclude.map((option) => option.value));
    if (excludedIngredients === undefined) {
      setExcludedIngredients([]);
    }
  };

  /**
   * Gets user-selected cuisines, translating empty selection to an empty list.
   */
  const getSelectedCuisines = () => {
    // handle empty cuisine selection
    if (selectedOptionsCuisine === null) {
      return [];
    } else {
      return selectedOptionsCuisine;
    }
  };

  /**
   * Handles when a user clicks the save button. Accounts for edge
   * case of trying to save before generation by checking if currMealPlan is
   * empty first.
   */
  const handleSave = () => {
    if (currMealPlan === emptyMealPlan) {
      alert("You must generate a meal plan before saving it!");
    } else {
      toggleShowSave();
    }
  };

  const toggleShowSave = () => {
    console.log("toggling");
    setSavePopup(!showSavePopup);
    setSaved(true);
  };

  const personalized: boolean = selectedAlg === "personalize";

  return (
    <div className="Home Page">
      <div className="paragraph-container" aria-label="welcome-text">
        {/* Add your paragraph content here */}
        <h3>Welcome to Rookie to Cookie!</h3>
        <p>
          Our meal planning web application is designed to seamlessly integrate
          cooking into your daily routine. Whether you're a beginner starting
          from scratch, eager to explore new recipes, or aiming to minimize your
          weekly food waste, we've got you covered.
        </p>
        <p>
          Get started by selecting from the following options. We'll consider
          intolerances on your profile in addition to what you enter here.
        </p>
      </div>
      <div className="days-of-the-week-container">
        {/* Section of days of the week prompt */}
        <div className="days-of-the-week-prompt-text">
          Select the days of the week you would like to plan for:
        </div>

        {/* Section of days of the week buttons */}
        <div className="days-of-the-week-buttons">
          <DaysOfTheWeekButtons
            aria-label="days-of-the-week-select-buttons"
            selectedButtons={selectedButtons}
            handleButtonClick={handleButtonClick}
          />
        </div>
      </div>

      <div className="cuisine-container">
        {/* Section of cuisine prompt */}
        <div className="cuisine-prompt-text">
          Select the type of cuisine that you would like to see:
        </div>
        {/* Section of cuisine dropdown box */}
        <div className="cuisine-options-box">
          <Select
            aria-label="cuisine-selection"
            options={cuisineOptions}
            value={selectedOptionsCuisine}
            onChange={setSelectedOptionsCuisine}
            isMulti // Enable multi-select
            placeholder="Select option(s)"
          />
        </div>
      </div>

      <div className="alg-container">
        {/* Algorithm prompt */}
        <div className="algorithm-prompt-text">
          Select the type of algorithm that you would like to use:
        </div>
        {/* Algorithm options */}
        <div className="algorithm-options-box">
          <div
            className="radio-container"
            aria-label="algorithm-selection-buttons"
          >
            <input
              aria-label="minimize-food-waste"
              type="radio"
              id="minimize_foodwaste"
              name="algorithm"
              value="minimize_foodwaste"
              checked={selectedAlg === "minimize_foodwaste"}
              onChange={handleAlgChange}
            />
            <label
              htmlFor="minimize_foodwaste"
              style={{
                fontWeight: !personalized ? 700 : 400,
              }}
            >
              Minimize Food Waste
            </label>
          </div>
          <div className="radio-container">
            <input
              aria-label="prioritize-user-taste"
              type="radio"
              id="prioritize_user_taste"
              name="algorithm"
              value="prioritize_user_taste"
              checked={selectedAlg === "prioritize_user_taste"}
              onChange={handleAlgChange}
            />
            <label
              htmlFor="prioritize_user_taste"
              style={{
                fontWeight: personalized ? 700 : 400,
              }}
            >
              Prioritize User Taste
            </label>
          </div>
        </div>
      </div>

      <div className="intolerance-container">
        {/* Section of intolerances prompt */}
        <div className="intolerances-prompt-text">
          Select any food intolerances:
        </div>
        {/* Section of cuisine dropdown box */}
        <div className="intolerances-options-box">
          <Select
            aria-label="intolerance-selector"
            options={intoleranceOptions}
            value={selectedOptionsIntolerance}
            onChange={setSelectedOptionsIntolerance}
            isMulti // Enable multi-select
            placeholder="Select option(s)"
          />
        </div>
      </div>
      <div className="people-container">
        {/* Section of number of people cookign for prompt */}
        <div className="num-people-prompt-text">
          Specify the number of servings you require:
        </div>
        {/* Section of max time integer input */}
        <div className="num-people-options-box">
          <IntegerInput value={numberOfPeople} onChange={setNumberOfPeople} />
        </div>
      </div>

      <div className="exclude-container">
        {/* Section of excluded ingredients prompt */}
        <div className="exclude-prompt-text">
          Specify any ingredients to exclude:
        </div>
        {/* Section of excluded ingredients input box */}
        <div className="exclude-options-box">
          <MultiSelectInput onSelectChange={handleExcludedIngredientsChange} />
        </div>
      </div>

      <div className="time-container">
        {/* Section of max time prompt */}
        <div className="max-time-prompt-text">
          Specify max cooking time (min):
        </div>
        {/* Section of max time integer input */}
        <div className="max-time-options-box" aria-label="max-time">
          <IntegerInput value={maxTime} onChange={setMaxTime} minValue={20} />
        </div>
      </div>

      {/* Button for generating */}
      <div className="generate-button-container">
        <button
          onClick={handleGenerate}
          aria-label="generate-button"
          className="generate-button"
        >
          Generate
        </button>
      </div>

      {/* Container for week calendar view */}
      <div className="week-calendar-container" aria-label="week-plan">
        <WeekView mealPlan={currMealPlan} saved={saved} />{" "}
        {/* causes a white screen */}
      </div>

      {/* Button for saving data */}
      <div className="save-data-button-container">
        <button
          className="save-button"
          aria-label="save-button"
          onClick={handleSave}
        >
          Save
        </button>
        {showSavePopup && (
          <MealPlanSave
            onClose={() => {
              setSavePopup(false);
            }}
          />
        )}
      </div>
    </div>
  );
};

export default Home;
