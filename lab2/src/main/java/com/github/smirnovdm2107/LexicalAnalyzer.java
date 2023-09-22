package com.github.smirnovdm2107;

import com.github.smirnovdm2107.source.CharSource;
import com.github.smirnovdm2107.source.token.NumberToken;
import com.github.smirnovdm2107.source.token.SimpleToken;
import com.github.smirnovdm2107.source.token.Token;
import com.github.smirnovdm2107.source.token.VariableToken;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.function.Predicate;

public class LexicalAnalyzer implements AutoCloseable {
    private final String UNEXPECTED_MESSAGE = "unexpected character";
    private final CharSource source;
    private boolean isEnded = false;

    private Token currentToken;

    public LexicalAnalyzer(final CharSource source) throws ParseException {
        this.source = source;
        nextToken();
    }


    private char nextChar() throws ParseException {
        try {
            return source.next();
        } catch (final IOException e) {
            throw createException(e.getMessage());
        }
    }

    public Token currentToken() {
        return currentToken;
    }

    private boolean take(final char ch) throws ParseException {
        if (source.current() != ch) {
            return false;
        }
        nextChar();
        return true;
    }

    public void expect(final Token token) throws ParseException {
        if (currentToken.equals(token)) {
            if (currentToken != SimpleToken.END) {
                nextToken();
            }
            return;
        }
        throw createException("expected token " + token + ", found " + currentToken);
    }

    public Token nextToken() throws ParseException {
        skipWhitespaces();
        if (!source.hasNext()) {
            if (!isEnded) {
                currentToken = SimpleToken.END;
                isEnded = true;
                return SimpleToken.END;
            } else {
                throw createException("run out of tokens");
            }
        }
        final Token result = currentToken;
        final char ch = source.current();
        currentToken = switch (nextChar()) {
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
                if (take('-')) {
                    yield SimpleToken.DEC;
                }
                final String nextChars = takeWhile(this::isArabianDigit);
                if (nextChars.isEmpty()) {
                    throw createException("digit expected, found '" + source.current() + "'");
                }
                yield numberToken("-" + nextChars);
            }
            case 'f' -> {
                final StringBuilder prefix = new StringBuilder("f");
                if (take('o')) {
                    prefix.append('o');
                    if (take('r')) {
                        prefix.append('r');
                        final String nextChars = takeWhile(this::isPossibleVariableChar);
                        if (nextChars.isEmpty()) {
                            yield SimpleToken.FOR;
                        }
                        yield new VariableToken(prefix + nextChars);
                    }
                }
                final String nextChars = takeWhile(this::isPossibleVariableChar);
                yield new VariableToken(prefix + nextChars);
            }
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                final StringBuilder sb = new StringBuilder().append(ch);
                sb.append(takeWhile(this::isArabianDigit));
                if (take('.')) {
                    sb.append('.')
                            .append(takeWhile(this::isArabianDigit));
                }
                yield numberToken(sb.toString());
            }
            default -> {
                if (isPossibleVariableChar(ch)) {
                    yield new VariableToken(ch + takeWhile(this::isPossibleVariableChar));
                }
                throw createException(UNEXPECTED_MESSAGE);
            }
        };
        return result;
    }

    private void expect(final char expected) throws ParseException {
        expect(ch -> ch == expected);
    }

    private void expect(final Predicate<Character> predicate) throws ParseException {
        if (!predicate.test(nextChar())) {
            throw createException(UNEXPECTED_MESSAGE);
        }
    }

    private String takeWhile(final Predicate<Character> predicate) throws ParseException {
        final StringBuilder sb = new StringBuilder();
        while (source.hasNext() && predicate.test(source.current())) {
            sb.append(nextChar());
        }
        return sb.toString();
    }

    public NumberToken numberToken(final String stringNumber) {
        final int length = stringNumber.length();
        final Number number;
        if (!stringNumber.contains(".")) {
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
        return new ParseException(message + ", on pose " + source.currentPos(), source.currentPos());
    }

}
