package com.github.smirnovdm2107.lang;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.github.smirnovdm2107.codegen.AttributeCodeRunner;
import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.ParserRule;
import com.github.smirnovdm2107.config.source.CharSource;
import com.github.smirnovdm2107.dfa.NDFA;

public class LangParser {
    private final Config config;
    private final String start;
    private final NDFA ndfa;

    private final LexerGenerator lexerGenerator;
    private final AttributeCodeRunner runner;

    public LangParser(final Config config, final String start, final Path path) {
        this.config = config;
        this.start = start;
        this.ndfa = new NDFA(config, start);
        this.lexerGenerator = new LexerGenerator(config);
        this.runner = new AttributeCodeRunner(config, path);
    }

    public Node parse(final CharSource source) throws IOException, ParseException {
        return new Helper(source).parse();
    }
    private final class Helper {
        private final Lexer lexer;
        private final List<Node> left = new ArrayList<>();
        private final List<String> leftNames = new ArrayList<>();
        public Helper(final CharSource source) {
            this.lexer = lexerGenerator.generate(source);
        }
        public Node parse() throws IOException, ParseException {
            while (true) {
                final ParserRule rule = ndfa.from(leftNames, lexer.current());
                if (ndfa.FINAL_RULE.equals(rule)) {
                    return left.get(0);
                }
                if (rule == null) {
                    shift();
                } else {
                    reduce(rule);
                }
            }
        }

        private void reduce(final ParserRule rule) {
            final List<Node> children = new ArrayList<>();
            final List<Context> contexts = new ArrayList<>();
            final Context context = runner.emptyContext(rule);
            final List<String> subRules = rule.rules();
            for (int i = 0; i < subRules.size(); i++) {
                children.add(left.remove(left.size() - 1));
                leftNames.remove(leftNames.size() - 1);
                contexts.add(children.get(children.size() - 1).context());
            }
            contexts.add(context);
            Collections.reverse(children);
            Collections.reverse(contexts);
            runner.run(rule, contexts);
            final Node newNode = new Node(
                    rule.name(),
                    children,
                    context
            );
            left.add(newNode);
            leftNames.add(newNode.name());
        }

        private void shift() throws IOException, ParseException {
            final Token token = lexer.nextToken();
            if (Token.EOF.equals(token)) {
                throw new RuntimeException("unexpected eof");
            }
            final Node node = new Node(token.type(), List.of(), new LexerContext(token.text()));
            left.add(node);
            leftNames.add(node.name());
        }
    }

}
