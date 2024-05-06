import React, { useEffect, useState } from "react";
import Recipe from "./Recipe";
import InfoView from "./InfoView";
import LikeButton from "./LikeButton";
import "../../styles/RecipeCard.css";
import {
  addDislike,
  addLike,
  removeDislike,
  removeLike,
} from "../../utils/api";

/**
 * Recipe card, displayed in meal plan views and like/dislike sections of the webapp
 */
interface RecipeCardProps {
  recipe: Recipe;
  saved: boolean;
  isLiked: number;
  setShowPopup: React.Dispatch<React.SetStateAction<boolean>>;
}
/**
 * Component representing a recipe card.
 * @param recipe - The recipe data to display on the card.
 * @param saved - Boolean indicating whether the recipe is saved.
 * @param isLiked - Boolean indicating whether the recipe is liked.
 * @returns A React component representing a recipe card.
 */
const RecipeCard: React.FC<RecipeCardProps> = ({ recipe, saved, isLiked }) => {
  const [showFullRecipe, setShowFullRecipe] = useState(false);
  const [liked, setLiked] = useState(isLiked);

  /**
   * Toggles the visibility of the full recipe details.
   */
  const toggleShowFullRecipe = () => {
    setShowFullRecipe(!showFullRecipe);
  };
  //Use effect handles like click by updating firestore

  /**
   * Updates the like status of the recipe in the database based on user interaction.
   */
  useEffect(() => {
    const updateLikes = async () => {
      if (liked == 1) {
        await addLike(recipe.id);
      } else if (liked == 2) {
        await removeLike(recipe.id);
        await addDislike(recipe.id);
      } else {
        await removeDislike(recipe.id);
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
            <LikeButton liked={liked} setLiked={setLiked} canLike={saved} />
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
          {/*Button that toggles the infoview popup*/}
          <button className="recipe-button" onClick={toggleShowFullRecipe}>
            View Recipe
          </button>
        </div>
      </div>
    </div>
  );
};

export default RecipeCard;
