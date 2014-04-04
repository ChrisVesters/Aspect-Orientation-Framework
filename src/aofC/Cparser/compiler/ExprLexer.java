// $ANTLR 3.4 src/main/antlr3/compiler/Expr.g 2013-11-09 16:35:06

package aofC.Cparser.compiler;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class ExprLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__29=29;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__50=50;
    public static final int T__51=51;
    public static final int BREAK=4;
    public static final int CHAR=5;
    public static final int CHARCONST=6;
    public static final int COMMENT=7;
    public static final int CONST=8;
    public static final int CONTINUE=9;
    public static final int DEREFERENCE=10;
    public static final int ELSE=11;
    public static final int FLOAT=12;
    public static final int FLOATCONST=13;
    public static final int FOR=14;
    public static final int ID=15;
    public static final int IF=16;
    public static final int INT=17;
    public static final int INTCONST=18;
    public static final int ML_COMMENT=19;
    public static final int POINTER=20;
    public static final int READ=21;
    public static final int RETURN=22;
    public static final int STDIOIMPORT=23;
    public static final int STRING=24;
    public static final int VOID=25;
    public static final int WHILE=26;
    public static final int WRITE=27;
    public static final int WS=28;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public ExprLexer() {} 
    public ExprLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ExprLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "src/main/antlr3/compiler/Expr.g"; }

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:11:7: ( '!' )
            // src/main/antlr3/compiler/Expr.g:11:9: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:12:7: ( '!=' )
            // src/main/antlr3/compiler/Expr.g:12:9: '!='
            {
            match("!="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:13:7: ( '&&' )
            // src/main/antlr3/compiler/Expr.g:13:9: '&&'
            {
            match("&&"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:14:7: ( '(' )
            // src/main/antlr3/compiler/Expr.g:14:9: '('
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
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:15:7: ( ')' )
            // src/main/antlr3/compiler/Expr.g:15:9: ')'
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
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:16:7: ( '+' )
            // src/main/antlr3/compiler/Expr.g:16:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:17:7: ( ',' )
            // src/main/antlr3/compiler/Expr.g:17:9: ','
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
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:18:7: ( '-' )
            // src/main/antlr3/compiler/Expr.g:18:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:19:7: ( '/' )
            // src/main/antlr3/compiler/Expr.g:19:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:20:7: ( ':' )
            // src/main/antlr3/compiler/Expr.g:20:9: ':'
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
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:21:7: ( ';' )
            // src/main/antlr3/compiler/Expr.g:21:9: ';'
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
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:22:7: ( '<' )
            // src/main/antlr3/compiler/Expr.g:22:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:23:7: ( '<=' )
            // src/main/antlr3/compiler/Expr.g:23:9: '<='
            {
            match("<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:24:7: ( '=' )
            // src/main/antlr3/compiler/Expr.g:24:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:25:7: ( '==' )
            // src/main/antlr3/compiler/Expr.g:25:9: '=='
            {
            match("=="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:26:7: ( '>' )
            // src/main/antlr3/compiler/Expr.g:26:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:27:7: ( '>=' )
            // src/main/antlr3/compiler/Expr.g:27:9: '>='
            {
            match(">="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:28:7: ( '?' )
            // src/main/antlr3/compiler/Expr.g:28:9: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:29:7: ( '[' )
            // src/main/antlr3/compiler/Expr.g:29:9: '['
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
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:30:7: ( ']' )
            // src/main/antlr3/compiler/Expr.g:30:9: ']'
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
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:31:7: ( '{' )
            // src/main/antlr3/compiler/Expr.g:31:9: '{'
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
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:32:7: ( '||' )
            // src/main/antlr3/compiler/Expr.g:32:9: '||'
            {
            match("||"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:33:7: ( '}' )
            // src/main/antlr3/compiler/Expr.g:33:9: '}'
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
    // $ANTLR end "T__51"

    // $ANTLR start "ML_COMMENT"
    public final void mML_COMMENT() throws RecognitionException {
        try {
            int _type = ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1623:11: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // src/main/antlr3/compiler/Expr.g:1623:13: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 



            // src/main/antlr3/compiler/Expr.g:1623:18: ( options {greedy=false; } : . )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='*') ) {
                    int LA1_1 = input.LA(2);

                    if ( (LA1_1=='/') ) {
                        alt1=2;
                    }
                    else if ( ((LA1_1 >= '\u0000' && LA1_1 <= '.')||(LA1_1 >= '0' && LA1_1 <= '\uFFFF')) ) {
                        alt1=1;
                    }


                }
                else if ( ((LA1_0 >= '\u0000' && LA1_0 <= ')')||(LA1_0 >= '+' && LA1_0 <= '\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // src/main/antlr3/compiler/Expr.g:1623:45: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            match("*/"); 



            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ML_COMMENT"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1624:8: ( '//' ( options {greedy=false; } : . )* ( '\\n' | '\\r' ) )
            // src/main/antlr3/compiler/Expr.g:1624:10: '//' ( options {greedy=false; } : . )* ( '\\n' | '\\r' )
            {
            match("//"); 



            // src/main/antlr3/compiler/Expr.g:1624:15: ( options {greedy=false; } : . )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\n'||LA2_0=='\r') ) {
                    alt2=2;
                }
                else if ( ((LA2_0 >= '\u0000' && LA2_0 <= '\t')||(LA2_0 >= '\u000B' && LA2_0 <= '\f')||(LA2_0 >= '\u000E' && LA2_0 <= '\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // src/main/antlr3/compiler/Expr.g:1624:42: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            if ( input.LA(1)=='\n'||input.LA(1)=='\r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1627:7: ( '\"' ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' | '\\\\' | '\\%' | '!' | '#' | '$' | '&' | '\\'' | '(' | ')' | '*' | '+' | ',' | '.' | '-' | '/' | '@' | '[' | ']' | '{' | '}' | ' ' | '^' | '_' | '`' | '~' | ':' | ';' | '<' | '=' | '>' | '?' )* '\"' )
            // src/main/antlr3/compiler/Expr.g:1627:9: '\"' ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' | '\\\\' | '\\%' | '!' | '#' | '$' | '&' | '\\'' | '(' | ')' | '*' | '+' | ',' | '.' | '-' | '/' | '@' | '[' | ']' | '{' | '}' | ' ' | '^' | '_' | '`' | '~' | ':' | ';' | '<' | '=' | '>' | '?' )* '\"'
            {
            match('\"'); 

            // src/main/antlr3/compiler/Expr.g:1627:13: ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' | '\\\\' | '\\%' | '!' | '#' | '$' | '&' | '\\'' | '(' | ')' | '*' | '+' | ',' | '.' | '-' | '/' | '@' | '[' | ']' | '{' | '}' | ' ' | '^' | '_' | '`' | '~' | ':' | ';' | '<' | '=' | '>' | '?' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= ' ' && LA3_0 <= '!')||(LA3_0 >= '#' && LA3_0 <= '{')||(LA3_0 >= '}' && LA3_0 <= '~')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // src/main/antlr3/compiler/Expr.g:
            	    {
            	    if ( (input.LA(1) >= ' ' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '{')||(input.LA(1) >= '}' && input.LA(1) <= '~') ) {
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
            	    break loop3;
                }
            } while (true);


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "BREAK"
    public final void mBREAK() throws RecognitionException {
        try {
            int _type = BREAK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1628:6: ( 'break' )
            // src/main/antlr3/compiler/Expr.g:1628:8: 'break'
            {
            match("break"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BREAK"

    // $ANTLR start "CONTINUE"
    public final void mCONTINUE() throws RecognitionException {
        try {
            int _type = CONTINUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1629:9: ( 'continue' )
            // src/main/antlr3/compiler/Expr.g:1629:11: 'continue'
            {
            match("continue"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CONTINUE"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1630:5: ( 'else' )
            // src/main/antlr3/compiler/Expr.g:1630:7: 'else'
            {
            match("else"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1631:3: ( 'if' )
            // src/main/antlr3/compiler/Expr.g:1631:5: 'if'
            {
            match("if"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1632:4: ( 'int' )
            // src/main/antlr3/compiler/Expr.g:1632:6: 'int'
            {
            match("int"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1633:4: ( 'for' )
            // src/main/antlr3/compiler/Expr.g:1633:6: 'for'
            {
            match("for"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FOR"

    // $ANTLR start "RETURN"
    public final void mRETURN() throws RecognitionException {
        try {
            int _type = RETURN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1634:7: ( 'return' )
            // src/main/antlr3/compiler/Expr.g:1634:9: 'return'
            {
            match("return"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RETURN"

    // $ANTLR start "WHILE"
    public final void mWHILE() throws RecognitionException {
        try {
            int _type = WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1635:6: ( 'while' )
            // src/main/antlr3/compiler/Expr.g:1635:8: 'while'
            {
            match("while"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHILE"

    // $ANTLR start "CHAR"
    public final void mCHAR() throws RecognitionException {
        try {
            int _type = CHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1636:5: ( 'char' )
            // src/main/antlr3/compiler/Expr.g:1636:7: 'char'
            {
            match("char"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHAR"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1637:6: ( 'float' )
            // src/main/antlr3/compiler/Expr.g:1637:8: 'float'
            {
            match("float"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "CHARCONST"
    public final void mCHARCONST() throws RecognitionException {
        try {
            int _type = CHARCONST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1638:10: ( '\\'' ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' | '\\\\' | '\\\\0' | '\\%' | '\\\\n' | '!' | '\"' | '#' | '$' | '&' | '\\'' | '(' | ')' | '*' | '+' | ',' | '.' | '-' | '/' | '@' | '[' | ']' | '{' | '}' | ' ' | '^' | '_' | '`' | '~' | ':' | ';' | '<' | '=' | '>' | '?' ) '\\'' )
            // src/main/antlr3/compiler/Expr.g:1638:12: '\\'' ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' | '\\\\' | '\\\\0' | '\\%' | '\\\\n' | '!' | '\"' | '#' | '$' | '&' | '\\'' | '(' | ')' | '*' | '+' | ',' | '.' | '-' | '/' | '@' | '[' | ']' | '{' | '}' | ' ' | '^' | '_' | '`' | '~' | ':' | ';' | '<' | '=' | '>' | '?' ) '\\''
            {
            match('\''); 

            // src/main/antlr3/compiler/Expr.g:1638:17: ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' | '\\\\' | '\\\\0' | '\\%' | '\\\\n' | '!' | '\"' | '#' | '$' | '&' | '\\'' | '(' | ')' | '*' | '+' | ',' | '.' | '-' | '/' | '@' | '[' | ']' | '{' | '}' | ' ' | '^' | '_' | '`' | '~' | ':' | ';' | '<' | '=' | '>' | '?' )
            int alt4=37;
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
                {
                alt4=1;
                }
                break;
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
                alt4=2;
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
                alt4=3;
                }
                break;
            case '\\':
                {
                switch ( input.LA(2) ) {
                case '0':
                    {
                    alt4=5;
                    }
                    break;
                case 'n':
                    {
                    alt4=7;
                    }
                    break;
                case '\'':
                    {
                    alt4=4;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 4, input);

                    throw nvae;

                }

                }
                break;
            case '%':
                {
                alt4=6;
                }
                break;
            case '!':
                {
                alt4=8;
                }
                break;
            case '\"':
                {
                alt4=9;
                }
                break;
            case '#':
                {
                alt4=10;
                }
                break;
            case '$':
                {
                alt4=11;
                }
                break;
            case '&':
                {
                alt4=12;
                }
                break;
            case '\'':
                {
                alt4=13;
                }
                break;
            case '(':
                {
                alt4=14;
                }
                break;
            case ')':
                {
                alt4=15;
                }
                break;
            case '*':
                {
                alt4=16;
                }
                break;
            case '+':
                {
                alt4=17;
                }
                break;
            case ',':
                {
                alt4=18;
                }
                break;
            case '.':
                {
                alt4=19;
                }
                break;
            case '-':
                {
                alt4=20;
                }
                break;
            case '/':
                {
                alt4=21;
                }
                break;
            case '@':
                {
                alt4=22;
                }
                break;
            case '[':
                {
                alt4=23;
                }
                break;
            case ']':
                {
                alt4=24;
                }
                break;
            case '{':
                {
                alt4=25;
                }
                break;
            case '}':
                {
                alt4=26;
                }
                break;
            case ' ':
                {
                alt4=27;
                }
                break;
            case '^':
                {
                alt4=28;
                }
                break;
            case '_':
                {
                alt4=29;
                }
                break;
            case '`':
                {
                alt4=30;
                }
                break;
            case '~':
                {
                alt4=31;
                }
                break;
            case ':':
                {
                alt4=32;
                }
                break;
            case ';':
                {
                alt4=33;
                }
                break;
            case '<':
                {
                alt4=34;
                }
                break;
            case '=':
                {
                alt4=35;
                }
                break;
            case '>':
                {
                alt4=36;
                }
                break;
            case '?':
                {
                alt4=37;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }

            switch (alt4) {
                case 1 :
                    // src/main/antlr3/compiler/Expr.g:1638:18: 'A' .. 'Z'
                    {
                    matchRange('A','Z'); 

                    }
                    break;
                case 2 :
                    // src/main/antlr3/compiler/Expr.g:1638:27: 'a' .. 'z'
                    {
                    matchRange('a','z'); 

                    }
                    break;
                case 3 :
                    // src/main/antlr3/compiler/Expr.g:1638:36: '0' .. '9'
                    {
                    matchRange('0','9'); 

                    }
                    break;
                case 4 :
                    // src/main/antlr3/compiler/Expr.g:1638:45: '\\\\'
                    {
                    match('\\'); 

                    }
                    break;
                case 5 :
                    // src/main/antlr3/compiler/Expr.g:1638:50: '\\\\0'
                    {
                    match("\\0"); 



                    }
                    break;
                case 6 :
                    // src/main/antlr3/compiler/Expr.g:1638:56: '\\%'
                    {
                    match('%'); 

                    }
                    break;
                case 7 :
                    // src/main/antlr3/compiler/Expr.g:1638:61: '\\\\n'
                    {
                    match("\\n"); 



                    }
                    break;
                case 8 :
                    // src/main/antlr3/compiler/Expr.g:1638:67: '!'
                    {
                    match('!'); 

                    }
                    break;
                case 9 :
                    // src/main/antlr3/compiler/Expr.g:1638:71: '\"'
                    {
                    match('\"'); 

                    }
                    break;
                case 10 :
                    // src/main/antlr3/compiler/Expr.g:1638:75: '#'
                    {
                    match('#'); 

                    }
                    break;
                case 11 :
                    // src/main/antlr3/compiler/Expr.g:1638:79: '$'
                    {
                    match('$'); 

                    }
                    break;
                case 12 :
                    // src/main/antlr3/compiler/Expr.g:1638:83: '&'
                    {
                    match('&'); 

                    }
                    break;
                case 13 :
                    // src/main/antlr3/compiler/Expr.g:1638:87: '\\''
                    {
                    match('\''); 

                    }
                    break;
                case 14 :
                    // src/main/antlr3/compiler/Expr.g:1638:92: '('
                    {
                    match('('); 

                    }
                    break;
                case 15 :
                    // src/main/antlr3/compiler/Expr.g:1638:96: ')'
                    {
                    match(')'); 

                    }
                    break;
                case 16 :
                    // src/main/antlr3/compiler/Expr.g:1638:100: '*'
                    {
                    match('*'); 

                    }
                    break;
                case 17 :
                    // src/main/antlr3/compiler/Expr.g:1638:104: '+'
                    {
                    match('+'); 

                    }
                    break;
                case 18 :
                    // src/main/antlr3/compiler/Expr.g:1638:108: ','
                    {
                    match(','); 

                    }
                    break;
                case 19 :
                    // src/main/antlr3/compiler/Expr.g:1638:112: '.'
                    {
                    match('.'); 

                    }
                    break;
                case 20 :
                    // src/main/antlr3/compiler/Expr.g:1638:116: '-'
                    {
                    match('-'); 

                    }
                    break;
                case 21 :
                    // src/main/antlr3/compiler/Expr.g:1638:120: '/'
                    {
                    match('/'); 

                    }
                    break;
                case 22 :
                    // src/main/antlr3/compiler/Expr.g:1638:124: '@'
                    {
                    match('@'); 

                    }
                    break;
                case 23 :
                    // src/main/antlr3/compiler/Expr.g:1638:128: '['
                    {
                    match('['); 

                    }
                    break;
                case 24 :
                    // src/main/antlr3/compiler/Expr.g:1638:132: ']'
                    {
                    match(']'); 

                    }
                    break;
                case 25 :
                    // src/main/antlr3/compiler/Expr.g:1638:136: '{'
                    {
                    match('{'); 

                    }
                    break;
                case 26 :
                    // src/main/antlr3/compiler/Expr.g:1638:140: '}'
                    {
                    match('}'); 

                    }
                    break;
                case 27 :
                    // src/main/antlr3/compiler/Expr.g:1638:144: ' '
                    {
                    match(' '); 

                    }
                    break;
                case 28 :
                    // src/main/antlr3/compiler/Expr.g:1638:148: '^'
                    {
                    match('^'); 

                    }
                    break;
                case 29 :
                    // src/main/antlr3/compiler/Expr.g:1638:152: '_'
                    {
                    match('_'); 

                    }
                    break;
                case 30 :
                    // src/main/antlr3/compiler/Expr.g:1638:156: '`'
                    {
                    match('`'); 

                    }
                    break;
                case 31 :
                    // src/main/antlr3/compiler/Expr.g:1638:160: '~'
                    {
                    match('~'); 

                    }
                    break;
                case 32 :
                    // src/main/antlr3/compiler/Expr.g:1638:164: ':'
                    {
                    match(':'); 

                    }
                    break;
                case 33 :
                    // src/main/antlr3/compiler/Expr.g:1638:168: ';'
                    {
                    match(';'); 

                    }
                    break;
                case 34 :
                    // src/main/antlr3/compiler/Expr.g:1638:172: '<'
                    {
                    match('<'); 

                    }
                    break;
                case 35 :
                    // src/main/antlr3/compiler/Expr.g:1638:176: '='
                    {
                    match('='); 

                    }
                    break;
                case 36 :
                    // src/main/antlr3/compiler/Expr.g:1638:180: '>'
                    {
                    match('>'); 

                    }
                    break;
                case 37 :
                    // src/main/antlr3/compiler/Expr.g:1638:184: '?'
                    {
                    match('?'); 

                    }
                    break;

            }


            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHARCONST"

    // $ANTLR start "READ"
    public final void mREAD() throws RecognitionException {
        try {
            int _type = READ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1639:5: ( 'scanf' )
            // src/main/antlr3/compiler/Expr.g:1639:7: 'scanf'
            {
            match("scanf"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "READ"

    // $ANTLR start "WRITE"
    public final void mWRITE() throws RecognitionException {
        try {
            int _type = WRITE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1640:6: ( 'printf' )
            // src/main/antlr3/compiler/Expr.g:1640:8: 'printf'
            {
            match("printf"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WRITE"

    // $ANTLR start "INTCONST"
    public final void mINTCONST() throws RecognitionException {
        try {
            int _type = INTCONST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1645:9: ( ( '0' .. '9' )+ )
            // src/main/antlr3/compiler/Expr.g:1645:11: ( '0' .. '9' )+
            {
            // src/main/antlr3/compiler/Expr.g:1645:11: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0 >= '0' && LA5_0 <= '9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // src/main/antlr3/compiler/Expr.g:
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
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INTCONST"

    // $ANTLR start "FLOATCONST"
    public final void mFLOATCONST() throws RecognitionException {
        try {
            int _type = FLOATCONST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1649:11: ( ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )? )
            // src/main/antlr3/compiler/Expr.g:1649:13: ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )?
            {
            // src/main/antlr3/compiler/Expr.g:1649:13: ( '0' .. '9' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0 >= '0' && LA6_0 <= '9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // src/main/antlr3/compiler/Expr.g:
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
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            // src/main/antlr3/compiler/Expr.g:1649:25: ( '.' ( '0' .. '9' )+ )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='.') ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // src/main/antlr3/compiler/Expr.g:1649:26: '.' ( '0' .. '9' )+
                    {
                    match('.'); 

                    // src/main/antlr3/compiler/Expr.g:1649:30: ( '0' .. '9' )+
                    int cnt7=0;
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( ((LA7_0 >= '0' && LA7_0 <= '9')) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // src/main/antlr3/compiler/Expr.g:
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
                    	    break;

                    	default :
                    	    if ( cnt7 >= 1 ) break loop7;
                                EarlyExitException eee =
                                    new EarlyExitException(7, input);
                                throw eee;
                        }
                        cnt7++;
                    } while (true);


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FLOATCONST"

    // $ANTLR start "POINTER"
    public final void mPOINTER() throws RecognitionException {
        try {
            int _type = POINTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1650:8: ( '*' )
            // src/main/antlr3/compiler/Expr.g:1650:10: '*'
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
    // $ANTLR end "POINTER"

    // $ANTLR start "DEREFERENCE"
    public final void mDEREFERENCE() throws RecognitionException {
        try {
            int _type = DEREFERENCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1651:12: ( '&' )
            // src/main/antlr3/compiler/Expr.g:1651:14: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DEREFERENCE"

    // $ANTLR start "VOID"
    public final void mVOID() throws RecognitionException {
        try {
            int _type = VOID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1652:5: ( 'void' )
            // src/main/antlr3/compiler/Expr.g:1652:7: 'void'
            {
            match("void"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VOID"

    // $ANTLR start "CONST"
    public final void mCONST() throws RecognitionException {
        try {
            int _type = CONST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1653:6: ( 'const' )
            // src/main/antlr3/compiler/Expr.g:1653:8: 'const'
            {
            match("const"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CONST"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1657:3: ( ( 'A' .. 'Z' | 'a' .. 'z' | '_' ) ( 'A' .. 'Z' | 'a' .. 'z' | '_' | '0' .. '9' )* )
            // src/main/antlr3/compiler/Expr.g:1657:5: ( 'A' .. 'Z' | 'a' .. 'z' | '_' ) ( 'A' .. 'Z' | 'a' .. 'z' | '_' | '0' .. '9' )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // src/main/antlr3/compiler/Expr.g:1657:29: ( 'A' .. 'Z' | 'a' .. 'z' | '_' | '0' .. '9' )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0 >= '0' && LA9_0 <= '9')||(LA9_0 >= 'A' && LA9_0 <= 'Z')||LA9_0=='_'||(LA9_0 >= 'a' && LA9_0 <= 'z')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // src/main/antlr3/compiler/Expr.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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
            	    break loop9;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1660:3: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
            // src/main/antlr3/compiler/Expr.g:1660:5: ( ' ' | '\\t' | '\\n' | '\\r' )+
            {
            // src/main/antlr3/compiler/Expr.g:1660:5: ( ' ' | '\\t' | '\\n' | '\\r' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0 >= '\t' && LA10_0 <= '\n')||LA10_0=='\r'||LA10_0==' ') ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // src/main/antlr3/compiler/Expr.g:
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
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "STDIOIMPORT"
    public final void mSTDIOIMPORT() throws RecognitionException {
        try {
            int _type = STDIOIMPORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr3/compiler/Expr.g:1663:12: ( '#include \"stdio.h\"' | '#include <stdio.h>' | '#include<stdio.h>' | '#include\"stdio.h\"' )
            int alt11=4;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='#') ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1=='i') ) {
                    int LA11_2 = input.LA(3);

                    if ( (LA11_2=='n') ) {
                        int LA11_3 = input.LA(4);

                        if ( (LA11_3=='c') ) {
                            int LA11_4 = input.LA(5);

                            if ( (LA11_4=='l') ) {
                                int LA11_5 = input.LA(6);

                                if ( (LA11_5=='u') ) {
                                    int LA11_6 = input.LA(7);

                                    if ( (LA11_6=='d') ) {
                                        int LA11_7 = input.LA(8);

                                        if ( (LA11_7=='e') ) {
                                            switch ( input.LA(9) ) {
                                            case ' ':
                                                {
                                                int LA11_9 = input.LA(10);

                                                if ( (LA11_9=='\"') ) {
                                                    alt11=1;
                                                }
                                                else if ( (LA11_9=='<') ) {
                                                    alt11=2;
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 11, 9, input);

                                                    throw nvae;

                                                }
                                                }
                                                break;
                                            case '<':
                                                {
                                                alt11=3;
                                                }
                                                break;
                                            case '\"':
                                                {
                                                alt11=4;
                                                }
                                                break;
                                            default:
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 11, 8, input);

                                                throw nvae;

                                            }

                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 11, 7, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 11, 6, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 11, 5, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 11, 4, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 11, 3, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 2, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;

            }
            switch (alt11) {
                case 1 :
                    // src/main/antlr3/compiler/Expr.g:1663:14: '#include \"stdio.h\"'
                    {
                    match("#include \"stdio.h\""); 



                    }
                    break;
                case 2 :
                    // src/main/antlr3/compiler/Expr.g:1663:37: '#include <stdio.h>'
                    {
                    match("#include <stdio.h>"); 



                    }
                    break;
                case 3 :
                    // src/main/antlr3/compiler/Expr.g:1663:60: '#include<stdio.h>'
                    {
                    match("#include<stdio.h>"); 



                    }
                    break;
                case 4 :
                    // src/main/antlr3/compiler/Expr.g:1663:82: '#include\"stdio.h\"'
                    {
                    match("#include\"stdio.h\""); 



                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STDIOIMPORT"

    public void mTokens() throws RecognitionException {
        // src/main/antlr3/compiler/Expr.g:1:8: ( T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | ML_COMMENT | COMMENT | STRING | BREAK | CONTINUE | ELSE | IF | INT | FOR | RETURN | WHILE | CHAR | FLOAT | CHARCONST | READ | WRITE | INTCONST | FLOATCONST | POINTER | DEREFERENCE | VOID | CONST | ID | WS | STDIOIMPORT )
        int alt12=48;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // src/main/antlr3/compiler/Expr.g:1:10: T__29
                {
                mT__29(); 


                }
                break;
            case 2 :
                // src/main/antlr3/compiler/Expr.g:1:16: T__30
                {
                mT__30(); 


                }
                break;
            case 3 :
                // src/main/antlr3/compiler/Expr.g:1:22: T__31
                {
                mT__31(); 


                }
                break;
            case 4 :
                // src/main/antlr3/compiler/Expr.g:1:28: T__32
                {
                mT__32(); 


                }
                break;
            case 5 :
                // src/main/antlr3/compiler/Expr.g:1:34: T__33
                {
                mT__33(); 


                }
                break;
            case 6 :
                // src/main/antlr3/compiler/Expr.g:1:40: T__34
                {
                mT__34(); 


                }
                break;
            case 7 :
                // src/main/antlr3/compiler/Expr.g:1:46: T__35
                {
                mT__35(); 


                }
                break;
            case 8 :
                // src/main/antlr3/compiler/Expr.g:1:52: T__36
                {
                mT__36(); 


                }
                break;
            case 9 :
                // src/main/antlr3/compiler/Expr.g:1:58: T__37
                {
                mT__37(); 


                }
                break;
            case 10 :
                // src/main/antlr3/compiler/Expr.g:1:64: T__38
                {
                mT__38(); 


                }
                break;
            case 11 :
                // src/main/antlr3/compiler/Expr.g:1:70: T__39
                {
                mT__39(); 


                }
                break;
            case 12 :
                // src/main/antlr3/compiler/Expr.g:1:76: T__40
                {
                mT__40(); 


                }
                break;
            case 13 :
                // src/main/antlr3/compiler/Expr.g:1:82: T__41
                {
                mT__41(); 


                }
                break;
            case 14 :
                // src/main/antlr3/compiler/Expr.g:1:88: T__42
                {
                mT__42(); 


                }
                break;
            case 15 :
                // src/main/antlr3/compiler/Expr.g:1:94: T__43
                {
                mT__43(); 


                }
                break;
            case 16 :
                // src/main/antlr3/compiler/Expr.g:1:100: T__44
                {
                mT__44(); 


                }
                break;
            case 17 :
                // src/main/antlr3/compiler/Expr.g:1:106: T__45
                {
                mT__45(); 


                }
                break;
            case 18 :
                // src/main/antlr3/compiler/Expr.g:1:112: T__46
                {
                mT__46(); 


                }
                break;
            case 19 :
                // src/main/antlr3/compiler/Expr.g:1:118: T__47
                {
                mT__47(); 


                }
                break;
            case 20 :
                // src/main/antlr3/compiler/Expr.g:1:124: T__48
                {
                mT__48(); 


                }
                break;
            case 21 :
                // src/main/antlr3/compiler/Expr.g:1:130: T__49
                {
                mT__49(); 


                }
                break;
            case 22 :
                // src/main/antlr3/compiler/Expr.g:1:136: T__50
                {
                mT__50(); 


                }
                break;
            case 23 :
                // src/main/antlr3/compiler/Expr.g:1:142: T__51
                {
                mT__51(); 


                }
                break;
            case 24 :
                // src/main/antlr3/compiler/Expr.g:1:148: ML_COMMENT
                {
                mML_COMMENT(); 


                }
                break;
            case 25 :
                // src/main/antlr3/compiler/Expr.g:1:159: COMMENT
                {
                mCOMMENT(); 


                }
                break;
            case 26 :
                // src/main/antlr3/compiler/Expr.g:1:167: STRING
                {
                mSTRING(); 


                }
                break;
            case 27 :
                // src/main/antlr3/compiler/Expr.g:1:174: BREAK
                {
                mBREAK(); 


                }
                break;
            case 28 :
                // src/main/antlr3/compiler/Expr.g:1:180: CONTINUE
                {
                mCONTINUE(); 


                }
                break;
            case 29 :
                // src/main/antlr3/compiler/Expr.g:1:189: ELSE
                {
                mELSE(); 


                }
                break;
            case 30 :
                // src/main/antlr3/compiler/Expr.g:1:194: IF
                {
                mIF(); 


                }
                break;
            case 31 :
                // src/main/antlr3/compiler/Expr.g:1:197: INT
                {
                mINT(); 


                }
                break;
            case 32 :
                // src/main/antlr3/compiler/Expr.g:1:201: FOR
                {
                mFOR(); 


                }
                break;
            case 33 :
                // src/main/antlr3/compiler/Expr.g:1:205: RETURN
                {
                mRETURN(); 


                }
                break;
            case 34 :
                // src/main/antlr3/compiler/Expr.g:1:212: WHILE
                {
                mWHILE(); 


                }
                break;
            case 35 :
                // src/main/antlr3/compiler/Expr.g:1:218: CHAR
                {
                mCHAR(); 


                }
                break;
            case 36 :
                // src/main/antlr3/compiler/Expr.g:1:223: FLOAT
                {
                mFLOAT(); 


                }
                break;
            case 37 :
                // src/main/antlr3/compiler/Expr.g:1:229: CHARCONST
                {
                mCHARCONST(); 


                }
                break;
            case 38 :
                // src/main/antlr3/compiler/Expr.g:1:239: READ
                {
                mREAD(); 


                }
                break;
            case 39 :
                // src/main/antlr3/compiler/Expr.g:1:244: WRITE
                {
                mWRITE(); 


                }
                break;
            case 40 :
                // src/main/antlr3/compiler/Expr.g:1:250: INTCONST
                {
                mINTCONST(); 


                }
                break;
            case 41 :
                // src/main/antlr3/compiler/Expr.g:1:259: FLOATCONST
                {
                mFLOATCONST(); 


                }
                break;
            case 42 :
                // src/main/antlr3/compiler/Expr.g:1:270: POINTER
                {
                mPOINTER(); 


                }
                break;
            case 43 :
                // src/main/antlr3/compiler/Expr.g:1:278: DEREFERENCE
                {
                mDEREFERENCE(); 


                }
                break;
            case 44 :
                // src/main/antlr3/compiler/Expr.g:1:290: VOID
                {
                mVOID(); 


                }
                break;
            case 45 :
                // src/main/antlr3/compiler/Expr.g:1:295: CONST
                {
                mCONST(); 


                }
                break;
            case 46 :
                // src/main/antlr3/compiler/Expr.g:1:301: ID
                {
                mID(); 


                }
                break;
            case 47 :
                // src/main/antlr3/compiler/Expr.g:1:304: WS
                {
                mWS(); 


                }
                break;
            case 48 :
                // src/main/antlr3/compiler/Expr.g:1:307: STDIOIMPORT
                {
                mSTDIOIMPORT(); 


                }
                break;

        }

    }


    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA12_eotS =
        "\1\uffff\1\46\1\50\5\uffff\1\53\2\uffff\1\55\1\57\1\61\7\uffff\7"+
        "\42\1\uffff\2\42\1\76\1\uffff\1\42\20\uffff\4\42\1\105\7\42\2\uffff"+
        "\5\42\1\uffff\1\123\1\124\11\42\1\136\1\137\2\uffff\5\42\1\145\1"+
        "\146\1\42\1\150\2\uffff\1\151\1\42\1\153\1\154\1\42\2\uffff\1\42"+
        "\2\uffff\1\157\2\uffff\1\160\1\42\2\uffff\1\162\1\uffff";
    static final String DFA12_eofS =
        "\163\uffff";
    static final String DFA12_minS =
        "\1\11\1\75\1\46\5\uffff\1\52\2\uffff\3\75\7\uffff\1\162\1\150\1"+
        "\154\1\146\1\154\1\145\1\150\1\uffff\1\143\1\162\1\56\1\uffff\1"+
        "\157\20\uffff\1\145\1\156\1\141\1\163\1\60\1\164\1\162\1\157\1\164"+
        "\1\151\1\141\1\151\2\uffff\1\151\1\141\1\163\1\162\1\145\1\uffff"+
        "\2\60\1\141\1\165\1\154\2\156\1\144\1\153\1\151\1\164\2\60\2\uffff"+
        "\1\164\1\162\1\145\1\146\1\164\2\60\1\156\1\60\2\uffff\1\60\1\156"+
        "\2\60\1\146\2\uffff\1\165\2\uffff\1\60\2\uffff\1\60\1\145\2\uffff"+
        "\1\60\1\uffff";
    static final String DFA12_maxS =
        "\1\175\1\75\1\46\5\uffff\1\57\2\uffff\3\75\7\uffff\1\162\1\157\1"+
        "\154\1\156\1\157\1\145\1\150\1\uffff\1\143\1\162\1\71\1\uffff\1"+
        "\157\20\uffff\1\145\1\156\1\141\1\163\1\172\1\164\1\162\1\157\1"+
        "\164\1\151\1\141\1\151\2\uffff\1\151\1\141\1\164\1\162\1\145\1\uffff"+
        "\2\172\1\141\1\165\1\154\2\156\1\144\1\153\1\151\1\164\2\172\2\uffff"+
        "\1\164\1\162\1\145\1\146\1\164\2\172\1\156\1\172\2\uffff\1\172\1"+
        "\156\2\172\1\146\2\uffff\1\165\2\uffff\1\172\2\uffff\1\172\1\145"+
        "\2\uffff\1\172\1\uffff";
    static final String DFA12_acceptS =
        "\3\uffff\1\4\1\5\1\6\1\7\1\10\1\uffff\1\12\1\13\3\uffff\1\22\1\23"+
        "\1\24\1\25\1\26\1\27\1\32\7\uffff\1\45\3\uffff\1\52\1\uffff\1\56"+
        "\1\57\1\60\1\2\1\1\1\3\1\53\1\30\1\31\1\11\1\15\1\14\1\17\1\16\1"+
        "\21\1\20\14\uffff\1\50\1\51\5\uffff\1\36\15\uffff\1\37\1\40\11\uffff"+
        "\1\43\1\35\5\uffff\1\54\1\33\1\uffff\1\55\1\44\1\uffff\1\42\1\46"+
        "\2\uffff\1\41\1\47\1\uffff\1\34";
    static final String DFA12_specialS =
        "\163\uffff}>";
    static final String[] DFA12_transitionS = {
            "\2\43\2\uffff\1\43\22\uffff\1\43\1\1\1\24\1\44\2\uffff\1\2\1"+
            "\34\1\3\1\4\1\40\1\5\1\6\1\7\1\uffff\1\10\12\37\1\11\1\12\1"+
            "\13\1\14\1\15\1\16\1\uffff\32\42\1\17\1\uffff\1\20\1\uffff\1"+
            "\42\1\uffff\1\42\1\25\1\26\1\42\1\27\1\31\2\42\1\30\6\42\1\36"+
            "\1\42\1\32\1\35\2\42\1\41\1\33\3\42\1\21\1\22\1\23",
            "\1\45",
            "\1\47",
            "",
            "",
            "",
            "",
            "",
            "\1\51\4\uffff\1\52",
            "",
            "",
            "\1\54",
            "\1\56",
            "\1\60",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\62",
            "\1\64\6\uffff\1\63",
            "\1\65",
            "\1\66\7\uffff\1\67",
            "\1\71\2\uffff\1\70",
            "\1\72",
            "\1\73",
            "",
            "\1\74",
            "\1\75",
            "\1\77\1\uffff\12\37",
            "",
            "\1\100",
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
            "",
            "",
            "",
            "",
            "",
            "\1\101",
            "\1\102",
            "\1\103",
            "\1\104",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
            "",
            "",
            "\1\115",
            "\1\116",
            "\1\120\1\117",
            "\1\121",
            "\1\122",
            "",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "",
            "\1\140",
            "\1\141",
            "\1\142",
            "\1\143",
            "\1\144",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\147",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\152",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\155",
            "",
            "",
            "\1\156",
            "",
            "",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\161",
            "",
            "",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | ML_COMMENT | COMMENT | STRING | BREAK | CONTINUE | ELSE | IF | INT | FOR | RETURN | WHILE | CHAR | FLOAT | CHARCONST | READ | WRITE | INTCONST | FLOATCONST | POINTER | DEREFERENCE | VOID | CONST | ID | WS | STDIOIMPORT );";
        }
    }
 

}