import React, { useState } from "react";
import RecipeCard from "../RecipeCard/RecipeCard";
import "../../styles/WeekView.css";

const WeekView = ({ mealPlan, saved }) => {
  const [showPopup, setShowPopup] = useState<boolean>(false);

  return (
    <div className="week-calendar">
      <div className="top-grid">
        {/* Render week name cells */}
        <div
          className={`week-name-grid-cell ${
            mealPlan[0].recipeExists ? "recipe-exists" : "recipe-not-exists"
          }`}
        >
          Monday
        </div>
        <div
          className={`week-name-grid-cell ${
            mealPlan[1].recipeExists ? "recipe-exists" : "recipe-not-exists"
          }`}
        >
          Tuesday
        </div>
        <div
          className={`week-name-grid-cell ${
            mealPlan[2].recipeExists ? "recipe-exists" : "recipe-not-exists"
          }`}
        >
          Wednesday
        </div>
        <div
          className={`week-name-grid-cell ${
            mealPlan[3].recipeExists ? "recipe-exists" : "recipe-not-exists"
          }`}
        >
          Thursday
        </div>
        <div
          className={`week-name-grid-cell ${
            mealPlan[4].recipeExists ? "recipe-exists" : "recipe-not-exists"
          }`}
        >
          Friday
        </div>
        <div
          className={`week-name-grid-cell ${
            mealPlan[5].recipeExists ? "recipe-exists" : "recipe-not-exists"
          }`}
        >
          Saturday
        </div>
        <div
          className={`week-name-grid-cell ${
            mealPlan[6].recipeExists ? "recipe-exists" : "recipe-not-exists"
          }`}
        >
          Sunday
        </div>
      </div>
      <div className="bottom-grid">
        {/* Render recipe cells */}
        {mealPlan.map(({ day, recipeExists, recipe, liked }) => (
          <div
            key={day}
            className={`grid-cell ${
              recipeExists ? "recipe-exists" : "recipe-not-exists"
            }`}
          >
            {recipeExists && (
              <RecipeCard
                recipe={recipe}
                setShowPopup={setShowPopup}
                saved={saved}
                isLiked={liked}
              />
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default WeekView;
