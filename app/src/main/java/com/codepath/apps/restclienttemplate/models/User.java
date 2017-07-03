package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by jennifershin on 6/26/17.
 */

public class User implements Serializable{
    // list all the attributes
    public String name;
    public long uid;
    public String screeName;
    public String profileImageUrl;

    public String tagline;
    public int followersCount;
    public int followingCount;

    // deserialize the JSON
    public static User fromJSON(JSONObject json) throws JSONException{
        User user = new User();

        // extract and fill the values
        user.name= json.getString("name");
        user.uid = json.getLong("id");
        user.screeName= json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");

        user.tagline = json.getString("description");
        user.followersCount = json.getInt("followers_count");
        user.followingCount = json.getInt("friends_count");

        return user;
    }
}
