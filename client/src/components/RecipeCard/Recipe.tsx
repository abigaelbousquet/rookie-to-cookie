interface Recipe {
  name: string;
  cuisine: string;
  instructions: string;
  time: number;
  liked: boolean;
}

export default Recipe;

export function getRecipe(recipe) {
  return recipe.id;
}
