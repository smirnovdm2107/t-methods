package com.github.smirnovdm2107.util;


import java.io.IOException;

import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.source.CharSource;

public final class ConfigUtils {
    public static final String LINE_SEPARATOR = System.lineSeparator();
    private ConfigUtils() {}

    public static void skipWhitespaces(final CharSource source) throws IOException {
        while (true) {
            while (Character.isWhitespace(source.current())) {
                source.next();
            }
            boolean breaked = false;
            source.mark();
            for (int i = 0; i < LINE_SEPARATOR.length(); i++) {
                if (source.current() != LINE_SEPARATOR.charAt(i)) {
                    source.reset();
                    breaked = true;
                    break;
                }
            }
            if (breaked) {
                break;
            }
        }
    }

    public static String getAttributeCodeClassName(final Config config) {
        return config.grammarName() + "Attributes";
    }

    public static boolean isLexerRule(final String name) {
        return Character.isUpperCase(name.charAt(0));
    }
}
