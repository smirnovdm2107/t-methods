import com.github.smirnovdm2107.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

class ParserTest {

    /*
        Test empty input.
     */
    @Test
    void testEmpty() {
        test("", true);
    }

    /*
        Just normal case.
     */
    @Test
    void testNormal() {
        test("for (int i = 0; i < 10; i++)", false);
    }

    /*
        Testing variables.
     */
    @Test
    void testVariable() {
        test ("for (int i = 0; i < 10; i++)", false);
        test ("for (int aaaaaaaaaaaaaaa = 0; aaaaaaaaaaaaaa < 10; aaaaaaaaaaaaa++)", false);
        test("for (int a0 = 0; a0 < 10; a0++)", false);
        test("for (int 0a = 0; 0a < 10; 0a++", true);
    }

    /*
        Testing number.
     */
    @Test
    void testNumber() {
        test("for (int i = 0; i < 10.0; i++)", false);
        test("for (int i = a; i < b; i++)", true);
    }

    /*
        Testing comparison.
     */
    @Test
    void testCompare() {
        test ("for (int i = 0; i < 10; i++)", false);
        test ("for (int i = 0; i <= 10; i++)", false);
        test ("for (int i = 0; i > 10; i++)", false);
        test ("for (int i = 0; i >= 10; i++)", false);
        test ("for (int i = 0; i <=> 10; i++)", true);
    }

    /*
        Testing operation.
     */
    @Test
    void testInc() {
        test("for (int i = 0; i < 10; i++)", false);
        test("for (int i = 0; i < 10; i--)", false);
        test("for (int i = 0 ; i < 10; i+)", true);
        test("for (int i = 0 ; i < 10; i-)", true);
        test("for (int i = 0; i < 10; i&&)", true);
    }

    /*
        Testing missing element of for statement.
     */
    @Test
    void testMissing() {
        // missing for
        test("( int i = 0; i < 10; i++)", true);
        // missing type
        test("for ( i = 0 ; i < 10; i++)", true);
        // missing variable (first)
        test("for (int = 0; i < 10; i++)", true);
        // missing equals
        test("for (int i 0; i < 10; i++)", true);
        // missing second variable
        test("for (int i = 0; < 10; i++)", true);
        // missing compare
        test("for (int i = 0; i 10; i++)", true);
        // missing second number
        test("for (int i = 0; i < ; i++)", true);
        // missing last variable
        test("for (int i = 0; i < 10; ++)", true);
        // missing increment
        test("for (int i = 0; i < 10; i)", true);
        // missing left parent
        test("for int i = 0; i < 10; i++)", true);
        // missing right parent
        test("for (int i = 0; i < 10; i++", true);
        // missing parents
        test("for int i = 0; i < 10; i++", true);
        // missing main part
        test("for ", true);
        // missing first semicolon
        test("for (int i = 0; i < 10 i++)", true);
        // missing second semicolon
        test("for (int i = 0 i < 10; i++)", true);

        // without any part
        test("for (; i < 10; i++)", false);
        test("for (int i = 0; ; i++)", false);
        test("for (int i = 0; i < 10; )", false);
        test("for (; i < 10;)", false);
        test("for (; ; i++)", false);
        test("for (;;)", false);
    }

    /*
        Testing spaces.
     */
    @Test
    void testSpaces() {
        test("for            (int i = 0; i < 10; i++)", false);
        test("for (int i = 0; i < 10; i++)          ", false);
        test("for (int             i          =    0       ; i < 10; i++)", false);
        test("for (int i = 0;      i         <      10     ;i++)", false);
        test("for (int i = 0; i < 10; i          ++      )", false);
        test("        for         (          int    i        =   0   ;   i    <    10  ;  i ++   )  ", false);

    }
    

    public void test(final String source, final boolean shouldFail) {
        try {
            new Parser().parse(new ByteArrayInputStream(source.getBytes()));
            if (shouldFail) {
                Assertions.fail();
            }
        } catch (final Throwable e) {
            if (!shouldFail) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                Assertions.fail();
            }
        }
    }
}
