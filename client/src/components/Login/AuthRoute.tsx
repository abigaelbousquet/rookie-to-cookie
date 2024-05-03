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
  const [masterLoaded, setMasterLoaded] = useState(false);
  const [masterComponent, setMasterComponent] = useState<React.ReactNode>(null);
  useEffect(() => {
    const fetchData = async () => {
      //   if (!authing && import.meta.env.VITE_APP_NODE_ENV === "test") {
      //     setAuthing(1);
      //   }
      const masterElement = await Master({
        setAuthing: setAuthing,
      }); // Load Master component
      setMasterComponent(masterElement);
      setMasterLoaded(true);
    };
    fetchData();
  }, [authing]);

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
        />
      ) : null}
      <LoginLogout authing={authing} setAuthing={setAuthing} />
    </>
  );
};
export default AuthRoute;
