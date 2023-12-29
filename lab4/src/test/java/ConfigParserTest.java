import java.util.List;

import com.github.smirnovdm2107.config.Attribute;
import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.ConfigParser;
import com.github.smirnovdm2107.config.LexerRule;
import com.github.smirnovdm2107.config.ParserRule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfigParserTest extends BaseTest {

    private static final ConfigParser parser = new ConfigParser();


    private Config ofConfig(final String str) throws Exception {
        return parser.parse(str);
    }

    @Test
    void testSimple() throws Exception {
        final Config config = ofConfig(""" 
                grammar 'java_parser';
                """);
        Assertions.assertEquals(new Config("java_parser", List.of(LexerRule.EOF, LexerRule.EPS), List.of()), config);
    }

    @Test
    void testMath() throws Exception {
        final Config config = ofConfig("""
                grammar 'math_grammar';
                e: e PLUS t {$0.val = $1.val + $3.val;};
                e: e MINUS t {$0.val = $1.val - $3.val;};
                e: t {$0.val = $1.val;};
                t: t MUL f {$0.val = $1.val * $3.val;};
                t: t DIV f {$0.val = $1.val / $3.val;};
                t: f {$0.val = $1.val;};
                
                f: NUM {$1.val = Integer.parseInteger($1.text);};
                f: LP e RP {$0.val = $2.val;};
                
                RP: ')';
                LP: '(';
                MINUS: '-';
                DIV: '/';
                MUL: '*';
                PLUS: '+';
                NUM: '-'?[0-9]*;
                """);


        final Config answer = new Config("math_grammar",
                List.of(
                        new LexerRule("NUM", "-?[0-9]*"),
                        new LexerRule("PLUS", "+"),
                        new LexerRule("DIV", "/"),
                        new LexerRule("MUL", "*"),
                        new LexerRule("MINUS", "-"),
                        new LexerRule("RP", ")"),
                        new LexerRule("LP", "("),
                        LexerRule.EOF,
                        LexerRule.EPS
                ),
                List.of(
                        new ParserRule("e", List.of("e", "PLUS", "t"), List.of(), "$0.val = $1.val + $3.val;"),
                        new ParserRule("e", List.of("e", "MINUS", "t"), List.of(), "$0.val = $1.val - $3.val;"),
                        new ParserRule("e", List.of("t"), List.of(),"$0.val = $1.val;"),
                        new ParserRule("t", List.of("t", "MUL", "f"), List.of(), "$0.val = $1.val * $3.val;"),
                        new ParserRule("t", List.of("t", "DIV", "f"), List.of(), "$0.val = $1.val / $3.val;"),
                        new ParserRule("t", List.of("f"), List.of(), "$0.val = $1.val;"),
                        new ParserRule("f", List.of("NUM"), List.of(), "$1.val = Integer.parseInteger($1.text);"),
                        new ParserRule("f", List.of("LP", "e", "RP"), List.of(), "$0.val = $2.val;")
                        )
        );
        Assertions.assertEquals(answer.grammarName(), config.grammarName());
        hasSameElements(answer.parserRules(), config.parserRules());
        hasSameElements(answer.lexerRules(), config.lexerRules());
    }


    @Test
    void testAttributes() throws Exception {
        final Config actual = ofConfig("""
        grammar 'attributes';
        parser returns [int a, int b]: NUM;
        """);
        final Config expected = new Config(
                "attributes",
                List.of(LexerRule.EOF, LexerRule.EPS),
                List.of(
                        new ParserRule(
                                "parser",
                            List.of("NUM"),
                            List.of(new Attribute("int", "a"), new Attribute("int", "b")),
                            null
                        )
                )
        );
        Assertions.assertEquals(expected, actual);
    }
}
