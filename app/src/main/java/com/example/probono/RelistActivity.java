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
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_relist);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        outputView =(TextView) findViewById(R.id.output);

        Button rebtn=(Button)findViewById(R.id.rebtn);
        rebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentWriteActivity();
            }
        });

    }
    public void showCommentWriteActivity(){
        float rating=ratingBar.getRating();

        Intent intent = new Intent(getApplicationContext(),ReviewActivity.class);
        intent.putExtra("rating",rating);
        startActivityForResult(intent,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == 101){

            if(intent !=null){
                String contents=intent.getStringExtra("contents");
                float ratingBarupdate = intent.getFloatExtra("ratingbarupdate",0.0f);
                outputView.setText(contents);
                ratingBar.setRating(ratingBarupdate);
            }
        }
    }


}