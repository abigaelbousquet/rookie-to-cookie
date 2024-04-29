import React, { useState } from "react";
import Recipe from "./Recipe";
import InfoView from "./InfoView";

import { AiFillLike, AiFillDislike, AiFillHeart } from "react-icons/ai";
import LikeButton from "./LikeButton";
import HeartButton from "../HeartButton"; // Import the HeartButton component
import "../../styles/RecipeCard.css";

interface RecipeCardProps {
  recipe: Recipe;
  setShowPopup: React.Dispatch<React.SetStateAction<boolean>>;
}

const RecipeCard: React.FC<RecipeCardProps> = ({ recipe }) => {
  const [showFullRecipe, setShowFullRecipe] = useState(false);
  const [liked, setLiked] = useState(recipe.liked);
  const toggleShowFullRecipe = () => {
    setShowFullRecipe(!showFullRecipe);
  };
  return (

        <div className="recipe-card-container">
    <div className="recipe-card">
      <div className="recipe-header">
        <div className="recipe-title">{recipe.name}</div>
        {/* Old Heart Button */}
        <LikeButton liked={liked} setLiked={setLiked} />
      </div>
      </div>
      {showFullRecipe ? (
        <InfoView
          recipe={recipe}
          onClose={() => setShowFullRecipe(false)}
          onToggleLike={() => {}}
          {/*onToggleLike={handleLikeToggle}*/}
           {/* liked={liked}*/}
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

          <button className="recipe-button" onClick={toggleShowFullRecipe}>
            View Recipe
          </button>
        </div>
      </div>
    </div>
  );
};

export default RecipeCard;
