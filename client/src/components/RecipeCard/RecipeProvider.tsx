import React, { createContext, useState, useContext, useEffect } from "react";
import Recipe from "./Recipe";
import {
  addDislike,
  addLike,
  removeDislike,
  removeLike,
  getLikes,
  getDislikes,
} from "../../utils/api";

interface RecipeContextType {
  likedRecipes: Recipe[];
  dislikedRecipes: Recipe[];
  addLikedRecipe: (recipeId: string) => void;
  removeLikedRecipe: (recipeId: string) => void;
  addDislikedRecipe: (recipeId: string) => void;
  removeDislikedRecipe: (recipeId: string) => void;
}

const RecipeContext = createContext<RecipeContextType>({
  likedRecipes: [],
  dislikedRecipes: [],
  addLikedRecipe: () => {},
  removeLikedRecipe: () => {},
  addDislikedRecipe: () => {},
  removeDislikedRecipe: () => {},
});

export const RecipeProvider = ({ children }) => {
  const [likedRecipes, setLikedRecipes] = useState<Recipe[]>([]);
  const [dislikedRecipes, setDislikedRecipes] = useState<Recipe[]>([]);

  useEffect(() => {
    // Fetch and update liked recipes
    const fetchLikedRecipes = async () => {
      const response = await getLikes(); // Fetch liked recipes from API
      const likes = response["Recipes"];
      setLikedRecipes(likes);
    };

    // Fetch and update disliked recipes
    const fetchDislikedRecipes = async () => {
      const response = await getDislikes(); // Fetch disliked recipes from API
      const dislikes = response["Recipes"];
      setDislikedRecipes(dislikes);
    };

    fetchLikedRecipes();
    fetchDislikedRecipes();
  }, []); // Run only once on component mount

  const addLikedRecipe = async (recipeId) => {
    setLikedRecipes([...likedRecipes, recipeId]);
    await addLike(recipeId);
  };

  const removeLikedRecipe = async (recipeId) => {
    setLikedRecipes(likedRecipes.filter((recipe) => recipe.id !== recipeId));
    await removeLike(recipeId);
  };

  const addDislikedRecipe = async (recipeId) => {
    setDislikedRecipes([...dislikedRecipes, recipeId]);
    await addDislike(recipeId);
  };

  const removeDislikedRecipe = async (recipeId) => {
    setDislikedRecipes(
      dislikedRecipes.filter((recipe) => recipe.id !== recipeId)
    );
    await removeDislike(recipeId);
  };

  return (
    <RecipeContext.Provider
      value={{
        likedRecipes,
        dislikedRecipes,
        addLikedRecipe,
        removeLikedRecipe,
        addDislikedRecipe,
        removeDislikedRecipe,
      }}
    >
      {children}
    </RecipeContext.Provider>
  );
};

export const useRecipeContext = () => useContext(RecipeContext);
