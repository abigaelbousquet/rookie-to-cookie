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

const ProfilePage: React.FC<ProfileProps> = (props) => {
  console.log("exp" + props.experienceLevel);
  const [showPopup, setShowPopup] = useState<boolean>(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const userData = await getProfileData();
        setUser(userData);
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };

    fetchData();
  }, []);

  const getProfileData = async () => {
    const response = await getLikes();
    const likes = response["Recipes"];
    const response2 = await getDislikes();
    const dislikes = response2["Recipes"];
    const userJson: User = await getUser();
    const userData = userJson["User"];
    console.log(userData);
    return {
      name: userData.name,
      experienceLevel: userData["exp"],
      diet: userData.diet,
      intolerances: userData.intolerances,
      likedRecipes: [], //TODO: fix
      dislikedRecipes: [],
    };
  };

  if (!user) {
    return <div>Loading...</div>;
  }

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
        <h4>{"Cooking for " + props.familySize}</h4>
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
