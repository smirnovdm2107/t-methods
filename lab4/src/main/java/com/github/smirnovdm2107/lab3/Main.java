package com.github.smirnovdm2107.lab3;

import java.io.FileInputStream;
import java.nio.file.Path;

import com.github.smirnovdm2107.config.ConfigParser;
import com.github.smirnovdm2107.config.source.InputStreamSource;
import com.github.smirnovdm2107.config.source.StringSource;
import com.github.smirnovdm2107.lang.LangParser;
import com.github.smirnovdm2107.lang.Node;

public class Main {
    public static void main(String[] args) throws Exception {
        final Path saveGraphFilePath = Path.of("graph.dot");
        final Path visualizerOutputPath = Path.of("graph.svg");
        final ConfigParser configParser = new ConfigParser();
        final LangParser parser = new LangParser(configParser.parse(Path.of("lab3config")), "s", Path.of("src/main/java"));
        final Node tree = parser.parse(new StringSource("for ( int i = 0; i <= 10; i++ )"));
        FileIO.saveTree(nodeToTree(tree), saveGraphFilePath);
        Visualizer.graph(saveGraphFilePath, visualizerOutputPath);
    }

    private static Tree nodeToTree(final Node node) {
        return new Tree(node.name(), node.children().stream().map(Main::nodeToTree).toArray(Tree[]::new));
    }
}