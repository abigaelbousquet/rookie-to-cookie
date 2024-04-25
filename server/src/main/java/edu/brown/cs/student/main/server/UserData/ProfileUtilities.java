package edu.brown.cs.student.main.server.UserData;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;

public class ProfileUtilities {

  private static final Moshi MOSHI = new Moshi.Builder().build();
  private static final JsonAdapter<Profile> PROFILE_JSON_ADAPTER = MOSHI.adapter(Profile.class);

  /**
   * Deserializes a raw profile json into Profile object.
   *
   * @param rawJson the raw profile json to deserialize
   * @return a Profile, the deserialized version of rawJson
   * @throws IOException if moshi is unable to deserialize rawJson into a Profile
   * @throws IllegalArgumentException if rawJson describes a Profile without a name
   */
  public static Profile deserializeProfile(String rawJson)
      throws IOException, IllegalArgumentException {
    Profile profile = PROFILE_JSON_ADAPTER.fromJson(rawJson);
    if (profile == null || (profile.getName() == null)) {
      throw new IllegalArgumentException(
          "Error parsing: Given invalid profile json with no name. Raw json: " + rawJson);
    }
    return profile;
  }
}
