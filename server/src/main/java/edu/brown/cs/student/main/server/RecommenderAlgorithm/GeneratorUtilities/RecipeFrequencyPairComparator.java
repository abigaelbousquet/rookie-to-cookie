package edu.brown.cs.student.main.server.RecommenderAlgorithm.GeneratorUtilities;

import java.util.Comparator;

/**
 * Class describing a Comparator for RecipeFrequencyPairs.
 */
public class RecipeFrequencyPairComparator implements Comparator<RecipeFrequencyPair> {

  /**
   * Compares two DistanceRecipePairs.
   *
   * @param pair1 the first object to be compared
   * @param pair2 the second object to be compared
   * @return 0 if equal, 1 if pair1 > pair2, or -1 if pair1 < pair2
   */
  @Override
  public int compare(RecipeFrequencyPair pair1, RecipeFrequencyPair pair2) {
    if (pair1.frequency() == pair2.frequency()) {
      return 0;
    } else if (pair1.frequency() > pair2.frequency()) {
      return 1;
    } else {
      // pair1 distance < pair2 distance
      return -1;
    }
  }
}