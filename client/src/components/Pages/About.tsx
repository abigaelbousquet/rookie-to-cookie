import React from "react";

/**
 * This component renders information about our project!
 * @returns About page component
 */
const About: React.FC = () => {
  return (
    <div className="About">
      <div className="logo-big"></div>
      <div className="about-text">
        <h1 className="name-display">About Us</h1>
        <h3>Our Mission</h3>
        <p>
          Rookie to Cookie is a service that aims to help people who do not
          currently cook as well as people who are seeking to branch out or
          looking to cook within constraints, by providing specialized,
          encouraging, and clear meal plans.
        </p>
        <p>
          Whether you're a college student starting to cook for yourself,
          someone trying to impress your vegan date, or simply a homecook tiring
          of your usual rotation but overwhelmed by the internet's vast number
          of recipes, Rookie to Cookie is here for you.
        </p>
        <h3>Who We Are</h3>
        <p>
          Rookie To Cookie is a project built by four Brown CS32 Students in
          2024 : mshaffe3, ffnaqvi, abousque, ddedona.
        </p>
        <p>
          For comments, questions, or concerns, you can contact us{" "}
          <a href="https://forms.gle/Rzzcvp2dvgmvJRXm7">here.</a>
        </p>
      </div>
    </div>
  );
};
export default About;
