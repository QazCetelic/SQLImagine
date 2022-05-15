// Generated from Grammar.g4 by ANTLR 4.9.3
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, SPACING=6, COMMENT=7, STRING=8, 
		INTEGER=9, DECIMAL=10, DATE=11, BOOL=12, NULLABLE=13, TABLE_NAME=14, REFERENCED_ATTRIBUTE_NAME=15, 
		ATTRIBUTE_NAME=16, PRIMARY_KEY_OPERATOR=17, REFERENCED_OPERATOR=18;
	public static final int
		RULE_type = 0, RULE_type_declare = 1, RULE_reference_declare = 2, RULE_attribute_declare = 3, 
		RULE_table_declare = 4, RULE_main = 5;
	private static String[] makeRuleNames() {
		return new String[] {
			"type", "type_declare", "reference_declare", "attribute_declare", "table_declare", 
			"main"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "','", "':'", "'\n'", null, null, "'Str'", "'Int'", 
			"'Dec'", "'Date'", "'Bool'", "'?'", null, null, null, "'!'", "'->'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "SPACING", "COMMENT", "STRING", "INTEGER", 
			"DECIMAL", "DATE", "BOOL", "NULLABLE", "TABLE_NAME", "REFERENCED_ATTRIBUTE_NAME", 
			"ATTRIBUTE_NAME", "PRIMARY_KEY_OPERATOR", "REFERENCED_OPERATOR"
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
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(GrammarParser.STRING, 0); }
		public TerminalNode INTEGER() { return getToken(GrammarParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(GrammarParser.DECIMAL, 0); }
		public TerminalNode DATE() { return getToken(GrammarParser.DATE, 0); }
		public TerminalNode BOOL() { return getToken(GrammarParser.BOOL, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << BOOL))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class Type_declareContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode NULLABLE() { return getToken(GrammarParser.NULLABLE, 0); }
		public Type_declareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_declare; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterType_declare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitType_declare(this);
		}
	}

	public final Type_declareContext type_declare() throws RecognitionException {
		Type_declareContext _localctx = new Type_declareContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_type_declare);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			match(T__0);
			setState(15);
			type();
			setState(17);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NULLABLE) {
				{
				setState(16);
				match(NULLABLE);
				}
			}

			setState(19);
			match(T__1);
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

	public static class Reference_declareContext extends ParserRuleContext {
		public TerminalNode REFERENCED_OPERATOR() { return getToken(GrammarParser.REFERENCED_OPERATOR, 0); }
		public List<TerminalNode> REFERENCED_ATTRIBUTE_NAME() { return getTokens(GrammarParser.REFERENCED_ATTRIBUTE_NAME); }
		public TerminalNode REFERENCED_ATTRIBUTE_NAME(int i) {
			return getToken(GrammarParser.REFERENCED_ATTRIBUTE_NAME, i);
		}
		public Reference_declareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reference_declare; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterReference_declare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitReference_declare(this);
		}
	}

	public final Reference_declareContext reference_declare() throws RecognitionException {
		Reference_declareContext _localctx = new Reference_declareContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_reference_declare);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			match(REFERENCED_OPERATOR);
			setState(22);
			match(REFERENCED_ATTRIBUTE_NAME);
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(23);
				match(T__2);
				setState(24);
				match(REFERENCED_ATTRIBUTE_NAME);
				}
				}
				setState(29);
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

	public static class Attribute_declareContext extends ParserRuleContext {
		public TerminalNode ATTRIBUTE_NAME() { return getToken(GrammarParser.ATTRIBUTE_NAME, 0); }
		public Type_declareContext type_declare() {
			return getRuleContext(Type_declareContext.class,0);
		}
		public TerminalNode PRIMARY_KEY_OPERATOR() { return getToken(GrammarParser.PRIMARY_KEY_OPERATOR, 0); }
		public Reference_declareContext reference_declare() {
			return getRuleContext(Reference_declareContext.class,0);
		}
		public Attribute_declareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute_declare; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterAttribute_declare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitAttribute_declare(this);
		}
	}

	public final Attribute_declareContext attribute_declare() throws RecognitionException {
		Attribute_declareContext _localctx = new Attribute_declareContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_attribute_declare);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PRIMARY_KEY_OPERATOR) {
				{
				setState(30);
				match(PRIMARY_KEY_OPERATOR);
				}
			}

			setState(33);
			match(ATTRIBUTE_NAME);
			setState(34);
			type_declare();
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==REFERENCED_OPERATOR) {
				{
				setState(35);
				reference_declare();
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

	public static class Table_declareContext extends ParserRuleContext {
		public TerminalNode TABLE_NAME() { return getToken(GrammarParser.TABLE_NAME, 0); }
		public List<Attribute_declareContext> attribute_declare() {
			return getRuleContexts(Attribute_declareContext.class);
		}
		public Attribute_declareContext attribute_declare(int i) {
			return getRuleContext(Attribute_declareContext.class,i);
		}
		public Table_declareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_declare; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterTable_declare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitTable_declare(this);
		}
	}

	public final Table_declareContext table_declare() throws RecognitionException {
		Table_declareContext _localctx = new Table_declareContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_table_declare);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(TABLE_NAME);
			setState(39);
			match(T__3);
			setState(42); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(40);
					match(T__4);
					setState(41);
					attribute_declare();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(44); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class MainContext extends ParserRuleContext {
		public List<Table_declareContext> table_declare() {
			return getRuleContexts(Table_declareContext.class);
		}
		public Table_declareContext table_declare(int i) {
			return getRuleContext(Table_declareContext.class,i);
		}
		public MainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_main; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterMain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitMain(this);
		}
	}

	public final MainContext main() throws RecognitionException {
		MainContext _localctx = new MainContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_main);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			table_declare();
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(48); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(47);
					match(T__4);
					}
					}
					setState(50); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__4 );
				setState(52);
				table_declare();
				}
				}
				setState(57);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\24=\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\3\3\3\3\3\5\3\24\n\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\7\4\34\n\4\f\4\16\4\37\13\4\3\5\5\5\"\n\5\3\5\3\5"+
		"\3\5\5\5\'\n\5\3\6\3\6\3\6\3\6\6\6-\n\6\r\6\16\6.\3\7\3\7\6\7\63\n\7\r"+
		"\7\16\7\64\3\7\7\78\n\7\f\7\16\7;\13\7\3\7\2\2\b\2\4\6\b\n\f\2\3\3\2\n"+
		"\16\2=\2\16\3\2\2\2\4\20\3\2\2\2\6\27\3\2\2\2\b!\3\2\2\2\n(\3\2\2\2\f"+
		"\60\3\2\2\2\16\17\t\2\2\2\17\3\3\2\2\2\20\21\7\3\2\2\21\23\5\2\2\2\22"+
		"\24\7\17\2\2\23\22\3\2\2\2\23\24\3\2\2\2\24\25\3\2\2\2\25\26\7\4\2\2\26"+
		"\5\3\2\2\2\27\30\7\24\2\2\30\35\7\21\2\2\31\32\7\5\2\2\32\34\7\21\2\2"+
		"\33\31\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\35\36\3\2\2\2\36\7\3\2\2\2"+
		"\37\35\3\2\2\2 \"\7\23\2\2! \3\2\2\2!\"\3\2\2\2\"#\3\2\2\2#$\7\22\2\2"+
		"$&\5\4\3\2%\'\5\6\4\2&%\3\2\2\2&\'\3\2\2\2\'\t\3\2\2\2()\7\20\2\2),\7"+
		"\6\2\2*+\7\7\2\2+-\5\b\5\2,*\3\2\2\2-.\3\2\2\2.,\3\2\2\2./\3\2\2\2/\13"+
		"\3\2\2\2\609\5\n\6\2\61\63\7\7\2\2\62\61\3\2\2\2\63\64\3\2\2\2\64\62\3"+
		"\2\2\2\64\65\3\2\2\2\65\66\3\2\2\2\668\5\n\6\2\67\62\3\2\2\28;\3\2\2\2"+
		"9\67\3\2\2\29:\3\2\2\2:\r\3\2\2\2;9\3\2\2\2\t\23\35!&.\649";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}