package com.indmind.moviecataloguetwo.utils;

import android.database.Cursor;

import com.indmind.moviecataloguetwo.models.Movie;

import java.util.ArrayList;

class CursorHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> notesList = new ArrayList<>();
        while (notesCursor.moveToNext()) {

            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow("id"));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow("title"));
            String overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow("overview"));

            notesList.add(new Movie(
                    id, 0, false, 0, title, 0.0,
                    "", "", "", "",
                    false, overview, "", null, null
            ));
        }
        return notesList;
    }
}
