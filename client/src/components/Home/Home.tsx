import "../../styles/Home.css";
import React, { useState } from "react";
import Select from "react-select";

import RecipeCard from "../RecipeCard/RecipeCard";
import InfoView from "../RecipeCard/InfoView";
import Recipe from "../RecipeCard/Recipe";

const Home: React.FC = () => {
  // Define the options array for the dropdown
  const cuisineOptions = [
    { label: "african", value: "Option 1" },
    { label: "chinese", value: "Option 2" },
    { label: "japanese", value: "Option 3" },
    { label: "korean", value: "Option 4" },
    { label: "vietnamese", value: "Option 5" },
    { label: "thai", value: "Option 6" },
    { label: "indian", value: "Option 6" },
    { label: "british", value: "Option 6" },
    { label: "irish", value: "Option 6" },
    { label: "french", value: "Option 6" },
    { label: "italian", value: "Option 6" },
    { label: "mexican", value: "Option 6" },
    { label: "spanish", value: "Option 6" },
    { label: "middle eastern", value: "Option 6" },
    { label: "jewish", value: "Option 6" },
    { label: "american", value: "Option 6" },
    { label: "cajun", value: "Option 6" },
    { label: "southern", value: "Option 6" },
    { label: "greek", value: "Option 6" },
    { label: "german", value: "Option 6" },
    { label: "nordic", value: "Option 6" },
    { label: "eastern european", value: "Option 6" },
    { label: "caribbean", value: "Option 6" },
    { label: "latin american", value: "Option 6" },
  ];

  const algorithmType = [
    { label: "minimize food waste", value: "minimize food waste" },
    { label: "use personalized taste", value: "use personalized taste" },
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
  const [selectedOptionsAlg, setSelectedOptionsAlg] = useState<
    { label: string; value: string }[]
  >([]);

  // State to control the visibility of the popup
  const [showPopup, setShowPopup] = useState<boolean>(false);

  // Function to handle button click
  const handleButtonClick = (buttonName: string) => {
    if (selectedButtons.includes(buttonName)) {
      setSelectedButtons(selectedButtons.filter((item) => item !== buttonName));
    } else {
      setSelectedButtons([...selectedButtons, buttonName]);
    }
  };

  // Function to handle option selection for multi-select
  const handleMultiSelectChangeCuisine = (
    selectedOptionsCuisine: { label: string; value: string }[]
  ) => {
    setSelectedOptionsCuisine(selectedOptionsCuisine);
  };

  // Function to handle option selection for multi-select
  const handleMultiSelectChangeAlg = (
    selectedOptionsAlg: { label: string; value: string }[]
  ) => {
    setSelectedOptionsAlg(selectedOptionsAlg);
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

      {/* TODO!!! change this to be a souble sided button */}
      {/* Section of algorithm prompt */}
      <div className="algorithm-prompt-text">
        Select the type of algorithm that you would like to use:
      </div>

      {/* Section of cuisine dropdown box */}
      <div className="algorithm-options-box">
        <Select
          options={algorithmType}
          value={selectedOptionsAlg}
          onChange={handleMultiSelectChangeAlg}
          isMulti // Enable multi-select
          placeholder="Select option(s)"
        />
      </div>
      {/* TODO!!! */}

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
