import React from "react";
import { AiFillDislike, AiFillHeart, AiFillLike } from "react-icons/ai";

/**
 * This component is a three state button which can either show liked, disliked, or non-liked/disliked status
 */
interface likeButtonProps {
  liked: number;
  setLiked: React.Dispatch<React.SetStateAction<number>>;
  canLike: boolean;
}
function LikeButton(props: likeButtonProps) {
  const handleLikeClick = () => {
    if (props.canLike) {
      if (props.liked == 0) {
        props.setLiked(1);
        console.log("liked!");
      } else if (props.liked === 1) {
        props.setLiked(2);
        console.log("disliked");
      } else {
        props.setLiked(0);
      }
    } else {
      alert(
        "You must save this meal plan below in order to add a recipe to your likes!"
      );
    }
  };
  if (props.liked === 1) {
    return (
      <AiFillLike
        aria-Label="thumbs-up"
        color="#7a9364"
        size="30"
        onClick={handleLikeClick}
      />
    );
  } else if (props.liked === 2)
    return (
      <AiFillDislike
        aria-Label="thumbs-down"
        color="#e58a44"
        size="30"
        onClick={handleLikeClick}
      />
    );
  else {
    return (
      <AiFillHeart
        aria-Label="unliked-button"
        color="grey"
        size="30"
        onClick={handleLikeClick}
      />
    );
  }
}
export default LikeButton;
