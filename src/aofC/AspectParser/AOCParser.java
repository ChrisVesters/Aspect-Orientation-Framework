// $ANTLR 3.5 src/aofC/AspectParser/AOC.g 2013-12-18 15:25:05

package aofC.AspectParser;

import aof.*;
import aofC.*;
import aofC.Cparser.symbolTable.AnyType;
import aofC.Cparser.symbolTable.ArrayType;
import aofC.Cparser.symbolTable.PrimaryType;
import aofC.Cparser.symbolTable.PointerType;
import aofC.Cparser.symbolTable.CType;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class AOCParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "DIGIT", "LETTER", "NAME", "OTHERCHARS", 
		"SPECIALCHARS", "WS", "'('", "')'", "'*'", "','", "':'", "';'", "'['", 
		"']'", "'advice'", "'after'", "'around'", "'before'", "'call'", "'execute'", 
		"'get'", "'order'", "'pointcut'", "'set'", "'{'", "'}'"
	};
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
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public AOCParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public AOCParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return AOCParser.tokenNames; }
	@Override public String getGrammarFileName() { return "src/aofC/AspectParser/AOC.g"; }


	static int counter = 0;



	// $ANTLR start "file"
	// src/aofC/AspectParser/AOC.g:27:1: file : ( WS | order | pointcut | advice )* EOF ;
	public final void file() throws RecognitionException {
		try {
			// src/aofC/AspectParser/AOC.g:27:5: ( ( WS | order | pointcut | advice )* EOF )
			// src/aofC/AspectParser/AOC.g:27:7: ( WS | order | pointcut | advice )* EOF
			{
			// src/aofC/AspectParser/AOC.g:27:7: ( WS | order | pointcut | advice )*
			loop1:
			while (true) {
				int alt1=5;
				switch ( input.LA(1) ) {
				case WS:
					{
					alt1=1;
					}
					break;
				case 25:
					{
					alt1=2;
					}
					break;
				case 26:
					{
					alt1=3;
					}
					break;
				case 18:
				case 19:
				case 20:
				case 21:
					{
					alt1=4;
					}
					break;
				}
				switch (alt1) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:27:8: WS
					{
					match(input,WS,FOLLOW_WS_in_file51); 
					}
					break;
				case 2 :
					// src/aofC/AspectParser/AOC.g:27:13: order
					{
					pushFollow(FOLLOW_order_in_file55);
					order();
					state._fsp--;

					}
					break;
				case 3 :
					// src/aofC/AspectParser/AOC.g:27:21: pointcut
					{
					pushFollow(FOLLOW_pointcut_in_file59);
					pointcut();
					state._fsp--;

					}
					break;
				case 4 :
					// src/aofC/AspectParser/AOC.g:27:32: advice
					{
					pushFollow(FOLLOW_advice_in_file63);
					advice();
					state._fsp--;

					}
					break;

				default :
					break loop1;
				}
			}

			match(input,EOF,FOLLOW_EOF_in_file67); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "file"



	// $ANTLR start "order"
	// src/aofC/AspectParser/AOC.g:29:1: order : 'order' ( WS )? '{' ( WS )? aName= NAME ( ( WS )? ';' ( WS )? aName= NAME )* ( WS )? '}' ;
	public final void order() throws RecognitionException {
		Token aName=null;

		try {
			// src/aofC/AspectParser/AOC.g:30:3: ( 'order' ( WS )? '{' ( WS )? aName= NAME ( ( WS )? ';' ( WS )? aName= NAME )* ( WS )? '}' )
			// src/aofC/AspectParser/AOC.g:30:5: 'order' ( WS )? '{' ( WS )? aName= NAME ( ( WS )? ';' ( WS )? aName= NAME )* ( WS )? '}'
			{

			      String pred = new String();
			      String post = new String(); 
			    
			match(input,25,FOLLOW_25_in_order83); 
			// src/aofC/AspectParser/AOC.g:34:13: ( WS )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==WS) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:34:13: WS
					{
					match(input,WS,FOLLOW_WS_in_order85); 
					}
					break;

			}

			match(input,28,FOLLOW_28_in_order88); 
			// src/aofC/AspectParser/AOC.g:34:21: ( WS )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==WS) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:34:21: WS
					{
					match(input,WS,FOLLOW_WS_in_order90); 
					}
					break;

			}

			aName=(Token)match(input,NAME,FOLLOW_NAME_in_order97); 

			      assert (aName != null);  
			      pred = "_" + aName.getText();
			    
			// src/aofC/AspectParser/AOC.g:39:5: ( ( WS )? ';' ( WS )? aName= NAME )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( (LA6_0==WS) ) {
					int LA6_1 = input.LA(2);
					if ( (LA6_1==15) ) {
						alt6=1;
					}

				}
				else if ( (LA6_0==15) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:39:6: ( WS )? ';' ( WS )? aName= NAME
					{
					// src/aofC/AspectParser/AOC.g:39:6: ( WS )?
					int alt4=2;
					int LA4_0 = input.LA(1);
					if ( (LA4_0==WS) ) {
						alt4=1;
					}
					switch (alt4) {
						case 1 :
							// src/aofC/AspectParser/AOC.g:39:6: WS
							{
							match(input,WS,FOLLOW_WS_in_order110); 
							}
							break;

					}

					match(input,15,FOLLOW_15_in_order113); 
					// src/aofC/AspectParser/AOC.g:39:14: ( WS )?
					int alt5=2;
					int LA5_0 = input.LA(1);
					if ( (LA5_0==WS) ) {
						alt5=1;
					}
					switch (alt5) {
						case 1 :
							// src/aofC/AspectParser/AOC.g:39:14: WS
							{
							match(input,WS,FOLLOW_WS_in_order115); 
							}
							break;

					}

					aName=(Token)match(input,NAME,FOLLOW_NAME_in_order122); 

					      assert (aName != null);  
					      post = "_" + aName.getText();
					      Weaver.addOrder(pred, post);
					      pred = post;
					    
					}
					break;

				default :
					break loop6;
				}
			}

			// src/aofC/AspectParser/AOC.g:46:8: ( WS )?
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==WS) ) {
				alt7=1;
			}
			switch (alt7) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:46:8: WS
					{
					match(input,WS,FOLLOW_WS_in_order137); 
					}
					break;

			}

			match(input,29,FOLLOW_29_in_order140); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "order"



	// $ANTLR start "advice"
	// src/aofC/AspectParser/AOC.g:50:1: advice : ( 'advice' WS aName= NAME ( WS )? ':' ( WS )? )? ( ( 'before' ) | ( 'after' ) | ( 'around' ) ) WS pName= NAME ( WS )? '(' args ')' ( WS )? '{' value+= ( ( . )* ) '}' ;
	public final void advice() throws RecognitionException {
		Token aName=null;
		Token pName=null;
		Token value=null;
		List<Object> list_value=null;
		ArrayList<Argument> args1 =null;

		try {
			// src/aofC/AspectParser/AOC.g:51:3: ( ( 'advice' WS aName= NAME ( WS )? ':' ( WS )? )? ( ( 'before' ) | ( 'after' ) | ( 'around' ) ) WS pName= NAME ( WS )? '(' args ')' ( WS )? '{' value+= ( ( . )* ) '}' )
			// src/aofC/AspectParser/AOC.g:51:5: ( 'advice' WS aName= NAME ( WS )? ':' ( WS )? )? ( ( 'before' ) | ( 'after' ) | ( 'around' ) ) WS pName= NAME ( WS )? '(' args ')' ( WS )? '{' value+= ( ( . )* ) '}'
			{
			// src/aofC/AspectParser/AOC.g:51:5: ( 'advice' WS aName= NAME ( WS )? ':' ( WS )? )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==18) ) {
				alt10=1;
			}
			switch (alt10) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:51:6: 'advice' WS aName= NAME ( WS )? ':' ( WS )?
					{
					match(input,18,FOLLOW_18_in_advice153); 
					match(input,WS,FOLLOW_WS_in_advice155); 
					aName=(Token)match(input,NAME,FOLLOW_NAME_in_advice161); 

					      if (aName.getText().contains("*"))
					      {
					        throw new RecognitionException();
					      }
					    
					// src/aofC/AspectParser/AOC.g:58:5: ( WS )?
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0==WS) ) {
						alt8=1;
					}
					switch (alt8) {
						case 1 :
							// src/aofC/AspectParser/AOC.g:58:5: WS
							{
							match(input,WS,FOLLOW_WS_in_advice173); 
							}
							break;

					}

					match(input,14,FOLLOW_14_in_advice176); 
					// src/aofC/AspectParser/AOC.g:58:13: ( WS )?
					int alt9=2;
					int LA9_0 = input.LA(1);
					if ( (LA9_0==WS) ) {
						alt9=1;
					}
					switch (alt9) {
						case 1 :
							// src/aofC/AspectParser/AOC.g:58:13: WS
							{
							match(input,WS,FOLLOW_WS_in_advice178); 
							}
							break;

					}

					}
					break;

			}

			Moment moment = null;
			// src/aofC/AspectParser/AOC.g:60:5: ( ( 'before' ) | ( 'after' ) | ( 'around' ) )
			int alt11=3;
			switch ( input.LA(1) ) {
			case 21:
				{
				alt11=1;
				}
				break;
			case 19:
				{
				alt11=2;
				}
				break;
			case 20:
				{
				alt11=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 11, 0, input);
				throw nvae;
			}
			switch (alt11) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:60:6: ( 'before' )
					{
					// src/aofC/AspectParser/AOC.g:60:6: ( 'before' )
					// src/aofC/AspectParser/AOC.g:60:7: 'before'
					{
					match(input,21,FOLLOW_21_in_advice195); 
					moment = Moment.BEFORE;
					}

					}
					break;
				case 2 :
					// src/aofC/AspectParser/AOC.g:61:7: ( 'after' )
					{
					// src/aofC/AspectParser/AOC.g:61:7: ( 'after' )
					// src/aofC/AspectParser/AOC.g:61:8: 'after'
					{
					match(input,19,FOLLOW_19_in_advice208); 
					moment = Moment.AFTER;
					}

					}
					break;
				case 3 :
					// src/aofC/AspectParser/AOC.g:62:7: ( 'around' )
					{
					// src/aofC/AspectParser/AOC.g:62:7: ( 'around' )
					// src/aofC/AspectParser/AOC.g:62:8: 'around'
					{
					match(input,20,FOLLOW_20_in_advice221); 
					moment = Moment.AROUND;
					}

					}
					break;

			}

			match(input,WS,FOLLOW_WS_in_advice231); 
			pName=(Token)match(input,NAME,FOLLOW_NAME_in_advice237); 
			// src/aofC/AspectParser/AOC.g:63:21: ( WS )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==WS) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:63:21: WS
					{
					match(input,WS,FOLLOW_WS_in_advice239); 
					}
					break;

			}

			match(input,10,FOLLOW_10_in_advice242); 
			pushFollow(FOLLOW_args_in_advice244);
			args1=args();
			state._fsp--;

			match(input,11,FOLLOW_11_in_advice246); 
			// src/aofC/AspectParser/AOC.g:63:38: ( WS )?
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0==WS) ) {
				alt13=1;
			}
			switch (alt13) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:63:38: WS
					{
					match(input,WS,FOLLOW_WS_in_advice248); 
					}
					break;

			}


			      if (pName.getText().contains("*"))
			      {
			        throw new RecognitionException();
			      }
			    
			match(input,28,FOLLOW_28_in_advice263); 
			// src/aofC/AspectParser/AOC.g:70:18: ( ( . )* )
			// src/aofC/AspectParser/AOC.g:70:19: ( . )*
			{
			// src/aofC/AspectParser/AOC.g:70:19: ( . )*
			loop14:
			while (true) {
				int alt14=2;
				int LA14_0 = input.LA(1);
				if ( (LA14_0==29) ) {
					alt14=2;
				}
				else if ( ((LA14_0 >= DIGIT && LA14_0 <= 28)) ) {
					alt14=1;
				}

				switch (alt14) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:70:19: .
					{
					value=input.LT(1);
					matchAny(input); 
					if (list_value==null) list_value=new ArrayList<Object>();
					list_value.add(value);
					}
					break;

				default :
					break loop14;
				}
			}

			}

			match(input,29,FOLLOW_29_in_advice274); 

			      String code = new String();
			      for (Object token: list_value) {
			        code += ((Token) token).getText();
			      }

			      String adviceName;
			      if (aName == null)
			      {
			        adviceName = "UnnamedAdvice_" + counter;
			        counter++;
			      }
			      else
			      {
			        adviceName = "_" + aName.getText();
			      }
			      CAdvice cAd = new CAdvice(adviceName, args1, moment, code);
			      Weaver.addAdvice(cAd, pName.getText());
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "advice"



	// $ANTLR start "pointcut"
	// src/aofC/AspectParser/AOC.g:94:1: pointcut : 'pointcut' WS NAME ( WS )? '(' args ')' ( WS )? '{' rules '}' ;
	public final void pointcut() throws RecognitionException {
		Token NAME2=null;
		ArrayList<Argument> args3 =null;
		ArrayList<PointcutRule> rules4 =null;

		try {
			// src/aofC/AspectParser/AOC.g:95:3: ( 'pointcut' WS NAME ( WS )? '(' args ')' ( WS )? '{' rules '}' )
			// src/aofC/AspectParser/AOC.g:95:5: 'pointcut' WS NAME ( WS )? '(' args ')' ( WS )? '{' rules '}'
			{
			match(input,26,FOLLOW_26_in_pointcut293); 
			match(input,WS,FOLLOW_WS_in_pointcut295); 
			NAME2=(Token)match(input,NAME,FOLLOW_NAME_in_pointcut297); 
			// src/aofC/AspectParser/AOC.g:95:24: ( WS )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==WS) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:95:24: WS
					{
					match(input,WS,FOLLOW_WS_in_pointcut299); 
					}
					break;

			}

			match(input,10,FOLLOW_10_in_pointcut302); 
			pushFollow(FOLLOW_args_in_pointcut304);
			args3=args();
			state._fsp--;

			match(input,11,FOLLOW_11_in_pointcut306); 
			// src/aofC/AspectParser/AOC.g:95:41: ( WS )?
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( (LA16_0==WS) ) {
				alt16=1;
			}
			switch (alt16) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:95:41: WS
					{
					match(input,WS,FOLLOW_WS_in_pointcut308); 
					}
					break;

			}


			      if (NAME2.getText().contains("*")) {
			        throw new RecognitionException();
			      }
			    
			match(input,28,FOLLOW_28_in_pointcut322); 
			pushFollow(FOLLOW_rules_in_pointcut324);
			rules4=rules();
			state._fsp--;

			match(input,29,FOLLOW_29_in_pointcut326); 

			      // TODO: check only named arguments.
			      Pointcut pc = new Pointcut(NAME2.getText(), args3, rules4);
			      Weaver.addPointcut(pc);
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "pointcut"



	// $ANTLR start "rules"
	// src/aofC/AspectParser/AOC.g:108:1: rules returns [ArrayList<PointcutRule> pcs = new ArrayList<PointcutRule>()] : ( WS | ( method ) | ( member ) )* ;
	public final ArrayList<PointcutRule> rules() throws RecognitionException {
		ArrayList<PointcutRule> pcs =  new ArrayList<PointcutRule>();


		MethodPointcutRule method5 =null;
		MemberPointcutRule member6 =null;

		try {
			// src/aofC/AspectParser/AOC.g:109:3: ( ( WS | ( method ) | ( member ) )* )
			// src/aofC/AspectParser/AOC.g:109:5: ( WS | ( method ) | ( member ) )*
			{
			// src/aofC/AspectParser/AOC.g:109:5: ( WS | ( method ) | ( member ) )*
			loop17:
			while (true) {
				int alt17=4;
				switch ( input.LA(1) ) {
				case WS:
					{
					alt17=1;
					}
					break;
				case 22:
				case 23:
					{
					alt17=2;
					}
					break;
				case 24:
				case 27:
					{
					alt17=3;
					}
					break;
				}
				switch (alt17) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:109:6: WS
					{
					match(input,WS,FOLLOW_WS_in_rules347); 
					}
					break;
				case 2 :
					// src/aofC/AspectParser/AOC.g:110:5: ( method )
					{
					// src/aofC/AspectParser/AOC.g:110:5: ( method )
					// src/aofC/AspectParser/AOC.g:110:6: method
					{
					pushFollow(FOLLOW_method_in_rules354);
					method5=method();
					state._fsp--;

					pcs.add(method5);
					}

					}
					break;
				case 3 :
					// src/aofC/AspectParser/AOC.g:111:5: ( member )
					{
					// src/aofC/AspectParser/AOC.g:111:5: ( member )
					// src/aofC/AspectParser/AOC.g:111:6: member
					{
					pushFollow(FOLLOW_member_in_rules364);
					member6=member();
					state._fsp--;

					pcs.add(member6);
					}

					}
					break;

				default :
					break loop17;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return pcs;
	}
	// $ANTLR end "rules"



	// $ANTLR start "member"
	// src/aofC/AspectParser/AOC.g:114:1: member returns [MemberPointcutRule pc] : ( ( 'set' ) | ( 'get' ) ) WS type WS NAME ( WS )? ';' ;
	public final MemberPointcutRule member() throws RecognitionException {
		MemberPointcutRule pc = null;


		Token NAME7=null;
		CType type8 =null;

		try {
			// src/aofC/AspectParser/AOC.g:115:3: ( ( ( 'set' ) | ( 'get' ) ) WS type WS NAME ( WS )? ';' )
			// src/aofC/AspectParser/AOC.g:115:5: ( ( 'set' ) | ( 'get' ) ) WS type WS NAME ( WS )? ';'
			{
			MemberPointcutRule.JoinPoint joinPoint = null;
			// src/aofC/AspectParser/AOC.g:116:5: ( ( 'set' ) | ( 'get' ) )
			int alt18=2;
			int LA18_0 = input.LA(1);
			if ( (LA18_0==27) ) {
				alt18=1;
			}
			else if ( (LA18_0==24) ) {
				alt18=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 18, 0, input);
				throw nvae;
			}

			switch (alt18) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:116:6: ( 'set' )
					{
					// src/aofC/AspectParser/AOC.g:116:6: ( 'set' )
					// src/aofC/AspectParser/AOC.g:116:7: 'set'
					{
					match(input,27,FOLLOW_27_in_member396); 
					joinPoint = MemberPointcutRule.JoinPoint.SET;
					}

					}
					break;
				case 2 :
					// src/aofC/AspectParser/AOC.g:117:7: ( 'get' )
					{
					// src/aofC/AspectParser/AOC.g:117:7: ( 'get' )
					// src/aofC/AspectParser/AOC.g:117:8: 'get'
					{
					match(input,24,FOLLOW_24_in_member408); 
					joinPoint = MemberPointcutRule.JoinPoint.GET;
					}

					}
					break;

			}

			match(input,WS,FOLLOW_WS_in_member418); 
			pushFollow(FOLLOW_type_in_member420);
			type8=type();
			state._fsp--;

			match(input,WS,FOLLOW_WS_in_member422); 
			NAME7=(Token)match(input,NAME,FOLLOW_NAME_in_member424); 
			// src/aofC/AspectParser/AOC.g:118:21: ( WS )?
			int alt19=2;
			int LA19_0 = input.LA(1);
			if ( (LA19_0==WS) ) {
				alt19=1;
			}
			switch (alt19) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:118:21: WS
					{
					match(input,WS,FOLLOW_WS_in_member426); 
					}
					break;

			}

			match(input,15,FOLLOW_15_in_member429); 

			      Argument arg = new Argument(NAME7.getText(), type8);
			      pc = new MemberPointcutRule(joinPoint, arg);
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return pc;
	}
	// $ANTLR end "member"



	// $ANTLR start "method"
	// src/aofC/AspectParser/AOC.g:124:1: method returns [MethodPointcutRule pc] : ( ( 'call' ) | ( 'execute' ) ) WS type WS NAME ( WS )? '(' args ')' ( WS )? ';' ;
	public final MethodPointcutRule method() throws RecognitionException {
		MethodPointcutRule pc = null;


		Token NAME10=null;
		CType type9 =null;
		ArrayList<Argument> args11 =null;

		try {
			// src/aofC/AspectParser/AOC.g:125:3: ( ( ( 'call' ) | ( 'execute' ) ) WS type WS NAME ( WS )? '(' args ')' ( WS )? ';' )
			// src/aofC/AspectParser/AOC.g:125:5: ( ( 'call' ) | ( 'execute' ) ) WS type WS NAME ( WS )? '(' args ')' ( WS )? ';'
			{
			MethodPointcutRule.JoinPoint joinPoint = null;
			// src/aofC/AspectParser/AOC.g:126:5: ( ( 'call' ) | ( 'execute' ) )
			int alt20=2;
			int LA20_0 = input.LA(1);
			if ( (LA20_0==22) ) {
				alt20=1;
			}
			else if ( (LA20_0==23) ) {
				alt20=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 20, 0, input);
				throw nvae;
			}

			switch (alt20) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:126:6: ( 'call' )
					{
					// src/aofC/AspectParser/AOC.g:126:6: ( 'call' )
					// src/aofC/AspectParser/AOC.g:126:7: 'call'
					{
					match(input,22,FOLLOW_22_in_method457); 
					joinPoint = MethodPointcutRule.JoinPoint.CALL;
					}

					}
					break;
				case 2 :
					// src/aofC/AspectParser/AOC.g:127:7: ( 'execute' )
					{
					// src/aofC/AspectParser/AOC.g:127:7: ( 'execute' )
					// src/aofC/AspectParser/AOC.g:127:8: 'execute'
					{
					match(input,23,FOLLOW_23_in_method469); 
					joinPoint = MethodPointcutRule.JoinPoint.EXECUTE;
					}

					}
					break;

			}

			match(input,WS,FOLLOW_WS_in_method479); 
			pushFollow(FOLLOW_type_in_method481);
			type9=type();
			state._fsp--;

			match(input,WS,FOLLOW_WS_in_method483); 
			NAME10=(Token)match(input,NAME,FOLLOW_NAME_in_method485); 
			// src/aofC/AspectParser/AOC.g:128:21: ( WS )?
			int alt21=2;
			int LA21_0 = input.LA(1);
			if ( (LA21_0==WS) ) {
				alt21=1;
			}
			switch (alt21) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:128:21: WS
					{
					match(input,WS,FOLLOW_WS_in_method487); 
					}
					break;

			}

			match(input,10,FOLLOW_10_in_method490); 
			pushFollow(FOLLOW_args_in_method492);
			args11=args();
			state._fsp--;

			match(input,11,FOLLOW_11_in_method494); 
			// src/aofC/AspectParser/AOC.g:128:38: ( WS )?
			int alt22=2;
			int LA22_0 = input.LA(1);
			if ( (LA22_0==WS) ) {
				alt22=1;
			}
			switch (alt22) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:128:38: WS
					{
					match(input,WS,FOLLOW_WS_in_method496); 
					}
					break;

			}

			match(input,15,FOLLOW_15_in_method499); 

			      pc = new MethodPointcutRule(joinPoint, type9, NAME10.getText(), args11);
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return pc;
	}
	// $ANTLR end "method"



	// $ANTLR start "args"
	// src/aofC/AspectParser/AOC.g:133:1: args returns [ArrayList<Argument> value = new ArrayList<Argument>()] : (s= type ( WS t= NAME )? ( ( WS )? ',' ( WS )? s= type ( WS t= NAME )? )* )? ;
	public final ArrayList<Argument> args() throws RecognitionException {
		ArrayList<Argument> value =  new ArrayList<Argument>();


		Token t=null;
		CType s =null;

		try {
			// src/aofC/AspectParser/AOC.g:134:3: ( (s= type ( WS t= NAME )? ( ( WS )? ',' ( WS )? s= type ( WS t= NAME )? )* )? )
			// src/aofC/AspectParser/AOC.g:134:5: (s= type ( WS t= NAME )? ( ( WS )? ',' ( WS )? s= type ( WS t= NAME )? )* )?
			{
			String name = new String();
			// src/aofC/AspectParser/AOC.g:135:5: (s= type ( WS t= NAME )? ( ( WS )? ',' ( WS )? s= type ( WS t= NAME )? )* )?
			int alt28=2;
			int LA28_0 = input.LA(1);
			if ( (LA28_0==NAME) ) {
				alt28=1;
			}
			switch (alt28) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:135:6: s= type ( WS t= NAME )? ( ( WS )? ',' ( WS )? s= type ( WS t= NAME )? )*
					{
					pushFollow(FOLLOW_type_in_args530);
					s=type();
					state._fsp--;

					// src/aofC/AspectParser/AOC.g:135:15: ( WS t= NAME )?
					int alt23=2;
					int LA23_0 = input.LA(1);
					if ( (LA23_0==WS) ) {
						int LA23_1 = input.LA(2);
						if ( (LA23_1==NAME) ) {
							alt23=1;
						}
					}
					switch (alt23) {
						case 1 :
							// src/aofC/AspectParser/AOC.g:135:16: WS t= NAME
							{
							match(input,WS,FOLLOW_WS_in_args533); 
							t=(Token)match(input,NAME,FOLLOW_NAME_in_args539); 
							name = t.getText();
							}
							break;

					}


					      value.add(new Argument(name, s));
					      name = new String();
					    
					// src/aofC/AspectParser/AOC.g:140:5: ( ( WS )? ',' ( WS )? s= type ( WS t= NAME )? )*
					loop27:
					while (true) {
						int alt27=2;
						int LA27_0 = input.LA(1);
						if ( (LA27_0==WS||LA27_0==13) ) {
							alt27=1;
						}

						switch (alt27) {
						case 1 :
							// src/aofC/AspectParser/AOC.g:140:6: ( WS )? ',' ( WS )? s= type ( WS t= NAME )?
							{
							// src/aofC/AspectParser/AOC.g:140:6: ( WS )?
							int alt24=2;
							int LA24_0 = input.LA(1);
							if ( (LA24_0==WS) ) {
								alt24=1;
							}
							switch (alt24) {
								case 1 :
									// src/aofC/AspectParser/AOC.g:140:6: WS
									{
									match(input,WS,FOLLOW_WS_in_args556); 
									}
									break;

							}

							match(input,13,FOLLOW_13_in_args559); 
							// src/aofC/AspectParser/AOC.g:140:14: ( WS )?
							int alt25=2;
							int LA25_0 = input.LA(1);
							if ( (LA25_0==WS) ) {
								alt25=1;
							}
							switch (alt25) {
								case 1 :
									// src/aofC/AspectParser/AOC.g:140:14: WS
									{
									match(input,WS,FOLLOW_WS_in_args561); 
									}
									break;

							}

							pushFollow(FOLLOW_type_in_args568);
							s=type();
							state._fsp--;

							// src/aofC/AspectParser/AOC.g:140:27: ( WS t= NAME )?
							int alt26=2;
							int LA26_0 = input.LA(1);
							if ( (LA26_0==WS) ) {
								int LA26_1 = input.LA(2);
								if ( (LA26_1==NAME) ) {
									alt26=1;
								}
							}
							switch (alt26) {
								case 1 :
									// src/aofC/AspectParser/AOC.g:140:28: WS t= NAME
									{
									match(input,WS,FOLLOW_WS_in_args571); 
									t=(Token)match(input,NAME,FOLLOW_NAME_in_args577); 
									name = t.getText();
									}
									break;

							}


							      value.add(new Argument(name, s));
							      name = new String();
							    
							}
							break;

						default :
							break loop27;
						}
					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "args"



	// $ANTLR start "type"
	// src/aofC/AspectParser/AOC.g:147:1: type returns [Type node] : ( NAME ( '[' ']' | '*' )? ) ;
	public final CType type() throws RecognitionException {
		CType node = null;


		Token NAME12=null;

		try {
			// src/aofC/AspectParser/AOC.g:148:3: ( ( NAME ( '[' ']' | '*' )? ) )
			// src/aofC/AspectParser/AOC.g:148:5: ( NAME ( '[' ']' | '*' )? )
			{
			// src/aofC/AspectParser/AOC.g:148:5: ( NAME ( '[' ']' | '*' )? )
			// src/aofC/AspectParser/AOC.g:148:6: NAME ( '[' ']' | '*' )?
			{
			NAME12=(Token)match(input,NAME,FOLLOW_NAME_in_type612); 

			      if (NAME12.getText().equals("int")) {
			        node = new PrimaryType(PrimaryType.PType.INT);
			      } else if (NAME12.getText().equals("float")) {
			        node = new PrimaryType(PrimaryType.PType.FLOAT);
			      } else if (NAME12.getText().equals("char")) {
			        node = new PrimaryType(PrimaryType.PType.CHAR);
			      } else if (NAME12.getText().equals("void")) {
			        node = new PrimaryType(PrimaryType.PType.VOID);
			      } else if (NAME12.getText().equals("bool")) {
			        node = new PrimaryType(PrimaryType.PType.BOOL);
			      } else if (NAME12.getText().equals("..")) {
			        node = new AnyType();
			      } else {
			        // We don't allow this in small C.
			        node = null;
			      }
			    
			// src/aofC/AspectParser/AOC.g:167:5: ( '[' ']' | '*' )?
			int alt29=3;
			int LA29_0 = input.LA(1);
			if ( (LA29_0==16) ) {
				alt29=1;
			}
			else if ( (LA29_0==12) ) {
				alt29=2;
			}
			switch (alt29) {
				case 1 :
					// src/aofC/AspectParser/AOC.g:167:6: '[' ']'
					{
					match(input,16,FOLLOW_16_in_type625); 
					match(input,17,FOLLOW_17_in_type627); 

					      if (node instanceof ArrayType) {
					        node = new ArrayType(((ArrayType) node).getType(), ((ArrayType) node).dimensions);
					      } else {
					        node = new ArrayType(node, 1);
					      }
					    
					}
					break;
				case 2 :
					// src/aofC/AspectParser/AOC.g:175:7: '*'
					{
					match(input,12,FOLLOW_12_in_type641); 

					      node = new PointerType(node);
					    
					}
					break;

			}

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return node;
	}
	// $ANTLR end "type"

	// Delegated rules



	public static final BitSet FOLLOW_WS_in_file51 = new BitSet(new long[]{0x00000000063C0200L});
	public static final BitSet FOLLOW_order_in_file55 = new BitSet(new long[]{0x00000000063C0200L});
	public static final BitSet FOLLOW_pointcut_in_file59 = new BitSet(new long[]{0x00000000063C0200L});
	public static final BitSet FOLLOW_advice_in_file63 = new BitSet(new long[]{0x00000000063C0200L});
	public static final BitSet FOLLOW_EOF_in_file67 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_25_in_order83 = new BitSet(new long[]{0x0000000010000200L});
	public static final BitSet FOLLOW_WS_in_order85 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_28_in_order88 = new BitSet(new long[]{0x0000000000000240L});
	public static final BitSet FOLLOW_WS_in_order90 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_order97 = new BitSet(new long[]{0x0000000020008200L});
	public static final BitSet FOLLOW_WS_in_order110 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_15_in_order113 = new BitSet(new long[]{0x0000000000000240L});
	public static final BitSet FOLLOW_WS_in_order115 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_order122 = new BitSet(new long[]{0x0000000020008200L});
	public static final BitSet FOLLOW_WS_in_order137 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_29_in_order140 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_18_in_advice153 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_WS_in_advice155 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_advice161 = new BitSet(new long[]{0x0000000000004200L});
	public static final BitSet FOLLOW_WS_in_advice173 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_advice176 = new BitSet(new long[]{0x0000000000380200L});
	public static final BitSet FOLLOW_WS_in_advice178 = new BitSet(new long[]{0x0000000000380000L});
	public static final BitSet FOLLOW_21_in_advice195 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_19_in_advice208 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_20_in_advice221 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_WS_in_advice231 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_advice237 = new BitSet(new long[]{0x0000000000000600L});
	public static final BitSet FOLLOW_WS_in_advice239 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_advice242 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_args_in_advice244 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_advice246 = new BitSet(new long[]{0x0000000010000200L});
	public static final BitSet FOLLOW_WS_in_advice248 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_28_in_advice263 = new BitSet(new long[]{0x000000003FFFFFF0L});
	public static final BitSet FOLLOW_29_in_advice274 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_26_in_pointcut293 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_WS_in_pointcut295 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_pointcut297 = new BitSet(new long[]{0x0000000000000600L});
	public static final BitSet FOLLOW_WS_in_pointcut299 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_pointcut302 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_args_in_pointcut304 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_pointcut306 = new BitSet(new long[]{0x0000000010000200L});
	public static final BitSet FOLLOW_WS_in_pointcut308 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_28_in_pointcut322 = new BitSet(new long[]{0x0000000029C00200L});
	public static final BitSet FOLLOW_rules_in_pointcut324 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_29_in_pointcut326 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_WS_in_rules347 = new BitSet(new long[]{0x0000000009C00202L});
	public static final BitSet FOLLOW_method_in_rules354 = new BitSet(new long[]{0x0000000009C00202L});
	public static final BitSet FOLLOW_member_in_rules364 = new BitSet(new long[]{0x0000000009C00202L});
	public static final BitSet FOLLOW_27_in_member396 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_24_in_member408 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_WS_in_member418 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_type_in_member420 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_WS_in_member422 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_member424 = new BitSet(new long[]{0x0000000000008200L});
	public static final BitSet FOLLOW_WS_in_member426 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_15_in_member429 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_22_in_method457 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_23_in_method469 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_WS_in_method479 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_type_in_method481 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_WS_in_method483 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_method485 = new BitSet(new long[]{0x0000000000000600L});
	public static final BitSet FOLLOW_WS_in_method487 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_method490 = new BitSet(new long[]{0x0000000000000840L});
	public static final BitSet FOLLOW_args_in_method492 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_method494 = new BitSet(new long[]{0x0000000000008200L});
	public static final BitSet FOLLOW_WS_in_method496 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_15_in_method499 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_in_args530 = new BitSet(new long[]{0x0000000000002202L});
	public static final BitSet FOLLOW_WS_in_args533 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_args539 = new BitSet(new long[]{0x0000000000002202L});
	public static final BitSet FOLLOW_WS_in_args556 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_args559 = new BitSet(new long[]{0x0000000000000240L});
	public static final BitSet FOLLOW_WS_in_args561 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_type_in_args568 = new BitSet(new long[]{0x0000000000002202L});
	public static final BitSet FOLLOW_WS_in_args571 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_args577 = new BitSet(new long[]{0x0000000000002202L});
	public static final BitSet FOLLOW_NAME_in_type612 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_16_in_type625 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_type627 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_type641 = new BitSet(new long[]{0x0000000000000002L});
}
