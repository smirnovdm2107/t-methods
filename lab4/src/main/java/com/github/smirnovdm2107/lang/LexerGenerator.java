package com.github.smirnovdm2107.lang;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.LexerRule;
import com.github.smirnovdm2107.config.source.CharSource;
import com.github.smirnovdm2107.util.ConfigUtils;

public class LexerGenerator {
    private final List<LexerRule> rules;
    private final List<Pattern> patterns;

    public LexerGenerator(Config config) {
        this.rules = config.lexerRules();
        this.patterns = new ArrayList<>();
        for (final LexerRule rule: rules) {
            if (rule.name().equals("EPS") || rule.name().equals("EOF")) {
                continue;
            }
            patterns.add(Pattern.compile("(" + rule.regexp() + ")"));
        }

    }
    public Lexer generate(final CharSource source) {
        return new PatternLexer(source);
    }

    private class PatternLexer implements Lexer {
        private static final int MAX_ID_LENGTH = 2048;
        private final CharSource source;
        private Token current;

        public PatternLexer(CharSource source) {
            this.source = source;
        }

        public Token current() throws IOException, ParseException {
            if (current == null) {
                current = nextToken();
            }
            return current;
        }
        @Override
        public Token nextToken() throws IOException, ParseException {
            if (current != null) {
                final Token result = current;
                current = null;
                return result;
            }
            skipWhitespaces();
            if (!source.hasNext()) {
                return Token.EOF;
            }
            source.mark();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < MAX_ID_LENGTH; i++) {
                sb.append((char) source.next());
                if (!source.hasNext()) {
                    break;
                }
            }
            for (int i = 0; i < patterns.size(); i++) {
                final Matcher matcher = patterns.get(i).matcher(sb);
                if (matcher.find()) {
                    if (matcher.start(0) == 0) {
                        final int end = matcher.end(0);
                        sb.setLength(end);
                        source.reset();
                        for (int j = 0; j < end; j++) {
                            source.next();
                        }
                        return new Token(rules.get(i).name(), sb.toString());
                    }
                }
            }
            throw new ParseException("unexpected token", source.currentPos());
        }


        private void skipWhitespaces() throws IOException {
            ConfigUtils.skipWhitespaces(source);
        }

    }

}
