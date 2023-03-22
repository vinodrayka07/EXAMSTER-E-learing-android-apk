package com.example.examaster;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class yearAdapter extends RecyclerView.Adapter<yearAdapter.MovieHolder> {

    private final Context context;
    private final List<Movie> movieList;

    public yearAdapter(Context context , List<Movie> movies){
        this.context = context;
        movieList = movies;
    }



    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item , parent , false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {

        Movie movie = movieList.get(position);
        Glide.with(context).load(movie.getImageurl()).into(holder.imageView);
        holder.title.setText(movie.getTitle());
        holder.subtitle.setText(movie.getSubtitle());
        Glide.with(context).load(movie.getImageurl2()).into(holder.imageView1);

        holder.constraintLayout.setOnClickListener(v -> {
            AppCompatActivity activity=(AppCompatActivity)v.getContext();
            peparFragment peparFragment =new peparFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,peparFragment).addToBackStack(null).commit();


            Bundle bundle = new Bundle();
            //bundle.putString("title" , movie.getTitle());
            //bundle.putString("subtitle" , movie.getSubtitle());
            //bundle.putString("imageurl" , movie.getImageurl());
            //bundle.putString("imageurl2" , movie.getImageurl2());
            bundle.putString("otherurl" , movie.getOtherurl());


            peparFragment.setArguments(bundle);




        });

    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder{

        ImageView imageView , imageView1;
        TextView title , subtitle;
        ConstraintLayout constraintLayout;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageview);
            title = itemView.findViewById(R.id.title_tv);
            subtitle = itemView.findViewById(R.id.rating);
            imageView1 = itemView.findViewById(R.id.imageView2);
            constraintLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}

