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
import Profile from "./Profile";
import About from "./About";

function Master() {
  const recipeHistoryToPassIn = mockRecipeHistory();
  console.log(mockRecipeList());
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />

          <Route
            path="/calendar"
            element={<Calendar recipeHistory={recipeHistoryToPassIn} />}
          />
          <Route
            path="/profile"
            element={
              <Profile
                name={"Nim Telson"}
                experienceLevel={"3"}
                familySize={1}
                diet={"Vegan"}
                intolerances={["Mushroom", "Shellfish"]}
                likedRecipes={mockRecipeList().filter(
                  (recipe) => recipe.liked == 1
                )}
                dislikedRecipes={mockRecipeList().filter(
                  (recipe) => recipe.liked == 2
                )}
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
