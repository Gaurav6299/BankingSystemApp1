package com.example.bankingsystemapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bankingsystemapp.databinding.ActivityToucherBinding;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Toucher_Activity extends AppCompatActivity {

    ActivityToucherBinding binding;
    ArrayList<UserModels>list_toucher=new ArrayList<>();
    ToucherAdapters adapters;

    String phone,name,currentbalance,transferamount,remainingamount;
    String selectuser_name,selectuser_phonenumber,selectuser_balance,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityToucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Transaction Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy,hh:mm a");
        date=simpleDateFormat.format(calendar.getTime());

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            name=bundle.getString("name");
            phone=bundle.getString("phone");
            currentbalance=bundle.getString("currentbalance");
            transferamount=bundle.getString("transferamount");
            showData(phone);

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void showData(String phone)
    {
        list_toucher.clear();
        Log.d("DEMO",phone);
        Cursor cursor=new DBHelper(this).readselectalluser(phone);
        while(cursor.moveToNext())
        {
            String mbalance=cursor.getString(2);
            Double balance=Double.parseDouble(mbalance);

            NumberFormat nf=NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price=nf.format(balance);


            UserModels models=new UserModels(cursor.getString(1),price,cursor.getString(0));

            list_toucher.add(models);

            adapters=new ToucherAdapters(this,list_toucher);
            binding.recyclerview.setAdapter(adapters);

            LinearLayoutManager layoutManager=new LinearLayoutManager(this);
            binding.recyclerview.setLayoutManager(layoutManager);
        }


    }

    public void selectuser(int position)
    {
        selectuser_phonenumber=list_toucher.get(position).getPhoneNumber();
        Cursor cursor=new DBHelper(this).getPhone(selectuser_phonenumber);
        while (cursor.moveToNext())
        {
            selectuser_name=cursor.getString(1);
            selectuser_balance=cursor.getString(2);
            Double Kselectuser_balance=Double.parseDouble(selectuser_balance);
            Double Tselectuser_balance=Double.parseDouble(transferamount);

            Double Dselectuser_remainingamount=Kselectuser_balance+Tselectuser_balance;
            new DBHelper(this).transferData(date,name,selectuser_name,transferamount,"Success");
            new DBHelper(this).updateAmount(selectuser_phonenumber,Dselectuser_remainingamount.toString());
            calculateAmount();
            Toast.makeText(this, "Transaction successfull", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Toucher_Activity.this,UserActivity.class));
            finish();

        }
    }

    private void calculateAmount()
    {
        Double Dcurrentbalance=Double.parseDouble(currentbalance);
        Double Dtrasnferbalance=Double.parseDouble(transferamount);
        Double Dremaining=Dcurrentbalance-Dtrasnferbalance;
        remainingamount=Dremaining.toString();
        new DBHelper(this).updateAmount(phone,remainingamount);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitbutton=new AlertDialog.Builder(this);
        exitbutton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DBHelper(Toucher_Activity.this).transferData(date,name,"Not selected",transferamount,"Failed");
                        Toast.makeText(Toucher_Activity.this, "Transaction cancelled", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Toucher_Activity.this,UserActivity.class));
                        finish();
                    }
                }).setNegativeButton("No",null);
        AlertDialog exit=exitbutton.create();
        exit.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.search,menu);
        MenuItem search=menu.findItem(R.id.action_search);
        android.widget.SearchView searchView= (android.widget.SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              newText=newText.toLowerCase();
              ArrayList<UserModels>newlist=new ArrayList<>();
              for(UserModels models:list_toucher) {
                  String name = models.getUserName().toLowerCase();
                  if(name.contains(newText)){
                      newlist.add(models);
                  }
              }
              adapters.setFilter(newlist);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}