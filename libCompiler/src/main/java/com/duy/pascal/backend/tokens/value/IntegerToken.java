package com.duy.pascal.backend.tokens.value;


import com.duy.pascal.backend.linenumber.LineInfo;

public class IntegerToken extends ValueToken {
    public int value;

    public IntegerToken(LineInfo line, int i) {
        super(line);
        value = i;
        mLineNumber.setLength(toCode().length());

    }

    @Override
    public String toCode() {
        return String.valueOf(value);
    }

    @Override
    public Object getValue() {
        return value;
    }
}
