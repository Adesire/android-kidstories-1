package com.project.android_kidstories.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.project.android_kidstories.Api.Responses.story.StoryAllResponse;
import com.project.android_kidstories.Model.Story;
import com.project.android_kidstories.R;
import com.project.android_kidstories.SingleStoryActivity;

import java.util.List;

/**
 * @author .: Oluwajuwon Fawole
 * @created : 16/10/19
 */
public class RecyclerStoriesAdapter extends RecyclerView.Adapter<RecyclerStoriesAdapter.CustomViewHolder>{


    private Context context;
    private List<Story> storiesList;
    StoryOnclickListener mListener;

    public interface  StoryOnclickListener{
        void onClick(int position);
    }



    public RecyclerStoriesAdapter(Context context, List<Story> storiesList) {
        this.context = context;
        this.storiesList = storiesList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        ImageView storyImage;
        TextView storyTitle;
        TextView authorName;
        TextView ageRange;
        TextView likes;
        TextView dislikes;
        ImageView like;
        ImageView dislike;
        ImageView bookmark;
        LinearLayout list_item;

        CustomViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            storyImage = view.findViewById(R.id.recyclerImage);
            storyTitle = view.findViewById(R.id.recyclerName);
            authorName = view.findViewById(R.id.tv2);
            ageRange = view.findViewById(R.id.tv3);
            likes = view.findViewById(R.id.count1);
            dislikes = view.findViewById(R.id.count2);
            bookmark = view.findViewById(R.id.bookmark);
            list_item = view.findViewById(R.id.l_clickable);


            storyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Story story = storiesList.get(getAdapterPosition());
                    Intent intent = new Intent(context, SingleStoryActivity.class);
                    intent.putExtra("story", story);
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Glide.with(context).load(storiesList.get(position).getImageUrl()).into(holder.storyImage);

        holder.storyTitle.setText(storiesList.get(position).getTitle());
        holder.authorName.setText(storiesList.get(position).getAuthor());

        holder.ageRange.setText("For kids ages "+storiesList.get(position).getAge());
        holder.likes.setText(storiesList.get(position).getLikesCount()+"");
        holder.dislikes.setText(storiesList.get(position).getDislikesCount()+"");


        holder.list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int story_id = storiesList.get(position).getId();
                Intent intent = new Intent(context, SingleStoryActivity.class);
                intent.putExtra("story_id", story_id);
                context.startActivity(intent);
            }
        });

        holder.likes.setTag(R.drawable.ic_thumb_up_black_24dp);    //When you change the drawable
        holder.dislikes.setTag(R.drawable.ic_thumb_down_black_24dp);

        holder.bookmark.setTag(R.drawable.ic_bookmark_border_black_24dp);
        //holder.favourite.setTag(R.drawable.ic_favorite_border);

        holder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int like_drawableId = (Integer)holder.likes.getTag();
                int dislike_drawableId = (Integer)holder.dislike.getTag();

                if(like_drawableId == R.drawable.ic_thumb_up_black_24dp ||  dislike_drawableId == R.drawable.ic_thumb_down_blue_24dp) {
                    holder.like.setImageResource(R.drawable.ic_thumb_up_blue_24dp);
                    holder.like.setTag(R.drawable.ic_thumb_up_blue_24dp);

                    int count = Integer.parseInt(holder.likes.getText().toString());
                    count++;
                    holder.likes.setText(""+count);

                    holder.dislike.setImageResource(R.drawable.ic_thumb_down_black_24dp);
                    holder.dislike.setTag(R.drawable.ic_thumb_down_black_24dp);
                }else{
                    holder.like.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                    holder.like.setTag(R.drawable.ic_thumb_up_black_24dp);

                    int count = Integer.parseInt(holder.likes.getText().toString());
                    count--;
                    holder.likes.setText(""+count);
                }
            }
        });

        holder.dislikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dislike_drawableId = (Integer)holder.dislike.getTag();
                int like_drawableId = (Integer)holder.like.getTag();

                if(dislike_drawableId == R.drawable.ic_thumb_down_black_24dp || like_drawableId == R.drawable.ic_thumb_up_blue_24dp) {
                    holder.dislike.setImageResource(R.drawable.ic_thumb_down_blue_24dp);
                    holder.dislike.setTag(R.drawable.ic_thumb_down_blue_24dp);

                    holder.like.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                    holder.like.setTag(R.drawable.ic_thumb_up_black_24dp);

                    int count = Integer.parseInt(holder.dislikes.getText().toString());
                    count++;
                    holder.dislikes.setText(""+count);
                }else{
                    holder.dislike.setImageResource(R.drawable.ic_thumb_down_black_24dp);
                    holder.dislike.setTag(R.drawable.ic_thumb_down_black_24dp);

                    int count = Integer.parseInt(holder.dislikes.getText().toString());
                    count--;
                    holder.dislikes.setText(""+count);
                }
            }
        });

        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bookmark_drawableId = (Integer)holder.bookmark.getTag();

                if(bookmark_drawableId == R.drawable.ic_bookmark_border_black_24dp) {
                    holder.bookmark.setImageResource(R.drawable.ic_bookmark_click_24dp);
                    holder.bookmark.setTag(R.drawable.ic_bookmark_click_24dp);

                }else{
                    holder.bookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    holder.bookmark.setTag(R.drawable.ic_bookmark_border_black_24dp);
                }
            }
        });

//        holder.favourite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int favourite_drawableId = (Integer)holder.favourite.getTag();
//
//                if(favourite_drawableId == R.drawable.ic_favorite_border) {
//                    holder.favourite.setImageResource(R.drawable.ic_favorite);
//                    holder.favourite.setTag(R.drawable.ic_favorite);
//
//                }else{
//                    holder.favourite.setImageResource(R.drawable.ic_favorite_border);
//                    holder.favourite.setTag(R.drawable.ic_favorite_border);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return storiesList.size();
    }


}
