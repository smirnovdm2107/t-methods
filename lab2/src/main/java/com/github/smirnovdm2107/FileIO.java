package com.github.smirnovdm2107;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class FileIO {
    public static void saveTree(final Tree tree, final Path path) {
        try (final BufferedWriter out = Files.newBufferedWriter(path)) {
            out.write("digraph {");
            out.newLine();
            final Set<Edge> edges = saveTree(tree, out);
            for (final Edge edge : edges) {
                out.write(edge(edge.from(), edge.to()));
                out.newLine();
            }
            out.write("}");
            out.newLine();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private record Edge(int from, int to) {
    }

    ;

    private static Set<Edge> saveTree(final Tree tree, final BufferedWriter out) throws IOException {
        final Set<Edge> edges = new HashSet<>();
        saveTree(tree, out, edges, 0);
        return edges;
    }

    private static int saveTree(final Tree tree, final BufferedWriter out, final Set<Edge> edges, final int currentId) throws IOException {
        int childId = currentId + 1;
        for (final Tree child : tree.getChildren()) {
            out.write(String.format("a%d[label=\"%s\"]", childId, child.getNode()));
            edges.add(new Edge(currentId, childId));
            out.newLine();
            childId = saveTree(child, out, edges, childId);
        }
        return childId;
    }

    private static String edge(final int fromId, final int toId) {
        return String.format("a%d -> a%d;", fromId, toId);
    }
}