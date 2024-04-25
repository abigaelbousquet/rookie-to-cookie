import React from "react";
import Recipe from "./Recipe";
import "../../styles/InfoView.css";

interface InfoViewProps {
  recipe: Recipe;
  onClose: () => void;
  onToggleLike: () => void; // Callback function to toggle like status
}

const InfoView: React.FC<InfoViewProps> = ({
  recipe,
  onClose,
  onToggleLike,
}) => {
  return (
    <div className="popup-overlay">
      <div className="popup-content">
        <h2>{recipe.name}</h2>
        <p>{recipe.instructions}</p>
        {/* Heart button for toggling like status */}
        <button onClick={onToggleLike}>
          {recipe.liked ? "❤️ Liked" : "🤍 Not Liked"}
        </button>
        {/* Close button */}
        <button className="close-button" onClick={onClose}>
          X
        </button>
      </div>
    </div>
  );
};

export default InfoView;

// import React, { useState } from "react";
// import Recipe from "./Recipe";
// import "../../styles/InfoView.css";

// interface InfoViewProps {
//   recipe: Recipe;
//   onClose: () => void;
// }

// const InfoView: React.FC<InfoViewProps> = ({ recipe, onClose }) => {
//   const [editedRecipe, setEditedRecipe] = useState(recipe);

//   const handleInputChange = (
//     e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
//   ) => {
//     const { name, value } = e.target;
//     setEditedRecipe({ ...editedRecipe, [name]: value });
//   };

//   return (
//     <div className="popup-overlay">
//       <div className="popup-content">
//         <h2>Edit Recipe</h2>
//         <label>
//           Name:
//           <input
//             type="text"
//             name="name"
//             value={editedRecipe.name}
//             onChange={handleInputChange}
//           />
//         </label>
//         <label>
//           Cuisine:
//           <input
//             type="text"
//             name="cuisine"
//             value={editedRecipe.cuisine}
//             onChange={handleInputChange}
//           />
//         </label>
//         <label>
//           Instructions:
//           <textarea
//             name="instructions"
//             value={editedRecipe.instructions}
//             onChange={handleInputChange}
//           />
//         </label>
//         {/* Close button */}
//         <button className="close-button" onClick={onClose}>
//           X
//         </button>
//       </div>
//     </div>
//   );
// };

// export default InfoView;
