// DaysOfWeekButtons.js
import "../../styles/DaysOfTheWeekButtons.css";
import React from "react";

const DaysOfTheWeekButtons = ({ selectedButtons, handleButtonClick }) => {
  return (
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
  );
};

export default DaysOfTheWeekButtons;
