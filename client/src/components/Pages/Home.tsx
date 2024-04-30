import "../../styles/Home.css";
import React, { useState } from "react";
import Select from "react-select";
import InfoView from "../RecipeCard/InfoView";
import MultiSelectInput from "../SelectionTypes/MultiSelectInput";
import IntegerInput from "../SelectionTypes/IntegerInput";
import WeekView from "../HomeComponents/WeekView";
import DaysOfTheWeekButtons from "../HomeComponents/DaysOfTheWeekButtons";

import { generateMealPlan, getUser, saveMealPlan } from "../../utils/api";
import { cuisineOptions, intoleranceOptions } from "../../data/Spoonacular";
import MealPlanSave from "../Save/MealPlanSave";

const Home: React.FC = () => {
  // Define the options array for the dropdown
  const spaghetti = {
    name: "Spaghetti Carbonara",
    cuisine: "Italian",
    instructions: ["Step 1", "Step 2", "Step 1000"],
    time: 10,
    liked: 0,
    ingredients: ["Pasta", "Sauce", "Something more"],
    image: "https://placeholder.com/312x231",
    credit: "Unknown",
  };

  const mockedMealPlan = [
    { day: "Sunday", recipeExists: true, recipe: spaghetti },
    { day: "Monday", recipeExists: false },
    { day: "Tuesday", recipeExists: true, recipe: spaghetti },
    { day: "Wednesday", recipeExists: false },
    { day: "Thursday", recipeExists: false },
    { day: "Friday", recipeExists: false },
    { day: "Saturday", recipeExists: false },
  ];

  // State to keep track of selected buttons
  const [selectedButtons, setSelectedButtons] = useState<string[]>([]);
  const [selectedOptionsCuisine, setSelectedOptionsCuisine] = useState<
    { label: string; value: string }[]
  >([]);
  const [selectedOptionsIntolerance, setSelectedOptionsIntolerance] = useState<
    { label: string; value: string }[]
  >([]);
  // State to hold the selected option
  const [selectedAlg, setSelectedAlg] = useState("minimize_foodwaste");
  // State to control the visibility of the popup
  const [showInfoViewPopup, setShowInfoViewPopup] = useState<boolean>(false);
  const [showSavePopup, setSavePopup] = useState<boolean>(false);
  const [numberOfPeople, setNumberOfPeople] = useState<number>(1);
  const [maxTime, setMaxTime] = useState<number>(1);
  const [excludedIngredients, setExcludedIngredients] = useState("");
  const [sunDate, setSunDate] = useState("");
  const [intols, setIntols] = useState([""]);

  /**
   * Saves mealPlan to Firebase
   * //TODO:POST HERE?
   */
  const handleSave = async () => {
    await saveMealPlan(sunDate);
  };

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
   * Calls generate on the backend
   */
  const handleGenerate = async () => {
    console.log("generating");
    const user = await getUser();
    const userData = user["User"];
    console.log(user["User"]);
    console.log;
    //handle user intolerances from profile
    if (selectedOptionsIntolerance.length === 0) {
      if (userData["intolerances"].length > 0) {
        setIntols(userData["intolerances"]);
      }
    } else {
      const choices = selectedOptionsIntolerance
        .map((val) => val.label)
        .concat(excludedIngredients);
      setIntols(
        choices.concat(
          userData["intolerances"].filter((val) => !choices.includes(val))
        )
      );
    } //TODO: check if fam size is empty then use user defaults
    const props = {
      daysToPlan: convertDaysOfWeekToCSVString(),
      maxReadyTime: maxTime.toString(),
      diet: userData["diet"],
      intolerances: intols,
      cuisine: selectedOptionsCuisine.map((val) => val.label) || "",
      requestedServings: numberOfPeople.toString(),
      exp: userData["exp"],
      mode: selectedAlg,
    };
    console.log(props);
    await generateMealPlan(props);
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
  };
  const handleExcludedIngredientsChange = (selectedToExclude) => {
    setExcludedIngredients(selectedToExclude.map((option) => option.value));
  };

  const toggleShowSave = () => {
    setSavePopup(!showSavePopup);
  };

  return (
    <div className="Home Page">
      <div className="days-of-the-week-container">
        {/* Section of days of the week prompt */}
        <div className="days-of-the-week-prompt-text">
          Select the days of the week you would like to plan for:
        </div>

        {/* Section of days of the week buttons */}
        <div className="days-of-the-week-buttons">
          <DaysOfTheWeekButtons
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
            options={cuisineOptions}
            value={selectedOptionsCuisine}
            onChange={setSelectedOptionsCuisine}
            isMulti // Enable multi-select
            placeholder="Select option(s)"
          />
        </div>
      </div>

      <div className="alg-container">
        {/* Section of algorithm prompt */}
        <div className="algorithm-prompt-text">
          Select the type of algorithm that you would like to use:
        </div>
        {/* Section of cuisine dropdown box */}
        <div className="algorithm-options-box">
          <div>
            <input
              type="radio"
              id="minimize_foodwaste"
              name="algorithm"
              value="minimize_foodwaste"
              checked={selectedAlg === "minimize_foodwaste"} // Check if this option is selected
              onChange={handleAlgChange} // Call function to update state on change
            />
            <label htmlFor="id_minimize_foodwaste">Minimize Food Waste</label>
          </div>

          <div>
            <input
              type="radio"
              id="prioritize user taste"
              name="algorithm"
              value="prioritize user taste"
              checked={selectedAlg === "prioritize user taste"} // Check if this option is selected
              onChange={handleAlgChange} // Call function to update state on change
            />
            <label htmlFor="prioritize user taste">Prioritize User Taste</label>
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
            options={intoleranceOptions}
            value={selectedOptionsIntolerance}
            onChange={setSelectedOptionsIntolerance}
            isMulti // Enable multi-select
            placeholder="Select option(s)"
          />
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

      <div className="people-container">
        {/* Section of number of people cookign for prompt */}
        <div className="num-people-prompt-text">
          Specify the amount of people you are cooking for:
        </div>
        {/* Section of max time integer input */}
        <div className="num-people-options-box">
          <IntegerInput
            value={numberOfPeople}
            onChange={setNumberOfPeople}
            minValue={1}
          />
        </div>
      </div>

      <div className="time-container">
        {/* Section of max time prompt */}
        <div className="max-time-prompt-text">
          Specify max cooking time (min):
        </div>
        {/* Section of max time integer input */}
        <div className="max-time-options-box">
          <IntegerInput value={maxTime} onChange={setMaxTime} minValue={20} />
        </div>
      </div>

      {/* Button for generating */}
      <div className="generate-button-container">
        <button onClick={handleGenerate} className="generate-button">
          Generate
        </button>
      </div>

      {/* Container for week calendar view */}
      <div className="week-calendar-container">
        <WeekView mealPlan={mockedMealPlan} /> {/* causes a white screen */}
      </div>

      {/* Button for regenerating */}
      <div className="regenerate-button-container">
        <button className="regenerate-button">Regenerate</button>
      </div>
      {/* Button for saving data */}
      <div className="save-data-button-container">
        <button
          className="save-button"
          onClick={() => {
            toggleShowSave;
          }}
        >
          Save
        </button>
        {showSavePopup && <MealPlanSave onClose={() => setSavePopup(false)} />}
      </div>
    </div>
  );
};

export default Home;
