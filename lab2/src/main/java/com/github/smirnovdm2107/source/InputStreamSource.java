package com.github.smirnovdm2107.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class InputStreamSource implements CharSource {
    private final BufferedReader in;
    private int current;
    private int pos;
    private final int END = -1;

    public InputStreamSource(final InputStream in, final Charset charset) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(in, charset));
        next();
    }

    public InputStreamSource(final InputStream in) throws IOException {
        this(in, CharSource.DEFAULT_CHARSET);
    }

    @Override
    public char current() {
        return (char) current;
    }

    @Override
    public boolean hasNext() {
        return current != END;
    }

    @Override
    public char next() throws IOException {
        final char result = (char) current;
        pos++;
        current = in.read();
        return result;
    }

    @Override
    public void close() throws Exception {
        in.close();
    }

    @Override
    public int currentPos() {
        return pos;
    }
}
