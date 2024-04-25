package edu.brown.cs.student.main.server.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * A class describing a user profile.
 */
public class Profile {
  private String name;
  private int exp;
  private List<String> intoleranceArray;
  private List<String> dietArray;

  /**
   * Constructor for the Profile class.
   *
   * @param name the user's name
   * @param exp the user's experience, expressed as an int
   * @param intoleranceArray a list of the user's intolerances
   * @param dietArray a list of the user's followed diets
   */
  public Profile(
      String name, int exp, List<String> intoleranceArray, List<String> dietArray) {
    this.name = name;
    this.exp = exp;
    this.intoleranceArray = intoleranceArray;
    this.dietArray = dietArray;
  }

  /**
   * Gets the user's name.
   *
   * @return the user's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the user's experience.
   *
   * @return the user's experience, expressed as an int
   */
  public int getExp() {
    return exp;
  }

  /**
   * Gets a list of the user's intolerances.
   *
   * @return a list of the user's intolerances
   */
  public List<String> getIntolerances() {
    return this.intoleranceArray;
  }

  /**
   * Gets a list of the user's followed diets.
   *
   * @return a list of the user's followed diets
   */
  public List<String> getDiet() {
    return this.dietArray;
  }

}
