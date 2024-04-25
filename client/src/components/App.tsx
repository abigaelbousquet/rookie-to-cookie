import { useState } from "react";
import "../styles/App.css";
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./Navbar";
import Home from "./Pages/Home";

import { initializeApp } from "firebase/app";
import AuthRoute from "./Login/AuthRoute";
import {
  mockRecipeHistory,
  mockEmptyRecipeList,
} from "../data/MockedRecipeHistory";
import Calendar from "./Pages/Calendar";

function App() {
  const recipeHistoryToPassIn = mockEmptyRecipeList();
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
          {/* Add more routes as needed */}
        </Routes>
      </div>
    </Router>
  );
}

export default App;
