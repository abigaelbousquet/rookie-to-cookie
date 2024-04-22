// AccountCreationPopup.tsx
import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import Select from "react-select";

import React, { Dispatch, SetStateAction, useState } from "react";
import { ControlledInput } from "./ControlledInput";
import Creatable from "react-select/creatable";
import { addUser } from "../../utils/api";
export interface profileProps {
  name: string;
  exp: string;
  diet: string[];
  intolerances: string[];
}

export function AccountCreation() {
  const [name, setName] = useState("");
  const [exp, setExp] = useState("");
  const [diet, setDiet] = useState([""]);
  const [allergen, setAllergen] = useState([""]);
  const [showPopup, setShowPopup] = useState<boolean>(false);
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
  const handleSubmit = async (props: profileProps) => {
    // Handle form submission here, e.g., send data to server
    await addUser(props);
    console.log(
      "?name=",
      props.name +
        "&exp=" +
        props.exp +
        "&diet=" +
        props.diet.toString() +
        "&intolerances=" +
        props.intolerances.toString()
    );
  };

  return (
    <div className="popup">
      <div className="popup-inner">
        <div className="popup-inmost">
          <div className="popup-header">
            <div id="account-header">
              <h2>Create Account</h2>
            </div>
            <button className="x-button">X</button>
          </div>
          <div className="form">
            <div>
              <legend>Name:</legend>
              <ControlledInput
                value={name}
                setValue={setName}
                ariaLabel={"Name"}
                placeholder={"Nim Telson"}
                styleID={"input-box"}
              />
            </div>
            <div>
              <legend>Cooking Experience:</legend>
              <Slider
                defaultValue={1}
                min={1}
                max={4}
                onChange={(e) => setExp(e.toString())}
                marks={heightMarks}
              />
            </div>
            <div>
              <legend>Diet:</legend>
              <Select
                options={diets}
                isMulti
                onChange={(opt) => {
                  setDiet(opt.map((tag) => tag.label));
                }}
              />
            </div>
            <div>
              <legend>Intolerances:</legend>
              <Creatable
                options={intolerance}
                isMulti
                onChange={(opt) => {
                  console.log(allergen);
                  setAllergen(opt.map((tag) => tag.value));
                }}
              />
            </div>

            <button
              className="butt"
              onClick={() =>
                handleSubmit({
                  name: name,
                  exp: exp,
                  diet: diet,
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
}
