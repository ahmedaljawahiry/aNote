package com.ahmed.anote.db.sql;

import android.content.ContentValues;
import android.database.Cursor;

import com.ahmed.anote.forms.FormInputValues;

import java.util.Map;

public interface SqlQueries {

    void CREATE_TABLE();
    void DELETE_TABLE();
    void INSERT(FormInputValues userInput);
    void UPDATE(FormInputValues userInput, String key);
    boolean RECORD_EXISTS(String key);
    Cursor GET_TABLE();
    String GET_NOTE_FROM_PK(String pk);
    void DELETE(String pk);

    default ContentValues convertDbMapToContent(FormInputValues userInput) {
        ContentValues content = new ContentValues();

        Map<String, String> dbValues = userInput.getDbValueMap();

        for (Map.Entry<String, String> entry : dbValues.entrySet()) {
            content.put(entry.getKey(), entry.getValue());
        }

        return content;
    }

    String READ_ONLY_DB_ERROR_MSG = "The input DB is read only!";
}
