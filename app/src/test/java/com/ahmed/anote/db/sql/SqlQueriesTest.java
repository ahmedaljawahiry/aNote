package com.ahmed.anote.db.sql;


import com.ahmed.anote.db.CipherDb;

import net.sqlcipher.Cursor;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

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

    private static Cursor cursorMock = mock(Cursor.class);

    private static Stream<Arguments> SqlQueriesInstancesWithValidDb() {
        CipherDb cipherDbMock = getCipherDbMock(
                cursorMock, 1, true, "NOTE"
        );
        return Stream.of(
                arguments(new NoteSQL(cipherDbMock)),
                arguments(new PinSQL(cipherDbMock))
        );
    }

    @ParameterizedTest
    @MethodSource("SqlQueriesInstancesWithValidDb")
    public void noteReturnedForGivenPk(SqlQueries SQL) {
        Cursor returnedCursor = SQL.GET_NOTE_FROM_PK("TITLE");
        assertThat(returnedCursor).isEqualTo(cursorMock);
    }

    private static Stream<Arguments> SqlQueriesInstancesWithInvalidDb() {
        CipherDb moreThanOneNotePerPk = getCipherDbMock(
                mock(Cursor.class), 3, true, "NOTE"
        );
        CipherDb noNoteCorrespondingToPk = getCipherDbMock(
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

    private static CipherDb getCipherDbMock(
            Cursor cursorMock, int count, boolean moveToNext, String noteValue
    ) {
        CipherDb cipherDbMock = mock(CipherDb.class);
        SQLiteDatabase dbMock = mock(SQLiteDatabase.class);

        doReturn(dbMock).when(cipherDbMock).getDb();
        doReturn(false).when(dbMock).isReadOnly();

        doReturn(cursorMock).when(dbMock).query(
                anyString(), anyObject(), anyString(),
                anyObject(), anyObject(), anyObject(), anyObject());

        doReturn(count).when(cursorMock).getCount();
        doReturn(moveToNext).when(cursorMock).moveToNext();
        doReturn(noteValue).when(cursorMock).getString(anyInt());
        return cipherDbMock;
    }
}
