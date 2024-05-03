package edu.brown.cs.student.main.server.storage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface StorageInterface {

  /**
   * Method to add a document
   *
   * @param uid
   * @param collection_id
   * @param doc_id
   * @param data
   */
  void addDocument(String uid, String collection_id, String doc_id, Map<String, Object> data);

  /**
   * Method to get a collection
   *
   * @param uid
   * @param collection_id
   * @return
   * @throws InterruptedException
   * @throws ExecutionException
   */
  List<Map<String, Object>> getCollection(String uid, String collection_id)
      throws InterruptedException, ExecutionException;

  /**
   * Method to clear a user
   *
   * @param uid
   * @throws InterruptedException
   * @throws ExecutionException
   */
  void clearUser(String uid) throws InterruptedException, ExecutionException;

  /**
   * Method to clar a recipe
   *
   * @param uid
   * @param path
   * @param recipeId
   * @throws IllegalArgumentException
   */
  void clearRecipes(String uid, String path, String recipeId) throws IllegalArgumentException;
}
