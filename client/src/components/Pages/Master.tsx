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

interface profileLoadedProps {
  loaded: boolean;
  setLoaded: React.Dispatch<React.SetStateAction<boolean>>;
}

async function Master(props: profileLoadedProps) {
  const recipeHistoryToPassIn = mockRecipeHistory();
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
              <Profile loaded={props.loaded} setLoaded={props.setLoaded} />
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
