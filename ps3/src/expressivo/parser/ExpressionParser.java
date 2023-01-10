// Generated from Expression.g4 by ANTLR 4.5.1

package expressivo.parser;
// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionParser extends Parser {
  static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache =
    new PredictionContextCache();
  public static final int
    T__0=1, T__1=2, T__2=3, T__3=4, NUMBER=5, VARIABLE=6, SPACES=7;
  public static final int
    RULE_root = 0, RULE_operator = 1, RULE_primitive = 2;
  public static final String[] ruleNames = {
    "root", "operator", "primitive"
  };

  private static final String[] _LITERAL_NAMES = {
    null, "'+'", "'*'", "'('", "')'"
  };
  private static final String[] _SYMBOLIC_NAMES = {
    null, null, null, null, null, "NUMBER", "VARIABLE", "SPACES"
  };
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
  public String getGrammarFileName() { return "Expression.g4"; }

  @Override
  public String[] getRuleNames() { return ruleNames; }

  @Override
  public String getSerializedATN() { return _serializedATN; }

  @Override
  public ATN getATN() { return _ATN; }


      // This method makes the lexer or parser stop running if it encounters
      // invalid input and throw a ParseCancellationException.
      public void reportErrorsAsExceptions() {
          // To prevent any reports to standard error, add this line:
          //removeErrorListeners();
          
          addErrorListener(new BaseErrorListener() {
              public void syntaxError(Recognizer<?, ?> recognizer,
                                      Object offendingSymbol,
                                      int line, int charPositionInLine,
                                      String msg, RecognitionException e) {
                  throw new ParseCancellationException(msg, e);
              }
          });
      }

  public ExpressionParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
  }
  public static class RootContext extends ParserRuleContext {
    public OperatorContext operator() {
      return getRuleContext(OperatorContext.class,0);
    }
    public TerminalNode EOF() { return getToken(ExpressionParser.EOF, 0); }
    public RootContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_root; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterRoot(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitRoot(this);
    }
  }

  public final RootContext root() throws RecognitionException {
    RootContext _localctx = new RootContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_root);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(6);
      operator();
      setState(7);
      match(EOF);
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

  public static class OperatorContext extends ParserRuleContext {
    public List<PrimitiveContext> primitive() {
      return getRuleContexts(PrimitiveContext.class);
    }
    public PrimitiveContext primitive(int i) {
      return getRuleContext(PrimitiveContext.class,i);
    }
    public OperatorContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_operator; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterOperator(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitOperator(this);
    }
  }

  public final OperatorContext operator() throws RecognitionException {
    OperatorContext _localctx = new OperatorContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_operator);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(9);
      primitive();
      setState(14);
      _errHandler.sync(this);
      _la = _input.LA(1);
      while (_la==T__0 || _la==T__1) {
        {
        {
        setState(10);
        _la = _input.LA(1);
        if ( !(_la==T__0 || _la==T__1) ) {
        _errHandler.recoverInline(this);
        } else {
          consume();
        }
        setState(11);
        primitive();
        }
        }
        setState(16);
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

  public static class PrimitiveContext extends ParserRuleContext {
    public TerminalNode VARIABLE() { return getToken(ExpressionParser.VARIABLE, 0); }
    public TerminalNode NUMBER() { return getToken(ExpressionParser.NUMBER, 0); }
    public OperatorContext operator() {
      return getRuleContext(OperatorContext.class,0);
    }
    public PrimitiveContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_primitive; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterPrimitive(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitPrimitive(this);
    }
  }

  public final PrimitiveContext primitive() throws RecognitionException {
    PrimitiveContext _localctx = new PrimitiveContext(_ctx, getState());
    enterRule(_localctx, 4, RULE_primitive);
    try {
      setState(23);
      switch (_input.LA(1)) {
      case VARIABLE:
        enterOuterAlt(_localctx, 1);
        {
        setState(17);
        match(VARIABLE);
        }
        break;
      case NUMBER:
        enterOuterAlt(_localctx, 2);
        {
        setState(18);
        match(NUMBER);
        }
        break;
      case T__2:
        enterOuterAlt(_localctx, 3);
        {
        setState(19);
        match(T__2);
        setState(20);
        operator();
        setState(21);
        match(T__3);
        }
        break;
      default:
        throw new NoViableAltException(this);
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
    "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\t\34\4\2\t\2\4"+
      "\3\t\3\4\4\t\4\3\2\3\2\3\2\3\3\3\3\3\3\7\3\17\n\3\f\3\16\3\22\13\3"+
      "\3\4\3\4\3\4\3\4\3\4\3\4\5\4\32\n\4\3\4\2\2\5\2\4\6\2\3\3\2\3\4\33"+
      "\2\b\3\2\2\2\4\13\3\2\2\2\6\31\3\2\2\2\b\t\5\4\3\2\t\n\7\2\2\3\n\3"+
      "\3\2\2\2\13\20\5\6\4\2\f\r\t\2\2\2\r\17\5\6\4\2\16\f\3\2\2\2\17\22"+
      "\3\2\2\2\20\16\3\2\2\2\20\21\3\2\2\2\21\5\3\2\2\2\22\20\3\2\2\2\23"+
      "\32\7\b\2\2\24\32\7\7\2\2\25\26\7\5\2\2\26\27\5\4\3\2\27\30\7\6\2"+
      "\2\30\32\3\2\2\2\31\23\3\2\2\2\31\24\3\2\2\2\31\25\3\2\2\2\32\7\3"+
      "\2\2\2\4\20\31";
  public static final ATN _ATN =
    new ATNDeserializer().deserialize(_serializedATN.toCharArray());
  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}