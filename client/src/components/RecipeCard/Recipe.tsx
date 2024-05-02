import { useState } from "react";

interface Recipe {
  name: string;
  cuisine: string;
  instructions: string[];
  time: number;
  ingredients: string[];
  image: string;
  credit: string;
  id: string;
  servings: string;
}
export default Recipe;
