// Generated from Java.g4 by ANTLR 4.7.2
package com.github.smirnovdm2107.parser;

import java.util.*;
import java.util.stream.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JavaLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, DOUBLE_QUOTE=2, RETURN=3, POINT=4, NUM=5, TRUE=6, FALSE=7, DEC=8, 
		INC=9, MATH_SYMBOL=10, ELSE=11, IF=12, EQ=13, CLASS=14, IMPORT=15, STATIC=16, 
		LBRACE=17, RBRACE=18, LBRACKET=19, RBRACKET=20, LPARENT=21, RPARENT=22, 
		ELLIPSIS=23, COMMA=24, ID=25, SEMICOLON=26, WHITESPACE=27, NOT_DOUBLE_QUOTE=28;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "DOUBLE_QUOTE", "RETURN", "POINT", "NUM", "TRUE", "FALSE", "DEC", 
			"INC", "MATH_SYMBOL", "ELSE", "IF", "EQ", "CLASS", "IMPORT", "STATIC", 
			"LBRACE", "RBRACE", "LBRACKET", "RBRACKET", "LPARENT", "RPARENT", "ELLIPSIS", 
			"COMMA", "ID", "SEMICOLON", "WHITESPACE", "NOT_DOUBLE_QUOTE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'*'", "'\"'", "'return'", "'.'", null, "'false'", "'true'", "'--'", 
			"'++'", null, "'else'", "'if'", "'='", "'class'", "'import'", "'static'", 
			"'{'", "'}'", "'['", "']'", "'('", "')'", "'...'", "','", null, "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, "DOUBLE_QUOTE", "RETURN", "POINT", "NUM", "TRUE", "FALSE", 
			"DEC", "INC", "MATH_SYMBOL", "ELSE", "IF", "EQ", "CLASS", "IMPORT", "STATIC", 
			"LBRACE", "RBRACE", "LBRACKET", "RBRACKET", "LPARENT", "RPARENT", "ELLIPSIS", 
			"COMMA", "ID", "SEMICOLON", "WHITESPACE", "NOT_DOUBLE_QUOTE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public JavaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Java.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\36\u00a3\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\6\6J\n\6\r\6\16\6K\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3"+
		"\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3"+
		"\30\3\31\3\31\3\32\3\32\7\32\u0093\n\32\f\32\16\32\u0096\13\32\3\33\3"+
		"\33\3\34\3\34\3\34\3\34\3\35\7\35\u009f\n\35\f\35\16\35\u00a2\13\35\2"+
		"\2\36\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36"+
		"\3\2\b\3\2\62;\5\2,-//\61\61\5\2C\\aac|\6\2\62;C\\aac|\5\2\13\f\17\17"+
		"\"\"\4\2$$``\2\u00a5\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2"+
		"\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2"+
		"\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2"+
		"\2\2\29\3\2\2\2\3;\3\2\2\2\5=\3\2\2\2\7?\3\2\2\2\tF\3\2\2\2\13I\3\2\2"+
		"\2\rM\3\2\2\2\17S\3\2\2\2\21X\3\2\2\2\23[\3\2\2\2\25^\3\2\2\2\27`\3\2"+
		"\2\2\31e\3\2\2\2\33h\3\2\2\2\35j\3\2\2\2\37p\3\2\2\2!w\3\2\2\2#~\3\2\2"+
		"\2%\u0080\3\2\2\2\'\u0082\3\2\2\2)\u0084\3\2\2\2+\u0086\3\2\2\2-\u0088"+
		"\3\2\2\2/\u008a\3\2\2\2\61\u008e\3\2\2\2\63\u0090\3\2\2\2\65\u0097\3\2"+
		"\2\2\67\u0099\3\2\2\29\u00a0\3\2\2\2;<\7,\2\2<\4\3\2\2\2=>\7$\2\2>\6\3"+
		"\2\2\2?@\7t\2\2@A\7g\2\2AB\7v\2\2BC\7w\2\2CD\7t\2\2DE\7p\2\2E\b\3\2\2"+
		"\2FG\7\60\2\2G\n\3\2\2\2HJ\t\2\2\2IH\3\2\2\2JK\3\2\2\2KI\3\2\2\2KL\3\2"+
		"\2\2L\f\3\2\2\2MN\7h\2\2NO\7c\2\2OP\7n\2\2PQ\7u\2\2QR\7g\2\2R\16\3\2\2"+
		"\2ST\7v\2\2TU\7t\2\2UV\7w\2\2VW\7g\2\2W\20\3\2\2\2XY\7/\2\2YZ\7/\2\2Z"+
		"\22\3\2\2\2[\\\7-\2\2\\]\7-\2\2]\24\3\2\2\2^_\t\3\2\2_\26\3\2\2\2`a\7"+
		"g\2\2ab\7n\2\2bc\7u\2\2cd\7g\2\2d\30\3\2\2\2ef\7k\2\2fg\7h\2\2g\32\3\2"+
		"\2\2hi\7?\2\2i\34\3\2\2\2jk\7e\2\2kl\7n\2\2lm\7c\2\2mn\7u\2\2no\7u\2\2"+
		"o\36\3\2\2\2pq\7k\2\2qr\7o\2\2rs\7r\2\2st\7q\2\2tu\7t\2\2uv\7v\2\2v \3"+
		"\2\2\2wx\7u\2\2xy\7v\2\2yz\7c\2\2z{\7v\2\2{|\7k\2\2|}\7e\2\2}\"\3\2\2"+
		"\2~\177\7}\2\2\177$\3\2\2\2\u0080\u0081\7\177\2\2\u0081&\3\2\2\2\u0082"+
		"\u0083\7]\2\2\u0083(\3\2\2\2\u0084\u0085\7_\2\2\u0085*\3\2\2\2\u0086\u0087"+
		"\7*\2\2\u0087,\3\2\2\2\u0088\u0089\7+\2\2\u0089.\3\2\2\2\u008a\u008b\7"+
		"\60\2\2\u008b\u008c\7\60\2\2\u008c\u008d\7\60\2\2\u008d\60\3\2\2\2\u008e"+
		"\u008f\7.\2\2\u008f\62\3\2\2\2\u0090\u0094\t\4\2\2\u0091\u0093\t\5\2\2"+
		"\u0092\u0091\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095"+
		"\3\2\2\2\u0095\64\3\2\2\2\u0096\u0094\3\2\2\2\u0097\u0098\7=\2\2\u0098"+
		"\66\3\2\2\2\u0099\u009a\t\6\2\2\u009a\u009b\3\2\2\2\u009b\u009c\b\34\2"+
		"\2\u009c8\3\2\2\2\u009d\u009f\t\7\2\2\u009e\u009d\3\2\2\2\u009f\u00a2"+
		"\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1:\3\2\2\2\u00a2"+
		"\u00a0\3\2\2\2\6\2K\u0094\u00a0\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}