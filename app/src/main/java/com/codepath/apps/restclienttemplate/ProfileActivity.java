package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");

        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.flContainer, userTimelineFragment);

        ft.commit();

        client = TwitterApplication.getRestClient();
        if(screenName == null){
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle("@" + user.screeName);
                        // populate the user headline
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("ProfileActivity", responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("ProfileActivity", errorResponse.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.e("ProfileActivity", errorResponse.toString());
                }
            });
        }
        else {
            client.getUserInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle("@" + user.screeName);
                        // populate the user headline
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("ProfileActivity", responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("ProfileActivity", errorResponse.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.e("ProfileActivity", errorResponse.toString());
                }
            });
        }
    }

    public void populateUserHeadline (User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.name);

        tvTagline.setText(user.tagline);
        tvFollowers.setText(user.followersCount + " Followers");
        tvFollowing.setText (user.followingCount + " Following");

        Glide.with(this).load(user.profileImageUrl).into(ivProfileImage);
    }
}