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
import { emptyMealPlan } from "../../data/MockedRecipeHistory";

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
  const [showPopup, setShowPopup] = useState<boolean>();
  const [mealPlan, setMealPlan] = useState(emptyMealPlan);
  const [likedRecipes, setLikedRecipes] = useState<any[]>([]); // Add state for liked recipes

  return (
    <div className="calendar-page">
      <h3>Cooking History</h3>
      <p>
        Click on a week number below to view your meal plan saved to that week.
        You can like and unlike recipes from this page!
      </p>
      <Calendar
        className="big-cal"
        onChange={onChange}
        value={value}
        showWeekNumbers={true}
        tileDisabled={() => true}
        onClickWeekNumber={async (weekNumber, date) => {
          setShowPopup(true);
          const formattedDate1 = date.toLocaleDateString("en-US", {
            month: "2-digit",
            day: "2-digit",
            year: "numeric",
          });
          const formattedDate = formattedDate1
            .replace("/", "-")
            .replace("/", "-");
          console.log(formattedDate);
          try {
            const mealplanJson = await getMealPlan(formattedDate1);
            const mealPlanDate = mealplanJson["Mealplan"];
            const mealPlan = await parseMealPlan(mealPlanDate[formattedDate]);
            setMealPlan(mealPlan);
          } catch (error) {
            console.error(error);
          }
        }}
      />
      {showPopup && (
        <MealPlanPopup
          mealPlan={mealPlan}
          onClose={() => setShowPopup(false)}
        />
      )}
    </div>
  );
};
export default CalendarPage;
