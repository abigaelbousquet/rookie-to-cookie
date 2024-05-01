import React, { useEffect, useState } from "react";
import "../../styles/profile.css";
import { getDislikes, getLikes, getUser } from "../../utils/api";
import Recipe from "../RecipeCard/Recipe";
import RecipeCard from "../RecipeCard/RecipeCard";
import { AccountCreation } from "../Login/AccountCreation";
import { ControlledInput } from "../Login/ControlledInput";
import { parseRecipe } from "../RecipeCard/ParseRecipe";

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
    const likesParsed = likes.map((value) => {
      return parseRecipe(value, 1);
    });
    const response2 = await getDislikes();
    const dislikes = response2["Recipes"];
    const dislikesParsed = dislikes.map((value) => {
      return parseRecipe(value, 2);
    });
    const userJson: User = await getUser();
    const userList = userJson["User"];
    const userData = userList[0];
    console.log(userData);
    return {
      name: userData.name,
      experienceLevel: userData["exp"],
      diet: userData.diet,
      fam_size: userData["familySize"],
      intolerances: userData.intolerances,
      likedRecipes: likesParsed,
      dislikedRecipes: dislikesParsed,
    };
  };

  if (!user) {
    return <div>Loading...</div>;
  }
  function getExp(level: string): string {
    let exp: string;
    switch (level) {
      case "1":
        exp = "Novice";
        break;
      case "2":
        exp = "Beginner";
        break;
      case "3":
        exp = "Experienced";
        break;
      case "4":
        exp = "Master";
        break;
      default:
        exp = "";
        break;
    }
    return exp + " Chef";
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
                <RecipeCard
                  recipe={recipe}
                  setShowPopup={setShowPopup}
                  saved={true}
                />
              </div>
            ))}
          </div>
        </div>
        <div className="dislikes">
          <h3>Disliked Recipes:</h3>
          {user.dislikedRecipes.map((recipe) => (
            <div>
              <RecipeCard
                recipe={recipe}
                setShowPopup={setShowPopup}
                saved={true}
              />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
