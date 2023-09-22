package com.github.smirnovdm2107.grammar;

import com.github.smirnovdm2107.GrammarAnalyzer;
import com.github.smirnovdm2107.grammar.Grammar;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PrettyGrammar {
    public static void main(String[] args) {
        final Grammar grammar = new Grammar(
                'S',
                Set.of(
                        'A',
                        'V',
                        'N',
                        'B',
                        'Z',
                        'E',
                        'F',
                        'C',
                        'O',
                        'S'),
                Map.of(
                        'S', List.of("F(A;B;C)"),
                        'F', List.of("for"),
                        'A', List.of("#", "VV=N"),
                        'N', List.of("n"),
                        'V', List.of("var"),
                        'B', List.of("#", "VZN"),
                        'Z', List.of("==", ">E", "<E"),
                        'E', List.of("#", "="),
                        'C', List.of("#", "VO"),
                        'O', List.of("++", "--")

                )
                );
        final GrammarAnalyzer analyzer = new GrammarAnalyzer(grammar);
        print(analyzer.first(), "FIRST");
        print(analyzer.follow(), "FOLLOW");
    }

    private static void print(final Map<Character, Set<Character>> map, final String name) {
        System.out.printf("-------------%s-------------\n", name);
        for (final Map.Entry<Character, Set<Character>> entry: map.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            System.out.print(entry.getValue().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            System.out.println();
        }
        System.out.println("--------------------------------");
    }
}
