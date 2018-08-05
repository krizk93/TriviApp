package com.karimrizk.triviapp.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

public class TriviaContract {

    private TriviaContract() {

    }

    public static final String AUTHORITY = "com.karimrizk.triviapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TRIVIA_ENTRY = "entries";

    public static final class TriviaEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRIVIA_ENTRY).build();

        public static final String TABLE_NAME = "trivia";
        public static final String COLUMN_ID = "questionID";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_CORRECT_ANSWER = "correctAnswer";
        public static final String COLUMN_INCORRECT_ANSWER_1 = "incorrectAnswer1";
        public static final String COLUMN_INCORRECT_ANSWER_2 = "incorrectAnswer2";
        public static final String COLUMN_INCORRECT_ANSWER_3 = "incorrectAnswer3";
        public static final String COLUMN_PLAYER_ANSWER = "playerAnswer";
    }


}
