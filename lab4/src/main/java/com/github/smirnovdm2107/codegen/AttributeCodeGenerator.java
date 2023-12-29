package com.github.smirnovdm2107.codegen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.smirnovdm2107.config.Attribute;
import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.ParserRule;
import com.github.smirnovdm2107.lang.Context;
import com.github.smirnovdm2107.lang.LexerContext;
import com.github.smirnovdm2107.util.ConfigUtils;


public class AttributeCodeGenerator {

    public void generate(final Config config, final Path path) throws IOException {
        new Helper(config, path).generate();
    }

    private static final class Helper {
        private final Config config;
        private final StringBuilder sb = new StringBuilder();
        private final Path path;
        private static final String LINE_SEPARATOR = System.lineSeparator();
        private static final String TAB = "    ";
        public Helper(final Config config, final Path path) {
            this.config = config;
            this.path = path;
        }

        private String getClassName() {
            return ConfigUtils.getAttributeCodeClassName(config);
        }
        private Path getFilePath() {
            return path.resolve(getClassName() + ".java");
        }

        public void generate() throws IOException {
            generateImports(List.class, Context.class, LexerContext.class);
            sb.append("public class ")
                    .append(getClassName())
                    .append(" {");
            sb.append(LINE_SEPARATOR);
            generateCode();
            generateContexts();
            sb.append('}');

            Files.writeString(getFilePath(), sb.toString());
        }

        private void generateImports(Class<?>... classes) {
            for (final Class<?> clazz: classes) {
                sb.append("import ")
                        .append(clazz.getCanonicalName())
                        .append(';')
                        .append(LINE_SEPARATOR);
            }
        }

        private void generateCode() {
            final List<ParserRule> parserRules = config.parserRules();
            for (int i = 0; i < parserRules.size(); i++) {
                final ParserRule rule = parserRules.get(i);
                if (rule.codeBlock() == null) {
                    continue;
                }
                sb.append(TAB)
                        .append("public void ")
                        .append(rule.name())
                        .append(i)
                        .append("(List<Context> contexts)")
                        .append(" {")
                        .append(LINE_SEPARATOR);
                String currentCode = rule.codeBlock();
                final List<String> subRules = rule.rules();
                currentCode = currentCode.replace("$" + 0 + ".",
                        "((" + getContextClassName(rule.name()) + ")" + "contexts.get(" + 0 + ")).");
                for (int j = 1; j < subRules.size() + 1; j++) {
                    currentCode = currentCode.replace("$" + j + ".",
                            "((" + getContextClassName(subRules.get(j - 1)) + ")" + "contexts.get(" + j + ")).");
                }
                sb.append(TAB)
                        .append(TAB)
                        .append(currentCode)
                        .append(LINE_SEPARATOR)
                        .append(TAB)
                        .append("}")
                        .append(LINE_SEPARATOR);
            }
        }

        public void generateContexts() {
            final List<ParserRule> parserRules = config.parserRules();
            final Set<String> names = new HashSet<>();
            for (final ParserRule rule : parserRules) {
                if (names.contains(rule.name())) {
                    continue;
                }
                names.add(rule.name());
                sb.append(TAB)
                        .append("public static class ")
                        .append(getContextClassName(rule.name()))
                        .append(" implements Context {")
                        .append(LINE_SEPARATOR);
                for (final Attribute attribute : rule.attributes()) {
                    sb.append(TAB)
                            .append(TAB)
                            .append("public ")
                            .append(attribute.type())
                            .append(" ")
                            .append(attribute.name())
                            .append(";")
                            .append(LINE_SEPARATOR);
                }
                sb.append(TAB).append("}");
                sb.append(LINE_SEPARATOR);
            }
        }

        private String getContextClassName(final String rule) {
            if (ConfigUtils.isLexerRule(rule)) {
                return "LexerContext";
            }
            return rule + "Context";
        }
    }

}
