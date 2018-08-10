package com.karimrizk.triviapp.ui;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.adapters.AnswersAdapter;
import com.karimrizk.triviapp.databinding.ActivityUserAnswersBinding;
import com.karimrizk.triviapp.persistence.TriviaContract;

public class UserAnswersActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    ActivityUserAnswersBinding userAnswersBinding;

    private static final String TAG = QuizActivity.class.getName();
    private Context mContext;
    private static final int LOADER_ID = 0;
    private AnswersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_answers);
        userAnswersBinding = DataBindingUtil.setContentView(this,R.layout.activity_user_answers);

        mContext = getApplicationContext();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        userAnswersBinding.recyclerView.setLayoutManager(layoutManager);

        mAdapter = new AnswersAdapter(mContext);
        userAnswersBinding.recyclerView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

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
            public void deliverResult(Cursor data) {
                super.deliverResult(data);
            }

            @Nullable
            @Override
            public Cursor loadInBackground() {
                try {
                    String selection = TriviaContract.TriviaEntry.COLUMN_PLAYER_ANSWER + "!= ?";
                    String[] selectionArgs = new String[] {""};
                    return result = getContentResolver().query(TriviaContract.TriviaEntry.CONTENT_URI, null, selection, selectionArgs, TriviaContract.TriviaEntry.COLUMN_ID);

                } catch(Exception e){
                    Log.e(TAG, "Failed to asynchronously load data");
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}

