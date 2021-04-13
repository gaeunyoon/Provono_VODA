package com.example.probono;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DessertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist4);

        final Button koreanButton=(Button) findViewById(R.id.c1);
        final Button chickenButton=(Button) findViewById(R.id.c2);
        final Button ttoButton=(Button) findViewById(R.id.c3);
        final Button dessertButton=(Button) findViewById(R.id.c4);

        koreanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DessertActivity.this, KoreanActivity.class);
                DessertActivity.this.startActivity(intent);
                koreanButton.setBackgroundColor(getResources().getColor(R.color.white));
                koreanButton.setTextColor(getResources().getColor(R.color.black));
            }
        });
        chickenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DessertActivity.this, ChickenActivity.class);
                DessertActivity.this.startActivity(intent);
                chickenButton.setBackgroundColor(getResources().getColor(R.color.white));
                chickenButton.setTextColor(getResources().getColor(R.color.black));
            }
        });
        ttoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DessertActivity.this, TtoActivity.class);
                DessertActivity.this.startActivity(intent);
                ttoButton.setBackgroundColor(getResources().getColor(R.color.white));
                ttoButton.setTextColor(getResources().getColor(R.color.black));
            }
        });
        dessertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DessertActivity.this, DessertActivity.class);
                DessertActivity.this.startActivity(intent);
                dessertButton.setBackgroundColor(getResources().getColor(R.color.white));
                dessertButton.setTextColor(getResources().getColor(R.color.black));
            }
        });
    }
}
