package org.example.learn.db.export.exception;

public class DbExportBaseException extends RuntimeException {

    public DbExportBaseException(Throwable cause) {
        super(cause);
    }

    public DbExportBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
