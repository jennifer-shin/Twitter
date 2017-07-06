package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

/**
 * Created by jennifershin on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    Context context;
    private TweetAdapterListener mListener;

    public interface TweetAdapterListener {
        public void onItemSelected (View view, int position);
    }

    // pass tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets, TweetAdapterListener listener) {
        mTweets = tweets;
        mListener = listener;
    }
    // inflate layout for each row and cache references into ViewHolder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind values based on position of element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        holder.tvUsername.setText("@" + tweet.user.screeName);
        holder.tvBody.setText(tweet.body);
        holder.tvTimeStamp.setText(tweet.getRelativeTimeAgo(tweet.createdAt));
        holder.tvName.setText(tweet.user.name);

        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);
    }

    // create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTimeStamp;
        public TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            tvName= (TextView) itemView.findViewById(R.id.tvName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        mListener.onItemSelected(view, position);
                    }
                }
            });

            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ProfileActivity.class);
                    i.putExtra("screen_name",tvUsername.getText().toString());
                    context.startActivity(i);
                }
            });
        }
    }

    // get number of tweets
    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

}