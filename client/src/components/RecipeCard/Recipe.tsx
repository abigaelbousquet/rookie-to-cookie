interface Recipe {
  name: string;
  cuisine: string;
  instructions: string[];
  time: number;
  liked: number;
  ingredients: string[];
  image: string;
  credit: string;
}

export default Recipe;
