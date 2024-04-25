import React, { useState } from "react";
import RecipeCard from "./RecipeCard";
import "../../styles/WeekView.css";

const WeekView = ({ mealPlan }) => {
  const [showPopup, setShowPopup] = useState<boolean>(false);

  return (
    <div className="week-calendar">
      <div className="top-grid">
        {/* Render week name cells */}
        <div className="left-week-name-grid-cell">Sunday</div>
        <div className="week-name-grid-cell">Monday</div>
        <div className="week-name-grid-cell">Tuesday</div>
        <div className="week-name-grid-cell">Wednesday</div>
        <div className="week-name-grid-cell">Thursday</div>
        <div className="week-name-grid-cell">Friday</div>
        <div className="right-week-name-grid-cell">Saturday</div>
      </div>
      <div className="bottom-grid">
        {/* Render recipe cells */}
        {mealPlan.map(({ day, recipeExists, recipe }) => (
          <div key={day} className="grid-cell">
            {recipeExists && (
              <RecipeCard recipe={recipe} setShowPopup={setShowPopup} />
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default WeekView;
