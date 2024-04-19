import { useState } from "react";
import "../styles/App.css";
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./Navbar";
import Home from "./Home/Home";
import { initializeApp } from "firebase/app";
import AuthRoute from "./Login/AuthRoute";

// const firebaseConfig = {
//   apiKey: process.env.API_KEY,
//   authDomain: process.env.AUTH_DOMAIN,
//   projectId: process.env.PROJECT_ID,
//   storageBucket: process.env.STORAGE_BUCKET,
//   messagingSenderId: process.env.MESSAGING_SENDER_ID,
//   appId: process.env.APP_ID,
//   measurementId: process.env.MEASUREMENT_ID,
// };
// initializeApp(firebaseConfig);

/**
 * This is the highest level component!
 */
// function App() {
//   return (
//     //Yes, by putting the Navbar component in the App component, you ensure that the navigation bar appears on every page of your application, as long as those pages are rendered within the App component's context. This is a common pattern for creating a consistent layout across multiple pages in a React application.
//     <div className="App">
//       <p className="App-header">
//         <h2>Rookie To Cookie</h2>
//       </p>
//       {/* <AuthRoute /> */}
//     </div>
//   );
// }

// export default App;

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          {/* Add more routes as needed */}
        </Routes>
      </div>
    </Router>
  );
}

export default App;

/**
 * This function represents the highest level component of the Mock
 * which represents the frontend Mock login page
 */
// function App() {
//   const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);

//   return (
//     <div className="App">
//       <p className="App-header">
//         <h1>🔥Mock🔥</h1>
//         <LoginButton isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
//       </p>

//       {isLoggedIn && <REPL />}
//     </div>
//   );
// }

// export default App;
