package ru.maria.exception;

/**
 * Created by mariafedotova on 22.11.2017.
 */
public class BalanceException extends Exception {

    public static final int LOAD_PAPERS_ERROR = 1;

    public static final int LOAD_CLIENT_ERROR = 2;

    public static final int CALCULATE_ERROR = 3;

    public static final int SAVE_RESULTS_ERROR = 4;

    private int code;

    public BalanceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
