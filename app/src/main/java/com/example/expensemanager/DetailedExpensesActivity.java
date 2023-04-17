package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DetailedExpensesActivity extends AppCompatActivity {
    TextView title_txt_detailed, desc_txt_detailed, textView_date_detailed;
    CardView notes_container_detailed;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_expenses);

        title_txt_detailed = (TextView) findViewById(R.id.title_txt_detailed);
        desc_txt_detailed = (TextView) findViewById(R.id.desc_txt_detailed);
        textView_date_detailed = (TextView) findViewById(R.id.textView_date_detailed);
        notes_container_detailed = (CardView) findViewById(R.id.notes_container_detailed);

        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("value");
        String date = getIntent().getStringExtra("date");
        String id = getIntent().getStringExtra("id");

        title_txt_detailed.setText(title);
        desc_txt_detailed.setText(desc);
        textView_date_detailed.setText(date);

        title_txt_detailed.setTextColor(Color.WHITE);
        desc_txt_detailed.setTextColor(Color.WHITE);
        textView_date_detailed.setTextColor(Color.WHITE);

        int colorCode = getRandomColor();
        notes_container_detailed.setCardBackgroundColor(getResources().getColor(colorCode));

    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);
        colorCode.add(R.color.color6);
        colorCode.add(R.color.color7);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}