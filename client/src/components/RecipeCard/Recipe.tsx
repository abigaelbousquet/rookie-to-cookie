interface Recipe {
  name: string;
  cuisine: string;
  instructions: string;
  time: number;
}

export default Recipe;

export function getRecipe(recipe) {
  return recipe.id;
}
