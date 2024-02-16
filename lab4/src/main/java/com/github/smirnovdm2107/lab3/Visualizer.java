package com.github.smirnovdm2107.lab3;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

public class Visualizer {
    public static void graph(final Path input, final Path output) {
        try {
            final String string = String.format("dot -Tsvg %s -O", input.toAbsolutePath());
            final String[] command = string.split(" ");

            final Process process = new ProcessBuilder(command).start();
            try {
                process.waitFor();
            } catch (final InterruptedException e) {
                process.destroy();
            }
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
