import { useEffect, useState } from "react";
import LoginLogout from "./LoginLogout";
import React from "react";
import { AccountCreation } from "./AccountCreation";
import { getAuth, onAuthStateChanged } from "firebase/auth";
import Home from "../Pages/Home";
import Master from "../Pages/Master";

const AuthRoute = () => {
  const [authing, setAuthing] = useState(0);
  const [showPopup, setShowPopup] = useState<boolean>(false);

  return (
    <>
      <div className="App-header">
        <h2>Rookie To Cookie</h2>
        <div className="logo"></div>
      </div>
      {authing === 1 ? <Master /> : null}
      {authing === 2 ? (
        <AccountCreation
          onClose={() => setShowPopup(false)}
          setAuthing={setAuthing}
        />
      ) : null}
      <LoginLogout authing={authing} setAuthing={setAuthing} />
    </>
  );
};
export default AuthRoute;
