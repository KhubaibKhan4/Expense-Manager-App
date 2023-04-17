package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensemanager.Database.DatabaseHelper;
import com.example.expensemanager.Models.Expense;

import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity {
    EditText edtTitle, edtAmount;
    Button saveBtn;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtAmount = (EditText) findViewById(R.id.edtAmount);
        saveBtn = (Button) findViewById(R.id.btnSave);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Data");
        progressDialog.setMessage("Your Expenses are Being Stored inside the Database.");
        progressDialog.setCancelable(false);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTitle.getText().toString().isEmpty() && edtAmount.getText().toString().isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(AddExpenseActivity.this, "Values can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Date date = new Date();
                    int day = date.getDay();
                    int month = date.getMonth();
                    int year = date.getYear();
                    long minutes = date.getMinutes();
                    long hours = date.getHours();
                    String Date = minutes + "-" + hours + "," + day + "/" + month + "/" + year;

                    String title = edtTitle.getText().toString();
                    String amount = edtAmount.getText().toString();
                    databaseHelper.expenseDao().addExpense(new Expense(title, amount, Date));
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            startActivity(new Intent(AddExpenseActivity.this, MainActivity.class));
                        }
                    }, 3000);
                }
            }
        });
    }
}