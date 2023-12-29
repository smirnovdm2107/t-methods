package com.github.smirnovdm2107.codegen;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.ParserRule;
import com.github.smirnovdm2107.exc.AttributeException;
import com.github.smirnovdm2107.lang.Context;
import com.github.smirnovdm2107.util.ConfigUtils;

public class AttributeCodeRunner {

    private final Object object;
    private final Config config;
    private final Map<ParserRule, String> parserRuleMethodNameMap = new HashMap<>();
    private final Map<String, Class<?>> classMap = new HashMap<>();
    public AttributeCodeRunner(
            final Config config,
            final Path path
    ) {
        try {
            this.config = config;
            final List<ParserRule> parserRuleList = config.parserRules();
            for (int i = 0; i < parserRuleList.size(); i++) {
                final ParserRule rule = parserRuleList.get(i);
                parserRuleMethodNameMap.put(rule, rule.name() + i);
            }

            final AttributeCodeGenerator attributeCodeGenerator = new AttributeCodeGenerator();
            attributeCodeGenerator.generate(config, path);

            final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            final String className = ConfigUtils.getAttributeCodeClassName(config);
            final int code =
                    compiler.run(null, null, null, path.resolve(className + ".java").toString());
            if (code != 0) {
                throw new RuntimeException("attribute code compile error");
            }
            final URL[] url = {path.toUri().toURL()};
            final ClassLoader loader = URLClassLoader.newInstance(url, ClassLoader.getSystemClassLoader());
            final Class<?> clazz = loader.loadClass(className);

            final Collection<String> parserRulesNames = config.parserRules().stream().map(ParserRule::name).collect(Collectors.toSet());
            for (final String parserRuleName: parserRulesNames) {
                final String subClassName = className + "$" + parserRuleName + "Context";
                final Class<?> subClass = loader.loadClass(subClassName);
                classMap.put(parserRuleName, subClass);
            }

            this.object = clazz.getConstructors()[0].newInstance();
        } catch (final Exception e) {
            throw new AttributeException(e);
        }
    }

    public void run(final ParserRule rule, List<Context> contexts) {
        try {
            object.getClass().getMethod(parserRuleMethodNameMap.get(rule), List.class).invoke(
                    object, contexts
            );
        } catch (final Exception e) {
            throw new AttributeException(e);
        }
    }

    public Context emptyContext(final ParserRule rule) {
        try {
            final Class<?> clazz = classMap.get(rule.name());
            return (Context) clazz.getConstructors()[0].newInstance();
        } catch (final Exception e) {
            throw new AttributeException(e);
        }
    }
}
