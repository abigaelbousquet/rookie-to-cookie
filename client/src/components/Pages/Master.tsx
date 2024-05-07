import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./Navbar";
import Home from "./Home";
import Profile from "./Profile";
import About from "./About";
import CalendarPage from "./Calendar";
import { RecipeProvider } from "../RecipeCard/RecipeProvider";

/**
 * This component houses the nav bar
 * @returns High level webapp component
 */
const Master: React.FC = () => {
  return (
    <RecipeProvider>
      <Router>
        <div className="App">
          <Navbar handleLogout={() => null} />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/calendar" element={<CalendarPage />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/about" element={<About></About>}></Route>
          </Routes>
        </div>
      </Router>
    </RecipeProvider>
  );
};
export default Master;
