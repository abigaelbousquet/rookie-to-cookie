package edu.brown.cs.student.main.server.Endpoints;

import java.util.ArrayList;

public class Profile {

  private String name;
  private int exp;

  ArrayList<String> intoleranceArray;
  ArrayList<String> dietArray;

  public Profile(
      String name, int exp, ArrayList<String> intoleranceArray, ArrayList<String> dietArray) {
    this.name = name;
    this.exp = exp;
    this.intoleranceArray = intoleranceArray;
    this.dietArray = dietArray;
  }

  public String getName() {
    return this.name;
  }

  public int getExp() {
    return exp;
  }

  public ArrayList<String> getIntolerances() {
    return this.intoleranceArray;
  }

  public ArrayList<String> getDiet() {
    return this.dietArray;
  }
}
