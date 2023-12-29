package com.github.smirnovdm2107;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.LexerRule;
import com.github.smirnovdm2107.config.ParserRule;

public final class GrammarAnalyzer {

    private final Config config;
    private Map<String, Collection<LexerRule>> first;
    private Map<String, Collection<LexerRule>> follow;
    private final Map<String, LexerRule> lexerRuleMap;
    private final String start;

    public GrammarAnalyzer(final Config config, final String start) {
        this.config = config;
        this.lexerRuleMap = config.lexerRules().stream().collect(Collectors.toMap(LexerRule::name, Function.identity()));
        this.start = start;
        follow();
    }

    public Map<String, Collection<LexerRule>> first() {
        if (first == null) {
            constructFirst();
        }
        return first;
    }

    public Map<String, Collection<LexerRule>> follow() {
        if (follow == null) {
            first();
            constructFollow();
        }
        return follow;
    }

    private void constructFollow() {
        follow = new HashMap<>();

        for (final ParserRule parserRule: config.parserRules()) {
            follow.computeIfAbsent(parserRule.name(), (k) -> new HashSet<>());
        }
        follow.get(start).add(LexerRule.EOF);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (final ParserRule parserRule: config.parserRules()) {
                final List<String> rules = parserRule.rules();
                for (int i = 0; i < rules.size(); i++) {
                    final String rule = rules.get(i);
                    if (lexerRuleMap.containsKey(rule)) {
                        continue;
                    }
                    final Collection<LexerRule> y = first(rules.subList(i + 1, rules.size()));
                    final Collection<LexerRule> f = follow.get(rule);
                    final int prevSize = f.size();
                    final boolean hasEps = y.contains(LexerRule.EPS);
                    final boolean needRemoveEps = hasEps && !f.contains(LexerRule.EPS);
                    f.addAll(y);
                    if (needRemoveEps) {
                        f.remove(LexerRule.EPS);
                    }
                    if (hasEps) {
                        f.addAll(follow.get(parserRule.name()));
                    }
                    if (prevSize != f.size()) {
                        changed = true;
                    }
                }
            }
        }
    }

    public void constructFirst() {
        first = new HashMap<>();
        for (final ParserRule parserRule: config.parserRules()) {
            first.computeIfAbsent(parserRule.name(), (k) -> new HashSet<>());
        }
        boolean changed = true;
        while (changed) {
            changed = false;
            for (final ParserRule parserRule: config.parserRules()) {
                final Collection<LexerRule> rules = first.get(parserRule.name());
                final int lastSize = rules.size();
                rules.addAll(first(parserRule.rules()));
                if (lastSize != rules.size()) {
                    changed = true;
                }
            }
        }
    }

    public Collection<LexerRule> first(final List<String> list) {
        if (list.isEmpty()) {
            return List.of(LexerRule.EPS);
        }
        final String first = list.get(0);
        if (lexerRuleMap.containsKey(first)) {
            return List.of(lexerRuleMap.get(first));
        }
        final Collection<LexerRule> b = this.first.get(first);
        final Collection<LexerRule> result = new ArrayList<>(b);
        result.remove(LexerRule.EPS);
        if (b.contains(LexerRule.EPS)) {
            final Collection<LexerRule> y = first(list.subList(1, list.size()));
            result.addAll(y);
        }
        return result;
    }

    
}
