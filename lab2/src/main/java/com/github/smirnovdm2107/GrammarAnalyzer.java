package com.github.smirnovdm2107;

import com.github.smirnovdm2107.grammar.Grammar;

import java.util.*;

public class GrammarAnalyzer {
    public static final char EMPTY = '#';
    public static final char END = '$';
    private final Grammar grammar;

    private Map<Character, Set<Character>> first = null;
    private Map<Character, Set<Character>> follow = null;
    public GrammarAnalyzer(final Grammar grammar) {
        this.grammar = grammar;
    }


    private Set<Character> first(final Map<Character, Set<Character>> firstNow, final String alpha) {
        if (alpha.isEmpty()) {
            final Set<Character> result = new HashSet<>();
            result.add(EMPTY);
            return result;
        }
        final char first = alpha.charAt(0);
        if (first < 'A' || first > 'Z') {
            final Set<Character> result = new HashSet<>();
            result.add(first);
            return result;
        }
        final Set<Character> result = new HashSet<>(firstNow.get(first));
        if (result.remove(EMPTY)) {
            result.addAll(first(firstNow, alpha.substring(1)));
        }
        return result;
    }

    private Map<Character, Set<Character>> emptyMap() {
        final Map<Character, Set<Character>> resultMap = new HashMap<>();
        final Set<Character> nonTerminals = grammar.nonTerminals();
        for (final Character nonTerminal: nonTerminals) {
            resultMap.put(nonTerminal, new HashSet<>());
        }
        return resultMap;
    }
    public Map<Character, Set<Character>> first() {
        if (first != null) {
            return first;
        }
        final Map<Character, Set<Character>> resultMap = emptyMap();
        boolean changes = true;
        while(changes) {
            changes = false;
            for (final Map.Entry<Character, List<String>> entry: grammar.rules().entrySet()) {
                final Character nonTerminal = entry.getKey();
                final Set<Character> set = resultMap.get(nonTerminal);
                for (final String rule: entry.getValue()) {
                    if (set.addAll(first(resultMap, rule))) {
                        changes = true;
                    }
                }
            }

        }
        first = resultMap;
        return resultMap;
    }

    public Map<Character, Set<Character>> follow() {
        if (follow != null) {
            return follow;
        }
        if (first == null) {
            first();
        }
        final Map<Character, Set<Character>> resultMap = emptyMap();
        resultMap.get(grammar.start()).add(END);
        boolean changes = true;
        while(changes) {
            changes = false;
            for (final Map.Entry<Character, List<String>> entry: grammar.rules().entrySet()) {
                final Character nonTerminal = entry.getKey();
                final Set<Character> followMain = resultMap.get(nonTerminal);
                for (final String rule: entry.getValue()) {
                    for (int i = 0; i < rule.length(); i++) {
                        final char charNow = rule.charAt(i);
                        if ('A' <= charNow && charNow <= 'Z') {
                            final Set<Character> followNow = resultMap.get(charNow);
                            final int beforeSize = followNow.size();
                            final Set<Character> firstFollow = first(first, rule.substring(i + 1));
                            final boolean hasEmpty = firstFollow.remove(EMPTY);
                            followNow.addAll(firstFollow);
                            if (hasEmpty) {
                                followNow.addAll(followMain);
                            }
                            if (beforeSize != followNow.size()) {
                                changes = true;
                            }
                        }
                    }
                }

            }
        }
        follow = resultMap;
        return resultMap;
    }

}
