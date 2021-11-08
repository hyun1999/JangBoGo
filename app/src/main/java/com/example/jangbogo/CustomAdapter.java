package com.example.jangbogo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<list> arrayList;
    private Context context;

    public CustomAdapter(ArrayList<list> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_name.setText(arrayList.get(position).getStore_name());
        holder.tv_intro.setText(arrayList.get(position).getStore_intro());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tv_name;
        public TextView tv_intro;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_intro = (TextView) itemView.findViewById(R.id.tv_intro);}

            //아이템 클릭시 화면 전환
        @Override
        public void onClick(View v) {
            Snackbar.make(v,"스낵바입니다.",Snackbar.LENGTH_LONG).show();

            System.out.println(getPosition());
            Intent intent = new Intent(v.getContext() , StoreActivity.class);
            v.getContext().startActivity(intent);

        }
    }
}