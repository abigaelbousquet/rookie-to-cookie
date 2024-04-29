interface Recipe {
  name: string;
  cuisine: string;
  instructions: string;
  time: number;
  liked: number;
  ingredients: string[];
}

export default Recipe;

export function getRecipe(recipe) {
  return recipe.id;
}
