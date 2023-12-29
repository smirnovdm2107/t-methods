package com.github.smirnovdm2107.lang;

public record Token(String type, String text) {
    public static Token EOF = new Token("EOF", "");
}
