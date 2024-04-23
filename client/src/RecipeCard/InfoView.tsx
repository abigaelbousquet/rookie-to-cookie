import React from "react";
import Recipe from "./Recipe";
import "../../styles/InfoView.css";

interface InfoViewProps {
  recipe: Recipe;
  onClose: () => void;
}

const InfoView: React.FC<InfoViewProps> = ({ recipe, onClose }) => {
  return (
    <div className="popup-overlay">
      <div className="popup-content">
        <h2>{recipe.name}</h2>
        <p>{recipe.instructions}</p>
        {/* Close button */}
        <button className="close-button" onClick={onClose}>
          X
        </button>
      </div>
    </div>
  );
};

export default InfoView;
