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

/**
 * Displays a list of Strings as a 2-column table, dividing items between
 * the 2 columns.
 *
 * @param items the list of items to divide and display
 * @returns a 2-column table with items divided between the 2 columns
 */
const TwoColumnTable = (items: string[]) => {
  return (
    <div className="two-column-table">
      <div className="column">
        {/* 1st half of items in the first column */}
        {items.slice(0, Math.ceil(items.length / 2)).map((item, index) => (
          <div key={index} className="table-item">
            {item}
          </div>
        ))}
      </div>
      <div className="column">
        {/* 2nd half of items in the second column */}
        {items.slice(Math.ceil(items.length / 2)).map((item, index) => (
          <div key={index} className="table-item">
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
    <div className="popup-overlay">
      <div className="popup-content">
        <div className="recipe-title">
          {recipe.name}
          <button className="close-button" onClick={onClose}>
            X
          </button>
        </div>
        <div className="grid-container">
          <div className="recipe-image-container">
            <img
              src={recipe.image}
              alt={recipe.name}
              className="recipe-image"
            />
          </div>
          <div className="recipe-info">
            <div className="info-container">
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
              <p>
                <strong>Ingredients: </strong>
                {/* {recipe.ingredients.map((ingredient, index) => {
                  return <p>{ingredient}</p>;
                })} */}
                {/* {recipe.ingredients.join(", ")} */}
                {TwoColumnTable(recipe.ingredients)}
              </p>
            </div>
          </div>
        </div>
        <div className="steps">
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
        <div className="like-button-container-info">
          <LikeButton liked={liked} setLiked={setLiked} canLike={true} />
        </div>
      </div>
    </div>
  );
};

export default InfoView;
