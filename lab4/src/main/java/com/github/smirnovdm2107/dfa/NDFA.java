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

    private final Set<Situation> situations = new HashSet<>();

    private final Set<State> states = new HashSet<>();

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
        for (final ParserRule rule: this.parserRules) {
            for (int i = 0; i < rule.rules().size() + 1; i++) {
                final Situation situation = new Situation(i, rule, new HashSet<>());
                situations.add(situation);
            }
        }
        final ArrayDeque<State> deque = new ArrayDeque<>();
        startState = new State(eps(Set.of(startSituation)), new HashMap<>());
        deque.addLast(startState);
        while (!deque.isEmpty()) {
            final State current = deque.removeFirst();
            if (states.contains(current)) {
                continue;
            }
            states.add(current);
            final Map<String, List<Situation>> by = current.situations.stream()
                    .filter(it -> it.getCurrent() != null)
                    .collect(Collectors.groupingBy(Situation::getCurrent));

            for (final Map.Entry<String, List<Situation>> entry: by.entrySet()) {
                final Set<Situation> nextSituations = entry.getValue().stream()
                        .map(Situation::increase)
                        .collect(Collectors.toSet());
                final State newState = new State(eps(nextSituations), new HashMap<>());
                if (states.contains(newState)) {
                    continue;
                }
                current.map.put(entry.getKey(), newState);
                deque.addLast(newState);
            }
        }
    }

    private Set<Situation> eps(Set<Situation> s) {
        final Set<Situation> result = new HashSet<>();
        final Deque<Situation> rest = new ArrayDeque<>(s);

        while (!rest.isEmpty()) {
            final Situation situation = rest.removeFirst();
            if (result.contains(situation)) {
                continue;
            }
            result.add(situation);
            if (situation.getCurrent() != null && !isLexerRule(situation.getCurrent())) {
                final Collection<Situation> current = this.situations.stream().filter(it ->
                    it.index == 0 && it.rule.name().equals(situation.getCurrent())
                ).toList();
                final List<String> tail = situation.getTail();
                final Collection<LexerRule> first = new ArrayList<>();
                for (final LexerRule la: situation.lookAhead) {
                    final List<String> subRule = new ArrayList<>(tail);
                    subRule.add(la.name());
                    first.addAll(analyzer.first(subRule));
                }
                first.addAll(situation.lookAhead());
                for (final Situation subSituation: current) {
                    subSituation.lookAhead.addAll(first.stream().distinct().toList());
                    rest.add(subSituation);
                }
            }

        }
        return result;
    }


    public String finalStateName() {
        return "FINAL_STATE_" + Instant.now();
    }

        
    public ParserRule from(final List<String> seq, final Token next) {
        State current = startState;
        for (final String string: seq) {
            current = current.map.get(string);
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



    private record State(Set<Situation> situations, Map<String, State> map) {
        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof State other) {
                return situations.equals(other.situations);
            }
            return false;
        }
    }
    private record Situation(int index, ParserRule rule, Set<LexerRule> lookAhead) {

        public String getCurrent() {
            if (index == rule.rules().size()) {
                return null;
            }
            return rule.rules().get(index);
        }

        public List<String> getTail() {
            if (index >= rule.rules().size() - 1) {
                return List.of();
            }
            return rule.rules().subList(index + 1, rule.rules().size());
        }

        public boolean isComplete() {
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
