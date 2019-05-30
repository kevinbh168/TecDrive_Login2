package com.brena.tecdrive_login.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brena.tecdrive_login.Models.Posts;
import com.brena.tecdrive_login.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Posts> postsList;
    private Activity activity;
    public PostsAdapter(){
        this.postsList=new ArrayList<>();
    }
    public void setPostsList(List<Posts> postsList){
        this.postsList=postsList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_posts, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder viewHolder, int i) {
        final Posts posts=this.postsList.get(i);
        viewHolder.nameText.setText(posts.getUser().getName()+" "+posts.getUser().getLastname());
        viewHolder.titleText.setText(posts.getTitle());
        viewHolder.descriptionText.setText(posts.getDescription());
        Date fecha=posts.getCreatedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMM,yyyy");
        viewHolder.dateText.setText(sdf.format(fecha));
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private TextView dateText;
        private TextView descriptionText;
        private TextView titleText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText=itemView.findViewById(R.id.post_user);
            dateText=itemView.findViewById(R.id.post_date);
            titleText=itemView.findViewById(R.id.post_title);
            descriptionText=itemView.findViewById(R.id.post_description);

        }
    }

}
