import React, { useState } from "react";
import "../../styles/profile.css";
import { getDislikes, getLikes, getUser } from "../../utils/api";
import Recipe from "../RecipeCard/Recipe";
import RecipeCard from "../RecipeCard/RecipeCard";
import { AccountCreation } from "../Login/AccountCreation";
import { ControlledInput } from "../Login/ControlledInput";

export interface ProfileProps {
  loaded: boolean;
  setLoaded: React.Dispatch<React.SetStateAction<boolean>>;
}
interface User {
  name: string;
  experienceLevel: string;
  //familySize: number;
  diet: string;
  intolerances: string[];
  likedRecipes: Recipe[];
  dislikedRecipes: Recipe[];
}
async function getProfileProps() {
  const response = await getLikes();
  const likes = response["Recipes"];
  const response2 = await getDislikes();
  const dislikes = response2["Recipes"];
  const user: User = await getUser();
  const propsToPass = {
    name: user.name,
    experienceLevel: user.experienceLevel,
    //familySize: user.familySize,
    diet: user.diet,
    intolerances: user.intolerances,
    likedRecipes: likes,
    dislikedRecipes: dislikes,
  };
  return propsToPass;
}
const ProfilePage: React.FC<ProfileProps> = (props) => {
  const [showPopup, setShowPopup] = useState<boolean>(false);
  const user = await getProfileProps();
  if (props.loaded) {
    return (
      <div className="profile-container">
        <div className="left-side">
          <div className={"exp" + user.experienceLevel}></div>
          <h1 className="name-display">{user.name}</h1>
          <div>
            <h3>Diet:</h3>
            <p>{user.diet}</p>
          </div>
          <div>
            <h3>Intolerances:</h3>
            <p>{user.intolerances.toString()}</p>
          </div>
          <h4>{"Cooking for " + 1}</h4>
          {/* //<button onClick={editProfile}>Edit</button> */}
        </div>
        <div className="right-side">
          <div className="likes">
            <h3>Liked Recipes:</h3>
            <div>
              {user.likedRecipes.map((recipe) => (
                <div>
                  <RecipeCard recipe={recipe} setShowPopup={setShowPopup} />
                </div>
              ))}
            </div>
          </div>
          <div className="dislikes">
            <h3>Disliked Recipes:</h3>
            {user.dislikedRecipes.map((recipe) => (
              <div>
                <RecipeCard recipe={recipe} setShowPopup={setShowPopup} />
              </div>
            ))}
          </div>
        </div>
      </div>
    );
  } else {
    return <div></div>;
  }
};

export default ProfilePage;
