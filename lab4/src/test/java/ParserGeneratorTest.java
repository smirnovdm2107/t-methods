import java.nio.file.Path;

import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.ConfigParser;
import com.github.smirnovdm2107.config.source.StringSource;
import com.github.smirnovdm2107.lang.LangParser;
import com.github.smirnovdm2107.lang.Node;
import org.junit.jupiter.api.Test;

public class ParserGeneratorTest extends BaseTest {

    final ConfigParser configParser = new ConfigParser();
    Config config;

    LangParser parser;
    private void ofConfig(final String string) throws Exception {
        this.config = configParser.parse(string);
        this.parser = new LangParser(config, config.parserRules().get(0).name(), Path.of(""));
    }

    private Node parse(final String input) throws Exception {
        return parser.parse(new StringSource(input));
    }


    @Test
    void testMath() throws Exception {
        ofConfig(MATH_CONFIG);

        final Node node = parse("2 * 2");
        final int val = node.context().getClass().getField("val").getInt(node.context());
        System.out.println(val);
    }

}
