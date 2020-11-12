package com.example.bankingsystemapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bankingsystemapp.databinding.ActivityUserBinding;

import java.text.NumberFormat;
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding;
    ArrayList<UserModels> list=new ArrayList<>();

    String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Users List");
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
        list.clear();
        Cursor cursor=new DBHelper(this).readalldata();
        while (cursor.moveToNext())
        {
            String mbalance=cursor.getString(2);
            Double balance=Double.parseDouble(mbalance);
            NumberFormat nf=NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(2);

            String price=nf.format(balance);

            UserModels models=new UserModels(cursor.getString(1),price,cursor.getString(0));
            list.add(models);

            UserAdapters adapters=new UserAdapters(this,list);
            binding.userRecyclerView.setAdapter(adapters);

            LinearLayoutManager layoutManager=new LinearLayoutManager(this);
            binding.userRecyclerView.setLayoutManager(layoutManager);
        }
    }
    public void nextActivity(int position)
    {
        phonenumber=list.get(position).getPhoneNumber();
        Intent intent=new Intent(UserActivity.this,DetailsActivity.class);
        intent.putExtra("phonenumber",phonenumber);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId()==R.id.tranBtn)
       {
           startActivity(new Intent(UserActivity.this,TransactionActivity.class));
       }
        return super.onOptionsItemSelected(item);
    }
}