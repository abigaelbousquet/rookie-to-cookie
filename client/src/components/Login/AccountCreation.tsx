// AccountCreationPopup.tsx
import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import "./../../styles/login.css";
import Select from "react-select";
import React, { Dispatch, SetStateAction, useEffect, useState } from "react";
import { ControlledInput } from "../SelectionTypes/ControlledInput";
import Creatable from "react-select/creatable";
import { addUser } from "../../utils/api";
import { diets, intoleranceOptions } from "../../data/Spoonacular";
import IntegerInput from "../SelectionTypes/IntegerInput";

/**
 * This interface includes the attributes stored to firestore for a user profile account
 */
export interface profileProps {
  name: string;
  exp: string;
  diet: string;
  fam_size: string;
  intolerances: string[];
}

/**
 * The authing setStateAction is passed in by the AuthRoute class to switch to logged in mode after account is created.
 */
interface acctProps {
  setAuthing: React.Dispatch<React.SetStateAction<number>>;
}

/**
 * This component is the account creation seen by a new user upon entering an email and password
 * @param param0 setAuthing
 * @returns Account creation component
 */
export const AccountCreation: React.FC<acctProps> = ({ setAuthing }) => {
  const [name, setName] = useState("");
  const [exp, setExp] = useState("1");
  const [diet, setDiet] = useState("");
  const [allergen, setAllergen] = useState<{ label: string; value: string }[]>(
    []
  );
  const [fam_size, setFam_Size] = useState(1);
  const heightMarks = {
    1: "novice",
    2: "beginner",
    3: "moderate",
    4: "master",
  }; //marks for the experience slider

  useEffect(() => {
    // Add event listener to listen for Enter key press on the document body
    document.body.addEventListener("keypress", handleKeyPress);
    return () => {
      // Cleanup: remove event listener when component unmounts
      document.body.removeEventListener("keypress", handleKeyPress);
    };
  }, []);

  const handleKeyPress = (event: KeyboardEvent) => {
    if (event.key === "Enter") {
      // Press enter to trigger login button click
      handleSubmit({
        name: name,
        exp: exp,
        diet: diet,
        fam_size: fam_size.toString(),
        intolerances: allergen.map((val) => val.value),
      });
    }
  };

  const handleSubmit = async (props: profileProps) => {
    // Handle form submission here, e.g., send data to server
    if (
      props.name === undefined ||
      props.name === "" ||
      props.name === null ||
      props.exp === undefined ||
      props.exp === "" ||
      props.exp === null ||
      props.fam_size === undefined
    ) {
      alert("Please enter name, experience, and family size.");
    } else {
      try {
        await addUser(props); //add user to firestore
        setAuthing(1); //log in
      } catch (error) {
        alert(error); //display error
      }
    }
  };
  return (
    <div className="popup">
      <div className="popup-inner">
        <div className="acct-inmost">
          <div className="popup-header">
            <div id="account-header">
              <h2>Create Account</h2>
            </div>
          </div>
          <div className="form">
            <div className="acct-elt">
              <legend>Name:</legend>
              <ControlledInput
                type="text"
                value={name}
                setValue={setName}
                ariaLabel={"Name"}
                placeholder={"Nim Telson"}
                styleID={"input-box"}
              />
            </div>
            <div className="exp-slider">
              <legend>Cooking Experience:</legend>
              <Slider
                defaultValue={1}
                min={1}
                max={4}
                onChange={(e) => setExp(e.toString())}
                marks={heightMarks}
              />
            </div>
            <div className="acct-elt">
              <legend>Diet:</legend>
              <div className="selector">
                <Select
                  options={diets}
                  onChange={(opt) => setDiet(opt.value)}
                  defaultValue={"None"}
                />
              </div>
            </div>
            <div className="acct-elt">
              <legend>Intolerances:</legend>
              <div className="selector">
                <Creatable
                  options={intoleranceOptions}
                  isMulti
                  onChange={(opt) => {
                    console.log(allergen);
                    setAllergen(opt);
                  }}
                />
              </div>
            </div>
            <div className="acct-elt">
              <legend>Family Size:</legend>
              <IntegerInput
                value={fam_size}
                onChange={(num) => setFam_Size(num)}
              ></IntegerInput>
            </div>

            <button
              className="butt"
              onClick={() =>
                handleSubmit({
                  name: name,
                  exp: exp,
                  diet: diet,
                  fam_size: fam_size.toString(),
                  intolerances: allergen.map((val) => val.value),
                })
              }
              type="submit"
            >
              Create Account
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
