import "../../styles/MealPlanSave.css";
import React, { useState } from "react";
import WeekView from "../HomeComponents/WeekView";

interface MealPlanPopupProps {
  onClose: () => void;
  mealPlan: any;
}
const MealPlanPopup: React.FC<MealPlanPopupProps> = ({ onClose, mealPlan }) => {
  return (
    <div className="popup-plan-overlay">
      <div className="popup-plan-content">
        <div className="mealplan-text">Your Saved Meal Plan</div>
        <div className="mealplan-popup">
          <WeekView mealPlan={mealPlan} saved={true}></WeekView>
        </div>
        <button className="close-button-save" onClick={onClose}>
          X
        </button>
      </div>
    </div>
  );
};
export default MealPlanPopup;
