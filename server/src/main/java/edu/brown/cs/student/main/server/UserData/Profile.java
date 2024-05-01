package edu.brown.cs.student.main.server.UserData;

import java.util.List;

/** A class describing a user profile. */
public class Profile {
  private String name;
  private int exp;
  private List<String> intolerances;
  private List<String> diet;
  private int famSize;

  /**
   * Constructor for the Profile class.
   *
   * @param name the user's name
   * @param exp the user's experience, expressed as an int
   * @param intolerances a list of the user's intolerances
   * @param diet a list of the user's followed diets
   */
  public Profile(String name, int exp, List<String> intolerances, List<String> diet, int fam_size) {
    this.name = name;
    this.exp = exp;
    this.intolerances = intolerances;
    this.diet = diet;
    this.famSize = fam_size;
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
  public List<String> getDiet() {
    return this.diet;
  }

  public int getFamSize(){
    return this.famSize;
  }
}
