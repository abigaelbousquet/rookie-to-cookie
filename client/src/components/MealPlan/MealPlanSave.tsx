import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import "../../styles/MealPlanSave.css";
import React, { useState } from "react";
import { saveMealPlan } from "../../utils/api";
type ValuePiece = Date | null;
type Value = ValuePiece | [ValuePiece, ValuePiece];
interface MealPlanSaveProps {
  onClose: () => void;
}

const MealPlanSave: React.FC<MealPlanSaveProps> = ({ onClose }) => {
  const [value, onChange] = useState<Value>(new Date());
  const [weekChosen, setWeekChosen] = useState<ValuePiece>(null);
  return (
    <div className="popup-save-overlay">
      <div className="popup-save-content">
        <div>
          <Calendar
            className="small-cal"
            onChange={onChange}
            value={value}
            showWeekNumbers={true}
            tileDisabled={() => true}
            onClickWeekNumber={async (weekNumber, date) => {
              setWeekChosen(date);
              console.log(
                date.getMonth().toString() +
                  "/" +
                  date.toDateString() +
                  "/" +
                  date.getFullYear().toString()
              );
              console.log("saved plan to: " + date);
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
