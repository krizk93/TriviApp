package com.karimrizk.triviapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.databinding.ActivityGameOverBinding;

import static com.karimrizk.triviapp.Values.HIGH_SCORE;
import static com.karimrizk.triviapp.Values.SCORE_KEY;
import static com.karimrizk.triviapp.Values.SHARED_PREFERENCE_NAME;

public class GameOverActivity extends AppCompatActivity {

    ActivityGameOverBinding gameOverBinding;
    SharedPreferences sharedPreferences;
    Integer score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        gameOverBinding = DataBindingUtil.setContentView(this, R.layout.activity_game_over);
        score = getIntent().getIntExtra(SCORE_KEY, 0);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Integer highScore = sharedPreferences.getInt(HIGH_SCORE, 0);
        if (score > highScore) {
            highScore = score;
            sharedPreferences.edit().putInt(HIGH_SCORE, highScore).apply();
            gameOverBinding.labelNewHighscore.setVisibility(View.VISIBLE);
        }
        gameOverBinding.tvScore.setText(String.valueOf(score));
    }


    public void onPlayAgain(View v) {
        Intent intent = new Intent(GameOverActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void onViewAnswers(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GameOverActivity.this,HomeActivity.class);
        startActivity(intent);
    }
}
