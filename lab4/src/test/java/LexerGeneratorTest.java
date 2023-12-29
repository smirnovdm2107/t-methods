import java.io.IOException;
import java.text.ParseException;

import com.github.smirnovdm2107.config.ConfigParser;
import com.github.smirnovdm2107.config.source.StringSource;
import com.github.smirnovdm2107.lang.Lexer;
import com.github.smirnovdm2107.lang.LexerGenerator;
import com.github.smirnovdm2107.lang.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LexerGeneratorTest extends BaseTest {

    private final ConfigParser parser = new ConfigParser();
    private LexerGenerator gen;
    private void ofConfig(final String config) throws IOException, ParseException {
        this.gen = new LexerGenerator(parser.parse(config));
    }

    private void check(final Lexer lexer, final Token... tokens) throws Exception {
        for (final Token token: tokens) {
            Assertions.assertEquals(token, lexer.nextToken());
        }
    }

    @Test
    void testSimple() throws Exception {
        ofConfig("""
                grammar 'simple';
                a: a LP a RP;
                a: EPS;
                
                LP: '(';
                RP: ')';
                """);
        final Lexer lexer = gen.generate(new StringSource("()(())()"));
        final Token LP = new Token("LP", "(");
        final Token RP = new Token("RP", ")");
        check(lexer, LP, RP, LP, LP, RP, RP, LP, RP, Token.EOF);
    }

    @Test
    void testMath() throws Exception {
        ofConfig("""
                grammar 'math_grammar';
                e returns [int val]: e PLUS t {$0.val = $1.val + $3.val;};
                e returns [int val]: t {$0.val = $1.val;};
                t returns [int val]: t MUL f {$0.val = $1.val * $3.val;};
                t returns [int val]: f {$0.val = $1.val;};
                
                f returns [int val]: NUM {$1.val = Integer.parseInteger($1.text);};
                f returns [int val]: LP e RP {$0.val = $2.val;};
                
                RP: ')';
                LP: '(';
                PLUS: '+';
                MUL: '*';
                NUM: '-'?[0-9]*;
                """);
        final Lexer lexer = gen.generate(new StringSource("(1 + 2) * (3 + 4)"));
        check(lexer,
                new Token("LP", "("),
                new Token("NUM", "1"),
                new Token("PLUS", "+"),
                new Token("NUM", "2"),
                new Token("RP", ")"),
                new Token("MUL", "*"),
                new Token("LP", "("),
                new Token("NUM", "3"),
                new Token("PLUS", "+"),
                new Token("NUM", "4"),
                new Token("RP", ")"),
                Token.EOF
        );
    }


}
