package com.example.probono;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class KoreanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist1);

        final Button koreanButton=(Button) findViewById(R.id.c1);
        final Button chickenButton=(Button) findViewById(R.id.c2);
        final Button ttoButton=(Button) findViewById(R.id.c3);
        final Button dessertButton=(Button) findViewById(R.id.c4);

        final Button menuButton=(Button) findViewById(R.id.mb11);
        ImageView imageButton=(ImageView) findViewById(R.id.im11);


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KoreanActivity.this,Korean1_Activity.class);
                KoreanActivity.this.startActivity(intent);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KoreanActivity.this, Korean1_Activity.class);
                KoreanActivity.this.startActivity(intent);
            }
        });


        koreanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KoreanActivity.this, KoreanActivity.class);
                KoreanActivity.this.startActivity(intent);
                koreanButton.setBackgroundColor(getResources().getColor(R.color.white));
                koreanButton.setTextColor(getResources().getColor(R.color.black));

            }
        });
        chickenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KoreanActivity.this, ChickenActivity.class);
                KoreanActivity.this.startActivity(intent);
                chickenButton.setBackgroundColor(getResources().getColor(R.color.white));
                chickenButton.setTextColor(getResources().getColor(R.color.black));

            }
        });
        ttoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KoreanActivity.this, TtoActivity.class);
                KoreanActivity.this.startActivity(intent);
                ttoButton.setBackgroundColor(getResources().getColor(R.color.white));
                ttoButton.setTextColor(getResources().getColor(R.color.black));

            }
        });
        dessertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KoreanActivity.this, DessertActivity.class);
                KoreanActivity.this.startActivity(intent);
                dessertButton.setBackgroundColor(getResources().getColor(R.color.white));
                dessertButton.setTextColor(getResources().getColor(R.color.black));
            }
        });


    }
}
