package com.github.smirnovdm2107.source;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class StringSource extends InputStreamSource {
    public StringSource(final String input, final Charset charset) throws IOException {
        super(new ByteArrayInputStream(input.getBytes(charset)), charset);
    }

    public StringSource(final String input) throws IOException {
        this(input, CharSource.DEFAULT_CHARSET);
    }
}
