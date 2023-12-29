import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

import com.github.smirnovdm2107.codegen.AttributeCodeGenerator;
import com.github.smirnovdm2107.codegen.AttributeCodeRunner;
import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.ConfigParser;
import org.junit.jupiter.api.Test;

public class AttributeGeneratorTest extends BaseTest {

    final ConfigParser configParser = new ConfigParser();
    Config config;
    private void ofConfig(final String config) throws IOException, ParseException {
        this.config = configParser.parse(config);
        new AttributeCodeGenerator().generate(this.config, Path.of(""));
    }
    @Test
    void testAttributeGenerator() throws Exception {
        ofConfig(MATH_CONFIG);
    }

    @Test
    void testAttributeCodeRunner() throws Exception {
        ofConfig(MATH_CONFIG);
        new AttributeCodeRunner(config, dir);
    }

}
