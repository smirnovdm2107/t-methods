package com.github.smirnovdm2107;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws Exception {
        final String example = "for (int i=1; int < 10; a++)";
        final Path saveGraphFilePath = Path.of("graph.dot");
        final Path visualizerOutputPath = Path.of("graph.svg");
        final Tree tree = new Parser().parse(new ByteArrayInputStream(example.getBytes()));
        FileIO.saveTree(tree, saveGraphFilePath);
        Visualizer.graph(saveGraphFilePath, visualizerOutputPath);
    }
}