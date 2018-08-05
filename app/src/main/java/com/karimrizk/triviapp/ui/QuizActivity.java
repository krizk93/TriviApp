package com.karimrizk.triviapp.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.persistence.TriviaContract;

public class QuizActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = QuizActivity.class.getName();
    private static final int TASK_LOADER_ID = 0;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        QuizFragment quizFragment = new QuizFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, quizFragment)
                .commit();

        uri = TriviaContract.TriviaEntry.CONTENT_URI.buildUpon().appendPath("1").build();
        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QuizActivity.this,HomeActivity.class);
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
            Toast.makeText(getApplicationContext(), question, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        //not applicable
    }
}
