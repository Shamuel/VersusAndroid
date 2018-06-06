package com.vs.versus.versus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class CreatingVersus extends AppCompatActivity {


    ImageView Text,images;


//Activity where You select the type of VERSUS you want to make
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_versus);

        Text = (ImageView) findViewById(R.id.Text);
        images = (ImageView) findViewById(R.id.images);


        Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatingVersus.this,TextVersus.class);
                startActivity(intent);

//                Text.setVisibility(View.INVISIBLE);
//                images.setVisibility(View.INVISIBLE);
//
//                VStext.setVisibility(View.VISIBLE);
//                VStext2.setVisibility(View.VISIBLE);
//                addtext.setVisibility(View.VISIBLE);

            }
        });

        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatingVersus.this,ImageVersus.class);
                startActivity(intent);

//                Text.setVisibility(View.INVISIBLE);
//                images.setVisibility(View.INVISIBLE);
//
//                VStext.setVisibility(View.VISIBLE);
//                VStext2.setVisibility(View.VISIBLE);
//                addtext.setVisibility(View.VISIBLE);

            }
        });


    }
}
