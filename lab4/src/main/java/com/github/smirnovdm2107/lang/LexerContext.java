package com.github.smirnovdm2107.lang;

public class LexerContext implements Context {

    public LexerContext(final String text) {
        this.text = text;
    }

    public String text;
}
