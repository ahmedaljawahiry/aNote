package com.anote.db.encryption;

public class EncrypterException extends Exception {

    public EncrypterException() {
        super();
    }

    public EncrypterException(String message) {
        super(message);
    }

    public EncrypterException(Throwable cause) {
        super(cause);
    }

    public EncrypterException(String message, Throwable cause) {
        super(message, cause);
    }
}
