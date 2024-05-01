import React, { useEffect, useState } from "react";
import "../../styles/profile.css";
import { getDislikes, getLikes, getUser } from "../../utils/api";
import Recipe from "../RecipeCard/Recipe";
import RecipeCard from "../RecipeCard/RecipeCard";
import { AccountCreation } from "../Login/AccountCreation";
import { ControlledInput } from "../Login/ControlledInput";

interface ProfileProps {
  loaded: boolean;
  setLoaded: React.Dispatch<React.SetStateAction<boolean>>;
}
export interface User {
  name: string;
  experienceLevel: string;
  diet: string;
  fam_size: string;
  intolerances: string[];
  likedRecipes: any[]; // Adjust this based on your Recipe type
  dislikedRecipes: any[]; // Adjust this based on your Recipe type
}

const ProfilePage: React.FC<ProfileProps> = (props) => {
  const [showPopup, setShowPopup] = useState<boolean>(false);
  const [user, setUser] = useState<User | null>(null);

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
    const userList = userJson["User"];
    const userData = userList[0];
    return {
      name: userData.name,
      experienceLevel: userData["exp"],
      diet: userData.diet,
      fam_size: userData["famSize"],
      intolerances: userData.intolerances,
      likedRecipes: [],
      dislikedRecipes: [],
    };
  };

  if (!user) {
    return <div>Loading...</div>;
  }
  function getExp(level: string): string {
    switch (level) {
      case "1":
        return "Novice Chef";
      case "2":
        return "Beginner Chef";
      case "3":
        return "Experienced Chef";
      case "4":
        return "Master Chef";
      default:
        return "Novice Chef";
    }
  }

  return (
    <div className="profile-container">
      <div className="left-side">
        <div className={"exp" + user.experienceLevel}></div>
        <h1 className="name-display">{user.name}</h1>
        <h3 className="exp_description">{getExp(user.experienceLevel)}</h3>
        <div>
          <h3 className="diet">Diet:</h3>
          <p>{user.diet}</p>
        </div>
        <div>
          <h3 className="diet">Intolerances:</h3>
          <p>{user.intolerances}</p>
        </div>
        <h4>{"Cooking for " + user.fam_size}</h4>
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
};

export default ProfilePage;
