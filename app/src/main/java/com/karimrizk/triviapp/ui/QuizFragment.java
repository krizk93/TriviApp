package com.karimrizk.triviapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.databinding.FragmentQuizBinding;

import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends Fragment {
    private static final String QUESTION_KEY = "questionKey";
    private static final String CORRECT_ANSWER_KEY = "correctAnswerKey";
    private static final String INCORRECT_ANSWERS_KEY = "incorrectAnswersKey";
    private String question = "";
    private String correctAnswer = "";
    private List<String> incorrectAnswers = new ArrayList<>();

    public QuizFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            question = savedInstanceState.getString(QUESTION_KEY);
            correctAnswer = savedInstanceState.getString(CORRECT_ANSWER_KEY);
            incorrectAnswers = savedInstanceState.getStringArrayList(INCORRECT_ANSWERS_KEY);
        }

        FragmentQuizBinding fragmentQuizBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false);
        fragmentQuizBinding.txtQuestion.setText(question);
        fragmentQuizBinding.btnAnswer1.setText(correctAnswer);
        if (!incorrectAnswers.isEmpty()) {
            fragmentQuizBinding.btnAnswer2.setText(incorrectAnswers.get(0));
            fragmentQuizBinding.btnAnswer3.setText(incorrectAnswers.get(1));
            fragmentQuizBinding.btnAnswer4.setText(incorrectAnswers.get(2));
        }

        return fragmentQuizBinding.getRoot();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(QUESTION_KEY, question);
        outState.putString(CORRECT_ANSWER_KEY, correctAnswer);
        outState.putStringArrayList(INCORRECT_ANSWERS_KEY, (ArrayList<String>) incorrectAnswers);

    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }
}

