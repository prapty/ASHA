package com.thenewboston.project;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowCard extends AppCompatActivity {
    //int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);
        //DBHelper helper = new DBHelper(this);
        ImageView imview = (ImageView) findViewById(R.id.mImageView);
        Context context=imview.getContext();
        int imageid= context.getResources().getIdentifier(FlashActivity.selCard,"drawable",context.getPackageName());
        imview.setImageResource(imageid);
        //CardItem newItem=new CardItem(FlashActivity.selCard,helper.findPath(FlashActivity.selCard),count);
        //helper.updateCard(newItem);
        //count++;
        //helper.updateMostHit("Flashcards",FlashActivity.selCard);
    }
}
