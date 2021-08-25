package com.example.retrofitparttwo.network;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitparttwo.R;
import com.example.retrofitparttwo.models.Post;

import java.util.List;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.MyViewHolder> {

    Context context;
    private List<Post> posts;

    public RetrofitAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_of_recyclerview, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Post post = posts.get(position);
        Log.d("test_otne","massage : "+ post);
        Log.d("test_otne","massage : "+ post.getTitle());
        holder.tv_One.setText(post.getTitle());
        holder.tv_Two.setText(String.valueOf(post.getId()));
        holder.tv_Three.setText(post.getBody());
        holder.tv_four.setText(String.valueOf(post.getUserId()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_One, tv_Two, tv_Three, tv_four;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_One =  itemView.findViewById(R.id.tvIdOne);
            tv_Two = itemView.findViewById(R.id.tvIdTwo);
            tv_Three =  itemView.findViewById(R.id.tvIdThree);
            tv_four =  itemView.findViewById(R.id.tvIdFour);
        }

    }
}