package com.karimrizk.triviapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karimrizk.triviapp.R;
import com.karimrizk.triviapp.databinding.FragmentQuizBinding;

public class QuizFragment extends Fragment {


    public QuizFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentQuizBinding fragmentQuizBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz,container,false);
        return fragmentQuizBinding.getRoot();

    }
}
