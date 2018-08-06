package com.karimrizk.triviapp.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.databinding.FragmentQuizBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.karimrizk.triviapp.Values.ANSWERS_KEY;
import static com.karimrizk.triviapp.Values.CORRECT_ANSWER_KEY;
import static com.karimrizk.triviapp.Values.INCORRECT_ANSWERS_KEY;
import static com.karimrizk.triviapp.Values.QUESTION_KEY;

public class QuizFragment extends Fragment implements View.OnClickListener {


    private String question = "";
    private String correctAnswer = "";
    private List<String> incorrectAnswers = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    private FragmentQuizBinding fragmentQuizBinding;

    OnAnswerClickedListener mCallback;

    public interface OnAnswerClickedListener {
        void onCorrectAnswerClicked();

        void onIncorrectAnswerClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnAnswerClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnAnswerClickedListener");
        }
    }

    public QuizFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            question = savedInstanceState.getString(QUESTION_KEY);
            correctAnswer = savedInstanceState.getString(CORRECT_ANSWER_KEY);
            incorrectAnswers = savedInstanceState.getStringArrayList(INCORRECT_ANSWERS_KEY);
            answers = savedInstanceState.getStringArrayList(ANSWERS_KEY);
        } else {
            answers.add(correctAnswer);
            answers.add(incorrectAnswers.get(0));
            answers.add(incorrectAnswers.get(1));
            answers.add(incorrectAnswers.get(2));
            Collections.shuffle(answers);
        }

        fragmentQuizBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false);
        fragmentQuizBinding.txtQuestion.setText(question);
        fragmentQuizBinding.btnAnswer1.setText(answers.get(0));
        if (!incorrectAnswers.isEmpty()) {
            fragmentQuizBinding.btnAnswer2.setText(answers.get(1));
            fragmentQuizBinding.btnAnswer3.setText(answers.get(2));
            fragmentQuizBinding.btnAnswer4.setText(answers.get(3));
        }

        fragmentQuizBinding.btnAnswer1.setOnClickListener(this);
        fragmentQuizBinding.btnAnswer2.setOnClickListener(this);
        fragmentQuizBinding.btnAnswer3.setOnClickListener(this);
        fragmentQuizBinding.btnAnswer4.setOnClickListener(this);

        //helper function
        helper(fragmentQuizBinding.btnAnswer1);
        helper(fragmentQuizBinding.btnAnswer2);
        helper(fragmentQuizBinding.btnAnswer3);
        helper(fragmentQuizBinding.btnAnswer4);

        return fragmentQuizBinding.getRoot();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(QUESTION_KEY, question);
        outState.putString(CORRECT_ANSWER_KEY, correctAnswer);
        outState.putStringArrayList(INCORRECT_ANSWERS_KEY, (ArrayList<String>) incorrectAnswers);
        outState.putStringArrayList(ANSWERS_KEY, (ArrayList<String>) answers);

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


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_answer1:
                checkForAnswer(fragmentQuizBinding.btnAnswer1);
                break;
            case R.id.btn_answer2:
                checkForAnswer(fragmentQuizBinding.btnAnswer2);
                break;
            case R.id.btn_answer3:
                checkForAnswer(fragmentQuizBinding.btnAnswer3);
                break;
            case R.id.btn_answer4:
                checkForAnswer(fragmentQuizBinding.btnAnswer4);
                break;
        }
    }

    private void checkForAnswer(Button button) {
        if (button.getText().toString().equals(correctAnswer)) {
            //button.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            mCallback.onCorrectAnswerClicked();
        } else {
            //button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            mCallback.onIncorrectAnswerClicked();
        }
    }

    private void helper(Button button) {
        if (button.getText().toString().equals(correctAnswer)) {
            button.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }
}

