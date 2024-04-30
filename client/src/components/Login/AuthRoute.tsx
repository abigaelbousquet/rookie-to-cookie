import React, { useState, useEffect } from "react";
import Master from "../Pages/Master";
import { AccountCreation } from "./AccountCreation";
import LoginLogout from "./LoginLogout";

const AuthRoute = () => {
  const [authing, setAuthing] = useState(0);
  const [showPopup, setShowPopup] = useState<boolean>(false);
  const [masterLoaded, setMasterLoaded] = useState(false);
  const [masterComponent, setMasterComponent] = useState<React.ReactNode>(null);
  const [profile, profileLoaded] = useState(false);
  useEffect(() => {
    const fetchData = async () => {
      if (!authing && import.meta.env.VITE_APP_NODE_ENV === "test") {
        setAuthing(1);
      }
      const masterElement = await Master({
        loaded: profile,
        setLoaded: profileLoaded,
      }); // Load Master component
      setMasterComponent(masterElement);
      setMasterLoaded(true);
    };
    fetchData();
  }, []);

  return (
    <>
      <div className="App-header">
        <h2>Rookie To Cookie</h2>
        <div className="logo"></div>
      </div>

      {authing === 1 && masterLoaded ? masterComponent : null}
      {authing === 2 ? (
        <AccountCreation
          onClose={() => setShowPopup(false)}
          setAuthing={setAuthing}
          setLoaded={profileLoaded}
        />
      ) : null}
      <LoginLogout authing={authing} setAuthing={setAuthing} />
    </>
  );
};

export default AuthRoute;
