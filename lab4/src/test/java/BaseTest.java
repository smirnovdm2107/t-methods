import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BaseTest {

    final String MATH_CONFIG = """
            grammar 'math_grammar';
            e returns [int val]: e PLUS t {$0.val = $1.val + $3.val;};
            e returns [int val]: t {$0.val = $1.val;};
            t returns [int val]: t MUL f {$0.val = $1.val * $3.val;};
            t: f {$0.val = $1.val;};
            
            f returns [int val]: NUM {$0.val = Integer.parseInt($1.text);};
            f returns [int val]: LP e RP {$0.val = $2.val;};
            
            RP: ')';
            LP: '(';
            PLUS: '+';
            MUL: '*';
            NUM: '-'?[0-9]*;
            """;

    protected static Path dir;

    @BeforeAll
    static void generateTestDir() throws IOException {
        dir = Files.createTempDirectory("TEST");
    }

    @AfterAll
    static void clearUp() throws IOException {
        Files.walkFileTree(dir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.deleteIfExists(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    void hasSameElements(Collection<?> expected, Collection<?> actual) {
        Assertions.assertEquals(expected.size(), actual.size());
        final List<?> actualList = new ArrayList<>(actual);
        for (Object o : expected) {
            if (!actualList.contains(o)) {
                Assertions.fail("has no element: " + o);
            } else {
                actualList.remove(o);
            }
        }
    }

    protected String FULL_MATH = """
            grammar 'math_grammar';
            e returns [int val]: e PLUS t {$0.val = $1.val + $3.val;};
            e returns [int val]: e MINUS t {$0.val = $1.val - $3.val;};
            e returns [int val]: t {$0.val = $1.val;};
            t returns [int val]: t MUL f {$0.val = $1.val * $3.val;};
            t returns [int val]: t DIV f {if ($3.val == 0) $0.val = $1.val; else $0.val = $1.val / $3.val;};
            t returns [int val]: f {$0.val = $1.val;};
            
            f returns [int val]: NUM {$0.val = Integer.parseInt($1.text);};
            f returns [int val]: LP e RP {$0.val = $2.val;};
            
            NUM: '-'?[0-9]+;
            RP: ')';
            LP: '(';
            MINUS: '-';
            DIV: '/';
            MUL: '*';
            PLUS: '+';
            """;
}
