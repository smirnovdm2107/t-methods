import java.util.List;
import com.github.smirnovdm2107.lang.Context;
import com.github.smirnovdm2107.lang.LexerContext;
public class math_grammarAttributes {
    public void e0(List<Context> contexts) {
        ((eContext)contexts.get(0)).val = ((eContext)contexts.get(1)).val + ((tContext)contexts.get(3)).val;
    }
    public void e1(List<Context> contexts) {
        ((eContext)contexts.get(0)).val = ((eContext)contexts.get(1)).val - ((tContext)contexts.get(3)).val;
    }
    public void e2(List<Context> contexts) {
        ((eContext)contexts.get(0)).val = ((tContext)contexts.get(1)).val;
    }
    public void t3(List<Context> contexts) {
        ((tContext)contexts.get(0)).val = ((tContext)contexts.get(1)).val * ((fContext)contexts.get(3)).val;
    }
    public void t4(List<Context> contexts) {
        if (((fContext)contexts.get(3)).val == 0) ((tContext)contexts.get(0)).val = ((tContext)contexts.get(1)).val; else ((tContext)contexts.get(0)).val = ((tContext)contexts.get(1)).val / ((fContext)contexts.get(3)).val;
    }
    public void t5(List<Context> contexts) {
        ((tContext)contexts.get(0)).val = ((fContext)contexts.get(1)).val;
    }
    public void f6(List<Context> contexts) {
        ((fContext)contexts.get(0)).val = Integer.parseInt(((LexerContext)contexts.get(1)).text);
    }
    public void f7(List<Context> contexts) {
        ((fContext)contexts.get(0)).val = ((eContext)contexts.get(2)).val;
    }
    public static class eContext implements Context {
        public int val;
    }
    public static class tContext implements Context {
        public int val;
    }
    public static class fContext implements Context {
        public int val;
    }
}