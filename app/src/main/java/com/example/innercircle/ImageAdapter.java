package com.example.innercircle;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.data.model.Resource;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private ArrayList<Image> mDataset;
    private ActivityFeed mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;
        public Button mLikeButton;
        public TextView mLikeText;
        public Drawable drawable;

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView2);
            mImageView = v.findViewById(R.id.imageView);
            mLikeButton = v.findViewById(R.id.likeButton);
            mLikeText = v.findViewById(R.id.likeText);
        }
    }

    public ImageAdapter(ArrayList<Image> myDataset, ActivityFeed activity) {
        mDataset = myDataset;
        mActivity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Image image = (Image) mDataset.get(position);
        if (image.user != null) {
            holder.mTextView.setText(image.user.displayName);
        }
        Picasso.get().load(image.downloadUrl).into(holder.mImageView);

        holder.mLikeText.setText(image.likes + " Likes");
        if(image.hasLiked) {
            //holder.mLikeButton.setBackgroundColor(mActivity.getResources().getColor(R.color.colorAccent));
            holder.mLikeButton.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
        } else {
            //holder.mLikeButton.setBackgroundColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.mLikeButton.setBackgroundResource(R.drawable.ic_favorite_white_24dp);
        }
        holder.mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setLiked(image);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addImage(Image image) {
        mDataset.add(0, image);
        notifyDataSetChanged();
    }
}

