package com.karimrizk.triviapp.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.utils.Values;
import com.karimrizk.triviapp.api.Client;
import com.karimrizk.triviapp.api.Service;
import com.karimrizk.triviapp.databinding.ActivityCategoryChooserBinding;
import com.karimrizk.triviapp.model.Question;
import com.karimrizk.triviapp.model.Results;
import com.karimrizk.triviapp.persistence.TriviaContract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class CategoryChooserActivity extends AppCompatActivity {

    ActivityCategoryChooserBinding categoryChooserBinding;
    private static final String TAG = CategoryChooserActivity.class.getName();
    private int chosenCategory = 0;
    private static Integer questionNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_chooser);
        categoryChooserBinding = DataBindingUtil.setContentView(this, R.layout.activity_category_chooser);

        if (Values.isNewQuiz) {
            questionNumber = 0;
        }
    }

    public void onCategoryChosen(View v) {
        switch (v.getId()) {
            case R.id.btn_category_general:
                chosenCategory = Values.category.generalKnowledge.getCategoryID();
                break;
            case R.id.btn_category_sports:
                chosenCategory = Values.category.sports.getCategoryID();
                break;
            case R.id.btn_category_celebrities:
                chosenCategory = Values.category.celebrities.getCategoryID();
                break;
            case R.id.btn_category_geography:
                chosenCategory = Values.category.geography.getCategoryID();
                break;
            case R.id.btn_category_books:
                chosenCategory = Values.category.books.getCategoryID();
                break;
            case R.id.btn_category_film:
                chosenCategory = Values.category.film.getCategoryID();
                break;
        }
        if (!isOnline()) {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        } else {
            categoryChooserBinding.progressBar.setVisibility(View.VISIBLE);
            categoryChooserBinding.btnCategoryGeneral.setEnabled(false);
            categoryChooserBinding.btnCategorySports.setEnabled(false);
            categoryChooserBinding.btnCategoryCelebrities.setEnabled(false);
            categoryChooserBinding.btnCategoryGeography.setEnabled(false);
            categoryChooserBinding.btnCategoryBooks.setEnabled(false);
            categoryChooserBinding.btnCategoryFilm.setEnabled(false);

            new TriviaQueryTask().execute();
        }
    }

    public class TriviaQueryTask extends AsyncTask<Void, Void, List<Question>> {


        @Override
        protected List<Question> doInBackground(Void... voids) {

            Service apiService = Client.getClient().create(Service.class);
            Call<Results> callEasy = apiService.getResults(3, chosenCategory, Values.difficulty.easy.toString(), Values.QUIZ_TYPE);
            List<Question> questions = new ArrayList<>();
            try {
                questions = callEasy.execute().body().getQuestions();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Call<Results> callMedium = apiService.getResults(4, chosenCategory, Values.difficulty.medium.toString(), Values.QUIZ_TYPE);
            List<Question> mediumQuestions = new ArrayList<>();
            try {
                mediumQuestions = callMedium.execute().body().getQuestions();
            } catch (IOException e) {
                e.printStackTrace();

            }
            questions.addAll(mediumQuestions);


            Call<Results> callHard = apiService.getResults(3, chosenCategory, Values.difficulty.hard.toString(), Values.QUIZ_TYPE);
            List<Question> questionsHard = new ArrayList<>();
            try {
                questionsHard = callHard.execute().body().getQuestions();
            } catch (IOException e) {
                e.printStackTrace();
            }
            questions.addAll(questionsHard);

            return questions;
        }

        @Override
        protected void onPostExecute(List<Question> questions) {

            super.onPostExecute(questions);
            List<Uri> uris = new ArrayList<>();
            for (int i = 0; i < questions.size(); i++) {
                Uri uri = addToDatabase(questions.get(i));
                if (uri != null) {
                    uris.add(uri);
                }
            }
            categoryChooserBinding.progressBar.setVisibility(View.INVISIBLE);
            categoryChooserBinding.btnCategoryGeneral.setEnabled(true);
            categoryChooserBinding.btnCategorySports.setEnabled(true);
            categoryChooserBinding.btnCategoryCelebrities.setEnabled(true);
            categoryChooserBinding.btnCategoryGeography.setEnabled(true);
            categoryChooserBinding.btnCategoryBooks.setEnabled(true);
            categoryChooserBinding.btnCategoryFilm.setEnabled(true);

            if (!uris.isEmpty()) {
                Intent intent = new Intent(CategoryChooserActivity.this, QuizActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Error storing data", Toast.LENGTH_LONG).show();
            }

        }

    }

    public boolean isOnline() {

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private Uri addToDatabase(Question question) {
        Uri returnUri = null;
        questionNumber++;

        ContentValues contentValues = new ContentValues();

        contentValues.put(TriviaContract.TriviaEntry.COLUMN_ID, questionNumber);
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_CATEGORY, question.getCategory());
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_DIFFICULTY, question.getDifficulty());
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_QUESTION, question.getQuestion());
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_CORRECT_ANSWER, question.getCorrectAnswer());

        List<String> incorrectAnswers = question.getIncorrectAnswers();

        if (incorrectAnswers.size() == 3) {
            contentValues.put(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_1, incorrectAnswers.get(0));
            contentValues.put(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_2, incorrectAnswers.get(1));
            contentValues.put(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_3, incorrectAnswers.get(2));
        }

        contentValues.put(TriviaContract.TriviaEntry.COLUMN_PLAYER_ANSWER, "");

        try {
            returnUri = getContentResolver().insert(TriviaContract.TriviaEntry.CONTENT_URI, contentValues);

            if (returnUri != null) {
                Log.d(TAG, returnUri.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnUri;
    }
}
