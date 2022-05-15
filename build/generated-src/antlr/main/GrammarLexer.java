// Generated from Grammar.g4 by ANTLR 4.9.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, SPACING=6, COMMENT=7, STRING=8, 
		INTEGER=9, DECIMAL=10, DATE=11, BOOL=12, NULLABLE=13, TABLE_NAME=14, REFERENCED_ATTRIBUTE_NAME=15, 
		ATTRIBUTE_NAME=16, PRIMARY_KEY_OPERATOR=17, REFERENCED_OPERATOR=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "SPACING", "COMMENT", "STRING", 
			"INTEGER", "DECIMAL", "DATE", "BOOL", "NULLABLE", "TABLE_NAME", "ATTRIBUTE_NAME_SELF", 
			"REFERENCED_ATTRIBUTE_NAME", "ATTRIBUTE_NAME", "PRIMARY_KEY_OPERATOR", 
			"REFERENCED_OPERATOR"
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


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\24\u0089\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7"+
		"\6\7\65\n\7\r\7\16\7\66\3\7\3\7\3\b\3\b\7\b=\n\b\f\b\16\b@\13\b\3\b\3"+
		"\b\3\b\3\b\7\bF\n\b\f\b\16\bI\13\b\3\b\3\b\5\bM\n\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r"+
		"\3\r\3\r\3\r\3\16\3\16\3\17\3\17\7\17k\n\17\f\17\16\17n\13\17\3\20\3\20"+
		"\5\20r\n\20\3\20\7\20u\n\20\f\20\16\20x\13\20\3\21\3\21\3\21\3\21\3\22"+
		"\3\22\3\22\5\22\u0081\n\22\3\22\3\22\3\23\3\23\3\24\3\24\3\24\2\2\25\3"+
		"\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37"+
		"\2!\21#\22%\23\'\24\3\2\b\4\2\13\13\"\"\3\2\f\f\3\2\61\61\3\2C\\\4\2C"+
		"\\c|\3\2c|\2\u008f\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2!\3\2\2\2\2#\3"+
		"\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\3)\3\2\2\2\5+\3\2\2\2\7-\3\2\2\2\t/\3\2"+
		"\2\2\13\61\3\2\2\2\r\64\3\2\2\2\17L\3\2\2\2\21P\3\2\2\2\23T\3\2\2\2\25"+
		"X\3\2\2\2\27\\\3\2\2\2\31a\3\2\2\2\33f\3\2\2\2\35h\3\2\2\2\37o\3\2\2\2"+
		"!y\3\2\2\2#\u0080\3\2\2\2%\u0084\3\2\2\2\'\u0086\3\2\2\2)*\7*\2\2*\4\3"+
		"\2\2\2+,\7+\2\2,\6\3\2\2\2-.\7.\2\2.\b\3\2\2\2/\60\7<\2\2\60\n\3\2\2\2"+
		"\61\62\7\f\2\2\62\f\3\2\2\2\63\65\t\2\2\2\64\63\3\2\2\2\65\66\3\2\2\2"+
		"\66\64\3\2\2\2\66\67\3\2\2\2\678\3\2\2\289\b\7\2\29\16\3\2\2\2:>\7%\2"+
		"\2;=\n\3\2\2<;\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2\2\2?M\3\2\2\2@>\3\2\2"+
		"\2AB\7\61\2\2BC\7,\2\2CG\3\2\2\2DF\n\4\2\2ED\3\2\2\2FI\3\2\2\2GE\3\2\2"+
		"\2GH\3\2\2\2HJ\3\2\2\2IG\3\2\2\2JK\7,\2\2KM\7\61\2\2L:\3\2\2\2LA\3\2\2"+
		"\2MN\3\2\2\2NO\b\b\2\2O\20\3\2\2\2PQ\7U\2\2QR\7v\2\2RS\7t\2\2S\22\3\2"+
		"\2\2TU\7K\2\2UV\7p\2\2VW\7v\2\2W\24\3\2\2\2XY\7F\2\2YZ\7g\2\2Z[\7e\2\2"+
		"[\26\3\2\2\2\\]\7F\2\2]^\7c\2\2^_\7v\2\2_`\7g\2\2`\30\3\2\2\2ab\7D\2\2"+
		"bc\7q\2\2cd\7q\2\2de\7n\2\2e\32\3\2\2\2fg\7A\2\2g\34\3\2\2\2hl\t\5\2\2"+
		"ik\t\6\2\2ji\3\2\2\2kn\3\2\2\2lj\3\2\2\2lm\3\2\2\2m\36\3\2\2\2nl\3\2\2"+
		"\2ov\t\7\2\2pr\7a\2\2qp\3\2\2\2qr\3\2\2\2rs\3\2\2\2su\t\7\2\2tq\3\2\2"+
		"\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2w \3\2\2\2xv\3\2\2\2yz\5\35\17\2z{\7\60"+
		"\2\2{|\5\37\20\2|\"\3\2\2\2}~\5\35\17\2~\177\7\60\2\2\177\u0081\3\2\2"+
		"\2\u0080}\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0083"+
		"\5\37\20\2\u0083$\3\2\2\2\u0084\u0085\7#\2\2\u0085&\3\2\2\2\u0086\u0087"+
		"\7/\2\2\u0087\u0088\7@\2\2\u0088(\3\2\2\2\13\2\66>GLlqv\u0080\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}