package com.github.smirnovdm2107;

import com.github.smirnovdm2107.source.InputStreamSource;
import com.github.smirnovdm2107.source.token.NumberToken;
import com.github.smirnovdm2107.source.token.SimpleToken;
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

        public Helper(final InputStream inputStream) throws IOException {
            lexer = new LexicalAnalyzer(new InputStreamSource(inputStream));
        }

        public Helper(final InputStream inputStream, final Charset charset) throws IOException {
            lexer = new LexicalAnalyzer(new InputStreamSource(inputStream, charset));

        }

        private Tree A() throws ParseException {
            final VariableToken type  = lexer.nextVariableTokenIf(ch -> ch == ';');
            if (type == null) {
                return new Tree("B");
            }
            lexer.nextVariableToken();
            expectNextSimple(SimpleToken.EQ);
            lexer.nextNumberToken();
            return new Tree("B", new Tree("V"), new Tree("V"), new Tree("N"));
        }

        private Tree B() throws ParseException {
            final VariableToken var = lexer.nextVariableTokenIf(it -> it != ';');
            if (var == null) {
                return new Tree("C");
            }
            final Tree z = Z();
            final NumberToken number = lexer.nextNumberToken();
            return new Tree("C", z, new Tree("N"));
        }

        private Tree Z() throws ParseException {
            final SimpleToken token = lexer.nextSimpleToken();
            if (token != SimpleToken.EQ && token != SimpleToken.GT && token != SimpleToken.LT) {
                throw new AssertionError();
            }
            return new Tree("Z", new Tree(token.name()), E());
        }

        private Tree E() throws ParseException {
            final SimpleToken token = lexer.nextSimpleTokenIf(it -> it == '=');
            if (token == null) {
                return new Tree("E");
            }
            return new Tree("E", new Tree(SimpleToken.EQ.name()));
        }
        private Tree C() throws ParseException {
            final VariableToken var = lexer.nextVariableTokenIf(it -> it != ')');
            if (var == null) {
                return new Tree("C");
            }
            final SimpleToken token = lexer.nextSimpleToken();
            if (token != SimpleToken.INC && token != SimpleToken.DEC) {
                throw new AssertionError();
            }
            return new Tree("C", new Tree("V"), new Tree(token.name()));
        }

        private Tree expectNextSimple(final SimpleToken token) throws ParseException {
            if (lexer.nextSimpleToken() != token) {
                throw new AssertionError();
            }
            return new Tree(token.name());
        }

        private Tree S() throws ParseException {
            final Tree token_for = expectNextSimple(SimpleToken.FOR);
            final Tree left_paren = expectNextSimple(SimpleToken.LP);
            final Tree a = A();
            final Tree semicolon1 = expectNextSimple(SimpleToken.SEMICOLON);
            final Tree b = B();
            final Tree semicolon2 = expectNextSimple(SimpleToken.SEMICOLON);
            final Tree c = C();
            final Tree right_paren = expectNextSimple(SimpleToken.RP);
            return new Tree("S", token_for, left_paren, a, semicolon1, b, semicolon2, c, right_paren);
        }

        public Tree parse() throws ParseException {
            return S();
        }

    }
}
