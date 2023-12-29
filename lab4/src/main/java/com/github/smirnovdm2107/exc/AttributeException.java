package com.github.smirnovdm2107.exc;

import com.github.smirnovdm2107.codegen.AttributeCodeRunner;

public class AttributeException extends RuntimeException {
    public AttributeException(final Throwable throwable) {
        super(throwable);
    }
}
