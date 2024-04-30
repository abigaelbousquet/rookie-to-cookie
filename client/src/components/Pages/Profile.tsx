import React, { useState } from "react";
import "../../styles/profile.css";
import { getDislikes, getLikes, getUser } from "../../utils/api";
import Recipe from "../RecipeCard/Recipe";
import RecipeCard from "../RecipeCard/RecipeCard";
import { AccountCreation } from "../Login/AccountCreation";
import { ControlledInput } from "../Login/ControlledInput";

export interface ProfileProps {
  name: string;
  experienceLevel: string;
  //familySize: number;
  diet: string;
  intolerances: string[];
  likedRecipes: Recipe[];
  dislikedRecipes: Recipe[];
}

const ProfilePage: React.FC<ProfileProps> = (props) => {
  console.log("exp" + props.experienceLevel);
  const [showPopup, setShowPopup] = useState<boolean>(false);

  return (
    <div className="profile-container">
      <div className="left-side">
        <div className={"exp" + props.experienceLevel}></div>
        <h1 className="name-display">{props.name}</h1>
        <div>
          <h3>Diet:</h3>
          <p>{props.diet}</p>
        </div>
        <div>
          <h3>Intolerances:</h3>
          <p>{props.intolerances.toString()}</p>
        </div>
        <h4>{"Cooking for " + 1}</h4>
        {/* //<button onClick={editProfile}>Edit</button> */}
      </div>
      <div className="right-side">
        <div className="likes">
          <h3>Liked Recipes:</h3>
          <div>
            {props.likedRecipes.map((recipe) => (
              <div>
                <RecipeCard recipe={recipe} setShowPopup={setShowPopup} />
              </div>
            ))}
          </div>
        </div>
        <div className="dislikes">
          <h3>Disliked Recipes:</h3>
          {props.dislikedRecipes.map((recipe) => (
            <div>
              <RecipeCard recipe={recipe} setShowPopup={setShowPopup} />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
