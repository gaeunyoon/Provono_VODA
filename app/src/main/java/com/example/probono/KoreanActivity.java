package com.example.probono;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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

        final Button mb11 =(Button) findViewById(R.id.mb11);
        final Button mb12 =(Button) findViewById(R.id.mb12);
        final Button mb13 =(Button) findViewById(R.id.mb13);
        final Button mb14 =(Button) findViewById(R.id.mb14);

        final ImageButton im11 = (ImageButton) findViewById(R.id.im11);
        final ImageButton im12 = (ImageButton) findViewById(R.id.im12);
        final ImageButton im13 = (ImageButton) findViewById(R.id.im13);
        final ImageButton im14 = (ImageButton) findViewById(R.id.im14);

        mb11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KoreanActivity.this, Korean1_Activity.class);
                KoreanActivity.this.startActivity(intent);
            }
        });

        im11.setOnClickListener(new View.OnClickListener() {
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
