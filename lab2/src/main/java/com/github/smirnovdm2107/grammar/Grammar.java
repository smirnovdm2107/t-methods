package com.github.smirnovdm2107.grammar;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record Grammar(char start, Set<Character> nonTerminals, Map<Character, List<String>> rules) {
}
