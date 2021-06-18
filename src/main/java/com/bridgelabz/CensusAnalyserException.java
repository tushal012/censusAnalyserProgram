package com.bridgelabz;

public class CensusAnalyserException extends Exception {
    enum ExceptionType {
        UNABLE_TO_PARSE, CENSUS_FILE_PROBLEM, HEADERS_INVALID, NOT_A_CSV_TYPE;
    }

    ExceptionType exceptionType;

    public CensusAnalyserException(String message, ExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public CensusAnalyserException(String message, Throwable cause, ExceptionType exceptionType) {
        super(message, cause);
        this.exceptionType = exceptionType;
    }
}
