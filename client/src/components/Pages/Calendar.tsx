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
  const [weekChosen, setWeekChosen] = useState<ValuePiece>(null);

  return (
    <div className="calendar-page">
      <Calendar
        className="big-cal"
        onChange={onChange}
        value={value}
        showWeekNumbers={true}
        tileDisabled={() => true}
        onClickWeekNumber={async (weekNumber, date) => {
          setWeekChosen(date);
          const formattedDate = date.toLocaleDateString("en-US", {
            month: "2-digit",
            day: "2-digit",
            year: "numeric",
          });
          console.log("getting plan from:", formattedDate);

          try {
            const mealplan = await getMealPlan(formattedDate);
            console.log("?dateOfMonday=" + formattedDate);
            console.log("mealplan: " + mealplan);
          } catch (error) {
            alert(error);
          }
        }}
      />
    </div>
  );
};
export default CalendarPage;
