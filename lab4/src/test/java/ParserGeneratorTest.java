import java.nio.file.Path;
import java.util.Random;
import java.util.function.IntBinaryOperator;

import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.ConfigParser;
import com.github.smirnovdm2107.config.source.StringSource;
import com.github.smirnovdm2107.lang.LangParser;
import com.github.smirnovdm2107.lang.Node;
import org.junit.jupiter.api.Assertions;
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

    void check(final String string, final int result) throws Exception {
        final Node node = parse(string);
        final int val = node.context().getClass().getField("val").getInt(node.context());
        Assertions.assertEquals(result, val);
    }

    record MathResult(StringBuilder sb, int result) {}
    int randomMath(final int depth, final Random random, final StringBuilder sb) {
        if (depth == 0) {
            final int next = random.nextInt(1, 10);
            sb.append(next);
            return next;
        }
        return switch (random.nextInt(6)) {
             case 0 -> {
                final int next = random.nextInt(1, 10);
                sb.append(next);
                yield next;
            }
            case 1 -> {
                sb.append("(");
                final int result = randomMath(depth - 1, random, sb);
                sb.append(")");
                yield result;
            }
            case 2 -> mathOp(depth, random, sb, "+", Integer::sum);
            case 3 -> mathOp(depth, random, sb, "-", (a, b) -> a - b);
            case 4 -> mathOp(depth, random, sb, "*", (a, b) -> a * b);
            case 5 -> mathOp(depth, random, sb, "*", (a, b) -> a / b);
            default -> throw new RuntimeException();
        };
    }

    int mathOp(
        final int depth,
        final Random random,
        final StringBuilder sb,
        final String sign,
        final IntBinaryOperator op
    ) {
        sb.append("(");
        final int left = randomMath(depth - 1, random, sb);
        sb.append(' ').append(sign).append(' ');
        final int right = randomMath(depth - 1, random, sb);
        sb.append(')');
        return op.applyAsInt(left, right);
    }

    MathResult randomMath(final int depth) {
        final StringBuilder stringBuilder = new StringBuilder();
        final int result = randomMath(depth, new Random(), stringBuilder);
        return new MathResult(stringBuilder, result);
    }
    @Test
    void testMath() throws Exception {
        ofConfig(FULL_MATH);
        check("2 * 2", 4);
        check("2", 2);
        check("(2)", 2);
        check("((2))", 2);
        check("2 / 2", 1);

        check("( 2 + 2) * 2", 8);
        check(" 2 + 2 * 2", 6);
        check("(1 - -2 - -2)", 5);
        check("((2 * 2) * (2 * 2))", 16);
        check("1 - 2 - 3", -4);

        check("1 / 0", 1);
        check("1 + 100 / 0", 101);
    }



}
