package com.example.jangbogo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<list> arrayList;
    private ArrayList<String> uidList;
    private Context context;
    public static final int sub = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/

    public CustomAdapter(ArrayList<list> arrayList, ArrayList<String> uidList, Context context) {
        this.arrayList = arrayList;
        this.uidList = uidList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_name.setText(arrayList.get(position).getStore_name());
        holder.tv_intro.setText(arrayList.get(position).getStore_intro());
        holder.tv_sale.setText(arrayList.get(position).getStore_sale());
        holder.tv_sell_point.setText("판매수: " + arrayList.get(position).getStore_sell());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public list getItem(int position) {
        return arrayList != null ? arrayList.get(position) : null;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_name;
        public TextView tv_intro;
        public TextView tv_sale;
        public TextView tv_sell_point;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener((View.OnClickListener) this);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_intro = (TextView) itemView.findViewById(R.id.tv_intro);
            this.tv_sale = (TextView) itemView.findViewById(R.id.tv_sale);
            this.tv_sell_point = (TextView) itemView.findViewById(R.id.tv_sell_point);
        }

        //아이템 클릭시 화면 전환
        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();

            Intent intent = new Intent(v.getContext(), StoreActivity.class);
            intent.putExtra("item", getItem(pos));
            intent.putExtra("uid", uidList.get(pos));
            v.getContext().startActivity(intent);
        }
    }
}