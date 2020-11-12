package com.example.bankingsystemapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToucherAdapters extends RecyclerView.Adapter<ViewHolder> {

    Toucher_Activity toucher_activity;
    ArrayList<UserModels> list;
    Context context;

    public ToucherAdapters(Toucher_Activity toucher_activity, ArrayList<UserModels> list) {
        this.toucher_activity = toucher_activity;
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
                toucher_activity.selectuser(position);
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

    public void setFilter(ArrayList<UserModels> newlist)
    {
        list=new ArrayList<>();
        list.addAll(newlist);
        notifyDataSetChanged();
    }
}
