package com.github.smirnovdm2107.source;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSource extends InputStreamSource {

    public FileSource(final Path path) throws IOException {
        this(path, CharSource.DEFAULT_CHARSET);
    }
    public FileSource(final Path path, final Charset charset) throws IOException {
        super(Files.newInputStream(path), charset);
    }

}
