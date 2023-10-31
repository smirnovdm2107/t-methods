#!/bin/bash

antlr4 -no-listener -o src/main/java/com/github/smirnovdm2107/parser \
 -package com.github.smirnovdm2107.parser Java.g4 || exit 1
mvn clean install package

