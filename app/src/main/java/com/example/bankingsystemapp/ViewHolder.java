package com.example.bankingsystemapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView user,phone,balance,name1,name2,date,transaction,mBalance;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        user=itemView.findViewById(R.id.userName);
        phone=itemView.findViewById(R.id.userPhoneNumber);
        balance=itemView.findViewById(R.id.balance);
        name1=itemView.findViewById(R.id.mName1);
        name2=itemView.findViewById(R.id.mName2);
        date=itemView.findViewById(R.id.date);
        transaction=itemView.findViewById(R.id.status);
        mBalance=itemView.findViewById(R.id.tranAmount);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());

            }
        });
    }
    private ViewHolder.ClickListener mClickListener;
    public interface ClickListener{
        void onItemClick(View v,int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener)
    {
        mClickListener=clickListener;
    }
}
