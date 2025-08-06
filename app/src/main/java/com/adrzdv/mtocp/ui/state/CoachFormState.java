package com.adrzdv.mtocp.ui.state;

@Deprecated
public sealed class CoachFormState {

    public static final class Success extends CoachFormState { }

    public static final class Error extends CoachFormState {
        private final ErrorType errorType;

        public Error(ErrorType errorType) {
            this.errorType = errorType;
        }

        public ErrorType getErrorType() {
            return this.errorType;
        }
    }

    public enum ErrorType {
        INVALID_DEPOT,
        INVALID_COACH_TYPE,
        INVALID_ROUTE,
        DUPLICATE_ERROR,
        PATTERN_ERROR
    }
}
