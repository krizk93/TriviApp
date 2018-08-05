package com.karimrizk.triviapp;

public class Values {

    public static final String QUIZ_TYPE = "multiple";

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
