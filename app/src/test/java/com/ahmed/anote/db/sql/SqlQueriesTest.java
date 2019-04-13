package com.ahmed.anote.db.sql;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class SqlQueriesTest {

    private static Stream<Arguments> SqlQueriesInstancesWithValidDb() {
        SQLiteDatabase dbMock = getDbMock(
                mock(Cursor.class), 1, true, "NOTE"
        );
        return Stream.of(
                arguments(new NoteSQL(dbMock)),
                arguments(new PinSQL(dbMock))
        );
    }

    @ParameterizedTest
    @MethodSource("SqlQueriesInstancesWithValidDb")
    public void noteReturnedForGivenPk(SqlQueries SQL) {
        String returnedNote = SQL.GET_NOTE_FROM_PK("TITLE");
        assertThat(returnedNote).isEqualTo("NOTE");
    }

    private static Stream<Arguments> SqlQueriesInstancesWithInvalidDb() {
        SQLiteDatabase moreThanOneNotePerPk = getDbMock(
                mock(Cursor.class), 3, true, "NOTE"
        );
        SQLiteDatabase noNoteCorrespondingToPk = getDbMock(
                mock(Cursor.class), 1, false, "NOTE"
        );
        return Stream.of(
                arguments(new NoteSQL(moreThanOneNotePerPk)),
                arguments(new NoteSQL(noNoteCorrespondingToPk)),
                arguments(new PinSQL(moreThanOneNotePerPk)),
                arguments(new PinSQL(noNoteCorrespondingToPk))
        );
    }

    @ParameterizedTest
    @MethodSource("SqlQueriesInstancesWithInvalidDb")
    public void sqlExceptionsCorrectlyThrownWhenExtractingNoteFromPk(SqlQueries SQL) {
        assertThatExceptionOfType(SQLException.class)
                .isThrownBy(
                        () -> SQL.GET_NOTE_FROM_PK("anyTitle")
                );
    }

    private static SQLiteDatabase getDbMock(
            Cursor cursorMock, int count, boolean moveToNext, String noteValue
    ) {
        SQLiteDatabase dbMock = mock(SQLiteDatabase.class);
        doReturn(false).when(dbMock).isReadOnly();

        doReturn(cursorMock).when(dbMock).query(
                anyString(), anyObject(), anyString(),
                anyObject(), anyObject(), anyObject(), anyObject());

        doReturn(count).when(cursorMock).getCount();
        doReturn(moveToNext).when(cursorMock).moveToNext();
        doReturn(noteValue).when(cursorMock).getString(anyInt());
        return dbMock;
    }
}
