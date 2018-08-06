package com.karimrizk.triviapp;

public class Values {

    public static final String QUIZ_TYPE = "multiple";

    public static final String QUESTION_KEY = "questionKey";
    public static final String CORRECT_ANSWER_KEY = "correctAnswerKey";
    public static final String INCORRECT_ANSWERS_KEY = "incorrectAnswersKey";
    public static final String ANSWERS_KEY = "answersKey";
    public static final String CURRENT_QUESTION_KEY = "currentQuestion";
    public static final String SCORE_KEY = "score";
    public static final String SHARED_PREFERENCE_NAME = "High Scores";
    public static final String HIGH_SCORE = "high score";

    public enum category {
        generalKnowledge(9),
        sports(21),
        celebrities(26),
        geography(22),
        books(10),
        film(11);

        private int categoryID;

        category(int value) {
            categoryID = value;
        }

        public int getCategoryID() {
            return categoryID;
        }
    }

    public enum difficulty {
        easy,
        medium,
        hard
    }

    public static boolean isNewQuiz = true;



}
