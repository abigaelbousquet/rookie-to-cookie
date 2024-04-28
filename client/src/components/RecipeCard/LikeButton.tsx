import React, { useState } from "react";
import { AiFillDislike, AiFillHeart, AiFillLike } from "react-icons/ai";

function LikeButton() {
  const [liked, setLiked] = useState<number>(0);
  const handleLikeClick = () => {
    if (liked == 0) {
      setLiked(1);
    } else if (liked === 1) {
      setLiked(2);
    } else {
      setLiked(0);
    }
  };
  if (liked === 1) {
    return <AiFillLike color="blue" size="30" onClick={handleLikeClick} />;
  } else if (liked === 2)
    return <AiFillDislike color="red" size="30" onClick={handleLikeClick} />;
  else {
    return <AiFillHeart color="black" size="30" onClick={handleLikeClick} />;
  }
}
export default LikeButton;
