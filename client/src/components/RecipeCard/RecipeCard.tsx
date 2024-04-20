import React, { useState } from "react";
import Recipe from "./Recipe";
import InfoView from "./InfoView";

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
    <div className="recipe-card">
      <div className="recipe-header">
        <h3>{recipe.name}</h3>
        <button
          className={liked ? "like-button liked" : "like-button"}
          onClick={handleLikeToggle}
        >
          <span role="img" aria-label="heart">
            {liked ? "❤️" : "♡"}
          </span>{" "}
          Like
        </button>
      </div>
      {showFullRecipe ? (
        <InfoView recipe={recipe} onClose={() => setShowFullRecipe(false)} />
      ) : (
        <div className="recipe-content">
          <p>
            <strong>Cuisine:</strong> {recipe.cuisine}
          </p>
          <p>
            <strong>Instructions:</strong>
          </p>
          <p>{recipe.instructions.slice(0, 20)}</p>{" "}
          {/* Display only the first 20 characters */}
          <button onClick={toggleShowFullRecipe}>See more...</button>
        </div>
      )}
    </div>
  );
};

export default RecipeCard;
