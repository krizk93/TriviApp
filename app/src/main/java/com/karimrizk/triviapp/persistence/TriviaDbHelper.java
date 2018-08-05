package com.karimrizk.triviapp.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TriviaDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trivia.db";
    private static final int DATABASE_VERSION = 1;

    public TriviaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TRIVIA_TABLE = "CREATE TABLE " +
                TriviaContract.TriviaEntry.TABLE_NAME + " (" +
                TriviaContract.TriviaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TriviaContract.TriviaEntry.COLUMN_ID + " INTEGER NOT NULL, " +
                TriviaContract.TriviaEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                TriviaContract.TriviaEntry.COLUMN_DIFFICULTY + " TEXT NOT NULL, " +
                TriviaContract.TriviaEntry.COLUMN_QUESTION + " TEXT NOT NULL, " +
                TriviaContract.TriviaEntry.COLUMN_CORRECT_ANSWER + " TEXT NOT NULL, " +
                TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_1 + " TEXT NOT NULL, " +
                TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_2 + " TEXT NOT NULL,  " +
                TriviaContract.TriviaEntry.COLUMN_INCORRECT_ANSWER_3 + " TEXT NOT NULL, " +
                TriviaContract.TriviaEntry.COLUMN_PLAYER_ANSWER + " TEXT" +
                ");";



        db.execSQL(SQL_CREATE_TRIVIA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TriviaContract.TriviaEntry.TABLE_NAME);
        onCreate(db);

    }
}
