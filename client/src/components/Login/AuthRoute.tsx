import { useState } from "react";
import LoginLogout from "./LoginLogout";
import React from "react";
import Home from "../Home";
import { AccountCreation } from "./AccountCreation";
import { getAuth, onAuthStateChanged } from "firebase/auth";

const AuthRoute = () => {
  const [authing, setAuthing] = useState(0);
  const [showPopup, setShowPopup] = useState<boolean>(false);

  // USEFUL FOR PLAYWRIGHT TESTING PURPOSES: auto sets authing to true in test environment
  if (!authing && import.meta.env.VITE_APP_NODE_ENV === "test") {
    setAuthing(1);
  }

  return (
    <>
      <LoginLogout authing={authing} setAuthing={setAuthing} />
      {authing === 1 ? <Home></Home> : null}
      {authing === 2 ? (
        <AccountCreation
          onClose={() => setShowPopup(false)}
          setAuthing={setAuthing}
        />
      ) : null}
    </>
  );
};
export default AuthRoute;
