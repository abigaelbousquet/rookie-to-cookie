import React, { useState } from "react";
import SingleCalendarEvents from "react-single-calendar-events";
import Recipe from "../RecipeCard/Recipe";
import { getRecipe } from "../RecipeCard/Recipe";
import RecipeHistory from "../RecipeCard/RecipeHistory";
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
const Calendar: React.FC<CalendarProps> = ({ recipeHistory }) => {
  let options = {
    positionX: "right",
    positionY: "top",
    badge: "circle",
    pattern: "alternate",
    fontSize: 16,
    border: true,
    presentOnly: true,
    accessibility: true,
    tooltip: true,
    tooltipPosition: "top",
    tooltipTitle: true,
  };

  const createRecipeList = () => {
    return recipeHistory.map((recipeItem) => {
      const recipe: Recipe = recipeItem.recipe;
      const historyItem: listItem = {
        day: recipeItem.day,
        events: [
          {
            title: recipe.name,
            details: {
              Cuisine: recipe.cuisine,
              Instructions: recipe.instructions,
            },
          },
        ],
      };
      return historyItem; // Return the historyItem here
    });
  };

  let data = {
    month: "May",
    year: "2024",
    list: createRecipeList(),
  };

  return <SingleCalendarEvents options={options} events={data} />;
};
export default Calendar;
