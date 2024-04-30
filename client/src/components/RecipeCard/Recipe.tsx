interface Recipe {
  name: string;
  cuisine: string;
  instructions: string[];
  time: number;
  liked: number;
  ingredients: string[];
  image: string;
  credit: string;
  id: string;
}

export default Recipe;
