package com.github.smirnovdm2107.config;

import com.github.smirnovdm2107.config.source.CharSource;
import com.github.smirnovdm2107.util.ConfigUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.function.Predicate;

public class ConfigLexer {
    private final CharSource source;

    private static final int END = -1;
    public ConfigLexer(final CharSource source) {
        this.source = source;
    }

    private ConfigToken currentToken;

    public ConfigToken currentToken() throws IOException, ParseException {
        if (currentToken == null) {
            currentToken = nextToken();
        }
        return currentToken;
    }


    public ConfigToken nextToken() throws IOException, ParseException {
        if (currentToken != null) {
            final ConfigToken result = currentToken;
            currentToken = null;
            return result;
        }
        skipWhitespaces();
        if (take(';')) {
            return ConfigToken.SEMICOLON;
        } else if (take(':')) {
            return ConfigToken.COLON;
        } else if (take('|')) {
            return ConfigToken.OR;
        } else if (take("grammar")) {
            return ConfigToken.GRAMMAR;
        } else if (take("returns")) {
            return ConfigToken.RETURNS;
        } else if (take('*')){
            return ConfigToken.MANY;
        } else if (take('+')) {
            return ConfigToken.SOME;
        } else if (take('(')) {
            return ConfigToken.LP;
        } else if (take(')')) {
            return ConfigToken.RP;
        } else if (take('?')) {
            return ConfigToken.OPTIONAL;
        } else if (take('[')) {
            return ConfigToken.LB;
        } else if (take(']')) {
            return ConfigToken.RB;
        } else if (take(',')) {
            return ConfigToken.COMMA;
        }
        if (!source.hasNext()) {
            return ConfigToken.EOF;
        }
        final StringBuilder result = new StringBuilder();
        if (take('\'')) {
            while (!take('\'')) {
                if (source.current() == '\\') {
                    next();
                }
                result.append(next());
            }
            return ConfigToken.stringOf(result.toString());
        }
        source.mark();
        char ch1 = next();
        if (!take('-')) {
            source.reset();
        } else {
            final char ch2 = next();
            if (Character.isWhitespace(ch2)) {
                source.reset();
            } else {
                return ConfigToken.rangeOf(String.valueOf(ch1) + '-' + ch2);
            }
        }
        if (test(Character::isLowerCase)) {
            return ConfigToken.lowerCaseIdentifierOf(takeIdentifier());
        } else if (test(Character::isUpperCase)) {
            return ConfigToken.upperCaseIdentifierOf(takeIdentifier());
        } else if (take('{')) {
            int balance = 1;
            while (current() != '}' || balance != 1) {
                char ch = next();
                result.append(ch);
                if (ch == '{') {
                    balance++;
                } else if (ch == '}') {
                    balance--;
                }
            }
            next();
            return ConfigToken.codeBlockOf(result.toString());
        }
        throw new ParseException("unexpected character: " + current(), currentPos());
    }

    private char next() throws IOException {
        return (char) source.next();
    }

    private char current() {
        return (char) source.current();
    }
    private String takeIdentifier() throws IOException {
        final StringBuilder sb = new StringBuilder();
        while(source.hasNext() &&
                (Character.isLetterOrDigit(current())
                 || current() == '_' || current() == '-' )) {
            sb.append(next());
        }
        return sb.toString();
    }
    private boolean test(final Predicate<Character> predicate) {
        return predicate.test((char) source.current());
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
        if (source.current() != ch) {
            return false;
        }
        source.next();
        return true;
    }

    private void skipWhitespaces() throws IOException {
        ConfigUtils.skipWhitespaces(source);
    }
    public int currentPos() {
        return source.currentPos();
    }
}
