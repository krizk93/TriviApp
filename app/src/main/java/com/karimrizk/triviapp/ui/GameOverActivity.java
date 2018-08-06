package com.karimrizk.triviapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.karimrizk.triviapp.R;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }


    public void onPlayAgain(View v){
        Intent intent = new Intent(GameOverActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    public void onViewAnswers(View v){

    }
}
