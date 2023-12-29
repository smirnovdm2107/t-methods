import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.github.smirnovdm2107.GrammarAnalyzer;
import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.ConfigParser;
import com.github.smirnovdm2107.config.LexerRule;
import org.junit.jupiter.api.Test;

public class FirstFollowTest extends BaseTest {

    GrammarAnalyzer analyzer;
    final ConfigParser parser = new ConfigParser();


    private void ofConfig(final String str, final String start) throws Exception {
        final Config config = parser.parse(str);
        analyzer = new GrammarAnalyzer(config, start);
    }

    @Test
    void testSimpleFirst() throws Exception {
        ofConfig("""
                grammar 'test_first_follow';
                a: LP a RP;
                a: b;
                b: EPS;
                LP: '(';
                RP: ')';
                """, "a");
        final Map<String, Collection<LexerRule>> first = analyzer.first();
        final Map<String, Collection<LexerRule>> expected = Map.of(
                "a", Set.of(
                        new LexerRule("LP", "("),
                        LexerRule.EPS
                ),
                "b", Set.of(
                        LexerRule.EPS
                )
        );
        hasSameElements(expected.entrySet(), first.entrySet());
    }

    @Test
    void testSimpleFollow() throws Exception {
        ofConfig("""
                grammar 'test_first_follow';
                a: LP a RP;
                a: b;
                b: EPS;
                LP: '(';
                RP: ')';
                """, "a");
        final Map<String, Collection<LexerRule>> follow = analyzer.follow();
        final Map<String, Collection<LexerRule>> expected = Map.of(
                "a", Set.of(
                        LexerRule.EOF,
                        new LexerRule("RP", ")")
                ),
                "b", Set.of(
                        LexerRule.EOF,
                        new LexerRule("RP", ")")
                )
        );
        hasSameElements(expected.entrySet(), follow.entrySet());
    }

    @Test
    void testMath() throws Exception {
        ofConfig("""
                grammar 'math_grammar';
                e: e PLUS t {$0.val = $1.val + $3.val;};
                e: t {$0.val = $1.val;};
                t: t MUL f {$0.val = $1.val * $3.val;};
                t: f {$0.val = $1.val;};
                
                f: NUM {$1.val = Integer.parseInteger($1.text);};
                f: LP e RP {$0.val = $2.val;};
                
                RP: ')';
                LP: '(';
                PLUS: '+';
                MUL: '*';
                NUM: '-'?[0-9]*;
                """, "e"
        );
        final Map<String, Collection<LexerRule>> first = analyzer.first();

        final LexerRule NUM = new LexerRule("NUM", "-?[0-9]*");
        final LexerRule LP = new LexerRule("LP", "(");
        final LexerRule RP = new LexerRule("RP", ")");
        final LexerRule PLUS = new LexerRule("PLUS", "+");
        final LexerRule MUL = new LexerRule("MUL", "*");
        final Map<String, Collection<LexerRule>> expectedFirst = Map.of(
                "e", Set.of(
                        NUM,
                        LP
                ),
                "t", Set.of(
                        NUM,
                        LP
                ),
                "f", Set.of(
                        NUM,
                        LP
                )
        );
        hasSameElements(expectedFirst.entrySet(), first.entrySet());
        final Map<String, Collection<LexerRule>> follow = analyzer.follow();
        final Map<String, Collection<LexerRule>> expectedFollow = Map.of(
                "e", Set.of(
                        LexerRule.EOF,
                        RP,
                        PLUS
                ),
                "t", Set.of(
                        LexerRule.EOF,
                        RP,
                        MUL,
                        PLUS

                ),
                "f", Set.of(
                        LexerRule.EOF,
                        RP,
                        MUL,
                        PLUS
                )
        );

        hasSameElements(expectedFollow.entrySet(), follow.entrySet());
    }
}
