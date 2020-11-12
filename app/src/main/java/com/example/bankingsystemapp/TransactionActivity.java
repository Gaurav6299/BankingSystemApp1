package com.example.bankingsystemapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.bankingsystemapp.databinding.ActivityTransactionBinding;

import java.text.NumberFormat;
import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {

    ActivityTransactionBinding binding;
    ArrayList<TransactionModels> historylist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Transaction List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        showData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void showData()
    {
        historylist.clear();
        Cursor cursor=new DBHelper(this).readTransferData();
        while(cursor.moveToNext())
        {
            String mbalance=cursor.getString(4);
            Double balance=Double.parseDouble(mbalance);

            NumberFormat nf=NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(2);
            String price=nf.format(balance);


            TransactionModels models=new TransactionModels(cursor.getString(2),cursor.getString(3),cursor.getString(1),cursor.getString(5),price);
            historylist.add(models);

            TransactionAdapters adapters=new TransactionAdapters(this,historylist);
            binding.transactionRecyclerView.setAdapter(adapters);

            LinearLayoutManager layoutManager=new LinearLayoutManager(this);
            binding.transactionRecyclerView.setLayoutManager(layoutManager);


        }
        if(historylist.size()==0)
        {
            binding.emptyText.setVisibility(View.VISIBLE);
        }

    }
}