package edu.brown.cs.student.main.server.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MockedFirebase implements StorageInterface{
    @Override
    public void addDocument(String uid, String collection_id, String doc_id, Map<String, Object> data) {
        System.out.println("added document" + doc_id);
    }

    @Override
    public List<Map<String, Object>> getCollection(String uid, String collection_id) throws InterruptedException, ExecutionException {
        System.out.println("got collection" + collection_id);
        return new ArrayList<>();
    }

    @Override
    public void clearUser(String uid) throws InterruptedException, ExecutionException {
        System.out.println("cleared user");
    }

    @Override
    public void clearRecipes(String uid, String path, String recipeId) throws IllegalArgumentException {
        System.out.println("cleared recipes");
    }
}
