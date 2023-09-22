package com.github.smirnovdm2107;

import com.github.smirnovdm2107.source.InputStreamSource;
import com.github.smirnovdm2107.source.token.NumberToken;
import com.github.smirnovdm2107.source.token.SimpleToken;
import com.github.smirnovdm2107.source.token.Token;
import com.github.smirnovdm2107.source.token.VariableToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;

public class Parser {

    public Tree parse(final InputStream inputStream) throws IOException, ParseException {
        return new Helper(inputStream).parse();
    }

    private static final class Helper {
        private final LexicalAnalyzer lexer;

        public Helper(final InputStream inputStream) throws IOException, ParseException {
            lexer = new LexicalAnalyzer(new InputStreamSource(inputStream));
        }

        public Helper(final InputStream inputStream, final Charset charset) throws IOException, ParseException {
            lexer = new LexicalAnalyzer(new InputStreamSource(inputStream, charset));
        }

        public Tree expectSimpleToken(final SimpleToken token) throws ParseException {
            if (lexer.currentToken() == token) {
                lexer.nextToken();
                return new Tree(token.name());
            }
            throw new AssertionError("expected " + token.name() + ", found " + lexer.currentToken());
        }

        public Tree expectVariableToken() throws ParseException {
            if (lexer.currentToken() instanceof VariableToken) {
                return new Tree("V", new Tree(((VariableToken) lexer.nextToken()).value()));
            }
            throw new AssertionError("expected variable token, found "
                    + lexer.currentToken());
        }

        public Tree expectNumberToken() throws ParseException {
            if (lexer.currentToken() instanceof NumberToken) {
                return new Tree("N", new Tree(((NumberToken) lexer.nextToken()).value().toString()));
            }
            throw new AssertionError("expected number token, found "
                    + lexer.currentToken());
        }

        private Tree A() throws ParseException {
            final Tree tree = new Tree("A");
            final Token current = lexer.currentToken();
            if (current instanceof VariableToken) {
                return tree.addChild(expectVariableToken())
                        .addChild(expectVariableToken())
                        .addChild(expectSimpleToken(SimpleToken.EQ))
                        .addChild(expectNumberToken());
            } else if (current == SimpleToken.SEMICOLON) {
                return tree;
            }
            throw new AssertionError();
        }

        private Tree B() throws ParseException {
            final Tree tree = new Tree("B");
            final Token current = lexer.currentToken();
            if (current instanceof VariableToken) {
                return tree.addChild(expectVariableToken())
                        .addChild(Z())
                        .addChild(expectNumberToken());
            } else if (current == SimpleToken.SEMICOLON) {
                return tree;
            }
            throw new AssertionError();
        }

        private Tree Z() throws ParseException {
            final Tree tree = new Tree("Z");
            final Token current = lexer.currentToken();
            if (current == SimpleToken.EQ) {
                return tree.addChild(expectSimpleToken(SimpleToken.EQ))
                        .addChild(expectSimpleToken(SimpleToken.EQ));
            } else if (current == SimpleToken.LT || current == SimpleToken.GT) {
                return tree.addChild(expectSimpleToken((SimpleToken) current))
                        .addChild(E());
            }
            throw new AssertionError();
        }

        private Tree E() throws ParseException {
            final Token current = lexer.currentToken();
            final Tree tree = new Tree("E");
            if (current == SimpleToken.EQ) {
                return tree.addChild(expectSimpleToken(SimpleToken.EQ));
            } else if (current instanceof NumberToken) {
                return tree;
            }
            throw new AssertionError();
        }

        private Tree C() throws ParseException {
            final Token current = lexer.currentToken();
            final Tree tree = new Tree("C");
            if (current instanceof VariableToken) {
                return tree.addChild(expectVariableToken())
                        .addChild(O());
            } else if (current == SimpleToken.RP) {
                return tree;
            }
            throw new AssertionError();
        }

        private Tree O() throws ParseException {
            final Token current = lexer.currentToken();
            final Tree tree = new Tree("O");
            if (current == SimpleToken.INC) {
                return tree.addChild(expectSimpleToken(SimpleToken.INC));
            } else if (current == SimpleToken.DEC) {
                return tree.addChild(expectSimpleToken(SimpleToken.DEC));
            }
            throw new AssertionError();
        }

        private Tree S() throws ParseException {
            final Tree tree = new Tree(
                    "S",
                    expectSimpleToken(SimpleToken.FOR),
                    expectSimpleToken(SimpleToken.LP),
                    A(),
                    expectSimpleToken(SimpleToken.SEMICOLON),
                    B(),
                    expectSimpleToken(SimpleToken.SEMICOLON),
                    C(),
                    expectSimpleToken(SimpleToken.RP)
            );
            lexer.expect(SimpleToken.END);
            return tree;
        }

        public Tree parse() throws ParseException {
            return S();
        }

    }
}
