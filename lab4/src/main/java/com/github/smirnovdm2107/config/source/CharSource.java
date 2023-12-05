package com.github.smirnovdm2107.config.source;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Interface that represent char sequence generator
 */
public interface CharSource extends AutoCloseable {

    /**
     * Default charset to get char sequence from byte one.
     */
    Charset DEFAULT_CHARSET = Charset.defaultCharset();

    /**
     * Then if {@link #next()} has been called - returned char, else - first element of sequence.
     * @return current char sequence element.
     */
    char current();

    int currentPos();

    /**
     * true if char sequence has next element, else false.
     * @return state of char sequence.
     */
    boolean hasNext();

    /**
     * Next element from sequence. If before method {@link #hasNext()} returned true, then
     * must return next element, else undefined behavior.
     * @return next element from char sequence
     */
    char next() throws IOException;

    void mark() throws IOException;

    void reset() throws IOException;
}
