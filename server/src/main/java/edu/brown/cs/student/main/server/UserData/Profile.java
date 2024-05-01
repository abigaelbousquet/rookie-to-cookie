package edu.brown.cs.student.main.server.UserData;

import java.util.List;

/** A class describing a user profile. */
public class Profile {
  private String name;
  private int exp;
  private List<String> intolerances;
  private String diet;
  private String familySize;

  /**
   * Constructor for the Profile class.
   *
   * @param name the user's name
   * @param exp the user's experience, expressed as an int
   * @param intolerances a list of the user's intolerances
   * @param diet a list of the user's followed diets
   */
  public Profile(String name, int exp, List<String> intolerances, String diet, String familySize) {
    this.name = name;
    this.exp = exp;
    this.intolerances = intolerances;
    this.diet = diet;
    this.familySize = familySize;
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
    return this.intolerances;
  }

  /**
   * Gets a list of the user's followed diets.
   *
   * @return a list of the user's followed diets
   */
  public String getDiet() {
    return this.diet;
  }

  /**
   * Gets a list of the user's followed diets.
   *
   * @return a list of the user's followed diets
   */
  public String getFamSize() {
    return this.familySize;
  }
}
