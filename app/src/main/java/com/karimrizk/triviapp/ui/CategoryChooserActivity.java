package com.karimrizk.triviapp.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import com.karimrizk.triviapp.Values;
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
                questions = callEasy.execute().body().getquestions();
            } catch (IOException e) {
                e.printStackTrace();
            }

/*            callEasy.enqueue(new Callback<Results>() {
                @Override
                public void onResponse(Call<Results> call, Response<Results> response) {
                    List<Question> questions = response.body().getquestions();

                    for (int i = 0; i < questions.size(); i++) {
                        addToDatabase(questions.get(i));
                    }


                }

                @Override
                public void onFailure(Call<Results> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", "Error retrieving data");
                }
            });*/

            Call<Results> callMedium = apiService.getResults(4, chosenCategory, Values.difficulty.medium.toString(), Values.QUIZ_TYPE);
            List<Question> mediumQuestions = new ArrayList<>();
            try {
                mediumQuestions = callMedium.execute().body().getquestions();
            } catch (IOException e) {
                e.printStackTrace();

            }
            questions.addAll(mediumQuestions);

            /*callMedium.enqueue(new Callback<Results>() {
                @Override
                public void onResponse(Call<Results> call, Response<Results> response) {
                    List<Question> questions = response.body().getquestions();

                    for (int i = 0; i < questions.size(); i++) {
                        addToDatabase(questions.get(i));
                    }
                }

                @Override
                public void onFailure(Call<Results> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", "Error retrieving data");
                }
            });*/


            Call<Results> callHard = apiService.getResults(3, chosenCategory, Values.difficulty.hard.toString(), Values.QUIZ_TYPE);
            List<Question> questionsHard = new ArrayList<>();
            try {
                questionsHard = callHard.execute().body().getquestions();
            } catch (IOException e) {
                e.printStackTrace();
            }
            questions.addAll(questionsHard);
/*            callHard.enqueue(new Callback<Results>() {
                @Override
                public void onResponse(Call<Results> call, Response<Results> response) {
                    List<Question> questions = response.body().getquestions();

                    for (int i = 0; i < questions.size(); i++) {
                        addToDatabase(questions.get(i));
                    }
                }

                @Override
                public void onFailure(Call<Results> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", "Error retrieving data");
                }
            });*/
            return questions;
        }

        @Override
        protected void onPostExecute(List<Question> questions) {
            super.onPostExecute(questions);
            for (int i = 0; i < questions.size(); i++){
                addToDatabase(questions.get(i));
                categoryChooserBinding.progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(CategoryChooserActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        }

       /* @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            categoryChooserBinding.progressBar.setVisibility(View.INVISIBLE);
            //Intent intent = new Intent(CategoryChooserActivity.this, QuizActivity.class);
            //startActivity(intent);


        }*/
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private void addToDatabase(Question question) {
        questionNumber++;
        ContentValues contentValues = new ContentValues();
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_ID, questionNumber);
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_CATEGORY, question.getCategory());
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_DIFFICULTY, question.getDifficulty());
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_QUESTION, question.getQuestion());
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_CORRECT_ANSWER, question.getCorrectAnswer());
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_1, question.getIncorrectAnswers().get(0));
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_2, question.getIncorrectAnswers().get(1));
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_3, question.getIncorrectAnswers().get(2));
        contentValues.put(TriviaContract.TriviaEntry.COLUMN_PLAYER_ANSWER, "");

        Uri uri = getContentResolver().insert(TriviaContract.TriviaEntry.CONTENT_URI, contentValues);
        Log.d(TAG,uri.toString());

    }
}
