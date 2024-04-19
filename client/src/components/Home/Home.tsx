import "../../styles/Home.css";
import React, { useState } from "react";
import Select from "react-select";

const Home: React.FC = () => {
  // Define the options array for the dropdown
  const aquaticCreatures = [
    { label: "Option 1", value: "Option 1" },
    { label: "Option 2", value: "Option 2" },
    { label: "Option 3", value: "Option 3" },
    { label: "Option 4", value: "Option 4" },
    { label: "Option 5", value: "Option 5" },
    { label: "Option 6", value: "Option 6" },
  ];

  // State to keep track of selected buttons
  const [selectedButtons, setSelectedButtons] = useState<string[]>([]);
  const [selectedOption, setSelectedOption] = useState<string | null>(null);
  const [selectedOptions, setSelectedOptions] = useState<
    { label: string; value: string }[]
  >([]);

  // Function to handle button click
  const handleButtonClick = (buttonName: string) => {
    if (selectedButtons.includes(buttonName)) {
      setSelectedButtons(selectedButtons.filter((item) => item !== buttonName));
    } else {
      setSelectedButtons([...selectedButtons, buttonName]);
    }
  };

  // Function to handle option selection
  const handleOptionSelect = (selectedOption: any) => {
    setSelectedOption(selectedOption);
  };

  // Function to handle option selection for multi-select
  const handleMultiSelectChange = (
    selectedOptions: { label: string; value: string }[]
  ) => {
    setSelectedOptions(selectedOptions);
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
          options={aquaticCreatures}
          value={selectedOptions}
          onChange={handleMultiSelectChange}
          isMulti // Enable multi-select
          placeholder="Select option(s)"
        />
      </div>
    </div>
  );
};

export default Home;
