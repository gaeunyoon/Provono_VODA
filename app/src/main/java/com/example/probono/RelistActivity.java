package com.example.probono;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RelistActivity extends AppCompatActivity {

    TextView outputView;
    TextView number;
    Button rebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_relist);
        outputView =(TextView) findViewById(R.id.output);
        number =(TextView) findViewById(R.id.number);

        rebtn=(Button)findViewById(R.id.rebtn);
        rebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence rating=number.getText();

                Intent intent = new Intent(RelistActivity.this, ReviewActivity.class);
                RelistActivity.this.startActivity(intent);

                intent.putExtra("rating",rating);
                startActivityForResult(intent,101);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == 101){

            if(intent !=null){
                String contents=intent.getStringExtra("contents");
                float ratingBarupdate = intent.getFloatExtra("ratingbarupdate",0.0f);
                outputView.setText(contents);
                number.setText((int) ratingBarupdate);
            }
        }
    }


}