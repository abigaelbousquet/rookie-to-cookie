import React, { useState } from "react";
import Recipe from "./Recipe";
import InfoView from "./InfoView";
import { AiFillLike, AiFillDislike, AiFillHeart } from "react-icons/ai";
import HeartButton from "../HeartButton"; // Import the HeartButton component
import LikeButton from "./LikeButton";

interface RecipeCardProps {
  recipe: Recipe;
  setShowPopup: React.Dispatch<React.SetStateAction<boolean>>;
}

const RecipeCard: React.FC<RecipeCardProps> = ({ recipe }) => {
  const [showFullRecipe, setShowFullRecipe] = useState(false);

  const toggleShowFullRecipe = () => {
    setShowFullRecipe(!showFullRecipe);
  };

  return (
    <div className="recipe-card">
      <div className="recipe-header">
        <h3>{recipe.name}</h3>
        {/* Old Heart Button */}
        <LikeButton />
      </div>
      {showFullRecipe ? (
        <InfoView
          recipe={recipe}
          onClose={() => setShowFullRecipe(false)}
          onToggleLike={() => {}}
        />
      ) : (
        <div className="recipe-content">
          <p>
            <strong>Cuisine:</strong> {recipe.cuisine}
          </p>
          {/* <p>
            <strong>Instructions:</strong>
          </p> */}
          {/* <p>{recipe.instructions.slice(0, 10)}</p>{" "} */}
          <button className="recipe-button" onClick={toggleShowFullRecipe}>
            View Recipe
          </button>
        </div>
      )}
    </div>
  );
};

export default RecipeCard;
