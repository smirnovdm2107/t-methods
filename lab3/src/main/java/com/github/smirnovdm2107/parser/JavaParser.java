// Generated from Java.g4 by ANTLR 4.7.2
package com.github.smirnovdm2107.parser;

import java.util.*;
import java.util.stream.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JavaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, DOUBLE_QUOTE=2, RETURN=3, POINT=4, NUM=5, TRUE=6, FALSE=7, DEC=8, 
		INC=9, MATH_SYMBOL=10, ELSE=11, IF=12, EQ=13, CLASS=14, IMPORT=15, STATIC=16, 
		LBRACE=17, RBRACE=18, LBRACKET=19, RBRACKET=20, LPARENT=21, RPARENT=22, 
		ELLIPSIS=23, COMMA=24, ID=25, SEMICOLON=26, WHITESPACE=27, NOT_DOUBLE_QUOTE=28;
	public static final int
		RULE_program = 0, RULE_importStatement = 1, RULE_importPath = 2, RULE_function = 3, 
		RULE_statementBlock = 4, RULE_functionArgs = 5, RULE_functionArg = 6, 
		RULE_varArg = 7, RULE_functionBody = 8, RULE_expression = 9, RULE_notMathExpression = 10, 
		RULE_string = 11, RULE_returnStatement = 12, RULE_math = 13, RULE_functionCall = 14, 
		RULE_statement = 15, RULE_condition = 16, RULE_assignation = 17, RULE_declaration = 18, 
		RULE_obfusedId = 19, RULE_type = 20;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "importStatement", "importPath", "function", "statementBlock", 
			"functionArgs", "functionArg", "varArg", "functionBody", "expression", 
			"notMathExpression", "string", "returnStatement", "math", "functionCall", 
			"statement", "condition", "assignation", "declaration", "obfusedId", 
			"type"
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

	@Override
	public String getGrammarFileName() { return "Java.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    final List<Map<String, String>> scopes = new ArrayList<>();
	    final StringBuilder sb = new StringBuilder();
	    final static char[] OBFUSED_ID_CHARACTERS = new char[]{'1', '0', 'I', 'O'};
	    final static char[] OBFUSED_ID_FIRST_CHARACTERS = new char[]{'I', 'O'};
	    final static String[] OBFUSING_NAMES = new String[]{
	        "ABOBA",
	        "amogus",
	        "abobus",
	        "sus",
	        "Mitrolius", "got_over", "Anna",
	        "MrWrld",
	        "viphwydon",
	        "MrHeap",
	        "paVlaDog",
	        "nikitaSGirfriendCount"
	    };
	    final static int MAX_OBFUSED_ID_LENGTH = 10;
	    final static int MAX_OBFUSCATIONS = 4;
	    final static int MAX_USELESS_STATEMENTS = 10;
	    final static String TAB = "    ";
	    final static String[] MATH_OPS = new String[] { "*" , "/", "+", "-" };

	    void newScope() {
	        scopes.add(new HashMap());
	    }

	    void endScope() {
	        scopes.remove(scopes.size() - 1);
	    }

	    void addLine(final String line) {
	        sb.append(format(line)).append(System.lineSeparator());
	    }

	    void add(final String string) {
	        sb.append(string);
	    }

	    void newLine() {
	        sb.append(System.lineSeparator());
	    }

	    String addObfused(final String key) {
	        final int maxIndex = scopes.size() - 1;
	        int index = maxIndex;
	        while (index > 0) {
	            final String value = scopes.get(index).get(key);
	            if (value != null) {
	                return value;
	            }
	            index--;
	        }
	        final String name = randomName();
	        scopes.get(maxIndex).put(key, name);
	        return name;
	    }

	    void addPure(final String key) {
	        scopes.get(scopes.size() - 1).put(key, key);
	    }

	    String obfuseId(final String key) {
	        final String obfused = randomName();
	        scopes.get(scopes.size() - 1).put(key, obfused);
	        return obfused;
	    }

	    void obfuscate() {
	        final Random random = new Random();
	        for (int i = 0; i < random.nextInt(MAX_OBFUSCATIONS); i++) {
	            if (Math.random() > 0.5) {
	                ifBlock();
	            } else {
	                uselessScope();
	            }
	        }
	    }

	    void ifBlock() {
	        final String value = Math.random() > 0.5 ? "false" : "true";
	        uselessScope("if (" + value + ") ");
	    }

	    void uselessScope() {
	        uselessScope("");
	    }

	    void uselessScope(final String prefix) {
	        addLine(prefix + "{");
	        newScope();
	        uselessStatements();
	        endScope();
	        addLine("}");
	    }

	    void uselessStatements() {
	        final int maxStatementParts = 10;
	        final Random random = new Random();
	        final List<String> scope = new ArrayList<>();
	        for (int i = 0; i <= random.nextInt(MAX_USELESS_STATEMENTS); i++) {
	            final StringBuilder line = new StringBuilder();
	            final String name = OBFUSING_NAMES[random.nextInt(OBFUSING_NAMES.length)];
	            if (!scope.contains(name)) {
	                line.append("int ");
	            }
	            line.append(name)
	                .append(" = ");

	            final List<String> expr = random.ints(random.nextInt(maxStatementParts) + 1, 0, 1 + scope.size())
	                        .mapToObj(x -> x % (maxStatementParts + 1) == 0
	                        ? String.valueOf(random.nextInt(100) + 1)
	                         : scope.get(x - 1))
	                        .toList();
	            for (int j = 0; j < expr.size() - 2; j++) {
	                line.append(expr.get(j))
	                    .append(" ")
	                    .append(MATH_OPS[random.nextInt(MATH_OPS.length)])
	                    .append(" ");
	            }
	            line.append(expr.get(expr.size() - 1))
	                .append(";");
	            addLine(line.toString());
	            if (!scope.contains(name)) {
	                scope.add(name);
	            }
	        }
	    }


	    String format(final String string) {
	        final StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < scopes.size(); i++) {
	            sb.append(TAB);
	        }
	        return sb.append(string).toString();
	    }

	    String randomName() {
	        final Random random = new Random();
	        String name = null;
	        while (name == null) {
	            final int length = random.nextInt(MAX_OBFUSED_ID_LENGTH) + 1;
	            final int first_idx = random.nextInt(OBFUSED_ID_FIRST_CHARACTERS.length);
	            final String first = String.valueOf(OBFUSED_ID_FIRST_CHARACTERS[first_idx]);
	            final String rest = random.ints(length - 1, 0, OBFUSED_ID_CHARACTERS.length)
	                                        .mapToObj(i -> String.valueOf(OBFUSED_ID_CHARACTERS[i]))
	                                        .collect(Collectors.joining(""));
	            name = first + rest;
	        }
	        return name;
	    }



	public JavaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public String res;
		public Token ID;
		public TerminalNode CLASS() { return getToken(JavaParser.CLASS, 0); }
		public TerminalNode ID() { return getToken(JavaParser.ID, 0); }
		public TerminalNode LBRACE() { return getToken(JavaParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(JavaParser.RBRACE, 0); }
		public TerminalNode EOF() { return getToken(JavaParser.EOF, 0); }
		public List<ImportStatementContext> importStatement() {
			return getRuleContexts(ImportStatementContext.class);
		}
		public ImportStatementContext importStatement(int i) {
			return getRuleContext(ImportStatementContext.class,i);
		}
		public List<FunctionContext> function() {
			return getRuleContexts(FunctionContext.class);
		}
		public FunctionContext function(int i) {
			return getRuleContext(FunctionContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(42);
				importStatement();
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(48);
			match(CLASS);
			setState(49);
			((ProgramContext)_localctx).ID = match(ID);
			setState(50);
			match(LBRACE);

			        addLine("class " + ((ProgramContext)_localctx).ID.getText() +" {");
			        newScope();
			      
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(52);
				function();
				}
				}
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(58);
			match(RBRACE);
			setState(59);
			match(EOF);

			        endScope();
			        addLine("}");
			        ((ProgramContext)_localctx).res =  sb.toString();
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportStatementContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(JavaParser.IMPORT, 0); }
		public ImportPathContext importPath() {
			return getRuleContext(ImportPathContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(JavaParser.SEMICOLON, 0); }
		public TerminalNode STATIC() { return getToken(JavaParser.STATIC, 0); }
		public ImportStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importStatement; }
	}

	public final ImportStatementContext importStatement() throws RecognitionException {
		ImportStatementContext _localctx = new ImportStatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(IMPORT);
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STATIC) {
				{
				setState(63);
				match(STATIC);
				}
			}

			setState(66);
			importPath();
			setState(67);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportPathContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(JavaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JavaParser.ID, i);
		}
		public List<TerminalNode> POINT() { return getTokens(JavaParser.POINT); }
		public TerminalNode POINT(int i) {
			return getToken(JavaParser.POINT, i);
		}
		public ImportPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importPath; }
	}

	public final ImportPathContext importPath() throws RecognitionException {
		ImportPathContext _localctx = new ImportPathContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_importPath);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(ID);
			setState(74);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(70);
					match(POINT);
					setState(71);
					match(ID);
					}
					} 
				}
				setState(76);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==POINT) {
				{
				setState(77);
				match(POINT);
				setState(78);
				match(T__0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public TypeContext type;
		public Token id;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LPARENT() { return getToken(JavaParser.LPARENT, 0); }
		public FunctionArgsContext functionArgs() {
			return getRuleContext(FunctionArgsContext.class,0);
		}
		public TerminalNode RPARENT() { return getToken(JavaParser.RPARENT, 0); }
		public TerminalNode LBRACE() { return getToken(JavaParser.LBRACE, 0); }
		public FunctionBodyContext functionBody() {
			return getRuleContext(FunctionBodyContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(JavaParser.RBRACE, 0); }
		public TerminalNode ID() { return getToken(JavaParser.ID, 0); }
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_function);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			((FunctionContext)_localctx).type = type();
			setState(82);
			((FunctionContext)_localctx).id = match(ID);
			setState(83);
			match(LPARENT);

			        add(format(((FunctionContext)_localctx).type.name));
			        add(" ");
			        add((((FunctionContext)_localctx).id!=null?((FunctionContext)_localctx).id.getText():null));
			        newScope();
			        add("(");
			    
			setState(85);
			functionArgs();
			setState(86);
			match(RPARENT);
			setState(87);
			match(LBRACE);

			        add(") {");
			        newLine();
			    
			setState(89);
			functionBody();
			setState(90);
			match(RBRACE);

			        endScope();
			        addLine("}");
			        newLine();
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementBlockContext extends ParserRuleContext {
		public String prefix;
		public TerminalNode LBRACE() { return getToken(JavaParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(JavaParser.RBRACE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementBlockContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public StatementBlockContext(ParserRuleContext parent, int invokingState, String prefix) {
			super(parent, invokingState);
			this.prefix = prefix;
		}
		@Override public int getRuleIndex() { return RULE_statementBlock; }
	}

	public final StatementBlockContext statementBlock(String prefix) throws RecognitionException {
		StatementBlockContext _localctx = new StatementBlockContext(_ctx, getState(), prefix);
		enterRule(_localctx, 8, RULE_statementBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(LBRACE);

			        addLine(_localctx.prefix + "{");
			        newScope();
			    
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RETURN) | (1L << IF) | (1L << LBRACE) | (1L << ID))) != 0)) {
				{
				{
				setState(95);
				statement();
				}
				}
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(101);
			match(RBRACE);

			        endScope();
			        addLine("}");
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionArgsContext extends ParserRuleContext {
		public VarArgContext varg;
		public FunctionArgContext farg;
		public VarArgContext varArg() {
			return getRuleContext(VarArgContext.class,0);
		}
		public List<FunctionArgContext> functionArg() {
			return getRuleContexts(FunctionArgContext.class);
		}
		public FunctionArgContext functionArg(int i) {
			return getRuleContext(FunctionArgContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaParser.COMMA, i);
		}
		public FunctionArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionArgs; }
	}

	public final FunctionArgsContext functionArgs() throws RecognitionException {
		FunctionArgsContext _localctx = new FunctionArgsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_functionArgs);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(104);
				((FunctionArgsContext)_localctx).varg = varArg();
				add(((FunctionArgsContext)_localctx).varg.arg);
				}
				break;
			case 2:
				{
				setState(107);
				((FunctionArgsContext)_localctx).farg = functionArg();
				add(((FunctionArgsContext)_localctx).farg.arg);
				setState(115);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(109);
						match(COMMA);
						setState(110);
						((FunctionArgsContext)_localctx).farg = functionArg();

						        add(", ");
						        add(((FunctionArgsContext)_localctx).farg.arg);
						     
						}
						} 
					}
					setState(117);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				}
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(118);
					match(COMMA);
					setState(119);
					((FunctionArgsContext)_localctx).varg = varArg();

					        add(", ");
					        add(((FunctionArgsContext)_localctx).varg.arg);
					}
				}

				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionArgContext extends ParserRuleContext {
		public String arg;
		public TypeContext type;
		public ObfusedIdContext obfusedId;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ObfusedIdContext obfusedId() {
			return getRuleContext(ObfusedIdContext.class,0);
		}
		public FunctionArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionArg; }
	}

	public final FunctionArgContext functionArg() throws RecognitionException {
		FunctionArgContext _localctx = new FunctionArgContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_functionArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			((FunctionArgContext)_localctx).type = type();
			setState(127);
			((FunctionArgContext)_localctx).obfusedId = obfusedId();
			((FunctionArgContext)_localctx).arg =  ((FunctionArgContext)_localctx).type.name + " " + ((FunctionArgContext)_localctx).obfusedId.name;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarArgContext extends ParserRuleContext {
		public String arg;
		public TypeContext type;
		public ObfusedIdContext obfusedId;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ELLIPSIS() { return getToken(JavaParser.ELLIPSIS, 0); }
		public ObfusedIdContext obfusedId() {
			return getRuleContext(ObfusedIdContext.class,0);
		}
		public VarArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varArg; }
	}

	public final VarArgContext varArg() throws RecognitionException {
		VarArgContext _localctx = new VarArgContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_varArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			((VarArgContext)_localctx).type = type();
			setState(131);
			match(ELLIPSIS);
			setState(132);
			((VarArgContext)_localctx).obfusedId = obfusedId();
			((VarArgContext)_localctx).arg =  ((VarArgContext)_localctx).type.name + "... " + ((VarArgContext)_localctx).obfusedId.name;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionBodyContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public FunctionBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionBody; }
	}

	public final FunctionBodyContext functionBody() throws RecognitionException {
		FunctionBodyContext _localctx = new FunctionBodyContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_functionBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RETURN) | (1L << IF) | (1L << LBRACE) | (1L << ID))) != 0)) {
				{
				{
				obfuscate();
				setState(136);
				statement();
				{
				obfuscate();
				}
				}
				}
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public String text;
		public MathContext math;
		public NotMathExpressionContext nme;
		public MathContext math() {
			return getRuleContext(MathContext.class,0);
		}
		public NotMathExpressionContext notMathExpression() {
			return getRuleContext(NotMathExpressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_expression);
		try {
			setState(150);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(144);
				((ExpressionContext)_localctx).math = math();
				((ExpressionContext)_localctx).text =  ((ExpressionContext)_localctx).math.text;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(147);
				((ExpressionContext)_localctx).nme = notMathExpression();
				((ExpressionContext)_localctx).text =  ((ExpressionContext)_localctx).nme.text;
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotMathExpressionContext extends ParserRuleContext {
		public String text;
		public StringContext string;
		public ExpressionContext expression;
		public Token NUM;
		public Token TRUE;
		public Token FALSE;
		public ObfusedIdContext obfusedId;
		public AssignationContext assignation;
		public FunctionCallContext functionCall;
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode LPARENT() { return getToken(JavaParser.LPARENT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPARENT() { return getToken(JavaParser.RPARENT, 0); }
		public TerminalNode NUM() { return getToken(JavaParser.NUM, 0); }
		public TerminalNode TRUE() { return getToken(JavaParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(JavaParser.FALSE, 0); }
		public ObfusedIdContext obfusedId() {
			return getRuleContext(ObfusedIdContext.class,0);
		}
		public AssignationContext assignation() {
			return getRuleContext(AssignationContext.class,0);
		}
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public NotMathExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notMathExpression; }
	}

	public final NotMathExpressionContext notMathExpression() throws RecognitionException {
		NotMathExpressionContext _localctx = new NotMathExpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_notMathExpression);
		try {
			setState(175);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(152);
				((NotMathExpressionContext)_localctx).string = string();
				((NotMathExpressionContext)_localctx).text =  ((NotMathExpressionContext)_localctx).string.text;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(155);
				match(LPARENT);
				setState(156);
				((NotMathExpressionContext)_localctx).expression = expression();
				setState(157);
				match(RPARENT);
				((NotMathExpressionContext)_localctx).text =  "(" + ((NotMathExpressionContext)_localctx).expression.text + ")";
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(160);
				((NotMathExpressionContext)_localctx).NUM = match(NUM);
				((NotMathExpressionContext)_localctx).text =  (((NotMathExpressionContext)_localctx).NUM!=null?((NotMathExpressionContext)_localctx).NUM.getText():null);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(162);
				((NotMathExpressionContext)_localctx).TRUE = match(TRUE);
				((NotMathExpressionContext)_localctx).text =  (((NotMathExpressionContext)_localctx).TRUE!=null?((NotMathExpressionContext)_localctx).TRUE.getText():null);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(164);
				((NotMathExpressionContext)_localctx).FALSE = match(FALSE);
				((NotMathExpressionContext)_localctx).text =  (((NotMathExpressionContext)_localctx).FALSE!=null?((NotMathExpressionContext)_localctx).FALSE.getText():null);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(166);
				((NotMathExpressionContext)_localctx).obfusedId = obfusedId();
				((NotMathExpressionContext)_localctx).text =  ((NotMathExpressionContext)_localctx).obfusedId.name;
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(169);
				((NotMathExpressionContext)_localctx).assignation = assignation();
				((NotMathExpressionContext)_localctx).text =  (((NotMathExpressionContext)_localctx).assignation!=null?_input.getText(((NotMathExpressionContext)_localctx).assignation.start,((NotMathExpressionContext)_localctx).assignation.stop):null);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(172);
				((NotMathExpressionContext)_localctx).functionCall = functionCall();
				((NotMathExpressionContext)_localctx).text =  ((NotMathExpressionContext)_localctx).functionCall.text;
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public String text;
		public Token ndq;
		public List<TerminalNode> DOUBLE_QUOTE() { return getTokens(JavaParser.DOUBLE_QUOTE); }
		public TerminalNode DOUBLE_QUOTE(int i) {
			return getToken(JavaParser.DOUBLE_QUOTE, i);
		}
		public TerminalNode NOT_DOUBLE_QUOTE() { return getToken(JavaParser.NOT_DOUBLE_QUOTE, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			match(DOUBLE_QUOTE);
			setState(178);
			((StringContext)_localctx).ndq = match(NOT_DOUBLE_QUOTE);
			setState(179);
			match(DOUBLE_QUOTE);
			((StringContext)_localctx).text =  "\"" + (((StringContext)_localctx).ndq!=null?((StringContext)_localctx).ndq.getText():null) + "\"";
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public ExpressionContext expression;
		public TerminalNode RETURN() { return getToken(JavaParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(JavaParser.SEMICOLON, 0); }
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(RETURN);
			setState(183);
			((ReturnStatementContext)_localctx).expression = expression();
			setState(184);
			match(SEMICOLON);
			addLine("return " + ((ReturnStatementContext)_localctx).expression.text + ";");
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MathContext extends ParserRuleContext {
		public String text;
		public StringBuilder tmp = new StringBuilder();
		public NotMathExpressionContext nme;
		public Token ms;
		public ExpressionContext expression;
		public NotMathExpressionContext notMathExpression() {
			return getRuleContext(NotMathExpressionContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> MATH_SYMBOL() { return getTokens(JavaParser.MATH_SYMBOL); }
		public TerminalNode MATH_SYMBOL(int i) {
			return getToken(JavaParser.MATH_SYMBOL, i);
		}
		public MathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_math; }
	}

	public final MathContext math() throws RecognitionException {
		MathContext _localctx = new MathContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_math);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			((MathContext)_localctx).nme = notMathExpression();
			_localctx.tmp.append(((MathContext)_localctx).nme.text);
			setState(195);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(189);
					((MathContext)_localctx).ms = match(MATH_SYMBOL);
					setState(190);
					((MathContext)_localctx).expression = expression();
					_localctx.tmp.append((((MathContext)_localctx).ms!=null?((MathContext)_localctx).ms.getText():null)); _localctx.tmp.append(((MathContext)_localctx).expression.text);
					}
					} 
				}
				setState(197);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			((MathContext)_localctx).text =  _localctx.tmp.toString();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionCallContext extends ParserRuleContext {
		public String text;
		public StringBuilder tmp = new StringBuilder();
		public Token ID;
		public Token id;
		public ExpressionContext expression;
		public List<TerminalNode> ID() { return getTokens(JavaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JavaParser.ID, i);
		}
		public TerminalNode LPARENT() { return getToken(JavaParser.LPARENT, 0); }
		public TerminalNode RPARENT() { return getToken(JavaParser.RPARENT, 0); }
		public List<TerminalNode> POINT() { return getTokens(JavaParser.POINT); }
		public TerminalNode POINT(int i) {
			return getToken(JavaParser.POINT, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaParser.COMMA, i);
		}
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_functionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			((FunctionCallContext)_localctx).ID = match(ID);
			_localctx.tmp.append((((FunctionCallContext)_localctx).ID!=null?((FunctionCallContext)_localctx).ID.getText():null));
			setState(207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==POINT) {
				{
				{
				setState(202);
				match(POINT);
				setState(203);
				((FunctionCallContext)_localctx).id = match(ID);
				_localctx.tmp.append(".").append((((FunctionCallContext)_localctx).id!=null?((FunctionCallContext)_localctx).id.getText():null));
				}
				}
				setState(209);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(210);
			match(LPARENT);
			_localctx.tmp.append("(");
			setState(223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DOUBLE_QUOTE) | (1L << NUM) | (1L << TRUE) | (1L << FALSE) | (1L << LPARENT) | (1L << ID))) != 0)) {
				{
				setState(212);
				((FunctionCallContext)_localctx).expression = expression();
				_localctx.tmp.append(((FunctionCallContext)_localctx).expression.text);
				setState(220);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(214);
					match(COMMA);
					setState(215);
					((FunctionCallContext)_localctx).expression = expression();
					_localctx.tmp.append(", ").append(((FunctionCallContext)_localctx).expression.text);
					}
					}
					setState(222);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(225);
			match(RPARENT);

			            _localctx.tmp.append(")");
			            ((FunctionCallContext)_localctx).text =  _localctx.tmp.toString();
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public FunctionCallContext functionCall;
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public AssignationContext assignation() {
			return getRuleContext(AssignationContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(JavaParser.SEMICOLON, 0); }
		public StatementBlockContext statementBlock() {
			return getRuleContext(StatementBlockContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_statement);
		try {
			setState(237);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(228);
				condition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(229);
				returnStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(230);
				assignation();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(231);
				declaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(232);
				((StatementContext)_localctx).functionCall = functionCall();
				setState(233);
				match(SEMICOLON);
				addLine(((StatementContext)_localctx).functionCall.text);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(236);
				statementBlock("");
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public String prefix;
		public ExpressionContext e;
		public TerminalNode IF() { return getToken(JavaParser.IF, 0); }
		public TerminalNode LPARENT() { return getToken(JavaParser.LPARENT, 0); }
		public TerminalNode RPARENT() { return getToken(JavaParser.RPARENT, 0); }
		public List<StatementBlockContext> statementBlock() {
			return getRuleContexts(StatementBlockContext.class);
		}
		public StatementBlockContext statementBlock(int i) {
			return getRuleContext(StatementBlockContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ELSE() { return getToken(JavaParser.ELSE, 0); }
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_condition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(IF);
			setState(240);
			match(LPARENT);
			setState(241);
			((ConditionContext)_localctx).e = expression();
			setState(242);
			match(RPARENT);
			((ConditionContext)_localctx).prefix =  "if (" + ((ConditionContext)_localctx).e.text + ") "; 
			setState(244);
			statementBlock(_localctx.prefix);
			setState(248);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(245);
				match(ELSE);
				((ConditionContext)_localctx).prefix =  "else ";
				setState(247);
				statementBlock(_localctx.prefix);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignationContext extends ParserRuleContext {
		public StringBuilder tmp = new StringBuilder();
		public ObfusedIdContext obfusedId;
		public ExpressionContext expression;
		public ObfusedIdContext obfusedId() {
			return getRuleContext(ObfusedIdContext.class,0);
		}
		public TerminalNode EQ() { return getToken(JavaParser.EQ, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(JavaParser.SEMICOLON, 0); }
		public AssignationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignation; }
	}

	public final AssignationContext assignation() throws RecognitionException {
		AssignationContext _localctx = new AssignationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_assignation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			((AssignationContext)_localctx).obfusedId = obfusedId();
			setState(251);
			match(EQ);
			setState(252);
			((AssignationContext)_localctx).expression = expression();
			setState(253);
			match(SEMICOLON);

			        _localctx.tmp.append(((AssignationContext)_localctx).obfusedId.name + " = " + ((AssignationContext)_localctx).expression.text + ";");
			        addLine(_localctx.tmp.toString());
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public StringBuilder tmp = new StringBuilder();
		public TypeContext type;
		public ObfusedIdContext obfusedId;
		public ExpressionContext expression;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ObfusedIdContext obfusedId() {
			return getRuleContext(ObfusedIdContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(JavaParser.SEMICOLON, 0); }
		public TerminalNode EQ() { return getToken(JavaParser.EQ, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			((DeclarationContext)_localctx).type = type();
			setState(257);
			((DeclarationContext)_localctx).obfusedId = obfusedId();
			_localctx.tmp.append(((DeclarationContext)_localctx).type.name + " " + ((DeclarationContext)_localctx).obfusedId.name + " = ");
			setState(263);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQ) {
				{
				setState(259);
				match(EQ);
				setState(260);
				((DeclarationContext)_localctx).expression = expression();
				_localctx.tmp.append(((DeclarationContext)_localctx).expression.text);
				}
			}

			setState(265);
			match(SEMICOLON);

			        _localctx.tmp.append(";");
			        addLine(_localctx.tmp.toString());
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObfusedIdContext extends ParserRuleContext {
		public String name;
		public Token ID;
		public TerminalNode ID() { return getToken(JavaParser.ID, 0); }
		public ObfusedIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_obfusedId; }
	}

	public final ObfusedIdContext obfusedId() throws RecognitionException {
		ObfusedIdContext _localctx = new ObfusedIdContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_obfusedId);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			((ObfusedIdContext)_localctx).ID = match(ID);
			((ObfusedIdContext)_localctx).name =  addObfused((((ObfusedIdContext)_localctx).ID!=null?((ObfusedIdContext)_localctx).ID.getText():null));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public String name;
		public StringBuilder cur = new StringBuilder();
		public Token ID;
		public TerminalNode ID() { return getToken(JavaParser.ID, 0); }
		public List<TerminalNode> LBRACKET() { return getTokens(JavaParser.LBRACKET); }
		public TerminalNode LBRACKET(int i) {
			return getToken(JavaParser.LBRACKET, i);
		}
		public List<TerminalNode> RBRACKET() { return getTokens(JavaParser.RBRACKET); }
		public TerminalNode RBRACKET(int i) {
			return getToken(JavaParser.RBRACKET, i);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			((TypeContext)_localctx).ID = match(ID);
			_localctx.cur.append((((TypeContext)_localctx).ID!=null?((TypeContext)_localctx).ID.getText():null));
			setState(278);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACKET) {
				{
				{
				setState(273);
				match(LBRACKET);
				setState(274);
				match(RBRACKET);
				_localctx.cur.append("[]");
				}
				}
				setState(280);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			((TypeContext)_localctx).name =  _localctx.cur.toString();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\36\u011e\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\7\2.\n\2\f\2\16\2\61\13\2"+
		"\3\2\3\2\3\2\3\2\3\2\7\28\n\2\f\2\16\2;\13\2\3\2\3\2\3\2\3\2\3\3\3\3\5"+
		"\3C\n\3\3\3\3\3\3\3\3\4\3\4\3\4\7\4K\n\4\f\4\16\4N\13\4\3\4\3\4\5\4R\n"+
		"\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\7\6c\n"+
		"\6\f\6\16\6f\13\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7"+
		"t\n\7\f\7\16\7w\13\7\3\7\3\7\3\7\3\7\5\7}\n\7\5\7\177\n\7\3\b\3\b\3\b"+
		"\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\7\n\u008e\n\n\f\n\16\n\u0091"+
		"\13\n\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u0099\n\13\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\5\f\u00b2\n\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\7\17\u00c4\n\17\f\17\16\17\u00c7\13\17\3\17\3"+
		"\17\3\20\3\20\3\20\3\20\3\20\7\20\u00d0\n\20\f\20\16\20\u00d3\13\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u00dd\n\20\f\20\16\20\u00e0"+
		"\13\20\5\20\u00e2\n\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\5\21\u00f0\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\5\22\u00fb\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\5\24\u010a\n\24\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26"+
		"\3\26\3\26\3\26\7\26\u0117\n\26\f\26\16\26\u011a\13\26\3\26\3\26\3\26"+
		"\2\2\27\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*\2\2\2\u0126\2/\3"+
		"\2\2\2\4@\3\2\2\2\6G\3\2\2\2\bS\3\2\2\2\n_\3\2\2\2\f~\3\2\2\2\16\u0080"+
		"\3\2\2\2\20\u0084\3\2\2\2\22\u008f\3\2\2\2\24\u0098\3\2\2\2\26\u00b1\3"+
		"\2\2\2\30\u00b3\3\2\2\2\32\u00b8\3\2\2\2\34\u00bd\3\2\2\2\36\u00ca\3\2"+
		"\2\2 \u00ef\3\2\2\2\"\u00f1\3\2\2\2$\u00fc\3\2\2\2&\u0102\3\2\2\2(\u010e"+
		"\3\2\2\2*\u0111\3\2\2\2,.\5\4\3\2-,\3\2\2\2.\61\3\2\2\2/-\3\2\2\2/\60"+
		"\3\2\2\2\60\62\3\2\2\2\61/\3\2\2\2\62\63\7\20\2\2\63\64\7\33\2\2\64\65"+
		"\7\23\2\2\659\b\2\1\2\668\5\b\5\2\67\66\3\2\2\28;\3\2\2\29\67\3\2\2\2"+
		"9:\3\2\2\2:<\3\2\2\2;9\3\2\2\2<=\7\24\2\2=>\7\2\2\3>?\b\2\1\2?\3\3\2\2"+
		"\2@B\7\21\2\2AC\7\22\2\2BA\3\2\2\2BC\3\2\2\2CD\3\2\2\2DE\5\6\4\2EF\7\34"+
		"\2\2F\5\3\2\2\2GL\7\33\2\2HI\7\6\2\2IK\7\33\2\2JH\3\2\2\2KN\3\2\2\2LJ"+
		"\3\2\2\2LM\3\2\2\2MQ\3\2\2\2NL\3\2\2\2OP\7\6\2\2PR\7\3\2\2QO\3\2\2\2Q"+
		"R\3\2\2\2R\7\3\2\2\2ST\5*\26\2TU\7\33\2\2UV\7\27\2\2VW\b\5\1\2WX\5\f\7"+
		"\2XY\7\30\2\2YZ\7\23\2\2Z[\b\5\1\2[\\\5\22\n\2\\]\7\24\2\2]^\b\5\1\2^"+
		"\t\3\2\2\2_`\7\23\2\2`d\b\6\1\2ac\5 \21\2ba\3\2\2\2cf\3\2\2\2db\3\2\2"+
		"\2de\3\2\2\2eg\3\2\2\2fd\3\2\2\2gh\7\24\2\2hi\b\6\1\2i\13\3\2\2\2jk\5"+
		"\20\t\2kl\b\7\1\2l\177\3\2\2\2mn\5\16\b\2nu\b\7\1\2op\7\32\2\2pq\5\16"+
		"\b\2qr\b\7\1\2rt\3\2\2\2so\3\2\2\2tw\3\2\2\2us\3\2\2\2uv\3\2\2\2v|\3\2"+
		"\2\2wu\3\2\2\2xy\7\32\2\2yz\5\20\t\2z{\b\7\1\2{}\3\2\2\2|x\3\2\2\2|}\3"+
		"\2\2\2}\177\3\2\2\2~j\3\2\2\2~m\3\2\2\2\177\r\3\2\2\2\u0080\u0081\5*\26"+
		"\2\u0081\u0082\5(\25\2\u0082\u0083\b\b\1\2\u0083\17\3\2\2\2\u0084\u0085"+
		"\5*\26\2\u0085\u0086\7\31\2\2\u0086\u0087\5(\25\2\u0087\u0088\b\t\1\2"+
		"\u0088\21\3\2\2\2\u0089\u008a\b\n\1\2\u008a\u008b\5 \21\2\u008b\u008c"+
		"\b\n\1\2\u008c\u008e\3\2\2\2\u008d\u0089\3\2\2\2\u008e\u0091\3\2\2\2\u008f"+
		"\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\23\3\2\2\2\u0091\u008f\3\2\2"+
		"\2\u0092\u0093\5\34\17\2\u0093\u0094\b\13\1\2\u0094\u0099\3\2\2\2\u0095"+
		"\u0096\5\26\f\2\u0096\u0097\b\13\1\2\u0097\u0099\3\2\2\2\u0098\u0092\3"+
		"\2\2\2\u0098\u0095\3\2\2\2\u0099\25\3\2\2\2\u009a\u009b\5\30\r\2\u009b"+
		"\u009c\b\f\1\2\u009c\u00b2\3\2\2\2\u009d\u009e\7\27\2\2\u009e\u009f\5"+
		"\24\13\2\u009f\u00a0\7\30\2\2\u00a0\u00a1\b\f\1\2\u00a1\u00b2\3\2\2\2"+
		"\u00a2\u00a3\7\7\2\2\u00a3\u00b2\b\f\1\2\u00a4\u00a5\7\b\2\2\u00a5\u00b2"+
		"\b\f\1\2\u00a6\u00a7\7\t\2\2\u00a7\u00b2\b\f\1\2\u00a8\u00a9\5(\25\2\u00a9"+
		"\u00aa\b\f\1\2\u00aa\u00b2\3\2\2\2\u00ab\u00ac\5$\23\2\u00ac\u00ad\b\f"+
		"\1\2\u00ad\u00b2\3\2\2\2\u00ae\u00af\5\36\20\2\u00af\u00b0\b\f\1\2\u00b0"+
		"\u00b2\3\2\2\2\u00b1\u009a\3\2\2\2\u00b1\u009d\3\2\2\2\u00b1\u00a2\3\2"+
		"\2\2\u00b1\u00a4\3\2\2\2\u00b1\u00a6\3\2\2\2\u00b1\u00a8\3\2\2\2\u00b1"+
		"\u00ab\3\2\2\2\u00b1\u00ae\3\2\2\2\u00b2\27\3\2\2\2\u00b3\u00b4\7\4\2"+
		"\2\u00b4\u00b5\7\36\2\2\u00b5\u00b6\7\4\2\2\u00b6\u00b7\b\r\1\2\u00b7"+
		"\31\3\2\2\2\u00b8\u00b9\7\5\2\2\u00b9\u00ba\5\24\13\2\u00ba\u00bb\7\34"+
		"\2\2\u00bb\u00bc\b\16\1\2\u00bc\33\3\2\2\2\u00bd\u00be\5\26\f\2\u00be"+
		"\u00c5\b\17\1\2\u00bf\u00c0\7\f\2\2\u00c0\u00c1\5\24\13\2\u00c1\u00c2"+
		"\b\17\1\2\u00c2\u00c4\3\2\2\2\u00c3\u00bf\3\2\2\2\u00c4\u00c7\3\2\2\2"+
		"\u00c5\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c8\3\2\2\2\u00c7\u00c5"+
		"\3\2\2\2\u00c8\u00c9\b\17\1\2\u00c9\35\3\2\2\2\u00ca\u00cb\7\33\2\2\u00cb"+
		"\u00d1\b\20\1\2\u00cc\u00cd\7\6\2\2\u00cd\u00ce\7\33\2\2\u00ce\u00d0\b"+
		"\20\1\2\u00cf\u00cc\3\2\2\2\u00d0\u00d3\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1"+
		"\u00d2\3\2\2\2\u00d2\u00d4\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d4\u00d5\7\27"+
		"\2\2\u00d5\u00e1\b\20\1\2\u00d6\u00d7\5\24\13\2\u00d7\u00de\b\20\1\2\u00d8"+
		"\u00d9\7\32\2\2\u00d9\u00da\5\24\13\2\u00da\u00db\b\20\1\2\u00db\u00dd"+
		"\3\2\2\2\u00dc\u00d8\3\2\2\2\u00dd\u00e0\3\2\2\2\u00de\u00dc\3\2\2\2\u00de"+
		"\u00df\3\2\2\2\u00df\u00e2\3\2\2\2\u00e0\u00de\3\2\2\2\u00e1\u00d6\3\2"+
		"\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4\7\30\2\2\u00e4"+
		"\u00e5\b\20\1\2\u00e5\37\3\2\2\2\u00e6\u00f0\5\"\22\2\u00e7\u00f0\5\32"+
		"\16\2\u00e8\u00f0\5$\23\2\u00e9\u00f0\5&\24\2\u00ea\u00eb\5\36\20\2\u00eb"+
		"\u00ec\7\34\2\2\u00ec\u00ed\b\21\1\2\u00ed\u00f0\3\2\2\2\u00ee\u00f0\5"+
		"\n\6\2\u00ef\u00e6\3\2\2\2\u00ef\u00e7\3\2\2\2\u00ef\u00e8\3\2\2\2\u00ef"+
		"\u00e9\3\2\2\2\u00ef\u00ea\3\2\2\2\u00ef\u00ee\3\2\2\2\u00f0!\3\2\2\2"+
		"\u00f1\u00f2\7\16\2\2\u00f2\u00f3\7\27\2\2\u00f3\u00f4\5\24\13\2\u00f4"+
		"\u00f5\7\30\2\2\u00f5\u00f6\b\22\1\2\u00f6\u00fa\5\n\6\2\u00f7\u00f8\7"+
		"\r\2\2\u00f8\u00f9\b\22\1\2\u00f9\u00fb\5\n\6\2\u00fa\u00f7\3\2\2\2\u00fa"+
		"\u00fb\3\2\2\2\u00fb#\3\2\2\2\u00fc\u00fd\5(\25\2\u00fd\u00fe\7\17\2\2"+
		"\u00fe\u00ff\5\24\13\2\u00ff\u0100\7\34\2\2\u0100\u0101\b\23\1\2\u0101"+
		"%\3\2\2\2\u0102\u0103\5*\26\2\u0103\u0104\5(\25\2\u0104\u0109\b\24\1\2"+
		"\u0105\u0106\7\17\2\2\u0106\u0107\5\24\13\2\u0107\u0108\b\24\1\2\u0108"+
		"\u010a\3\2\2\2\u0109\u0105\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010b\3\2"+
		"\2\2\u010b\u010c\7\34\2\2\u010c\u010d\b\24\1\2\u010d\'\3\2\2\2\u010e\u010f"+
		"\7\33\2\2\u010f\u0110\b\25\1\2\u0110)\3\2\2\2\u0111\u0112\7\33\2\2\u0112"+
		"\u0118\b\26\1\2\u0113\u0114\7\25\2\2\u0114\u0115\7\26\2\2\u0115\u0117"+
		"\b\26\1\2\u0116\u0113\3\2\2\2\u0117\u011a\3\2\2\2\u0118\u0116\3\2\2\2"+
		"\u0118\u0119\3\2\2\2\u0119\u011b\3\2\2\2\u011a\u0118\3\2\2\2\u011b\u011c"+
		"\b\26\1\2\u011c+\3\2\2\2\26/9BLQdu|~\u008f\u0098\u00b1\u00c5\u00d1\u00de"+
		"\u00e1\u00ef\u00fa\u0109\u0118";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}