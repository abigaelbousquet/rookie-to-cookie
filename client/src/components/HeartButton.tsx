import React from "react";
import "../styles/HeartButton.css";

interface HeartButtonProps {
  isLiked: boolean;
  onClick: () => void;
  name: string; // Add the 'name' attribute to the interface
}

const HeartButton: React.FC<HeartButtonProps> = ({ isLiked, onClick }) => {
  return (
    <div
      className={`large-font text-center top-20 ${isLiked ? "liked" : ""}`}
      onClick={onClick}
    >
      <ion-icon name="heart">
        <div className="red-bg"></div>
      </ion-icon>
    </div>
  );
};

export default HeartButton;

// import React from "react";

// interface HeartButtonProps {
//   isLiked: boolean;
//   onClick: () => void;
//   name: string; // Add the 'name' attribute to the interface
// }

// const HeartButton: React.FC<HeartButtonProps> = ({ isLiked, onClick }) => {
//   return (
//     <div className="large-font text-center top-20">
//       <ion-icon

//         className={isLiked ? "active" : ""}
//         onClick={onClick}
//         name="heart" // Use the 'name' attribute here
//       ></ion-icon>
//     </div>
//   );
// };

// export default HeartButton;
