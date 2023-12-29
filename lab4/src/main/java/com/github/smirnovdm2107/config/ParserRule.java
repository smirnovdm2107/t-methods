package com.github.smirnovdm2107.config;

import java.util.List;

/**
 * Representation of parser rule!
 * @param rules sequence of rules of this very parser rule.
 */
public record ParserRule(
        String name,
        List<String> rules,
        List<Attribute> attributes,
        String codeBlock
) implements Rule {
}
