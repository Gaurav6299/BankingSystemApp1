package com.example.bankingsystemapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapters extends RecyclerView.Adapter<ViewHolder> {


    UserActivity userActivity;
    ArrayList<UserModels> list;
    Context context;

    public UserAdapters(UserActivity userActivity, ArrayList<UserModels> list) {
        this.userActivity = userActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_user,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                userActivity.nextActivity(position);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.user.setText(list.get(position).getUserName());
        holder.phone.setText(list.get(position).getPhoneNumber());
        holder.balance.setText(list.get(position).getBalance());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
