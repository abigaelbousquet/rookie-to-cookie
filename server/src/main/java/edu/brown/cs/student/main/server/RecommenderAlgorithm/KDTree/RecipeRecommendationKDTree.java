package edu.brown.cs.student.main.server.RecommenderAlgorithm.KDTree;

import edu.brown.cs.student.main.server.RecipeData.Recipe.Recipe;
import java.util.*;

// TODO: generalize to K-D tree class with caller-injected types

/**
 * Class describing a RecipeRecommendation K-D Tree.
 */
public class RecipeRecommendationKDTree {
  private RecipeNode root;
  private int size;
  private static final int DIMENSIONS = 3; // TODO: make this set dynamically in constructor

  public RecipeRecommendationKDTree() {
    root = null;
  }

  // Insert a recipe into the KD tree
  public void insert(Recipe recipe) {
    root = insertRec(root, new RecipeNode(recipe), 0);
    this.size++;
  }

  private RecipeNode insertRec(RecipeNode root, RecipeNode newNode, int depth) {
    if (root == null) {
      return newNode;
    }

    int axis = depth % DIMENSIONS;

    if (newNode.location[axis] < root.location[axis]) {
      root.left = insertRec(root.left, newNode, depth + 1);
    } else {
      root.right = insertRec(root.right, newNode, depth + 1);
    }

    return root;
  }

  // Search for a recipe in the KD tree
//  public boolean search(Recipe recipe) {
//    return searchRec(root, new RecipeNode(recipe), 0);
//  }
//
//  private boolean searchRec(RecipeNode root, RecipeNode targetNode, int depth) {
//    if (root == null) {
//      return false;
//    }
//
//    if (Arrays.equals(root.location, targetNode.location)) {
//      return true;
//    }
//
//    int axis = depth % DIMENSIONS;
//
//    if (targetNode.location[axis] < root.location[axis]) {
//      return searchRec(root.left, targetNode, depth + 1);
//    } else {
//      return searchRec(root.right, targetNode, depth + 1);
//    }
//  }

  /**
   * Gets the size of this K-D tree.
   *
   * @return the number of nodes in this K-D tree
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Finds the n nearest neighbors of a particular Recipe in this K-D tree.
   *
   * @param recipe the Recipe we want to find nearest neighbors for
   * @param n the number of nearest neighbors to recipe desired
   * @return up to n nearest neighbors of targetNode in the K-D tree starting at root, in a List of Recipes ordered closest to furthest nearest neighbors
   */
  public Recipe[] nearestNeighbors(Recipe recipe, int n) {
    return nearestNeighbors(root, new RecipeNode(recipe), n, new PriorityQueue<DistanceRecipePair>(new DistanceRecipePairComparator()), 0);
  }

  /**
   * Extracts the ordered list of Recipes from a Priority Queue of DistanceRecipePairs.
   *
   * @param queue the PriorityQueue to extract from (ordered largest to smallest distance)
   * @return queue converted to an ordered List of Recipes (ordered smallest to largest distance)
   */
  private static Recipe[] extractRecipesFromQueue(PriorityQueue<DistanceRecipePair> queue) {
    Recipe[] bestRecipesArray = new Recipe[queue.size()];
    for (int i = queue.size()-1; i > -1; i--) {
      bestRecipesArray[i] = queue.remove().recipe();
    }
    return bestRecipesArray;
  }

  /**
   * Adds a new DistanceRecipePair to a PriorityQueue of DistanceRecipePairs and trims the queue
   * to a max length.
   *
   * @param queue the queue to add to and trim
   * @param newPair the new DistanceRecipePair to add to queue
   * @param n the number of DistanceRecipePairs to restrict the final length of queue to
   */
  private static void addAndTrimQueue(PriorityQueue<DistanceRecipePair> queue, DistanceRecipePair newPair, int n) {
    queue.add(newPair);
    while (queue.size() > n) {
      // remove DistanceRecipePair with the largest distance (at head of queue)
      queue.remove();
    }
  }

  /**
   * Finds the n nearest neighbors of a particular Recipe in a K-D tree/subtree.
   *
   * @param root the root of the K-D tree to search through
   * @param targetNode the RecipeNode for which we want the nearest n neighbors
   * @param n the number of nearest neighbors to capture
   * @param bestRecipesQueue the Priority Queue (with associated Comparator) to maintain nearest neighbors in
   * @param depth the depth of the K-D tree in this search
   * @return up to n nearest neighbors of targetNode in the K-D tree starting at root, in a List of Recipes ordered closest to furthest nearest neighbors
   */
  private Recipe[] nearestNeighbors(RecipeNode root, RecipeNode targetNode, int n, PriorityQueue<DistanceRecipePair> bestRecipesQueue, int depth) {
    if (root == null) {
      return extractRecipesFromQueue(bestRecipesQueue);
    }

    double distance = distance(root.location, targetNode.location);
    addAndTrimQueue(bestRecipesQueue, new DistanceRecipePair(distance, root.recipe), n);

    int axis = depth % DIMENSIONS;
    RecipeNode closerSubtreeRoot, furtherSubtreeRoot;
    if (targetNode.location[axis] < root.location[axis]) {
      closerSubtreeRoot = root.left;
      furtherSubtreeRoot = root.right;
    } else {
      closerSubtreeRoot = root.right;
      furtherSubtreeRoot = root.left;
    }

    nearestNeighbors(closerSubtreeRoot, targetNode, n, bestRecipesQueue, depth + 1);

    /*
     We search the further subtree if:
     (1) we have done the DFS search of the closer subtree and haven't found n nearest neighbors yet
     OR
     (2) the distance from the targetNode to the current node along the splitting dimension is
     shorter than the distance of the farthest Recipe in the queue (indicating that there is a
     possibility that a closer neighbor exists on the other side of the splitting dimension).
     */
    if (bestRecipesQueue.size() < n || distance(targetNode.location[axis], root.location[axis]) < bestRecipesQueue.peek().distance()) {
      nearestNeighbors(furtherSubtreeRoot, targetNode, n, bestRecipesQueue, depth + 1);
    }

    return extractRecipesFromQueue(bestRecipesQueue);
  }

  /**
   * Computes the squared Euclidean distance between two points in the same k-dimensional space.
   * Squared Euclidean distance as opposed to true Euclidean distance is a small optimization here
   * to avoid the extra computational power required to compute square root.
   *
   * @param point1 the first point to consider
   * @param point2 the first point to consider
   * @return the squared Euclidean distance between point1 and point2
   */
  private double distance(int[] point1, int[] point2) {
    double sum = 0;
    for (int i = 0; i < DIMENSIONS; i++) {
      sum += Math.pow(point1[i] - point2[i], 2);
    }
    return sum;
  }

  /**
   * Computes Euclidean distance between two values in the same dimension.
   *
   * @param a the first value to consider
   * @param b the second value to consider
   * @return the positive distance between a and b
   */
  private double distance(int a, int b) {
    return Math.abs(a - b);
  }
}