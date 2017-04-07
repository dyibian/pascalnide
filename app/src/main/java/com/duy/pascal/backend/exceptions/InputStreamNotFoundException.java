package com.duy.pascal.backend.exceptions;

import com.js.interpreter.runtime.exception.RuntimePascalException;

public class InputStreamNotFoundException extends RuntimePascalException {
    public InputStreamNotFoundException() {
        super(null);
    }

    public InputStreamNotFoundException(String message) {
        super(null, message);
    }
}