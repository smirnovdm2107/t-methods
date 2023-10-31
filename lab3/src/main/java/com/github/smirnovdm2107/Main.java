package com.github.smirnovdm2107;

import com.github.smirnovdm2107.parser.JavaLexer;
import com.github.smirnovdm2107.parser.JavaParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("args must not be null");
            return;
        }
        if (args.length != 3) {
            System.out.println("expected [input file] [output file] as arguments, got " + args.length + " arguments");
        }
        final String inputFile = args[1];
        final String outputFile = args[2];
        final String output;
        try (InputStream inputStream = Files.newInputStream(Path.of(inputFile))) {
            final CharStream charStream = CharStreams.fromStream(inputStream, StandardCharsets.UTF_8);
            final Lexer lexer = new JavaLexer(charStream);
            final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            final JavaParser parser = new JavaParser(tokenStream);
            JavaParser.ProgramContext context = parser.program();
            if (context.exception != null) {
                System.out.println(context.res);
                System.out.println(context.exception.getMessage());
                context.exception.printStackTrace();
                return;
            }
            output = context.res;
        } catch (final IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        try (BufferedWriter out = Files.newBufferedWriter(Path.of(outputFile))) {
            out.write(output);
        } catch (final IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
