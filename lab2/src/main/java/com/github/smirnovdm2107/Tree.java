package com.github.smirnovdm2107;

import java.util.Arrays;
import java.util.List;

public class Tree {
    private final String node;
    private final List<Tree> children;
    public Tree(final String node, final Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public String getNode() {
        return node;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public Tree(final String node) {
        this.node = node;
        children = List.of();
    }

}
