import React from "react";
import Recipe from "./Recipe";
import LikeButton from "./LikeButton";
import "../../styles/InfoView.css";

/**
 * Displays a recipe that can be followed step by step, with an ingredient list, image, like button, and other information
 */
interface InfoViewProps {
  recipe: Recipe;
  onClose: () => void;
  setLiked: React.Dispatch<React.SetStateAction<number>>;
  liked: number;
}

/**
 * Displays a list of Strings as a 2-column table, dividing items between
 * the 2 columns.
 *
 * @param items the list of items to divide and display
 * @returns a 2-column table with items divided between the 2 columns
 */
const TwoColumnTable = (items: string[]) => {
  return (
    <div aria-label="ingredients-table" className="two-column-table">
      <div aria-label="ingredients-table-column" className="column">
        {/* 1st half of items in the first column */}
        {items.slice(0, Math.ceil(items.length / 2)).map((item, index) => (
          <div key={index} aria-label="ingredient" className="table-item">
            {item}
          </div>
        ))}
      </div>
      <div aria-label="ingredients-table-column" className="column">
        {/* 2nd half of items in the second column */}
        {items.slice(Math.ceil(items.length / 2)).map((item, index) => (
          <div key={index} aria-label="ingredient" className="table-item">
            {item}
          </div>
        ))}
      </div>
    </div>
  );
};

const InfoView: React.FC<InfoViewProps> = ({
  recipe,
  onClose,
  setLiked,
  liked,
}) => {
  return (
    <div aria-label="recipe-info-view-popup-overlay" className="popup-overlay">
      <div
        aria-label="recipe-info-view-popup-content"
        className="popup-content"
      >
        <div aria-label="recipe-title" className="recipe-title">
          {recipe.name}
          <button
            aria-label="close-recipe-info-popup-button"
            className="close-button"
            onClick={onClose}
          >
            X
          </button>
        </div>
        <div
          aria-label="recipe-image-grid-container"
          className="grid-container"
        >
          <div
            aria-label="recipe-image-container"
            className="recipe-image-container"
          >
            <img
              src={recipe.image}
              alt={recipe.name}
              aria-label="recipe-image"
              className="recipe-image"
            />
          </div>
          <div aria-label="recipe-info" className="recipe-info">
            <div aria-label="recipe-info-container" className="info-container">
              <p>
                <strong>Source:</strong> {recipe.credit}
              </p>
              <p>
                <strong>Cuisine:</strong> {recipe.cuisine}
              </p>
              <p>
                <strong>Time:</strong> {recipe.time}
              </p>
              <p>
                <strong>Servings:</strong> {recipe.servings}
              </p>
              <div>
                <strong>Ingredients: </strong>
                {TwoColumnTable(recipe.ingredients)}
              </div>
            </div>
          </div>
        </div>
        <div aria-label="recipe-steps" className="steps">
          <ol style={{ listStyleType: "none" }}>
            {recipe.instructions.map((step, index) => (
              <li key={index}>
                <input
                  type="checkbox"
                  id={`step-${index}`}
                  onChange={(event) => {
                    const parent = event.target.parentNode;
                    if (parent) {
                      const label = parent.querySelector("label");
                      if (label) {
                        label.classList.toggle("strikethrough");
                      }
                    }
                  }}
                />
                <label htmlFor={`step-${index}`}>
                  {index + 1}. {step}
                </label>
              </li>
            ))}
          </ol>
        </div>
        {/* Heart button for toggling like status */}
        <div
          aria-label="like-button-container-info"
          className="like-button-container-info"
        >
          <LikeButton
            aria-label="like-button"
            liked={liked}
            setLiked={setLiked}
            canLike={true}
          />
        </div>
      </div>
    </div>
  );
};

export default InfoView;
