import React, { useEffect, useState } from "react";
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
import CalendarPage from "./Calendar";
import { clearUser } from "../../utils/api";

const Master: React.FC = () => {
  return (
    <Router>
      <div className="App">
        <Navbar handleLogout={() => null} />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/calendar" element={<CalendarPage />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/about" element={<About></About>}></Route>
          {/* Add more routes as needed */}
        </Routes>
      </div>
    </Router>
  );
};
export default Master;
