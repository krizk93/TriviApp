package com.karimrizk.triviapp.ui;

import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.Values;
import com.karimrizk.triviapp.databinding.ActivityQuizBinding;
import com.karimrizk.triviapp.persistence.TriviaContract;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, QuizFragment.OnAnswerClickedListener {

    private static final String TAG = QuizActivity.class.getName();
    private static final int TASK_LOADER_ID = 0;
    private static Integer currentQuestion = 0;
    private static Integer lastQuestion;
    private static Integer score = 0;
    Uri uri;
    private QuizFragment quizFragment;
    android.support.v4.app.FragmentManager fragmentManager;

    ActivityQuizBinding activityQuizBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        activityQuizBinding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);


        if (Values.isNewQuiz) {
            currentQuestion = 0;
            score = 0;
        }

        lastQuestion = currentQuestion + 10;
        currentQuestion++;
        activityQuizBinding.txtScore.setText(String.valueOf(score));

        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);

    }

    public void onClose(View v) {
        Intent intent = new Intent(QuizActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new android.support.v4.content.AsyncTaskLoader<Cursor>(this) {

            Cursor result;

            @Override
            protected void onStartLoading() {
                //super.onStartLoading();
                if (result != null) {
                    deliverResult(result);
                } else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(@Nullable Cursor data) {
                result = data;
                super.deliverResult(data);
            }

            @Nullable
            @Override
            public Cursor loadInBackground() {
                try {
                    uri = TriviaContract.TriviaEntry.CONTENT_URI.buildUpon().appendPath(currentQuestion.toString()).build();
                    return getContentResolver().query(uri, null, null, null, null);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data");
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            String question = data.getString(data.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_QUESTION));
            Log.d(TAG, question);
            quizFragment = new QuizFragment();
            quizFragment.setQuestion(data.getString(data.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_QUESTION)));
            quizFragment.setCorrectAnswer(data.getString(data.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_CORRECT_ANSWER)));

            List<String> incorrectAnswers = new ArrayList<>();
            incorrectAnswers.add(data.getString(data.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_1)));
            incorrectAnswers.add(data.getString(data.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_2)));
            incorrectAnswers.add(data.getString(data.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_3)));
            quizFragment.setIncorrectAnswers(incorrectAnswers);

            fragmentManager = getSupportFragmentManager();

            if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, quizFragment)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,quizFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        //not applicable
    }

    @Override
    public void onCorrectAnswerClicked() {
        //Toast.makeText(getApplicationContext(),"CORRECT !",Toast.LENGTH_SHORT).show();
        score = score + 10;
        activityQuizBinding.txtScore.setText(String.valueOf(score));
        currentQuestion++;
        //Loader<Cursor> loader =  getLoaderManager().getLoader(TASK_LOADER_ID);
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID,null,this);

    }

    @Override
    public void onIncorrectAnswerClicked() {
        //Toast.makeText(getApplicationContext(),"INCORRECT !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(QuizActivity.this,GameOverActivity.class);
        startActivity(intent);
    }
}
