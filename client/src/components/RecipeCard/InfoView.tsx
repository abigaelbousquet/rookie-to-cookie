import React from "react";
import Recipe from "./Recipe";
import LikeButton from "./LikeButton";
import "../../styles/InfoView.css";

interface InfoViewProps {
  recipe: Recipe;
  onClose: () => void;
  setLiked: React.Dispatch<React.SetStateAction<number>>;
  liked: number;
}

const InfoView: React.FC<InfoViewProps> = ({
  recipe,
  onClose,
  setLiked,
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
          <strong>Time:</strong> {recipe.time}
        </p>
        <p>
          <strong>Ingredients:</strong> {recipe.ingredients}
        </p>
        <p>
          <strong>Instructions:</strong> {recipe.instructions}
        </p>

        {/* Heart button for toggling like status */}
        <div className="like-button-container-info">
          <LikeButton liked={liked} setLiked={setLiked} />
        </div>
        {/* Close button */}
      </div>
    </div>
  );
};

export default InfoView;
