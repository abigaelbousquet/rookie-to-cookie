import { useEffect, useState } from "react";
import LoginLogout from "./LoginLogout";
import React from "react";
import { AccountCreation } from "./AccountCreation";
import { getAuth, onAuthStateChanged } from "firebase/auth";
import Home from "../Pages/Home";
import Master from "../Pages/Master";

const AuthRoute = async () => {
  const [authing, setAuthing] = useState(0);
  const [masterComponent, setMasterComponent] = useState(<div></div>);
  const [showPopup, setShowPopup] = useState(false);
  async function fetchMasterComponent() {
    const component = await Master();
    setMasterComponent(component);
  }

  await fetchMasterComponent();
  // USEFUL FOR PLAYWRIGHT TESTING PURPOSES: auto sets authing to true in test environment
  if (!authing && import.meta.env.VITE_APP_NODE_ENV === "test") {
    setAuthing(1);
  }
  return (
    <>
      <div className="App-header">
        <h2>Rookie To Cookie</h2>
        <div className="logo"></div>
      </div>

      {authing === 1 ? { masterComponent } : null}
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
