import React, { useState } from "react";
import "../../styles/profile.css";
import { getDislikes, getLikes, getUser } from "../../utils/api";
import Recipe from "../RecipeCard/Recipe";
import RecipeCard from "../RecipeCard/RecipeCard";
import { AccountCreation } from "../Login/AccountCreation";
import { ControlledInput } from "../Login/ControlledInput";

interface ProfileProps {
  name: string;
  experienceLevel: string;
  familySize: number;
  diet: string;
  intolerances: string[];
  likedRecipes: Recipe[];
  dislikedRecipes: Recipe[];
}
interface User {
  name: string;
  experienceLevel: string;
  familySize: number;
  diet: string;
  intolerances: string[];
  likedRecipes: Recipe[];
  dislikedRecipes: Recipe[];
}

async function getProfileProps() {
  const user: User = await getUser();
  const propsToPass: ProfileProps = {
    name: user.name,
    experienceLevel: user.experienceLevel,
    familySize: user.familySize,
    diet: user.diet,
    intolerances: user.intolerances,
    likedRecipes: await getLikes(),
    dislikedRecipes: await getDislikes(),
  };
}

const ProfilePage: React.FC<ProfileProps> = (props) => {
  console.log("exp" + props.experienceLevel);
  const [showPopup, setShowPopup] = useState<boolean>(false);
  return (
    <div className="profile-container">
      <div className="left-side">
        <div className={"exp" + props.experienceLevel}></div>
        <h3>{props.name}</h3>
        <h3>{"Diet: " + props.diet}</h3>
        <h3>{"Intolerances:" + props.intolerances}</h3>
        <h3>{"Family Size: " + props.familySize}</h3>
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
        {/* Liked Recipes */}
        {/* Disliked Recipes */}
      </div>
    </div>
  );
};

export default ProfilePage;
