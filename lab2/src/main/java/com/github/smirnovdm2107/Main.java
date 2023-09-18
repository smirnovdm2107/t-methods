package com.github.smirnovdm2107;

import java.io.ByteArrayInputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        new Parser().parse(new ByteArrayInputStream("for (;;)".getBytes()));
    }
}