package oleg.sichev.cloudstorage.exception;

import java.io.IOException;

public class SaveFileException extends RuntimeException{
    public SaveFileException(String cannotSaveFile) {
        super(cannotSaveFile);
    }
}
