import { useState } from "react";
import LoginLogout from "./LoginLogout";
import React from "react";
import Home from "../Home";
import { AccountCreation } from "./AccountCreation";
import { getAuth, onAuthStateChanged } from "firebase/auth";

function AuthRoute() {
  const [authing, setAuthing] = useState(false);
  const [showPopup, setShowPopup] = useState<boolean>(false);

  // USEFUL FOR PLAYWRIGHT TESTING PURPOSES: auto sets authing to true in test environment
  if (!authing && import.meta.env.VITE_APP_NODE_ENV === "test") {
    setAuthing(true);
  }

  return (
    <>
      <LoginLogout authing={authing} setAuthing={setAuthing} />
      {authing ? <AccountCreation onClose={() => setShowPopup(false)} /> : null}
    </>
  );
}

export default AuthRoute;
