package com.github.smirnovdm2107.config;

import com.github.smirnovdm2107.config.source.CharSource;

import java.io.IOException;
import java.text.ParseException;

public class ConfigLexer {
    private final CharSource source;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private  static final int END = -1;
    public ConfigLexer(final CharSource source) {
        this.source = source;
    }



    public ConfigToken nextToken() throws IOException, ParseException {
        skipWhitespaces();
        if (take(';')) {
            return new ConfigToken(ConfigTokenType.SEMICOLON, ";");
        } else if (take(':')) {
            return new ConfigToken(ConfigTokenType.COLON, ":");
        } else if (take('|')) {
            return new ConfigToken(ConfigTokenType.OR, "|");
        } else if (take("grammar")) {
            return new ConfigToken(ConfigTokenType.GRAMMAR, "grammar");
        } else if (take('*')){
            return new ConfigToken(ConfigTokenType.MANY, "*");
        } else if (take('+')) {
            return new ConfigToken(ConfigTokenType.SOME, "+");
        } else if (take('(')) {
            return new ConfigToken(ConfigTokenType.LP, "(");
        } else if (take(')')) {
            return new ConfigToken(ConfigTokenType.RP, ")");
        }
        final StringBuilder result = new StringBuilder();
        if (take('\'')) {
            while(!take('\'')) {
                if (source.current() == '\\') {
                    source.next();
                }
                result.append(source.next());
            }
            return new ConfigToken(ConfigTokenType.STRING, result.toString());
        } else {
            // TODO
            expect('[');
            result.append('[');
            if (take('^')) {
                result.append(']');
            }
        }
        // TODO
        throw new RuntimeException();
    }

    private boolean take(final String string) throws IOException {
        source.mark();
        for (int i = 0; i < string.length(); i++) {
            if (!take(string.charAt(i))) {
                source.reset();
                return false;
            }
        }
        return true;
    }

    private void expect(final char ch) throws ParseException, IOException {
        if (source.current() != ch) {
            throw new ParseException("expected " + ch + ", found " + source.current(), source.currentPos());
        }
        source.next();
    }

    private boolean take(final char ch) throws IOException {
        if (source.current() == ch) {
            return false;
        }
        source.next();
        return true;
    }

    private void skipWhitespaces() throws IOException {
        while (true) {
            while (Character.isWhitespace(source.current())) {
                source.next();
            }
            boolean breaked = false;
            source.mark();
            for (int i = 0; i < LINE_SEPARATOR.length(); i++) {
                if (source.current() != LINE_SEPARATOR.charAt(i)) {
                    source.reset();
                    breaked = true;
                    break;
                }
            }
            if (breaked) {
                break;
            }
        }
    }
}
