interface Recipe {
  name: string;
  cuisine: string;
  instructions: { number: string; step: string }[];
  time: number;
  liked: number;
  ingredients: string[];
  image: string;
  credit: string;
  id: string;
}

export default Recipe;
