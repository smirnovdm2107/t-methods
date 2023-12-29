import java.util.List;

import com.github.smirnovdm2107.GrammarAnalyzer;
import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.ConfigParser;
import com.github.smirnovdm2107.config.LexerRule;
import com.github.smirnovdm2107.config.ParserRule;
import com.github.smirnovdm2107.dfa.NDFA;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DFATest extends BaseTest {

    NDFA ndfa;
    Config config;
    final ConfigParser configParser = new ConfigParser();

    private void ofConfig(final String string, final String start) throws Exception {
        final Config config = configParser.parse(string);
        this.config = config;
        this.ndfa = new NDFA(config, start);
    }


    @Test
    void testMath() throws Exception {
        ofConfig(MATH_CONFIG, "e");
        final List<LexerRule> lexerRules = config.lexerRules();

//        Assertions.assertEquals(
//                new ParserRule("f", List.of("NUM"), List.of(), "$1.val = Integer.parseInteger($1.text);"),
//                ndfa.from(List.of("NUM"), )
//        );
//
//        Assertions.assertEquals(
//                new ParserRule("f", List.of("LP", "e", "RP"), List.of(), "$0.val = $2.val;"),
//                ndfa.from(List.of("LP", "e", "RP"))
//        );
//
//        Assertions.assertEquals(
//                new ParserRule("f", List.of("NUM"), List.of(), "$1.val = Integer.parseInteger($1.text);"),
//                ndfa.from(List.of("LP", "NUM"))
//        );
//
//        Assertions.assertEquals(
//                new ParserRule("e", List.of("t"), List.of(), "$0.val = $1.val;"),
//                ndfa.from(List.of("LP", "t"))
//        );
    }

}
