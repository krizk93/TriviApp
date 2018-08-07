package com.karimrizk.triviapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.utils.Values;
import com.karimrizk.triviapp.databinding.ActivityHomeBinding;
import com.karimrizk.triviapp.persistence.TriviaContract;

import static com.karimrizk.triviapp.utils.Values.HIGH_SCORE;
import static com.karimrizk.triviapp.utils.Values.SHARED_PREFERENCE_NAME;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding homeBinding;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt(HIGH_SCORE, 0);
        homeBinding.tvHighScore.setText(String.valueOf(highScore));

        int removed = getContentResolver().delete(TriviaContract.TriviaEntry.CONTENT_URI, null, null);
        if (removed > 0) {
            Toast.makeText(getApplicationContext(), "Removed", Toast.LENGTH_SHORT).show();
        }
    }


    public void onStartClicked(View v) {
        Values.isNewQuiz = true;
        Intent intent = new Intent(HomeActivity.this, CategoryChooserActivity.class);
        startActivity(intent);
    }

}
