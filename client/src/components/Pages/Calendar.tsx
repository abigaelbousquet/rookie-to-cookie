import { useState } from "react";
import Calendar from "react-calendar";
import React from "react";
import "react-calendar/dist/Calendar.css";
import "../../styles/Calendar.css";
import { getMealPlan } from "../../utils/api";
import { parseMealPlanLikes } from "../../RecipeUtils/ParseMealPlan";
import MealPlanPopup from "../MealPlan/MealPlanPopup";
import { emptyMealPlan } from "../../data/MockedRecipeHistory";

type ValuePiece = Date | null;
type Value = ValuePiece | [ValuePiece, ValuePiece];
/**
 * This component renders the calendar page and manages mealplan popups
 * @returns calendar page
 */
const CalendarPage: React.FC = () => {
  const [value, onChange] = useState<Value>(new Date());
  const [showPopup, setShowPopup] = useState<boolean>();
  const [mealPlan, setMealPlan] = useState(emptyMealPlan);
  
  /**
   * Function to change the state to hide the popup and reset the meal plan.
   */
  const changePopup = () => {
    setShowPopup(false);
    setMealPlan(emptyMealPlan);
  };
  return (
    <div className="calendar-page">
      <h3>Cooking History</h3>
      <p>
        Click on a week (orange tile) below to view your meal plan saved to that
        week. You can like and unlike recipes from this page!
      </p>
      <Calendar
        aria-label="cooking-history-calendar"
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
            //queries back end for meal plan saved
            const mealplanJson = await getMealPlan(formattedDate1);
            const mealPlanDate = await mealplanJson["Mealplan"];
            //check if liked or not, to display recipe card correctly
            const mealPlan = await parseMealPlanLikes(
              mealPlanDate[formattedDate]
            );
            await setMealPlan(mealPlan); //sets current mealplan to set to the popup display
          } catch (error) {
            setMealPlan(emptyMealPlan); //sets mealplan to empty if no plan saved to that week
            alert("No plan saved for week of " + formattedDate);
          }
        }}
      />
      {showPopup && (
        <MealPlanPopup mealPlan={mealPlan} onClose={() => changePopup()} />
      )}
    </div>
  );
};
export default CalendarPage;
