import Recipe from "../RecipeCard/Recipe";
import RecipeHistory from "../RecipeCard/RecipeHistory";
import { useState } from "react";
import Calendar from "react-calendar";
import React from "react";
import "react-calendar/dist/Calendar.css";
import "../../styles/Calendar.css";
import InfoView from "../RecipeCard/InfoView";
import MealPlanSave from "../MealPlan/MealPlanSave";
import { getMealPlan } from "../../utils/api";
import { parseMealPlan } from "../MealPlan/MealPlanGenerate";
import MealPlanPopup from "../MealPlan/MealPlanPopup";

type ValuePiece = Date | null;
type Value = ValuePiece | [ValuePiece, ValuePiece];

interface listItem {
  day: number;
  events: [
    {
      title: string;
      details: {};
    }
  ];
}

interface CalendarProps {
  recipeHistory: RecipeHistory[];
}

const CalendarPage: React.FC<CalendarProps> = ({ recipeHistory }) => {
  const [value, onChange] = useState<Value>(new Date());
  const [showPopup, setShowPopup] = useState<boolean>(false);

  return (
    <div className="calendar-page">
      <Calendar
        className="big-cal"
        onChange={onChange}
        value={value}
        showWeekNumbers={true}
        tileDisabled={() => true}
        onClickWeekNumber={async (weekNumber, date) => {
          setShowPopup(true);
          const formattedDate = date.toLocaleDateString("en-US", {
            month: "2-digit",
            day: "2-digit",
            year: "numeric",
          });
          const queryDate = date
            .toString()
            .replace("GMT-0400 (Eastern Daylight Time)", "EDT")
            .replace("2024", "")
            .replace(" 0", "0")
            .concat(" 2024");
          console.log(queryDate);
          const mealplanJson = await getMealPlan(formattedDate);
          const mealPlanDate = mealplanJson["Mealplan"];
          const mealPlan = parseMealPlan(mealPlanDate[queryDate]);
          <MealPlanPopup
            mealPlan={mealPlan}
            onClose={() => setShowPopup(false)}
          />;

          console.log("?dateOfMonday=" + formattedDate);
          console.log("mealplan: " + mealPlan);
        }}
      />
    </div>
  );
};
export default CalendarPage;
