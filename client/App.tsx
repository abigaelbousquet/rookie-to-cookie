import { initializeApp } from "firebase/app";
import "../styles/App.css";
import AuthRoute from "./AuthRoute";
import React from "react";

const firebaseConfig = {
  apiKey: process.env.API_KEY,
  authDomain: process.env.AUTH_DOMAIN,
  projectId: process.env.PROJECT_ID,
  storageBucket: process.env.STORAGE_BUCKET,
  messagingSenderId: process.env.MESSAGING_SENDER_ID,
  appId: process.env.APP_ID,
};

initializeApp(firebaseConfig);
/**
 * This is the highest level component!
 */
function App() {
  return (
    <div className="App">
      <p aria-label="Title header" className="App-header">
        <h1>Term Project</h1>
      </p>
      <AuthRoute />
    </div>
  );
}

export default App;
