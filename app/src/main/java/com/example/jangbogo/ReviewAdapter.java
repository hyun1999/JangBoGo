package com.example.jangbogo;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private ArrayList<review> arrayList;
    private ArrayList<String> uidList;
    private Context context;
    public static final int sub = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/

    public ReviewAdapter(ArrayList<review> arrayList, Context context) {
        this.arrayList = arrayList;
        this.uidList = uidList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        ReviewViewHolder holder = new ReviewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        holder.re_title.setText(arrayList.get(position).getRe_title());
        holder.re_review.setText(arrayList.get(position).getRe_review());
        holder.re_rating.setText("평점: " + arrayList.get(position).getRe_rating());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public review getItem(int position) {
        return arrayList != null ? arrayList.get(position) : null;
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        public TextView re_title, re_review, re_rating;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            this.re_title = (TextView) itemView.findViewById(R.id.re_title);
            this.re_review = (TextView) itemView.findViewById(R.id.re_review);
            this.re_rating = (TextView) itemView.findViewById(R.id.re_rating);
        }
    }
}