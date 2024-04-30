import React, { useEffect, useState } from "react";
import Recipe from "./Recipe";
import InfoView from "./InfoView";
import { AiFillLike, AiFillDislike, AiFillHeart } from "react-icons/ai";
import LikeButton from "./LikeButton";
import "../../styles/RecipeCard.css";
import { addDislike, addLike } from "../../utils/api";

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
  useEffect(() => {
    const updateLikes = async () => {
      if (liked == 1) {
        await addLike(recipe.id);
        console.log("added liked to firestore: " + recipe.id);
      } else if (liked == 2) {
        await addDislike(recipe.id);
        console.log("added disliked to firestore: " + recipe.id);
      }
    };
    updateLikes();
  }, [liked]);
  return (
    <div className="recipe-card-container">
      <div className="recipe-card">
        <div className="recipe-header">
          <div className="recipe-title">{recipe.name}</div>
          <div className="like-button-container">
            <LikeButton liked={liked} setLiked={setLiked} />
          </div>
        </div>
        {showFullRecipe && (
          <InfoView
            recipe={recipe}
            onClose={() => setShowFullRecipe(false)}
            setLiked={setLiked}
            liked={liked}
          />
        )}
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
