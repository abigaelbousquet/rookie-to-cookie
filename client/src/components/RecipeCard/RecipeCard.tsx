import React, { useState } from "react";
import Recipe from "./Recipe";
import InfoView from "./InfoView";
import HeartButton from "../HeartButton"; // Import the HeartButton component
import "../../styles/RecipeCard.css";

interface RecipeCardProps {
  recipe: Recipe;
  setShowPopup: React.Dispatch<React.SetStateAction<boolean>>;
}

const RecipeCard: React.FC<RecipeCardProps> = ({ recipe }) => {
  const [showFullRecipe, setShowFullRecipe] = useState(false);
  const [liked, setLiked] = useState<boolean>(false);

  const handleLikeToggle = () => {
    setLiked(!liked);
  };

  const toggleShowFullRecipe = () => {
    setShowFullRecipe(!showFullRecipe);
  };
  return (
    <div className="recipe-card-container">
      <div className="recipe-card">
        <div className="recipe-header">
          <h3>{recipe.name}</h3>
          <div className="like-button-container">
            <button
              className={liked ? "like-button liked" : "like-button"}
              onClick={handleLikeToggle}
            >
              <span role="img" aria-label="heart">
                {liked ? "❤️" : "♡"}
              </span>{" "}
            </button>
          </div>
        </div>
        {showFullRecipe ? (
          <InfoView
            recipe={recipe}
            onClose={() => setShowFullRecipe(false)}
            onToggleLike={handleLikeToggle}
          />
        ) : (
          <div className="recipe-content">
            <p>
              <strong>Cuisine:</strong> {recipe.cuisine}
            </p>
            <p>
              <strong>Time:</strong> {recipe.time}
            </p>
            <p>
              <strong>Instructions: </strong>
              {"..."}
            </p>

            <button onClick={toggleShowFullRecipe}>See more...</button>
          </div>
        )}
      </div>
    </div>
  );
};

export default RecipeCard;
