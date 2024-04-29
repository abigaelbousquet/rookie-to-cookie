import React from "react";
import Recipe from "./Recipe";
import "../../styles/InfoView.css";

interface InfoViewProps {
  recipe: Recipe;
  onClose: () => void;
  onToggleLike: () => void;
}

const InfoView: React.FC<InfoViewProps> = ({
  recipe,
  onClose,
  onToggleLike,
}) => {
  return (
    <div className="popup-overlay">
      <div className="popup-content">
        <div className="recipe-title">
          {recipe.name}
          <button className="close-button" onClick={onClose}>
            X
          </button>
        </div>
        <p>
          <strong>Cuisine:</strong> {recipe.cuisine}
        </p>
        <p>
          <strong>Ingredients:</strong> {recipe.ingredients}
        </p>
        <p>
          <strong>Instructions:</strong> {recipe.instructions}
        </p>
        {/* Close button */}
      </div>
    </div>
  );
};

export default InfoView;
