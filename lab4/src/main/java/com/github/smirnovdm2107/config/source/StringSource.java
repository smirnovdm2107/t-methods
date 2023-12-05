package com.github.smirnovdm2107.config.source;

import com.github.smirnovdm2107.config.source.CharSource;
import com.github.smirnovdm2107.config.source.InputStreamSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class StringSource extends InputStreamSource {
    public StringSource(final String input, final Charset charset) throws IOException {
        super(new ByteArrayInputStream(input.getBytes(charset)), charset);
    }

    public StringSource(final String input) throws IOException {
        this(input, CharSource.DEFAULT_CHARSET);
    }
}
