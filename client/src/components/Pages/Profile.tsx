import React, { useState, useEffect } from "react";
import "../../styles/profile.css";
import { getDislikes, getLikes, getUser } from "../../utils/api";
import RecipeCard from "../RecipeCard/RecipeCard";

export interface ProfileProps {
  loaded: boolean;
  setLoaded: React.Dispatch<React.SetStateAction<boolean>>;
}

interface User {
  name: string;
  experienceLevel: string;
  diet: string;
  intolerances: string[];
  likedRecipes: any[]; // Adjust this based on your Recipe type
  dislikedRecipes: any[]; // Adjust this based on your Recipe type
}

const ProfilePage: React.FC<ProfileProps> = (props) => {
  const [user, setUser] = useState<User | null>(null);
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
    return {
      name: userData.name,
      experienceLevel: userData.experienceLevel,
      diet: userData.diet,
      intolerances: userData.intolerances,
      likedRecipes: null, //TODO: fix
      dislikedRecipes: null,
    };
  };

  if (!user) {
    return <div>Loading...</div>;
  }

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
          <p>
            {Array.isArray(user.intolerances)
              ? user.intolerances.join(", ")
              : ""}
          </p>
        </div>
        <h4>{"Cooking for 1"}</h4>
      </div>
      <div className="right-side">
        <div className="likes">
          <h3>Liked Recipes:</h3>
          <div>
            {user.likedRecipes &&
              user.likedRecipes.map((recipe, index) => (
                <div key={index}>
                  <RecipeCard recipe={recipe} setShowPopup={setShowPopup} />
                </div>
              ))}
          </div>
        </div>
        <div className="dislikes">
          <h3>Disliked Recipes:</h3>
          {user.dislikedRecipes &&
            user.dislikedRecipes.map((recipe, index) => (
              <div key={index}>
                <RecipeCard recipe={recipe} setShowPopup={setShowPopup} />
              </div>
            ))}
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
