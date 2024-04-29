import React from "react";
import Recipe from "./Recipe";
import "../../styles/InfoView.css";

interface InfoViewProps {
  recipe: Recipe;
  onClose: () => void;
  onToggleLike: () => void;
  liked: boolean;
}

const InfoView: React.FC<InfoViewProps> = ({
  recipe,
  onClose,
  onToggleLike,
  liked,
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
          <strong>Time:</strong> {recipe.time}
        </p>
        <p>
          <strong>Instructions:</strong> {recipe.instructions}
        </p>

        {/* Heart button for toggling like status */}
        <div className="like-button-container-info">
          <button
            className={liked ? "like-button-info liked" : "like-button"}
            onClick={onToggleLike}
          >
            <span role="img" aria-label="heart">
              {liked ? "❤️" : "♡"}
            </span>{" "}
          </button>
        </div>
        {/* Close button */}
      </div>
    </div>
  );
};

export default InfoView;
