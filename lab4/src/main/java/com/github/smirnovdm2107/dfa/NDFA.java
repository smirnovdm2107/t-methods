package com.github.smirnovdm2107.dfa;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import com.github.smirnovdm2107.GrammarAnalyzer;
import com.github.smirnovdm2107.config.Config;
import com.github.smirnovdm2107.config.LexerRule;
import com.github.smirnovdm2107.config.ParserRule;
import com.github.smirnovdm2107.lang.Token;

public class NDFA {

    private final Config config;
    private final Collection<ParserRule> parserRules;
    private final Map<String, LexerRule> lexerRuleMap;
    private final String start;

    public ParserRule FINAL_RULE;

    private final GrammarAnalyzer analyzer;

    private State startState;

    public NDFA(Config config, String start) {
        this.config = config;
        this.start = start;
        this.analyzer = new GrammarAnalyzer(config, start);
        this.parserRules = new HashSet<>(config.parserRules());
        this.lexerRuleMap = config.lexerRules().stream()
                .collect(Collectors.toMap(LexerRule::name, Function.identity()));
        construct();
    }

    private void construct() {
        this.FINAL_RULE =  new ParserRule(finalStateName(), List.of(start), List.of(), null);
        final Situation startSituation = new Situation(
                0, FINAL_RULE, Set.of(LexerRule.EOF)
        );
        this.parserRules.add(startSituation.rule);
        startState = new State(eps(Set.of(startSituation)));
    }

    private Set<Situation> eps(Set<Situation> s) {
        final Set<Situation> result = new HashSet<>(s);
        boolean changed = true;
        while (changed) {
            changed = false;
            final Map<SituationCore, Situation> next = result.stream()
                    .collect(Collectors.toMap(it -> new SituationCore(it.index, it.rule), Function.identity()));
            for (final Situation situation: result) {
                if (situation.getCurrent() != null && !isLexerRule(situation.getCurrent())) {
                    final Collection<ParserRule> current = this.parserRules.stream().filter(it ->
                            it.name().equals(situation.getCurrent())
                    ).toList();
                    final List<String> tail = situation.getTail();
                    final Set<LexerRule> first = new HashSet<>();
                    final List<String> subRule = new ArrayList<>(tail);
                    for (final LexerRule la : situation.lookAhead) {
                        subRule.add(la.name());
                        first.addAll(analyzer.first(subRule));
                        subRule.clear();
                        subRule.addAll(tail);
                    }

                    for (final ParserRule rule: current) {
                        final Situation newSituation = new Situation(0, rule, new HashSet<>(first));
                        if (next.containsKey(newSituation.core())) {
                            next.get(newSituation.core()).lookAhead.addAll(first);
                        } else {
                            next.put(newSituation.core(), newSituation);
                        }
                    }
                }
            }
            final int prev = result.size();
            result.addAll(next.values());
            if (prev != result.size()) {
                changed = true;
            }
            next.clear();
            next.putAll(result.stream().collect(Collectors.toMap(it -> it.core(), Function.identity())));
        }
        return result;
    }


    public String finalStateName() {
        return "FINAL_STATE_" + Instant.now();
    }

    private State next(final State state, final String token) {
        final Set<Situation> next = new HashSet<>();
        for (final Situation situation: state.situations) {
            if (situation.getCurrent() != null && situation.getCurrent().equals(token)) {
                next.add(situation.increase());
            }
        }
        if (next.isEmpty()) {
            return null;
        }
        return new State(eps(next));
    }
        
    public ParserRule from(final List<String> seq, final Token next) {
        State current = startState;
        for (final String string: seq) {
            current = next(current, string);
            if (current == null) {
                return null;
            }
        }
        final ParserRule result = current.situations
                .stream()
                .filter(Situation::isComplete)
                .filter(it -> it.lookAhead.contains(lexerRuleMap.get(next.type())))
                .map(it -> it.rule)
                .findFirst()
                .orElse(null);
        return result;
    }


    private record State(Set<Situation> situations) {
        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof State other) {
                return situations.equals(other.situations);
            }
            return false;
        }
    }

    private record SituationCore(int index, ParserRule rule) {}
    private record Situation(int index, ParserRule rule, Set<LexerRule> lookAhead) {

        public String getCurrent() {
            if (index == rule.rules().size()) {
                return null;
            }
            return rule.rules().get(index);
        }

        public SituationCore core() {
            return new SituationCore(index, rule);
        }
        public List<String> getTail() {
            if (index >= rule.rules().size() - 1) {
                return List.of();
            }
            return rule.rules().subList(index + 1, rule.rules().size());
        }

        public boolean isComplete() {
            if (rule.rules().size() == 1 && rule.rules().get(0).equals("EPS")) {
                return true;
            }
            return index == rule.rules().size();
        }

        public Situation increase() {
            return new Situation(index + 1, rule, lookAhead);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Situation other) {
                return index == other.index && rule == other.rule;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, rule);
        }
    }


    private boolean isLexerRule(final String name) {
        return Character.isUpperCase(name.charAt(0));
    }

}
