package se.kth.iv1351.mattls.musicschool.integration;

public class MusicSchoolDBException extends Exception {
/**
     * Create a new instance thrown because of the specified reason.
     *
     * @param reason Why the exception was thrown.
     */
    public MusicSchoolDBException(String reason) {
        super(reason);
    }

    /**
     * Create a new instance thrown because of the specified reason and exception.
     *
     * @param reason    Why the exception was thrown.
     * @param rootCause The exception that caused this exception to be thrown.
     */
    public MusicSchoolDBException(String reason, Throwable rootCause) {
        super(reason, rootCause);
    }
}
