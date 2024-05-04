import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import "../../styles/MealPlanSave.css";
import React, { useState } from "react";
import { getMealPlan, saveMealPlan } from "../../utils/api";

/**
 * This component pops up when you click save on a generated meal plan,
 *  and allows you to select a week on a calendar to save it to your cooking history.
 */
type ValuePiece = Date | null;
type Value = ValuePiece | [ValuePiece, ValuePiece];
interface MealPlanSaveProps {
  onClose: () => void;
}

const MealPlanSave: React.FC<MealPlanSaveProps> = ({ onClose }) => {
  const [value, onChange] = useState<Value>(new Date()); //Value of a calendar tile
  return (
    <div className="popup-save-overlay">
      <div className="popup-save-content">
        <div>
          <p>Select the week for the mealplan:</p>
          {/* Calendar display */}
          <Calendar
            aria-label="cooking-history"
            className="small-cal"
            onChange={onChange}
            value={value}
            showWeekNumbers={true}
            tileDisabled={() => true}
            onClickWeekNumber={async (weekNumber, date) => {
              {
                /* Handler function for saving the plan*/
              }
              const formattedDate = date.toLocaleDateString("en-US", {
                month: "2-digit",
                day: "2-digit",
                year: "numeric",
              });
              console.log("saved plan to:", formattedDate);

              try {
                const mealPlanJson = await getMealPlan(formattedDate);
                if (mealPlanJson["response_type"] == "failure") {
                  await saveMealPlan(formattedDate);
                  alert("Saved meal plan to week of " + formattedDate);
                } else {
                  alert("Unable to save. Week already planned for.");
                }
                onClose();
              } catch (error) {
                alert(error);
              }
            }}
          />
        </div>
        {/* Close button */}
        <button className="close-button-save" onClick={onClose}>
          X
        </button>
      </div>
    </div>
  );
};

export default MealPlanSave;
