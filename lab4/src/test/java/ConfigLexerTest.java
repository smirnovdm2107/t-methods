import com.github.smirnovdm2107.config.ConfigLexer;
import com.github.smirnovdm2107.config.ConfigToken;
import com.github.smirnovdm2107.config.source.StringSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class ConfigLexerTest extends BaseTest {
    private static final String CONFIG_NAME = "config";
    private static ConfigLexer lexer;


    private void ofConfig(final String str) throws IOException {
        lexer = new ConfigLexer(new StringSource(str));
    }

    private void check(final ConfigToken... tokens) throws Exception {
        for (final ConfigToken token: tokens) {
            Assertions.assertEquals(token, lexer.nextToken());
        }
    }

    @Test
    void testTokens() throws Exception {
        ofConfig(" : ; ? + [a-b] grammar * \'string\' privet PRIVET ) (  { { } } returns");
        check(
                ConfigToken.COLON,
                ConfigToken.SEMICOLON,
                ConfigToken.OPTIONAL,
                ConfigToken.SOME,
                ConfigToken.LB,
                ConfigToken.rangeOf("a-b"),
                ConfigToken.RB,
                ConfigToken.GRAMMAR,
                ConfigToken.MANY,
                ConfigToken.stringOf("string"),
                ConfigToken.lowerCaseIdentifierOf("privet"),
                ConfigToken.upperCaseIdentifierOf("PRIVET"),
                ConfigToken.RP,
                ConfigToken.LP,
                ConfigToken.codeBlockOf(" { } "),
                ConfigToken.RETURNS,
                ConfigToken.EOF
        );
    }

    @Test
    void testUnexpected() throws Exception {
        ofConfig("±");
        Assertions.assertThrows(Exception.class, () -> lexer.nextToken());
    }

    @Test
    void testEscape() throws Exception {
        ofConfig("'\\''");
        check(ConfigToken.stringOf("'"));
        ofConfig("'\\\\'");
        check(ConfigToken.stringOf("\\"));
        ofConfig("'\\'\\\\\\''");
        check(ConfigToken.stringOf("'\\'"));
    }

    @Test
    void testParserRule() throws Exception {
        ofConfig("expression : math | LP expression RP  |  END");
        check(
            ConfigToken.lowerCaseIdentifierOf("expression"),
                ConfigToken.COLON,
                ConfigToken.lowerCaseIdentifierOf("math"),
                ConfigToken.OR,
                ConfigToken.upperCaseIdentifierOf("LP"),
                ConfigToken.lowerCaseIdentifierOf("expression"),
                ConfigToken.upperCaseIdentifierOf("RP"),
                ConfigToken.OR,
                ConfigToken.upperCaseIdentifierOf("END")
        );
    }

    @Test
    void testLexerRule() throws Exception {
        ofConfig("MATH_OP : '*'|'/'");

        check(
                ConfigToken.upperCaseIdentifierOf("MATH_OP"),
                ConfigToken.COLON,
                ConfigToken.stringOf("*"),
                ConfigToken.OR,
                ConfigToken.stringOf("/")
        );
    }

    @Test
    void testCodeBlock() throws Exception {
        ofConfig("{ if (true) { $1 = 1; } else { $1 = 2;} }");
        var token = lexer.nextToken();
        Assertions.assertTrue(
                token.text().contains("if (true) { $1 = 1; } else { $1 = 2;}")
        );
    }


    @Test
    void testAttributes() throws Exception {
        ofConfig("parser returns [int a, int b]: NUM");
        check(
                ConfigToken.lowerCaseIdentifierOf("parser"),
                ConfigToken.RETURNS,
                ConfigToken.LB,
                ConfigToken.lowerCaseIdentifierOf("int"),
                ConfigToken.lowerCaseIdentifierOf("a"),
                ConfigToken.COMMA,
                ConfigToken.lowerCaseIdentifierOf("int"),
                ConfigToken.lowerCaseIdentifierOf("b"),
                ConfigToken.RB,
                ConfigToken.COLON,
                ConfigToken.upperCaseIdentifierOf("NUM")

                );
    }



}
