package com.karimrizk.triviapp.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.utils.Values;
import com.karimrizk.triviapp.databinding.ActivityQuizBinding;
import com.karimrizk.triviapp.persistence.TriviaContract;

import java.util.ArrayList;
import java.util.List;

import static com.karimrizk.triviapp.utils.Values.CURRENT_QUESTION_KEY;
import static com.karimrizk.triviapp.utils.Values.SCORE_KEY;

public class QuizActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, QuizFragment.OnAnswerClickedListener {

    private static final String TAG = QuizActivity.class.getName();
    private static final int TASK_LOADER_ID = 0;
    private static Integer currentQuestion = 0;
    private static Integer lastQuestion;
    private static Integer score = 0;
    private static boolean replace = false;
    Uri uri;
    android.support.v4.app.FragmentManager fragmentManager;

    ActivityQuizBinding activityQuizBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        activityQuizBinding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY);
            currentQuestion = savedInstanceState.getInt(CURRENT_QUESTION_KEY);
        } else if (Values.isNewQuiz) {
            currentQuestion = 1;
            score = 0;
        }

        //to make sure we only load 10 questions
        lastQuestion = currentQuestion + 9;

        activityQuizBinding.txtScore.setText(String.valueOf(score));

        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE_KEY, score);
        outState.putInt(CURRENT_QUESTION_KEY, currentQuestion);
    }

    public void onClose(View v) {
        Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new android.support.v4.content.AsyncTaskLoader<Cursor>(this) {

            Cursor result;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
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
            QuizFragment quizFragment = new QuizFragment();
            quizFragment.setQuestion(data.getString(data.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_QUESTION)));
            quizFragment.setCorrectAnswer(data.getString(data.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_CORRECT_ANSWER)));

            List<String> incorrectAnswers = new ArrayList<>();
            incorrectAnswers.add(data.getString(data.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_1)));
            incorrectAnswers.add(data.getString(data.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_2)));
            incorrectAnswers.add(data.getString(data.getColumnIndex(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_3)));
            quizFragment.setIncorrectAnswers(incorrectAnswers);

            fragmentManager = getSupportFragmentManager();

            if (fragmentManager.findFragmentById(R.id.fragment_container) == null && !replace) {
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, quizFragment)
                        .commit();
            } else if (replace) {
                replace = false;
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, quizFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        //not applicable
    }

    @Override
    public void onCorrectAnswerClicked(String answer) {
        score = score + 10;
        activityQuizBinding.txtScore.setText(String.valueOf(score));

        ContentValues values = new ContentValues();
        values.put(TriviaContract.TriviaEntry.COLUMN_PLAYER_ANSWER, answer);
        uri = TriviaContract.TriviaEntry.CONTENT_URI.buildUpon().appendPath(currentQuestion.toString()).build();
        int updated = getContentResolver().update(uri, values, null, null);

        currentQuestion++;
        replace = true;

        if (currentQuestion <= lastQuestion) {
            getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
        } else {
            Values.isNewQuiz = false;
            Intent intent = new Intent(QuizActivity.this, CategoryChooserActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onIncorrectAnswerClicked(String answer) {
        ContentValues values = new ContentValues();
        values.put(TriviaContract.TriviaEntry.COLUMN_PLAYER_ANSWER, answer);
        uri = TriviaContract.TriviaEntry.CONTENT_URI.buildUpon().appendPath(currentQuestion.toString()).build();
        int updated = getContentResolver().update(uri, values, null, null);

        Intent intent = new Intent(QuizActivity.this, GameOverActivity.class);
        intent.putExtra(SCORE_KEY, score);
        startActivity(intent);
    }
}
