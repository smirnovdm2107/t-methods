package com.github.smirnovdm2107.config;

import java.util.List;

public record Config(
        String grammarName,
        List<LexerRule> lexerRules,
        List<ParserRule> parserRules
) {
}
