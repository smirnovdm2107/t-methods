package com.github.smirnovdm2107.config;

import com.github.smirnovdm2107.config.source.FileSource;
import com.github.smirnovdm2107.config.source.StringSource;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ConfigParser {

    public Config parse(final Path config) throws IOException, ParseException {
        return new Helper(config).parse();
    }

    public Config parse(final String config) throws IOException, ParseException {
        return new Helper(config).parse();
    }


    private static final class Helper {

        private final ConfigLexer lexer;
        private final List<LexerRule> lexerRules = new ArrayList<>();
        private final List<ParserRule> parserRules = new ArrayList<>();

        public Helper(final Path config) throws IOException {
            this.lexer = new ConfigLexer(new FileSource(config));
        }

        public Helper(final String config) throws IOException {
            this.lexer = new ConfigLexer(new StringSource(config));
        }

        public Config parse() throws IOException, ParseException {
            final String grammarName = parseGrammar();
            while (true) {
                switch (lexer.currentToken().tokenType()) {
                    case LOWER_CASE_IDENTIFIER -> parserRules.add(parseParserRule());
                    case UPPER_CASE_IDENTIFIER -> lexerRules.add(parseLexerRule());
                    case EOF -> {
                        lexerRules.add(LexerRule.EOF);
                        lexerRules.add(LexerRule.EPS);
                        return new Config(grammarName, lexerRules, parserRules);
                    }
                    default -> throw new ParseException("unexpected token", lexer.currentPos());
                }
            }
        }


        private String parseGrammar() throws ParseException, IOException {
            expectTokenType(ConfigTokenType.GRAMMAR);
            final String result = expectTokenType(ConfigTokenType.STRING).text();
            expectTokenType(ConfigTokenType.SEMICOLON);
            return result;
        }


        private ParserRule parseParserRule() throws IOException, ParseException {
            final String name = expectTokenType(ConfigTokenType.LOWER_CASE_IDENTIFIER).text();
            final List<Attribute> attributes = new ArrayList<>();
            if (lexer.currentToken().tokenType() == ConfigTokenType.RETURNS) {
                lexer.nextToken();
                expectTokenType(ConfigTokenType.LB);
                while (true) {
                    final ConfigToken type = lexer.nextToken();
                    if (type.tokenType() != ConfigTokenType.LOWER_CASE_IDENTIFIER
                    && type.tokenType() != ConfigTokenType.UPPER_CASE_IDENTIFIER) {
                        throw new ParseException("expected identifier, got " + type, lexer.currentPos());
                    }
                    final ConfigToken varName = lexer.nextToken();
                    if (varName.tokenType() != ConfigTokenType.LOWER_CASE_IDENTIFIER
                            && varName.tokenType() != ConfigTokenType.UPPER_CASE_IDENTIFIER) {
                        throw new ParseException("expected identifier, got " + name, lexer.currentPos());
                    }
                    attributes.add(new Attribute(type.text(), varName.text()));
                    if (lexer.currentToken().tokenType() != ConfigTokenType.COMMA) {
                        break;
                    }
                    lexer.nextToken();
                }
                expectTokenType(ConfigTokenType.RB);
            }
            expectTokenType(ConfigTokenType.COLON);

            final List<String> currentRules = new ArrayList<>();
            while (lexer.currentToken().tokenType() == ConfigTokenType.UPPER_CASE_IDENTIFIER
            || lexer.currentToken().tokenType() == ConfigTokenType.LOWER_CASE_IDENTIFIER) {
                currentRules.add(lexer.nextToken().text());
            }
            if (currentRules.isEmpty()) {
                throw new ParseException("no rules in parser", lexer.currentPos());
            }
            String codeBlock = null;
            if (lexer.currentToken().tokenType() == ConfigTokenType.CODE_BLOCK) {
                codeBlock = lexer.nextToken().text();
            }
            expectTokenType(ConfigTokenType.SEMICOLON);
            return new ParserRule(name, currentRules, attributes, codeBlock);
        }

        private LexerRule parseLexerRule() throws IOException, ParseException {
            final String name = expectTokenType(ConfigTokenType.UPPER_CASE_IDENTIFIER).text();
            expectTokenType(ConfigTokenType.COLON);
            final StringBuilder regexp = new StringBuilder();
            while (lexer.currentToken().tokenType() != ConfigTokenType.SEMICOLON) {
                switch (lexer.currentToken().tokenType()) {
                    case OR, RANGE,
                            OPTIONAL, SOME, MANY,
                            RB, LB,
                            LP, RP -> regexp.append(lexer.nextToken().text());
                    case STRING -> regexp.append(Pattern.quote(lexer.nextToken().text()));
                    default -> throw new ParseException("unexpected token in lexer rule", lexer.currentPos());

                }
            }
            expectTokenType(ConfigTokenType.SEMICOLON);
            return new LexerRule(name, regexp.toString());
        }

        private ConfigToken expectTokenType(final ConfigTokenType type) throws IOException, ParseException {
            if (lexer.currentToken().tokenType() != type) {
                throw new ParseException("expected token type " + type, lexer.currentPos());
            }
            return lexer.nextToken();
        }
    }
}
