package com.github.smirnovdm2107.config;

public record LexerRule(String name, String regexp) implements Rule {
    public static final LexerRule EOF = new LexerRule("EOF", "");
    public static final LexerRule EPS = new LexerRule("EPS", "");
}
