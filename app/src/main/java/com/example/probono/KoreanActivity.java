package com.example.probono;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        koreanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KoreanActivity.this, KoreanActivity.class);
                KoreanActivity.this.startActivity(intent);
            }
        });
        chickenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KoreanActivity.this, ChickenActivity.class);
                KoreanActivity.this.startActivity(intent);
            }
        });
        ttoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KoreanActivity.this, TtoActivity.class);
                KoreanActivity.this.startActivity(intent);
            }
        });
        dessertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KoreanActivity.this, DessertActivity.class);
                KoreanActivity.this.startActivity(intent);
            }
        });
    }
}
