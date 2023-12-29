package com.github.smirnovdm2107.lang;

import java.io.IOException;
import java.text.ParseException;

public interface Lexer {
    Token nextToken() throws IOException, ParseException;
    Token current() throws IOException, ParseException;
}
