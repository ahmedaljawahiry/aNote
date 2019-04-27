package com.anote.db.sql;

import android.content.ContentValues;

import com.anote.common.userInput.FormInputValues;

import net.sqlcipher.Cursor;

import java.util.Map;

public interface SqlQueries {

    void CREATE_TABLE_IF_NOT_EXISTS();
    void DELETE_TABLE();
    void INSERT(FormInputValues userInput);
    void UPDATE(FormInputValues userInput, String key);
    boolean RECORD_EXISTS(String key);
    Cursor GET_TABLE();
    Cursor GET_NOTE_FROM_PK(String pk);
    void DELETE(String pk);

    default ContentValues convertDbMapToContent(FormInputValues userInput) {
        ContentValues content = new ContentValues();

        Map<String, Object> dbValues = userInput.getDbValueMap();

        for (Map.Entry<String, Object> entry : dbValues.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                content.put(entry.getKey(), (String) entry.getValue());
            }
            else if (value instanceof Boolean) {
                content.put(entry.getKey(), (Boolean) entry.getValue());
            }
        }

        return content;
    }
}
