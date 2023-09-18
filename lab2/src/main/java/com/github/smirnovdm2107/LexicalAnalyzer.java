package com.github.smirnovdm2107;

import com.github.smirnovdm2107.source.CharSource;
import com.github.smirnovdm2107.source.token.NumberToken;
import com.github.smirnovdm2107.source.token.SimpleToken;
import com.github.smirnovdm2107.source.token.VariableToken;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.function.Predicate;

public class LexicalAnalyzer implements AutoCloseable {
    private final String UNEXPECTED_MESSAGE = "unexpected character";
    private final CharSource source;

    public LexicalAnalyzer(final CharSource source) {
        this.source = source;
    }


    private char nextChar() throws ParseException {
        try {
            return source.next();
        } catch (final IOException e) {
            throw createException(e.getMessage());
        }
    }

    private void expect(final char expected) throws ParseException {
        expect(ch -> ch == expected);
    }

    private void expect(final Predicate<Character> predicate) throws ParseException {
        if (!predicate.test(nextChar())) {
            throw createException(UNEXPECTED_MESSAGE);
        }
    }

    public SimpleToken nextSimpleToken() throws ParseException {
        skipWhitespaces();
        return switch (nextChar()) {
            case '(' -> SimpleToken.LP;
            case ')' -> SimpleToken.RP;
            case ';' -> SimpleToken.SEMICOLON;
            case '>' -> SimpleToken.GT;
            case '<' -> SimpleToken.LT;
            case '=' -> SimpleToken.EQ;
            case '+' -> {
                expect('+');
                yield SimpleToken.INC;
            }
            case '-' -> {
                expect('-');
                yield SimpleToken.DEC;
            }
            case 'f' -> {
                expect('o');
                expect('r');
                yield SimpleToken.FOR;
            }
            default -> throw createException(UNEXPECTED_MESSAGE);
        };
    }

    private String takeWhile(final Predicate<Character> predicate) throws ParseException {
        skipWhitespaces();
        final StringBuilder sb = new StringBuilder();
        while(source.hasNext() && predicate.test(source.current())) {
            sb.append(nextChar());
        }
        return sb.toString();
    }
    public NumberToken nextNumberToken() throws ParseException {
        final String stringNumber = takeWhile(this::isArabianDigit) +
                takeWhile(it -> it == '.') +
                takeWhile(this::isArabianDigit);
        if (stringNumber.isEmpty() || stringNumber.startsWith(".")) {
            throw createException(UNEXPECTED_MESSAGE);
        }
        final int length = stringNumber.length();
        final Number number;
        if (stringNumber.contains(".")) {
            if (length < 9) {
                number = Integer.parseInt(stringNumber);
            } else if (length < 14) {
                number = Long.parseLong(stringNumber);
            } else {
                number = new BigInteger(stringNumber);
            }
        } else {
            if (length < 14) {
                number = Double.parseDouble(stringNumber);
            } else {
                number = new BigDecimal(stringNumber);
            }
        }
        return new NumberToken(number);
    }

    public VariableToken nextVariableToken() throws ParseException {
        skipWhitespaces();
        final char current = source.current();
        if (!isPossibleVariableChar(current) || isArabianDigit(current)) {
            throw createException(UNEXPECTED_MESSAGE);
        }
        final StringBuilder sb = new StringBuilder().append(nextChar());
        while (source.hasNext() && isPossibleVariableChar(source.current())) {
            sb.append(nextChar());
        }

        return new VariableToken(sb.toString());
    }

    public VariableToken nextVariableTokenIf(final Predicate<Character> predicate) throws ParseException {
        skipWhitespaces();
        if (predicate.test(source.current())) {
            return nextVariableToken();
        }
        return null;
    }

    public SimpleToken nextSimpleTokenIf(final Predicate<Character> predicate) throws ParseException {
        skipWhitespaces();;
        if (predicate.test(source.current())) {
            return nextSimpleToken();
        }
        return null;
    }

    private boolean isArabianDigit(final char ch) {
        return '0' <= ch && ch <= '9';
    }

    private boolean isPossibleVariableChar(final char ch) {
        return 'a' <= ch && ch <= 'z'
                || 'A' <= ch && ch <= 'Z'
                || ch == '_'
                || isArabianDigit(ch);
    }

    private void skipWhitespaces() throws ParseException {
        while (source.hasNext() && Character.isWhitespace(source.current())) {
            nextChar();
        }
    }

    @Override
    public void close() throws Exception {
        source.close();
    }

    public ParseException createException(final String message) {
        return new ParseException(message, source.currentPos());
    }

}
