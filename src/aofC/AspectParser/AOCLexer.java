// $ANTLR 3.5 src/aofC/AspectParser/AOC.g 2013-12-18 15:25:05

package aofC.AspectParser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class AOCLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int DIGIT=4;
	public static final int LETTER=5;
	public static final int NAME=6;
	public static final int OTHERCHARS=7;
	public static final int SPECIALCHARS=8;
	public static final int WS=9;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public AOCLexer() {} 
	public AOCLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public AOCLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "src/aofC/AspectParser/AOC.g"; }

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:11:7: ( '(' )
			// src/aofC/AspectParser/AOC.g:11:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__10"

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:12:7: ( ')' )
			// src/aofC/AspectParser/AOC.g:12:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:13:7: ( '*' )
			// src/aofC/AspectParser/AOC.g:13:9: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "T__13"
	public final void mT__13() throws RecognitionException {
		try {
			int _type = T__13;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:14:7: ( ',' )
			// src/aofC/AspectParser/AOC.g:14:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__13"

	// $ANTLR start "T__14"
	public final void mT__14() throws RecognitionException {
		try {
			int _type = T__14;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:15:7: ( ':' )
			// src/aofC/AspectParser/AOC.g:15:9: ':'
			{
			match(':'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__14"

	// $ANTLR start "T__15"
	public final void mT__15() throws RecognitionException {
		try {
			int _type = T__15;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:16:7: ( ';' )
			// src/aofC/AspectParser/AOC.g:16:9: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__15"

	// $ANTLR start "T__16"
	public final void mT__16() throws RecognitionException {
		try {
			int _type = T__16;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:17:7: ( '[' )
			// src/aofC/AspectParser/AOC.g:17:9: '['
			{
			match('['); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__16"

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:18:7: ( ']' )
			// src/aofC/AspectParser/AOC.g:18:9: ']'
			{
			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:19:7: ( 'advice' )
			// src/aofC/AspectParser/AOC.g:19:9: 'advice'
			{
			match("advice"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:20:7: ( 'after' )
			// src/aofC/AspectParser/AOC.g:20:9: 'after'
			{
			match("after"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:21:7: ( 'around' )
			// src/aofC/AspectParser/AOC.g:21:9: 'around'
			{
			match("around"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:22:7: ( 'before' )
			// src/aofC/AspectParser/AOC.g:22:9: 'before'
			{
			match("before"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:23:7: ( 'call' )
			// src/aofC/AspectParser/AOC.g:23:9: 'call'
			{
			match("call"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:24:7: ( 'execute' )
			// src/aofC/AspectParser/AOC.g:24:9: 'execute'
			{
			match("execute"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "T__24"
	public final void mT__24() throws RecognitionException {
		try {
			int _type = T__24;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:25:7: ( 'get' )
			// src/aofC/AspectParser/AOC.g:25:9: 'get'
			{
			match("get"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__24"

	// $ANTLR start "T__25"
	public final void mT__25() throws RecognitionException {
		try {
			int _type = T__25;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:26:7: ( 'order' )
			// src/aofC/AspectParser/AOC.g:26:9: 'order'
			{
			match("order"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__25"

	// $ANTLR start "T__26"
	public final void mT__26() throws RecognitionException {
		try {
			int _type = T__26;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:27:7: ( 'pointcut' )
			// src/aofC/AspectParser/AOC.g:27:9: 'pointcut'
			{
			match("pointcut"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__26"

	// $ANTLR start "T__27"
	public final void mT__27() throws RecognitionException {
		try {
			int _type = T__27;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:28:7: ( 'set' )
			// src/aofC/AspectParser/AOC.g:28:9: 'set'
			{
			match("set"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__27"

	// $ANTLR start "T__28"
	public final void mT__28() throws RecognitionException {
		try {
			int _type = T__28;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:29:7: ( '{' )
			// src/aofC/AspectParser/AOC.g:29:9: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__28"

	// $ANTLR start "T__29"
	public final void mT__29() throws RecognitionException {
		try {
			int _type = T__29;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:30:7: ( '}' )
			// src/aofC/AspectParser/AOC.g:30:9: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__29"

	// $ANTLR start "NAME"
	public final void mNAME() throws RecognitionException {
		try {
			int _type = NAME;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:181:5: ( ( LETTER | SPECIALCHARS | '..' ) ( LETTER | DIGIT | SPECIALCHARS | '..' )* )
			// src/aofC/AspectParser/AOC.g:181:7: ( LETTER | SPECIALCHARS | '..' ) ( LETTER | DIGIT | SPECIALCHARS | '..' )*
			{
			// src/aofC/AspectParser/AOC.g:181:7: ( LETTER | SPECIALCHARS | '..' )
			int alt1=3;
			switch ( input.LA(1) ) {
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				{
				alt1=1;
				}
				break;
			case '*':
			case '_':
				{
				alt1=2;
				}
				break;
			case '.':
				{
				alt1=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}
			switch (alt1) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:181:8: LETTER
					{
					mLETTER(); 

					}
					break;
				case 2 :
					// src/aofC/AspectParser/AOC.g:181:17: SPECIALCHARS
					{
					mSPECIALCHARS(); 

					}
					break;
				case 3 :
					// src/aofC/AspectParser/AOC.g:181:32: '..'
					{
					match(".."); 

					}
					break;

			}

			// src/aofC/AspectParser/AOC.g:181:38: ( LETTER | DIGIT | SPECIALCHARS | '..' )*
			loop2:
			while (true) {
				int alt2=5;
				switch ( input.LA(1) ) {
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'E':
				case 'F':
				case 'G':
				case 'H':
				case 'I':
				case 'J':
				case 'K':
				case 'L':
				case 'M':
				case 'N':
				case 'O':
				case 'P':
				case 'Q':
				case 'R':
				case 'S':
				case 'T':
				case 'U':
				case 'V':
				case 'W':
				case 'X':
				case 'Y':
				case 'Z':
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f':
				case 'g':
				case 'h':
				case 'i':
				case 'j':
				case 'k':
				case 'l':
				case 'm':
				case 'n':
				case 'o':
				case 'p':
				case 'q':
				case 'r':
				case 's':
				case 't':
				case 'u':
				case 'v':
				case 'w':
				case 'x':
				case 'y':
				case 'z':
					{
					alt2=1;
					}
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					{
					alt2=2;
					}
					break;
				case '*':
				case '_':
					{
					alt2=3;
					}
					break;
				case '.':
					{
					alt2=4;
					}
					break;
				}
				switch (alt2) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:181:39: LETTER
					{
					mLETTER(); 

					}
					break;
				case 2 :
					// src/aofC/AspectParser/AOC.g:181:48: DIGIT
					{
					mDIGIT(); 

					}
					break;
				case 3 :
					// src/aofC/AspectParser/AOC.g:181:56: SPECIALCHARS
					{
					mSPECIALCHARS(); 

					}
					break;
				case 4 :
					// src/aofC/AspectParser/AOC.g:181:71: '..'
					{
					match(".."); 

					}
					break;

				default :
					break loop2;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NAME"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:182:3: ( ( ' ' | '\\n' | '\\t' | '\\r' )+ )
			// src/aofC/AspectParser/AOC.g:182:5: ( ' ' | '\\n' | '\\t' | '\\r' )+
			{
			// src/aofC/AspectParser/AOC.g:182:5: ( ' ' | '\\n' | '\\t' | '\\r' )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '\t' && LA3_0 <= '\n')||LA3_0=='\r'||LA3_0==' ') ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "OTHERCHARS"
	public final void mOTHERCHARS() throws RecognitionException {
		try {
			int _type = OTHERCHARS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:183:11: (~ ( LETTER | DIGIT | SPECIALCHARS ) )
			// src/aofC/AspectParser/AOC.g:
			{
			if ( (input.LA(1) >= '\u0000' && input.LA(1) <= ')')||(input.LA(1) >= '+' && input.LA(1) <= '/')||(input.LA(1) >= ':' && input.LA(1) <= '@')||(input.LA(1) >= '[' && input.LA(1) <= '^')||input.LA(1)=='`'||(input.LA(1) >= '{' && input.LA(1) <= '\uFFFF') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OTHERCHARS"

	// $ANTLR start "DIGIT"
	public final void mDIGIT() throws RecognitionException {
		try {
			int _type = DIGIT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/aofC/AspectParser/AOC.g:184:6: ( ( '0' .. '9' ) )
			// src/aofC/AspectParser/AOC.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIGIT"

	// $ANTLR start "LETTER"
	public final void mLETTER() throws RecognitionException {
		try {
			// src/aofC/AspectParser/AOC.g:186:16: ( ( 'a' .. 'z' | 'A' .. 'Z' ) )
			// src/aofC/AspectParser/AOC.g:
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LETTER"

	// $ANTLR start "SPECIALCHARS"
	public final void mSPECIALCHARS() throws RecognitionException {
		try {
			// src/aofC/AspectParser/AOC.g:187:22: ( ( '*' | '_' ) )
			// src/aofC/AspectParser/AOC.g:
			{
			if ( input.LA(1)=='*'||input.LA(1)=='_' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SPECIALCHARS"

	@Override
	public void mTokens() throws RecognitionException {
		// src/aofC/AspectParser/AOC.g:1:8: ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | NAME | WS | OTHERCHARS | DIGIT )
		int alt4=24;
		alt4 = dfa4.predict(input);
		switch (alt4) {
			case 1 :
				// src/aofC/AspectParser/AOC.g:1:10: T__10
				{
				mT__10(); 

				}
				break;
			case 2 :
				// src/aofC/AspectParser/AOC.g:1:16: T__11
				{
				mT__11(); 

				}
				break;
			case 3 :
				// src/aofC/AspectParser/AOC.g:1:22: T__12
				{
				mT__12(); 

				}
				break;
			case 4 :
				// src/aofC/AspectParser/AOC.g:1:28: T__13
				{
				mT__13(); 

				}
				break;
			case 5 :
				// src/aofC/AspectParser/AOC.g:1:34: T__14
				{
				mT__14(); 

				}
				break;
			case 6 :
				// src/aofC/AspectParser/AOC.g:1:40: T__15
				{
				mT__15(); 

				}
				break;
			case 7 :
				// src/aofC/AspectParser/AOC.g:1:46: T__16
				{
				mT__16(); 

				}
				break;
			case 8 :
				// src/aofC/AspectParser/AOC.g:1:52: T__17
				{
				mT__17(); 

				}
				break;
			case 9 :
				// src/aofC/AspectParser/AOC.g:1:58: T__18
				{
				mT__18(); 

				}
				break;
			case 10 :
				// src/aofC/AspectParser/AOC.g:1:64: T__19
				{
				mT__19(); 

				}
				break;
			case 11 :
				// src/aofC/AspectParser/AOC.g:1:70: T__20
				{
				mT__20(); 

				}
				break;
			case 12 :
				// src/aofC/AspectParser/AOC.g:1:76: T__21
				{
				mT__21(); 

				}
				break;
			case 13 :
				// src/aofC/AspectParser/AOC.g:1:82: T__22
				{
				mT__22(); 

				}
				break;
			case 14 :
				// src/aofC/AspectParser/AOC.g:1:88: T__23
				{
				mT__23(); 

				}
				break;
			case 15 :
				// src/aofC/AspectParser/AOC.g:1:94: T__24
				{
				mT__24(); 

				}
				break;
			case 16 :
				// src/aofC/AspectParser/AOC.g:1:100: T__25
				{
				mT__25(); 

				}
				break;
			case 17 :
				// src/aofC/AspectParser/AOC.g:1:106: T__26
				{
				mT__26(); 

				}
				break;
			case 18 :
				// src/aofC/AspectParser/AOC.g:1:112: T__27
				{
				mT__27(); 

				}
				break;
			case 19 :
				// src/aofC/AspectParser/AOC.g:1:118: T__28
				{
				mT__28(); 

				}
				break;
			case 20 :
				// src/aofC/AspectParser/AOC.g:1:124: T__29
				{
				mT__29(); 

				}
				break;
			case 21 :
				// src/aofC/AspectParser/AOC.g:1:130: NAME
				{
				mNAME(); 

				}
				break;
			case 22 :
				// src/aofC/AspectParser/AOC.g:1:135: WS
				{
				mWS(); 

				}
				break;
			case 23 :
				// src/aofC/AspectParser/AOC.g:1:138: OTHERCHARS
				{
				mOTHERCHARS(); 

				}
				break;
			case 24 :
				// src/aofC/AspectParser/AOC.g:1:149: DIGIT
				{
				mDIGIT(); 

				}
				break;

		}
	}


	protected DFA4 dfa4 = new DFA4(this);
	static final String DFA4_eotS =
		"\3\uffff\1\32\5\uffff\10\23\3\uffff\1\26\13\uffff\12\23\3\uffff\6\23\1"+
		"\75\2\23\1\100\4\23\1\105\1\23\1\uffff\2\23\1\uffff\1\23\1\112\2\23\1"+
		"\uffff\1\23\1\116\1\23\1\120\1\uffff\1\121\1\122\1\23\1\uffff\1\23\3\uffff"+
		"\1\125\1\23\1\uffff\1\127\1\uffff";
	static final String DFA4_eofS =
		"\130\uffff";
	static final String DFA4_minS =
		"\1\0\2\uffff\1\52\5\uffff\1\144\1\145\1\141\1\170\1\145\1\162\1\157\1"+
		"\145\3\uffff\1\56\13\uffff\1\166\1\164\1\157\1\146\1\154\1\145\1\164\1"+
		"\144\1\151\1\164\3\uffff\1\151\1\145\1\165\1\157\1\154\1\143\1\52\1\145"+
		"\1\156\1\52\1\143\1\162\1\156\1\162\1\52\1\165\1\uffff\1\162\1\164\1\uffff"+
		"\1\145\1\52\1\144\1\145\1\uffff\1\164\1\52\1\143\1\52\1\uffff\2\52\1\145"+
		"\1\uffff\1\165\3\uffff\1\52\1\164\1\uffff\1\52\1\uffff";
	static final String DFA4_maxS =
		"\1\uffff\2\uffff\1\172\5\uffff\1\162\1\145\1\141\1\170\1\145\1\162\1\157"+
		"\1\145\3\uffff\1\56\13\uffff\1\166\1\164\1\157\1\146\1\154\1\145\1\164"+
		"\1\144\1\151\1\164\3\uffff\1\151\1\145\1\165\1\157\1\154\1\143\1\172\1"+
		"\145\1\156\1\172\1\143\1\162\1\156\1\162\1\172\1\165\1\uffff\1\162\1\164"+
		"\1\uffff\1\145\1\172\1\144\1\145\1\uffff\1\164\1\172\1\143\1\172\1\uffff"+
		"\2\172\1\145\1\uffff\1\165\3\uffff\1\172\1\164\1\uffff\1\172\1\uffff";
	static final String DFA4_acceptS =
		"\1\uffff\1\1\1\2\1\uffff\1\4\1\5\1\6\1\7\1\10\10\uffff\1\23\1\24\1\25"+
		"\1\uffff\1\26\1\27\1\30\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\12\uffff\1\23"+
		"\1\24\1\26\20\uffff\1\17\2\uffff\1\22\4\uffff\1\15\4\uffff\1\12\3\uffff"+
		"\1\20\1\uffff\1\11\1\13\1\14\2\uffff\1\16\1\uffff\1\21";
	static final String DFA4_specialS =
		"\1\0\127\uffff}>";
	static final String[] DFA4_transitionS = {
			"\11\26\2\25\2\26\1\25\22\26\1\25\7\26\1\1\1\2\1\3\1\26\1\4\1\26\1\24"+
			"\1\26\12\27\1\5\1\6\5\26\32\23\1\7\1\26\1\10\1\26\1\23\1\26\1\11\1\12"+
			"\1\13\1\23\1\14\1\23\1\15\7\23\1\16\1\17\2\23\1\20\7\23\1\21\1\26\1\22"+
			"\uff82\26",
			"",
			"",
			"\1\23\3\uffff\1\23\1\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff"+
			"\32\23",
			"",
			"",
			"",
			"",
			"",
			"\1\40\1\uffff\1\41\13\uffff\1\42",
			"\1\43",
			"\1\44",
			"\1\45",
			"\1\46",
			"\1\47",
			"\1\50",
			"\1\51",
			"",
			"",
			"",
			"\1\23",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\55",
			"\1\56",
			"\1\57",
			"\1\60",
			"\1\61",
			"\1\62",
			"\1\63",
			"\1\64",
			"\1\65",
			"\1\66",
			"",
			"",
			"",
			"\1\67",
			"\1\70",
			"\1\71",
			"\1\72",
			"\1\73",
			"\1\74",
			"\1\23\3\uffff\1\23\1\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff"+
			"\32\23",
			"\1\76",
			"\1\77",
			"\1\23\3\uffff\1\23\1\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff"+
			"\32\23",
			"\1\101",
			"\1\102",
			"\1\103",
			"\1\104",
			"\1\23\3\uffff\1\23\1\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff"+
			"\32\23",
			"\1\106",
			"",
			"\1\107",
			"\1\110",
			"",
			"\1\111",
			"\1\23\3\uffff\1\23\1\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff"+
			"\32\23",
			"\1\113",
			"\1\114",
			"",
			"\1\115",
			"\1\23\3\uffff\1\23\1\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff"+
			"\32\23",
			"\1\117",
			"\1\23\3\uffff\1\23\1\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff"+
			"\32\23",
			"",
			"\1\23\3\uffff\1\23\1\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff"+
			"\32\23",
			"\1\23\3\uffff\1\23\1\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff"+
			"\32\23",
			"\1\123",
			"",
			"\1\124",
			"",
			"",
			"",
			"\1\23\3\uffff\1\23\1\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff"+
			"\32\23",
			"\1\126",
			"",
			"\1\23\3\uffff\1\23\1\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff"+
			"\32\23",
			""
	};

	static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
	static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
	static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
	static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
	static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
	static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
	static final short[][] DFA4_transition;

	static {
		int numStates = DFA4_transitionS.length;
		DFA4_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
		}
	}

	protected class DFA4 extends DFA {

		public DFA4(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 4;
			this.eot = DFA4_eot;
			this.eof = DFA4_eof;
			this.min = DFA4_min;
			this.max = DFA4_max;
			this.accept = DFA4_accept;
			this.special = DFA4_special;
			this.transition = DFA4_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | NAME | WS | OTHERCHARS | DIGIT );";
		}
		@Override
		public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
			IntStream input = _input;
			int _s = s;
			switch ( s ) {
					case 0 : 
						int LA4_0 = input.LA(1);
						s = -1;
						if ( (LA4_0=='(') ) {s = 1;}
						else if ( (LA4_0==')') ) {s = 2;}
						else if ( (LA4_0=='*') ) {s = 3;}
						else if ( (LA4_0==',') ) {s = 4;}
						else if ( (LA4_0==':') ) {s = 5;}
						else if ( (LA4_0==';') ) {s = 6;}
						else if ( (LA4_0=='[') ) {s = 7;}
						else if ( (LA4_0==']') ) {s = 8;}
						else if ( (LA4_0=='a') ) {s = 9;}
						else if ( (LA4_0=='b') ) {s = 10;}
						else if ( (LA4_0=='c') ) {s = 11;}
						else if ( (LA4_0=='e') ) {s = 12;}
						else if ( (LA4_0=='g') ) {s = 13;}
						else if ( (LA4_0=='o') ) {s = 14;}
						else if ( (LA4_0=='p') ) {s = 15;}
						else if ( (LA4_0=='s') ) {s = 16;}
						else if ( (LA4_0=='{') ) {s = 17;}
						else if ( (LA4_0=='}') ) {s = 18;}
						else if ( ((LA4_0 >= 'A' && LA4_0 <= 'Z')||LA4_0=='_'||LA4_0=='d'||LA4_0=='f'||(LA4_0 >= 'h' && LA4_0 <= 'n')||(LA4_0 >= 'q' && LA4_0 <= 'r')||(LA4_0 >= 't' && LA4_0 <= 'z')) ) {s = 19;}
						else if ( (LA4_0=='.') ) {s = 20;}
						else if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||LA4_0=='\r'||LA4_0==' ') ) {s = 21;}
						else if ( ((LA4_0 >= '\u0000' && LA4_0 <= '\b')||(LA4_0 >= '\u000B' && LA4_0 <= '\f')||(LA4_0 >= '\u000E' && LA4_0 <= '\u001F')||(LA4_0 >= '!' && LA4_0 <= '\'')||LA4_0=='+'||LA4_0=='-'||LA4_0=='/'||(LA4_0 >= '<' && LA4_0 <= '@')||LA4_0=='\\'||LA4_0=='^'||LA4_0=='`'||LA4_0=='|'||(LA4_0 >= '~' && LA4_0 <= '\uFFFF')) ) {s = 22;}
						else if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {s = 23;}
						if ( s>=0 ) return s;
						break;
			}
			NoViableAltException nvae =
				new NoViableAltException(getDescription(), 4, _s, input);
			error(nvae);
			throw nvae;
		}
	}

}
