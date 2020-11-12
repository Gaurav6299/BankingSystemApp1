package com.example.bankingsystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bankingsystemapp.databinding.ActivityDetailsBinding;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    String phone;
    Double balance;
    ProgressDialog progress;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("User Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress=new ProgressDialog(this);
        progress.setTitle("Loading Data...");
        progress.show();

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            phone=bundle.getString("phonenumber");
            showData(phone);
        }

        binding.detailsTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterBalance();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void showData(String phone)
    {
        Cursor cursor=new DBHelper(this).getPhone(phone);
        while(cursor.moveToNext())
        {
       String mbalance=cursor.getString(2);

       balance=Double.parseDouble(mbalance);

            NumberFormat nf=NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price=nf.format(balance);

            progress.dismiss();
            binding.detailsName.setText(cursor.getString(1));
            binding.detailsPhone.setText(cursor.getString(0));
            binding.detailsEmail.setText(cursor.getString(3));
            binding.detailsPrice.setText(price);
            binding.account.setText(cursor.getString(4));


        }

    }

    public void enterBalance()
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(DetailsActivity.this);
        View nView=getLayoutInflater().inflate(R.layout.transfer_money,null);
        builder.setTitle("Enter Amount").setView(nView).setCancelable(false);

        final EditText amount=(EditText)nView.findViewById(R.id.enterMoney);

       builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

           }
       }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
               cancelTransaction();

           }
       });
       dialog=builder.create();
       dialog.show();
       dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(amount.getText().toString().isEmpty())
               {
                   amount.setError("Amount cant not be empty");
               }
              else if(Double.parseDouble(amount.getText().toString())>balance)
               {
                   amount.setError("Your account do not have enough space");
               }

              else{
                  Intent intent=new Intent(DetailsActivity.this,Toucher_Activity.class);
                  intent.putExtra("phone",binding.detailsPhone.getText().toString());
                  intent.putExtra("name",binding.detailsName.getText().toString());
                  intent.putExtra("currentbalance",balance.toString());
                  intent.putExtra("transferamount",amount.getText().toString());
                  startActivity(intent);
                  finish();
               }

           }
       });



    }

    public void cancelTransaction()
    {
        AlertDialog.Builder exitbutton=new AlertDialog.Builder(this);
        exitbutton.setTitle("Do you want to exit the tranaction?").setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar calendar= Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy,hh:mm a");
                        String date=simpleDateFormat.format(calendar.getTime());
                        new DBHelper(DetailsActivity.this).transferData(date,binding.detailsName.getText().toString(),"Not selected","0","Failed");
                        Toast.makeText(DetailsActivity.this, "Transaction Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterBalance();

            }
        });
        AlertDialog alert=exitbutton.create();
        alert.show();
    }
}