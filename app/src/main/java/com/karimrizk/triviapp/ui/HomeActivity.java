package com.karimrizk.triviapp.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.Values;
import com.karimrizk.triviapp.databinding.ActivityHomeBinding;
import com.karimrizk.triviapp.persistence.TriviaContract;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding homeBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeBinding = DataBindingUtil.setContentView(this,R.layout.activity_home);

        int removed = getContentResolver().delete(TriviaContract.TriviaEntry.CONTENT_URI,null,null);
        if (removed > 0) {
            Toast.makeText(getApplicationContext(),"Removed", Toast.LENGTH_SHORT).show();
        }


        homeBinding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Values.isNewQuiz = true;
                Intent intent = new Intent(HomeActivity.this,CategoryChooserActivity.class);
                startActivity(intent);
            }
        });
    }
}
