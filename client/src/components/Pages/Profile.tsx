import React, { useEffect, useState } from "react";
import "../../styles/profile.css";
import { getDislikes, getLikes, getUser } from "../../utils/api";
import RecipeCard from "../RecipeCard/RecipeCard";
import { AccountUpdate } from "../Login/AccountUpdate";
import { parseRecipe } from "../../RecipeUtils/ParseRecipe";
import { useRecipeContext } from "../RecipeCard/RecipeProvider";

/**
 * This component houses the profile page
 */
export interface User {
  name: string;
  experienceLevel: string;
  diet: string;
  fam_size: number;
  intolerances: string[];
  likedRecipes: any[]; // Adjust this based on your Recipe type
  dislikedRecipes: any[]; // Adjust this based on your Recipe type
}

const ProfilePage: React.FC = () => {
  const [showPopup, setShowPopup] = useState<boolean>(false);
  const [user, setUser] = useState<User | null>(null);
  const { likedRecipes, dislikedRecipes } = useRecipeContext();

  //Gets and sets user data asynchronously
  useEffect(() => {
    let isMounted = true;

    const fetchData = async () => {
      try {
        const timer = setTimeout(async () => {
          const userData = await getProfileData();
          if (isMounted) {
            setUser(userData);
          }
        }, 500); // delayed so that it works on click instead of having the use effect work before updating data

        // Clear the timeout if the component unmounts before the delay
        return () => clearTimeout(timer);
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };

    fetchData();

    return () => {
      isMounted = false;
    };
  }, [likedRecipes, dislikedRecipes]);
  //Combines user profile data and likes/dislikes
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
      experienceLevel: userData.exp,
      diet: userData.diet,
      fam_size: userData.famSize,
      intolerances: userData.intolerances,
      likedRecipes: likes,
      dislikedRecipes: dislikes,
    };
  };

  //Displayed if still fetching data
  if (!user) {
    return <div>Loading...</div>;
  }
  function getExp(level: string): string {
    let exp: string;
    switch (parseInt(level)) {
      case 1:
        exp = "Novice";
        break;
      case 2:
        exp = "Beginner";
        break;
      case 3:
        exp = "Experienced";
        break;
      case 4:
        exp = "Master";
        break;
      default:
        exp = "Chef";
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
          <p>{user.intolerances.join(", ")}</p>
        </div>
        <h4>{"Cooking for " + user.fam_size}</h4>
        <button
          aria-label="update-account-button"
          className="update-acct-button"
          onClick={() => {
            setShowPopup(true);
          }}
        >
          Update Account
        </button>
        {/*Account update button*/}
        {showPopup && (
          <div className="popup-container">
            {showPopup && <AccountUpdate onClose={() => setShowPopup(false)} />}
          </div>
        )}
      </div>
      <div className="right-side">
        <div className="likes">
          <h3>Liked Recipes:</h3>
          {user.likedRecipes.map((recipe) => {
            if (dislikedRecipes.some((disliked) => disliked.id === recipe.id)) {
              return null; // Don't render if the recipe is disliked
            }
            return (
              <div key={recipe.id}>
                <RecipeCard
                  recipe={parseRecipe(recipe)}
                  setShowPopup={setShowPopup}
                  saved={true}
                  isLiked={1}
                />
              </div>
            );
          })}
        </div>
        <div className="dislikes">
          <h3>Disliked Recipes:</h3>
          {/*Recipe cards of disliked recipes*/}
          {user.dislikedRecipes.map((recipe) => (
            <div key={recipe.id}>
              <RecipeCard
                recipe={parseRecipe(recipe)}
                setShowPopup={setShowPopup}
                saved={true}
                isLiked={2}
              />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
