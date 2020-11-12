package com.example.bankingsystemapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionAdapters extends RecyclerView.Adapter<ViewHolder> {

    TransactionActivity transactionActivity;
    ArrayList<TransactionModels> tran_list;
    Context context;

    public TransactionAdapters(TransactionActivity transactionActivity, ArrayList<TransactionModels> tran_list) {
        this.transactionActivity = transactionActivity;
        this.tran_list = tran_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_transaction,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name1.setText(tran_list.get(position).getName1());
        holder.name2.setText(tran_list.get(position).getName2());
        holder.mBalance.setText(tran_list.get(position).getBalance());
        holder.date.setText(tran_list.get(position).getDate());
        holder.transaction.setText(tran_list.get(position).getTransaction());

        if(tran_list.get(position).getTransaction().equals("Failed")){
            holder.transaction.setTextColor(Color.parseColor("#f40404"));
        }else{
            holder.transaction.setTextColor(Color.parseColor("#4BB534"));
        }

    }

    @Override
    public int getItemCount() {
        return tran_list.size();
    }
}
