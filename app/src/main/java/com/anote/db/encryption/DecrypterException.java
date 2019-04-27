package com.anote.db.encryption;

public class DecrypterException extends Exception {

    public DecrypterException() {
        super();
    }

    public DecrypterException(String message) {
        super(message);
    }

    public DecrypterException(Throwable cause) {
        super(cause);
    }

    public DecrypterException(String message, Throwable cause) {
        super(message, cause);
    }
}
