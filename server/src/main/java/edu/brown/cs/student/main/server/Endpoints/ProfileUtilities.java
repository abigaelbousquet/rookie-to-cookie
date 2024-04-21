package edu.brown.cs.student.main.server.Endpoints;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;

public class ProfileUtilities {

  private static final Moshi MOSHI = new Moshi.Builder().build();
  private static final JsonAdapter<Profile> PROFILE_JSON_ADAPTER = MOSHI.adapter(Profile.class);

  /**
   * Deserializes a raw recipe json into Recipe object.
   *
   * @param rawJson the raw recipe json to deserialize
   * @return a Recipe, the deserialized version of rawJson
   * @throws IOException if moshi is unable to deserialize rawJson into a Recipe
   * @throws IllegalArgumentException if rawJson describes a recipe without a title
   */
  public static Profile deserializeProfile(String rawJson)
      throws IOException, IllegalArgumentException {
    Profile profile = PROFILE_JSON_ADAPTER.fromJson(rawJson);
    if (profile == null || (profile.getName() == null)) {
      throw new IllegalArgumentException(
          "Error parsing: Given invalid recipe json with no title. Raw json: " + rawJson);
    }
    return profile;
  }
}
