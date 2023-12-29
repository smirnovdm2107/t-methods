package com.github.smirnovdm2107.config;

public record ConfigToken(ConfigTokenType tokenType, String text) {
    public static final ConfigToken COLON = new ConfigToken(ConfigTokenType.COLON, ":");
    public static final ConfigToken EOF = new ConfigToken(ConfigTokenType.EOF, "");
    public static final ConfigToken SEMICOLON = new ConfigToken(ConfigTokenType.SEMICOLON, ";");
    public static final ConfigToken OPTIONAL = new ConfigToken(ConfigTokenType.OPTIONAL, "?");
    public static final ConfigToken LP = new ConfigToken(ConfigTokenType.LP, "(");
    public static final ConfigToken RP = new ConfigToken(ConfigTokenType.RP, ")");
    public static final ConfigToken OR = new ConfigToken(ConfigTokenType.OR, ")");
    public static final ConfigToken MANY = new ConfigToken(ConfigTokenType.MANY, "*");
    public static final ConfigToken SOME = new ConfigToken(ConfigTokenType.SOME, "+");
    public static final ConfigToken GRAMMAR = new ConfigToken(ConfigTokenType.GRAMMAR, "grammar");
    public static final ConfigToken RETURNS = new ConfigToken(ConfigTokenType.RETURNS, "returns");
    public static final ConfigToken COMMA = new ConfigToken(ConfigTokenType.COMMA, ",");
    public static final ConfigToken LB = new ConfigToken(ConfigTokenType.LB, "[");
    public static final ConfigToken RB = new ConfigToken(ConfigTokenType.RB, "]");
    public static ConfigToken rangeOf(final String text) {
        return new ConfigToken(ConfigTokenType.RANGE, text);
    }

    public static ConfigToken stringOf(final String text) {
        return new ConfigToken(ConfigTokenType.STRING, text);
    }

    public static ConfigToken lowerCaseIdentifierOf(final String text) {
        return new ConfigToken(ConfigTokenType.LOWER_CASE_IDENTIFIER, text);
    }

    public static ConfigToken upperCaseIdentifierOf(final String text) {
        return new ConfigToken(ConfigTokenType.UPPER_CASE_IDENTIFIER, text);
    }

    public static ConfigToken codeBlockOf(final String text) {
        return new ConfigToken(ConfigTokenType.CODE_BLOCK, text);
    }
}
