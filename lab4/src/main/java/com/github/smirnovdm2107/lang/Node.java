package com.github.smirnovdm2107.lang;

import java.util.List;

public record Node(String name, List<Node> children, Context context) {
}
