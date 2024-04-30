// AccountCreationPopup.tsx
import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import "./../../styles/login.css";
import Select from "react-select";

import React, { Dispatch, SetStateAction, useEffect, useState } from "react";
import { ControlledInput } from "./ControlledInput";
import Creatable from "react-select/creatable";
import { addUser } from "../../utils/api";
import { createUserWithEmailAndPassword } from "firebase/auth";
export interface profileProps {
  name: string;
  exp: string;
  diet: string;
  fam_size: string;
  intolerances: string[];
}
interface acctProps {
  onClose: () => void;
  setAuthing: React.Dispatch<React.SetStateAction<number>>;
}

export const AccountCreation: React.FC<acctProps> = ({
  onClose,
  setAuthing,
}) => {
  const [name, setName] = useState("");
  const [exp, setExp] = useState("");
  const [diet, setDiet] = useState("");
  const [allergen, setAllergen] = useState([""]);
  const [fam_size, setFam_Size] = useState("");
  const heightMarks = {
    1: "novice",
    2: "beginner",
    3: "moderate",
    4: "master",
  };
  const diets = [
    { label: "Vegetarian", value: "Vegetarian" },
    { label: "Vegan", value: "Vegan" },
    { label: "Pescetarian", value: "Pescetarian" },
    { label: "Paleo", value: "Paleo" },
    { label: "Primal", value: "Primal" },
  ];
  let intolerance = [
    { label: "Shellfish", value: "Shellfish" },
    { label: "Egg", value: "Egg" },
    { label: "Peanut", value: "Peanut" },
    { label: "Nut", value: "Nut" },
    { label: "Soy", value: "Soy" },
    { label: "Sesame", value: "Sesame" },
    { label: "Tree nut", value: "Tree nut" },
    { label: "Sulfite", value: "Sulfite" },
    { label: "Dairy", value: "Dairy" },
    { label: "Gluten", value: "Gluten" },
  ];
  // const handleUserKeyPress = (event: KeyboardEvent) => {
  //   if (event.key === "Enter") {
  //     console.log("submitted!");
  //     handleSubmit({
  //       name: name,
  //       exp: exp,
  //       diet: diet,
  //       fam_size: fam_size,
  //       intolerances: allergen,
  //     });
  //     document.getElementById("history")?.focus();
  //     console.log("pressed enter");
  //   }
  // };
  // useEffect(() => {
  //   document.addEventListener("keydown", handleUserKeyPress);

  //   return () => {
  //     document.removeEventListener("keydown", handleUserKeyPress);
  //   };
  // }, []);
  const handleSubmit = async (props: profileProps) => {
    // Handle form submission here, e.g., send data to server
    //await addUser(props);
    if (props.name === null || props.exp === null || props.fam_size === null) {
      alert("Please enter name, experience, and family size.");
    } else {
      try {
        await addUser(props)
        console.log(
          "?name=",
          props.name +
            "&exp=" +
            props.exp +
            "&diet=" +
            props.diet.toString() +
            "&fam-size=" +
            props.fam_size +
            "&intolerances=" +
            props.intolerances.toString()
        );
        setAuthing(1);
      } catch (error) {
        alert(error);
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
                  onChange={(opt: any) => setDiet(opt!.label)}
                />
              </div>
            </div>
            <div className="acct-elt">
              <legend>Intolerances:</legend>
              <div className="selector">
                <Creatable
                  options={intolerance}
                  isMulti
                  onChange={(opt) => {
                    console.log(allergen);
                    setAllergen(opt.map((tag: { value: any }) => tag.value));
                  }}
                />
              </div>
            </div>
            <div className="acct-elt">
              <legend>Family Size:</legend>
              <ControlledInput
                value={fam_size}
                setValue={setFam_Size}
                ariaLabel="family-size"
                placeholder="2"
                styleID="input-box"
              />
            </div>

            <button
              className="butt"
              onClick={() =>
                handleSubmit({
                  name: name,
                  exp: exp,
                  diet: diet,
                  fam_size: fam_size,
                  intolerances: allergen,
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
