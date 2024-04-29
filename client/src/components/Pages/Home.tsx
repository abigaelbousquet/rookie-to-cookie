import "../../styles/Home.css";
import React, { useState } from "react";
import Select from "react-select";

import RecipeCard from "../RecipeCard/RecipeCard";
import InfoView from "../RecipeCard/InfoView";
import MultiSelectInput from "../SelectionTypes/MultiSelectInput";
import IntegerInput from "../SelectionTypes/IntegerInput";
import Recipe from "../RecipeCard/Recipe";
import WeekView from "../HomeComponents/WeekView";
import DaysOfTheWeekButtons from "../HomeComponents/DaysOfTheWeekButtons";
import MealPlanSave from "../Save/MealPlanSave";

const Home: React.FC = () => {
  // Define the options array for the dropdown
  const cuisineOptions = [
    { label: "african", value: "african" },
    { label: "chinese", value: "chinese" },
    { label: "japanese", value: "japanese" },
    { label: "korean", value: "korean" },
    { label: "vietnamese", value: "vietnamese" },
    { label: "thai", value: "thai" },
    { label: "indian", value: "indian" },
    { label: "british", value: "british" },
    { label: "irish", value: "irish" },
    { label: "french", value: "french" },
    { label: "italian", value: "italian" },
    { label: "mexican", value: "mexican" },
    { label: "spanish", value: "spanish" },
    { label: "middle eastern", value: "middle eastern" },
    { label: "jewish", value: "jewish" },
    { label: "american", value: "american" },
    { label: "cajun", value: "cajun" },
    { label: "southern", value: "southern" },
    { label: "greek", value: "greek" },
    { label: "german", value: "german" },
    { label: "nordic", value: "nordic" },
    { label: "eastern european", value: "eastern european" },
    { label: "caribbean", value: "caribbean" },
    { label: "latin american", value: "latin american" },
  ];

  const intoleranceOptions = [
    { label: "Shellfish", value: "Shellfish" },
    { label: "Egg", value: "Egg" },
    { label: "Peanut", value: "Peanut" },
    { label: "Nut", value: "Nut" },
    { label: "Soy", value: "Soy" },
    { label: "Sesame", value: "Sesame" },
    { label: "Tree nut", value: "Tree nut" },
    { label: "Sulfite", value: "Sulfite" },
    { label: "Dairy", value: "Dairy" },
    { label: "Gluten", value: "Gluten" },
  ];

  const spaghetti = {
    name: "Spaghetti Carbonara",
    cuisine: "Italian",
    instructions:
      "1. Cook spaghetti in boiling salted water until al dente. 2. Fry pancetta until crispy. 3. Whisk together eggs, cheese, and black pepper. 4. Drain spaghetti and toss with pancetta. 5. Add egg mixture and stir quickly. Serve immediately.",
    time: 10,
    liked: false,
  };

  const mealPlan = [
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

  // Function to handle button click
  const handleButtonClick = (buttonName: string) => {
    if (selectedButtons.includes(buttonName)) {
      setSelectedButtons(selectedButtons.filter((item) => item !== buttonName));
    } else {
      setSelectedButtons([...selectedButtons, buttonName]);
    }
  };
  const [excludedIngredients, setExcludedIngredients] = useState("");

  // Function to handle option selection for multi-select
  const handleMultiSelectChangeCuisine = (
    selectedOptionsCuisine: { label: string; value: string }[]
  ) => {
    setSelectedOptionsCuisine(selectedOptionsCuisine);
  };

  // Function to handle option selection for multi-select
  const handleMultiSelectChangeIntolerance = (
    selectedOptionsIntolerance: { label: string; value: string }[]
  ) => {
    setSelectedOptionsIntolerance(selectedOptionsIntolerance);
  };

  // Function to handle changes to the radio button selection
  const handleAlgChange = (event) => {
    setSelectedAlg(event.target.value);
  };
  const handleExcludedIngredientsChange = (selectedToExclude) => {
    setExcludedIngredients(selectedToExclude.map((option) => option.value));
  };
  const handleNumberOfPeopleChange = (value: number) => {
    setNumberOfPeople(value); // Update state with new value
  };
  const handleMaxTimeChange = (value: number) => {
    setMaxTime(value); // Update state with new value
  };
  const toggleShowSave = () => {
    setSavePopup(!showSavePopup);
  };

  return (
    <div className="Home Page">
      <h1>Rookie to Cookie</h1>

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
            onChange={handleMultiSelectChangeCuisine}
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
            <label htmlFor="prioritize user taste">Prioritize User Tatse</label>
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
            onChange={handleMultiSelectChangeIntolerance}
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
            onChange={handleNumberOfPeopleChange}
            minValue={1}
          />
        </div>
      </div>

      <div className="time-container">
        {/* Section of max time prompt */}
        <div className="max-time-prompt-text">Specify max cooking time:</div>

        {/* Section of max time integer input */}
        <div className="max-time-options-box">
          <IntegerInput
            value={maxTime}
            onChange={handleMaxTimeChange}
            minValue={5}
          />
        </div>
      </div>

      {/* Button for generating */}
      <div className="generate-button-container">
        <button className="generate-button">Generate</button>
      </div>

      {/* Container for week calendar view */}
      <div className="week-calendar-container">
        <WeekView mealPlan={mealPlan} /> {/* causes a white screen */}
      </div>

      {/* Button for regenerating */}
      <div className="regenerate-button-container">
        <button className="regenerate-button">Regenerate</button>
      </div>

      {/* Button for saving data */}
      <div className="save-data-button-container">
        <button className="save-button" onClick={toggleShowSave}>
          Save
        </button>
        {showSavePopup && <MealPlanSave onClose={() => setSavePopup(false)} />}
      </div>
    </div>
  );
};

export default Home;
