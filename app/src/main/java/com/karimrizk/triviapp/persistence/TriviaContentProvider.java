package com.karimrizk.triviapp.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TriviaContentProvider extends ContentProvider {

    public static final int ENTRIES = 100;
    public static final int ENTRY_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TriviaContract.AUTHORITY, TriviaContract.PATH_TRIVIA_ENTRY, ENTRIES);
        uriMatcher.addURI(TriviaContract.AUTHORITY, TriviaContract.PATH_TRIVIA_ENTRY + "/#", ENTRY_WITH_ID);

        return uriMatcher;
    }

    private TriviaDbHelper mTriviaDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mTriviaDbHelper = new TriviaDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mTriviaDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match) {
            case ENTRIES:
                retCursor = db.query(TriviaContract.TriviaEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ENTRY_WITH_ID:
                String id = uri.getLastPathSegment();
                String[] mSelectionArgs = new String[]{id};
                retCursor = db.query(TriviaContract.TriviaEntry.TABLE_NAME, projection, TriviaContract.TriviaEntry.COLUMN_ID + "=?", mSelectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mTriviaDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case ENTRIES:
                long id = db.insert(TriviaContract.TriviaEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(TriviaContract.TriviaEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into" + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mTriviaDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int returnId;

        switch (match) {
            case ENTRIES:
                returnId = db.delete(TriviaContract.TriviaEntry.TABLE_NAME, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }

        if (returnId > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnId;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mTriviaDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int entriesUpdated;

        switch (match) {
            case ENTRY_WITH_ID:
                String id = uri.getLastPathSegment();
                entriesUpdated = db.update(TriviaContract.TriviaEntry.TABLE_NAME, values, TriviaContract.TriviaEntry.COLUMN_ID + "=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }

        if (entriesUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return entriesUpdated;
    }
}
