import { useState } from "react";
import "../styles/App.css";
import React from "react";
import Navbar from "./Navbar";
import Home from "./Pages/Home";

import { initializeApp } from "firebase/app";
import AuthRoute from "./Login/AuthRoute";
import {
  mockRecipeHistory,
  mockEmptyRecipeList,
} from "../data/MockedRecipeHistory";
import Calendar from "./Pages/Calendar";

const firebaseConfig = {
  apiKey: process.env.API_KEY,
  authDomain: process.env.AUTH_DOMAIN,
  projectId: process.env.PROJECT_ID,
  storageBucket: process.env.STORAGE_BUCKET,
  messagingSenderId: process.env.MESSAGING_SENDER_ID,
  appId: process.env.APP_ID,
  measurementId: process.env.MEASUREMENT_ID,
};
initializeApp(firebaseConfig);
/**
 * This is the highest level component!
 */
function App() {
  return (
    <div className="App">
      <AuthRoute />
    </div>
  );
}

export default App;
