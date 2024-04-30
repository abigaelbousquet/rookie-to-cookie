import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import {
  mockEmptyRecipeList,
  mockRecipeHistory,
  mockRecipeList,
} from "../../data/MockedRecipeHistory";
import Navbar from "../Navbar";
import Calendar from "./Calendar";
import Home from "./Home";
import Profile, { ProfileProps } from "./Profile";
import About from "./About";
import CalendarPage from "./Calendar";
import { getDislikes, getLikes, getUser } from "../../utils/api";
import Recipe from "../RecipeCard/Recipe";
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
  const propsToPass: ProfileProps = {
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

async function Master() {
  const recipeHistoryToPassIn = mockRecipeHistory();
  const user = await getProfileProps();
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />

          <Route
            path="/calendar"
            element={<CalendarPage recipeHistory={recipeHistoryToPassIn} />}
          />
          <Route
            path="/profile"
            element={
              <Profile
                name={user.name}
                experienceLevel={user.experienceLevel}
                // familySize={user.familySize}
                diet={user.diet}
                intolerances={user.intolerances}
                likedRecipes={user.likedRecipes}
                dislikedRecipes={user.dislikedRecipes}
              />
            }
          />
          <Route path="/about" element={<About></About>}></Route>
          {/* Add more routes as needed */}
        </Routes>
      </div>
    </Router>
  );
}
export default Master;
