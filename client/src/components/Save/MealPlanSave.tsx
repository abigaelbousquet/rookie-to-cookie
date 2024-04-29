import "../../styles/MealPlanSave.css";
import React from "react";

interface MealPlanSaveProps {
  onClose: () => void;
}

const MealPlanSave: React.FC<MealPlanSaveProps> = ({ onClose }) => {
  return (
    <div className="popup-save-overlay">
      <div className="popup-save-content">
        <h2>{"Save?"}</h2>
        {/* Close button */}
        <button className="close-button-save" onClick={onClose}>
          X
        </button>
      </div>
    </div>
  );
};

export default MealPlanSave;
