package com.github.smirnovdm2107.config.source;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Interface that represent char sequence generator
 */
public interface CharSource extends AutoCloseable {

    int END = -1;

    /**
     * Default charset to get char sequence from byte one.
     */
    Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * Then if {@link #next()} has been called - returned char, else - first element of sequence.
     * @return current char sequence element.
     */

    int current();

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

    int next() throws IOException;

    void mark() throws IOException;

    void reset() throws IOException;

}
