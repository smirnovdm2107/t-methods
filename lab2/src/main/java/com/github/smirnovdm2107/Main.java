package com.github.smirnovdm2107;

import java.io.FileInputStream;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        final Path saveGraphFilePath = Path.of("graph.dot");
        final Path visualizerOutputPath = Path.of("graph.svg");
        final Tree tree = new Parser().parse(new FileInputStream("example.txt"));
        FileIO.saveTree(tree, saveGraphFilePath);
        Visualizer.graph(saveGraphFilePath, visualizerOutputPath);
    }
}