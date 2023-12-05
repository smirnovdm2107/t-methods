package com.github.smirnovdm2107.config;

import com.github.smirnovdm2107.config.source.CharSource;
import com.github.smirnovdm2107.config.source.FileSource;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ConfigParser {

    public Config parse(final Path config) throws IOException, ParseException {
        return new Helper(config).parse();
    }


    private static final class Helper {
        private static final String lineSeparator = System.lineSeparator();
        private final CharSource source;

        public Helper(final Path config) throws IOException {
            this.source = new FileSource(config);
        }

        public Config parse() throws IOException, ParseException {
            final List<ParserRule> parserRules = new ArrayList<>();
            final List<LexerRule> lexerRules = new ArrayList<>();
            final String grammarName = parseGrammarName();
            while (source.hasNext()) {
                if (Character.isUpperCase(source.current())) {
                    lexerRules.add(parseLexerRule());
                } else if (Character.isLowerCase(source.current())) {
                    parserRules.add(parseParserRule());
                } else {
                    throw new AssertionError();
                }
            }
            return new Config(lexerRules, parserRules);
        }


        private void expect(final char ch) throws ParseException, IOException {
            if (source.current() != ch) {
                throw new ParseException("expected " + ch + ", found " + source.current(), source.currentPos());
            }
            source.next();
        }

        private void expect(final String string) throws ParseException, IOException {
            for (int i = 0; i < string.length(); i++) {
                expect(string.charAt(i));
            }
        }

        private String parseGrammarName() throws ParseException, IOException {
            throw new RuntimeException();
        }

        private ParserRule parseParserRule() {
            throw new RuntimeException();
        }

        private LexerRule parseLexerRule() {
            throw new RuntimeException();
        }

    }
}
