import "../../styles/Home.css";
import React, { useState } from "react";
import Select from "react-select";

import RecipeCard from "../RecipeCard/RecipeCard";
import InfoView from "../RecipeCard/InfoView";
import MultiSelectInput from "../SelectionTypes/MultiSelectInput";
import IntegerInput from "../SelectionTypes/IntegerInput";
import Recipe from "../RecipeCard/Recipe";

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

  const recipe = {
    name: "Spaghetti Carbonara",
    cuisine: "Italian",
    instructions:
      "1. Cook spaghetti in boiling salted water until al dente. 2. Fry pancetta until crispy. 3. Whisk together eggs, cheese, and black pepper. 4. Drain spaghetti and toss with pancetta. 5. Add egg mixture and stir quickly. Serve immediately.",
  };

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
  const [showPopup, setShowPopup] = useState<boolean>(false);
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

  return (
    <div className="Home Page">
      <h1>Rookie to Cookie</h1>

      {/* Section of days of the week prompt */}
      <div className="days-of-the-week-prompt-text">
        Select the days of the week you would like to plan for:
      </div>

      {/* Section of days of the week buttons */}
      <div className="days-of-the-week-buttons">
        <button
          className={
            selectedButtons.includes("M")
              ? "selected circular-button"
              : "circular-button"
          }
          onClick={() => handleButtonClick("M")}
        >
          M
        </button>
        <button
          className={
            selectedButtons.includes("Tu")
              ? "selected circular-button"
              : "circular-button"
          }
          onClick={() => handleButtonClick("Tu")}
        >
          Tu
        </button>
        <button
          className={
            selectedButtons.includes("W")
              ? "selected circular-button"
              : "circular-button"
          }
          onClick={() => handleButtonClick("W")}
        >
          W
        </button>
        <button
          className={
            selectedButtons.includes("Th")
              ? "selected circular-button"
              : "circular-button"
          }
          onClick={() => handleButtonClick("Th")}
        >
          Th
        </button>
        <button
          className={
            selectedButtons.includes("F")
              ? "selected circular-button"
              : "circular-button"
          }
          onClick={() => handleButtonClick("F")}
        >
          F
        </button>
        <button
          className={
            selectedButtons.includes("Sa")
              ? "selected circular-button"
              : "circular-button"
          }
          onClick={() => handleButtonClick("Sa")}
        >
          Sa
        </button>
        <button
          className={
            selectedButtons.includes("Su")
              ? "selected circular-button"
              : "circular-button"
          }
          onClick={() => handleButtonClick("Su")}
        >
          Su
        </button>
      </div>

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

      {/* Section of excluded ingredients prompt */}
      <div className="exclude-prompt-text">
        Specify any ingredients to exclude:
      </div>

      {/* Section of excluded ingredients input box */}
      <div className="exclude-options-box">
        <MultiSelectInput onSelectChange={handleExcludedIngredientsChange} />
      </div>

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

      {/* Button for generating */}
      <div className="generate-button-container">
        <button className="generate-button">Generate</button>
      </div>

      {/* Container for week calendar view */}
      <div className="week-calendar-container">
        <div className="top-grid">
          {/* Top part of the grid */}
          <div className="left-week-name-grid-cell">Sunday</div>
          <div className="week-name-grid-cell">Monday</div>
          <div className="week-name-grid-cell">Tuesday</div>
          <div className="week-name-grid-cell">Wednesday</div>
          <div className="week-name-grid-cell">Thursday</div>
          <div className="week-name-grid-cell">Friday</div>
          <div className="right-week-name-grid-cell">Saturday</div>
        </div>
        <div className="bottom-grid">
          {/* Bottom part of the grid */}
          <div className="left-grid-cell">
            <RecipeCard recipe={recipe} setShowPopup={setShowPopup} />
          </div>
          <div className="grid-cell"></div>
          <div className="grid-cell"></div>
          <div className="grid-cell"></div>
          <div className="grid-cell"></div>
          <div className="grid-cell"></div>
          <div className="right-grid-cell"></div>
        </div>
      </div>

      {/* Show the popup if showPopup is true */}
      {showPopup && (
        <InfoView recipe={recipe} onClose={() => setShowPopup(false)} />
      )}

      {/* Button for regenerating */}
      <div className="regenerate-button-container">
        <button className="regenerate-button">Regenerate</button>
      </div>

      {/* Button for saving data */}
      <div className="save-data-button-container">
        <button className="save-button">Save</button>
      </div>
    </div>
  );
};

export default Home;
