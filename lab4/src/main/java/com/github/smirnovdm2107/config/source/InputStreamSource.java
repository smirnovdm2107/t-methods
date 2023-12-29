package com.github.smirnovdm2107.config.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class InputStreamSource implements CharSource {
    private static final int READ_AHEAD_LIMIT = 4096;
    private final BufferedReader in;
    private int current;
    private int pos;
    private int markedCurrent = -1;

    public InputStreamSource(final InputStream in, final Charset charset) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(in, charset));
        next();
    }

    public InputStreamSource(final InputStream in) throws IOException {
        this(in, CharSource.DEFAULT_CHARSET);
    }

    @Override
    public int current() {
        return current;
    }

    @Override
    public boolean hasNext() {
        return current != END;
    }

    @Override
    public void mark() throws IOException {
        in.mark(READ_AHEAD_LIMIT);
        markedCurrent = current;
    }

    @Override
    public void reset() throws IOException {
        in.reset();
        current = markedCurrent;
        markedCurrent = -1;
    }

    @Override
    public int next() throws IOException {
        if (current == -1) {
            return END;
        }
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
