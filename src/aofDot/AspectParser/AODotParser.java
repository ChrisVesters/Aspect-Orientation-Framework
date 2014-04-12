// $ANTLR 3.5 src/aofDot/AspectParser/AODot.g 2014-01-09 23:32:10

package aofDot.AspectParser;

import aof.*;
import aofDot.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class AODotParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "DIGIT", "LETTER", "NAME", "WS", 
		"'('", "')'", "','", "'--'", "'->'", "'..'", "'..graph'", "':'", "';'", 
		"'='", "'Edge'", "'Graph'", "'Node'", "'['", "']'", "'advice'", "'delete'", 
		"'digraph'", "'graph'", "'insert'", "'order'", "'pointcut'", "'subgraph'", 
		"'{'", "'}'"
	};
	public static final int EOF=-1;
	public static final int T__8=8;
	public static final int T__9=9;
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
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int T__32=32;
	public static final int DIGIT=4;
	public static final int LETTER=5;
	public static final int NAME=6;
	public static final int WS=7;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public AODotParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public AODotParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return AODotParser.tokenNames; }
	@Override public String getGrammarFileName() { return "src/aofDot/AspectParser/AODot.g"; }


	static int counter = 0;



	// $ANTLR start "file"
	// src/aofDot/AspectParser/AODot.g:22:1: file : ( WS | order | pointcut | advice )* EOF ;
	public final void file() throws RecognitionException {
		try {
			// src/aofDot/AspectParser/AODot.g:22:5: ( ( WS | order | pointcut | advice )* EOF )
			// src/aofDot/AspectParser/AODot.g:22:7: ( WS | order | pointcut | advice )* EOF
			{
			// src/aofDot/AspectParser/AODot.g:22:7: ( WS | order | pointcut | advice )*
			loop1:
			while (true) {
				int alt1=5;
				switch ( input.LA(1) ) {
				case WS:
					{
					alt1=1;
					}
					break;
				case 28:
					{
					alt1=2;
					}
					break;
				case 29:
					{
					alt1=3;
					}
					break;
				case 23:
				case 24:
				case 27:
					{
					alt1=4;
					}
					break;
				}
				switch (alt1) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:22:8: WS
					{
					match(input,WS,FOLLOW_WS_in_file51); 
					}
					break;
				case 2 :
					// src/aofDot/AspectParser/AODot.g:22:13: order
					{
					pushFollow(FOLLOW_order_in_file55);
					order();
					state._fsp--;

					}
					break;
				case 3 :
					// src/aofDot/AspectParser/AODot.g:22:21: pointcut
					{
					pushFollow(FOLLOW_pointcut_in_file59);
					pointcut();
					state._fsp--;

					}
					break;
				case 4 :
					// src/aofDot/AspectParser/AODot.g:22:32: advice
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
	// src/aofDot/AspectParser/AODot.g:25:1: order : 'order' ( WS )? '{' ( WS )? aname= NAME ( ( WS )? ';' ( WS )? aname= NAME )* ( WS )? '}' ;
	public final void order() throws RecognitionException {
		Token aname=null;

		try {
			// src/aofDot/AspectParser/AODot.g:26:3: ( 'order' ( WS )? '{' ( WS )? aname= NAME ( ( WS )? ';' ( WS )? aname= NAME )* ( WS )? '}' )
			// src/aofDot/AspectParser/AODot.g:26:5: 'order' ( WS )? '{' ( WS )? aname= NAME ( ( WS )? ';' ( WS )? aname= NAME )* ( WS )? '}'
			{

			      String pred = new String();
			      String post = new String(); 
			    
			match(input,28,FOLLOW_28_in_order84); 
			// src/aofDot/AspectParser/AODot.g:30:13: ( WS )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==WS) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:30:13: WS
					{
					match(input,WS,FOLLOW_WS_in_order86); 
					}
					break;

			}

			match(input,31,FOLLOW_31_in_order89); 
			// src/aofDot/AspectParser/AODot.g:30:21: ( WS )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==WS) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:30:21: WS
					{
					match(input,WS,FOLLOW_WS_in_order91); 
					}
					break;

			}

			aname=(Token)match(input,NAME,FOLLOW_NAME_in_order98); 

			      assert (aname != null);  
			      pred = "_" + aname.getText();
			    
			// src/aofDot/AspectParser/AODot.g:35:5: ( ( WS )? ';' ( WS )? aname= NAME )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( (LA6_0==WS) ) {
					int LA6_1 = input.LA(2);
					if ( (LA6_1==16) ) {
						alt6=1;
					}

				}
				else if ( (LA6_0==16) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:35:6: ( WS )? ';' ( WS )? aname= NAME
					{
					// src/aofDot/AspectParser/AODot.g:35:6: ( WS )?
					int alt4=2;
					int LA4_0 = input.LA(1);
					if ( (LA4_0==WS) ) {
						alt4=1;
					}
					switch (alt4) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:35:6: WS
							{
							match(input,WS,FOLLOW_WS_in_order111); 
							}
							break;

					}

					match(input,16,FOLLOW_16_in_order114); 
					// src/aofDot/AspectParser/AODot.g:35:14: ( WS )?
					int alt5=2;
					int LA5_0 = input.LA(1);
					if ( (LA5_0==WS) ) {
						alt5=1;
					}
					switch (alt5) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:35:14: WS
							{
							match(input,WS,FOLLOW_WS_in_order116); 
							}
							break;

					}

					aname=(Token)match(input,NAME,FOLLOW_NAME_in_order123); 

					      assert (aname != null);  
					      post = "_" + aname.getText();
					      Weaver.addOrder(pred, post);
					      pred = post;
					    
					}
					break;

				default :
					break loop6;
				}
			}

			// src/aofDot/AspectParser/AODot.g:42:8: ( WS )?
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==WS) ) {
				alt7=1;
			}
			switch (alt7) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:42:8: WS
					{
					match(input,WS,FOLLOW_WS_in_order138); 
					}
					break;

			}

			match(input,32,FOLLOW_32_in_order141); 
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
	// src/aofDot/AspectParser/AODot.g:44:1: advice : ( 'advice' WS aname= NAME ( WS )? ':' ( WS )? )? ( ( 'insert' ) | ( 'delete' ) ) WS pname= NAME ( WS )? '(' args ')' ( WS )? '{' rules '}' ;
	public final void advice() throws RecognitionException {
		Token aname=null;
		Token pname=null;
		ArrayList<Argument> args1 =null;
		ArrayList<Pointcut> rules2 =null;

		try {
			// src/aofDot/AspectParser/AODot.g:45:3: ( ( 'advice' WS aname= NAME ( WS )? ':' ( WS )? )? ( ( 'insert' ) | ( 'delete' ) ) WS pname= NAME ( WS )? '(' args ')' ( WS )? '{' rules '}' )
			// src/aofDot/AspectParser/AODot.g:45:5: ( 'advice' WS aname= NAME ( WS )? ':' ( WS )? )? ( ( 'insert' ) | ( 'delete' ) ) WS pname= NAME ( WS )? '(' args ')' ( WS )? '{' rules '}'
			{
			// src/aofDot/AspectParser/AODot.g:45:5: ( 'advice' WS aname= NAME ( WS )? ':' ( WS )? )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==23) ) {
				alt10=1;
			}
			switch (alt10) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:45:6: 'advice' WS aname= NAME ( WS )? ':' ( WS )?
					{
					match(input,23,FOLLOW_23_in_advice152); 
					match(input,WS,FOLLOW_WS_in_advice154); 
					aname=(Token)match(input,NAME,FOLLOW_NAME_in_advice160); 
					// src/aofDot/AspectParser/AODot.g:45:31: ( WS )?
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0==WS) ) {
						alt8=1;
					}
					switch (alt8) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:45:31: WS
							{
							match(input,WS,FOLLOW_WS_in_advice162); 
							}
							break;

					}

					match(input,15,FOLLOW_15_in_advice165); 
					// src/aofDot/AspectParser/AODot.g:45:39: ( WS )?
					int alt9=2;
					int LA9_0 = input.LA(1);
					if ( (LA9_0==WS) ) {
						alt9=1;
					}
					switch (alt9) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:45:39: WS
							{
							match(input,WS,FOLLOW_WS_in_advice167); 
							}
							break;

					}

					}
					break;

			}

			Action action = null;
			// src/aofDot/AspectParser/AODot.g:47:5: ( ( 'insert' ) | ( 'delete' ) )
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0==27) ) {
				alt11=1;
			}
			else if ( (LA11_0==24) ) {
				alt11=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 11, 0, input);
				throw nvae;
			}

			switch (alt11) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:47:7: ( 'insert' )
					{
					// src/aofDot/AspectParser/AODot.g:47:7: ( 'insert' )
					// src/aofDot/AspectParser/AODot.g:47:8: 'insert'
					{
					match(input,27,FOLLOW_27_in_advice186); 
					action = Action.INSERT;
					}

					}
					break;
				case 2 :
					// src/aofDot/AspectParser/AODot.g:48:7: ( 'delete' )
					{
					// src/aofDot/AspectParser/AODot.g:48:7: ( 'delete' )
					// src/aofDot/AspectParser/AODot.g:48:8: 'delete'
					{
					match(input,24,FOLLOW_24_in_advice199); 
					action = Action.DELETE;
					}

					}
					break;

			}

			match(input,WS,FOLLOW_WS_in_advice215); 
			pname=(Token)match(input,NAME,FOLLOW_NAME_in_advice221); 
			// src/aofDot/AspectParser/AODot.g:50:21: ( WS )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==WS) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:50:21: WS
					{
					match(input,WS,FOLLOW_WS_in_advice223); 
					}
					break;

			}

			match(input,8,FOLLOW_8_in_advice226); 
			pushFollow(FOLLOW_args_in_advice228);
			args1=args();
			state._fsp--;

			match(input,9,FOLLOW_9_in_advice230); 
			// src/aofDot/AspectParser/AODot.g:50:38: ( WS )?
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0==WS) ) {
				alt13=1;
			}
			switch (alt13) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:50:38: WS
					{
					match(input,WS,FOLLOW_WS_in_advice232); 
					}
					break;

			}

			match(input,31,FOLLOW_31_in_advice235); 
			pushFollow(FOLLOW_rules_in_advice237);
			rules2=rules();
			state._fsp--;

			match(input,32,FOLLOW_32_in_advice239); 

			      String advicename;
			      if (aname == null) {
			        advicename = "UnnamedAdvice_" + counter;
			        counter++;
			      } else {
			        advicename = "_" + aname.getText();
			      }
			      
			      DotAdvice dotAd = new DotAdvice(advicename, args1, action, rules2);
			      Weaver.addAdvice(dotAd, pname.getText());
			    
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
	// src/aofDot/AspectParser/AODot.g:65:1: pointcut : 'pointcut' WS pname= NAME ( WS )? '(' args ')' ( WS )? '{' rules '}' ;
	public final void pointcut() throws RecognitionException {
		Token pname=null;
		ArrayList<Argument> args3 =null;
		ArrayList<Pointcut> rules4 =null;

		try {
			// src/aofDot/AspectParser/AODot.g:66:3: ( 'pointcut' WS pname= NAME ( WS )? '(' args ')' ( WS )? '{' rules '}' )
			// src/aofDot/AspectParser/AODot.g:66:5: 'pointcut' WS pname= NAME ( WS )? '(' args ')' ( WS )? '{' rules '}'
			{
			match(input,29,FOLLOW_29_in_pointcut259); 
			match(input,WS,FOLLOW_WS_in_pointcut261); 
			pname=(Token)match(input,NAME,FOLLOW_NAME_in_pointcut267); 
			// src/aofDot/AspectParser/AODot.g:66:32: ( WS )?
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==WS) ) {
				alt14=1;
			}
			switch (alt14) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:66:32: WS
					{
					match(input,WS,FOLLOW_WS_in_pointcut269); 
					}
					break;

			}

			match(input,8,FOLLOW_8_in_pointcut272); 
			pushFollow(FOLLOW_args_in_pointcut274);
			args3=args();
			state._fsp--;

			match(input,9,FOLLOW_9_in_pointcut276); 
			// src/aofDot/AspectParser/AODot.g:66:49: ( WS )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==WS) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:66:49: WS
					{
					match(input,WS,FOLLOW_WS_in_pointcut278); 
					}
					break;

			}

			match(input,31,FOLLOW_31_in_pointcut281); 
			pushFollow(FOLLOW_rules_in_pointcut283);
			rules4=rules();
			state._fsp--;

			match(input,32,FOLLOW_32_in_pointcut285); 

			     
			      PointcutSet pc = new PointcutSet(pname.getText(), args3, rules4);
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



	// $ANTLR start "args"
	// src/aofDot/AspectParser/AODot.g:73:1: args returns [ArrayList<Argument> args = new ArrayList<Argument>()] : ( ( WS )? t= type WS n= NAME ( ( WS )? ',' ( WS )? t= type WS n= NAME )* ( WS )? )? ;
	public final ArrayList<Argument> args() throws RecognitionException {
		ArrayList<Argument> args =  new ArrayList<Argument>();


		Token n=null;
		String t =null;

		try {
			// src/aofDot/AspectParser/AODot.g:74:3: ( ( ( WS )? t= type WS n= NAME ( ( WS )? ',' ( WS )? t= type WS n= NAME )* ( WS )? )? )
			// src/aofDot/AspectParser/AODot.g:74:5: ( ( WS )? t= type WS n= NAME ( ( WS )? ',' ( WS )? t= type WS n= NAME )* ( WS )? )?
			{
			// src/aofDot/AspectParser/AODot.g:74:5: ( ( WS )? t= type WS n= NAME ( ( WS )? ',' ( WS )? t= type WS n= NAME )* ( WS )? )?
			int alt21=2;
			int LA21_0 = input.LA(1);
			if ( (LA21_0==WS||(LA21_0 >= 18 && LA21_0 <= 20)) ) {
				alt21=1;
			}
			switch (alt21) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:74:6: ( WS )? t= type WS n= NAME ( ( WS )? ',' ( WS )? t= type WS n= NAME )* ( WS )?
					{
					// src/aofDot/AspectParser/AODot.g:74:6: ( WS )?
					int alt16=2;
					int LA16_0 = input.LA(1);
					if ( (LA16_0==WS) ) {
						alt16=1;
					}
					switch (alt16) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:74:6: WS
							{
							match(input,WS,FOLLOW_WS_in_args307); 
							}
							break;

					}

					pushFollow(FOLLOW_type_in_args314);
					t=type();
					state._fsp--;

					match(input,WS,FOLLOW_WS_in_args316); 
					n=(Token)match(input,NAME,FOLLOW_NAME_in_args322); 
					args.add(new Argument(n.getText(), new Value(t)));
					// src/aofDot/AspectParser/AODot.g:75:7: ( ( WS )? ',' ( WS )? t= type WS n= NAME )*
					loop19:
					while (true) {
						int alt19=2;
						int LA19_0 = input.LA(1);
						if ( (LA19_0==WS) ) {
							int LA19_1 = input.LA(2);
							if ( (LA19_1==10) ) {
								alt19=1;
							}

						}
						else if ( (LA19_0==10) ) {
							alt19=1;
						}

						switch (alt19) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:75:8: ( WS )? ',' ( WS )? t= type WS n= NAME
							{
							// src/aofDot/AspectParser/AODot.g:75:8: ( WS )?
							int alt17=2;
							int LA17_0 = input.LA(1);
							if ( (LA17_0==WS) ) {
								alt17=1;
							}
							switch (alt17) {
								case 1 :
									// src/aofDot/AspectParser/AODot.g:75:8: WS
									{
									match(input,WS,FOLLOW_WS_in_args333); 
									}
									break;

							}

							match(input,10,FOLLOW_10_in_args336); 
							// src/aofDot/AspectParser/AODot.g:75:16: ( WS )?
							int alt18=2;
							int LA18_0 = input.LA(1);
							if ( (LA18_0==WS) ) {
								alt18=1;
							}
							switch (alt18) {
								case 1 :
									// src/aofDot/AspectParser/AODot.g:75:16: WS
									{
									match(input,WS,FOLLOW_WS_in_args338); 
									}
									break;

							}

							pushFollow(FOLLOW_type_in_args345);
							t=type();
							state._fsp--;

							match(input,WS,FOLLOW_WS_in_args347); 
							n=(Token)match(input,NAME,FOLLOW_NAME_in_args353); 
							args.add(new Argument(n.getText(), new Value(t)));
							}
							break;

						default :
							break loop19;
						}
					}

					// src/aofDot/AspectParser/AODot.g:77:5: ( WS )?
					int alt20=2;
					int LA20_0 = input.LA(1);
					if ( (LA20_0==WS) ) {
						alt20=1;
					}
					switch (alt20) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:77:5: WS
							{
							match(input,WS,FOLLOW_WS_in_args370); 
							}
							break;

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
		return args;
	}
	// $ANTLR end "args"



	// $ANTLR start "rules"
	// src/aofDot/AspectParser/AODot.g:80:1: rules returns [ArrayList<PointcutRule> pcs = new ArrayList<PointcutRule>()] : ( WS | ( graph ) | ( node ';' ) | ( edge ';' ) )* ;
	public final ArrayList<Pointcut> rules() throws RecognitionException {
		ArrayList<Pointcut> pcs =  new ArrayList<Pointcut>();


		GraphPointcut graph5 =null;
		NodePointcut node6 =null;
		EdgePointcut edge7 =null;

		try {
			// src/aofDot/AspectParser/AODot.g:81:3: ( ( WS | ( graph ) | ( node ';' ) | ( edge ';' ) )* )
			// src/aofDot/AspectParser/AODot.g:81:5: ( WS | ( graph ) | ( node ';' ) | ( edge ';' ) )*
			{
			// src/aofDot/AspectParser/AODot.g:81:5: ( WS | ( graph ) | ( node ';' ) | ( edge ';' ) )*
			loop22:
			while (true) {
				int alt22=5;
				switch ( input.LA(1) ) {
				case WS:
					{
					alt22=1;
					}
					break;
				case 14:
				case 25:
				case 26:
				case 30:
					{
					alt22=2;
					}
					break;
				case NAME:
					{
					switch ( input.LA(2) ) {
					case WS:
						{
						int LA22_7 = input.LA(3);
						if ( (LA22_7==17) ) {
							switch ( input.LA(4) ) {
							case WS:
								{
								int LA22_9 = input.LA(5);
								if ( (LA22_9==NAME||LA22_9==13) ) {
									alt22=3;
								}
								else if ( (LA22_9==8) ) {
									alt22=4;
								}

								}
								break;
							case NAME:
							case 13:
								{
								alt22=3;
								}
								break;
							case 8:
								{
								alt22=4;
								}
								break;
							}
						}
						else if ( (LA22_7==21) ) {
							alt22=3;
						}

						}
						break;
					case 17:
						{
						switch ( input.LA(3) ) {
						case WS:
							{
							int LA22_9 = input.LA(4);
							if ( (LA22_9==NAME||LA22_9==13) ) {
								alt22=3;
							}
							else if ( (LA22_9==8) ) {
								alt22=4;
							}

							}
							break;
						case NAME:
						case 13:
							{
							alt22=3;
							}
							break;
						case 8:
							{
							alt22=4;
							}
							break;
						}
						}
						break;
					case 16:
					case 21:
						{
						alt22=3;
						}
						break;
					}
					}
					break;
				case 13:
					{
					alt22=3;
					}
					break;
				case 8:
					{
					alt22=4;
					}
					break;
				}
				switch (alt22) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:81:6: WS
					{
					match(input,WS,FOLLOW_WS_in_rules391); 
					}
					break;
				case 2 :
					// src/aofDot/AspectParser/AODot.g:82:7: ( graph )
					{
					// src/aofDot/AspectParser/AODot.g:82:7: ( graph )
					// src/aofDot/AspectParser/AODot.g:82:8: graph
					{
					pushFollow(FOLLOW_graph_in_rules400);
					graph5=graph();
					state._fsp--;

					pcs.add(graph5);
					}

					}
					break;
				case 3 :
					// src/aofDot/AspectParser/AODot.g:83:7: ( node ';' )
					{
					// src/aofDot/AspectParser/AODot.g:83:7: ( node ';' )
					// src/aofDot/AspectParser/AODot.g:83:8: node ';'
					{
					pushFollow(FOLLOW_node_in_rules412);
					node6=node();
					state._fsp--;

					pcs.add(node6);
					match(input,16,FOLLOW_16_in_rules416); 
					}

					}
					break;
				case 4 :
					// src/aofDot/AspectParser/AODot.g:84:7: ( edge ';' )
					{
					// src/aofDot/AspectParser/AODot.g:84:7: ( edge ';' )
					// src/aofDot/AspectParser/AODot.g:84:8: edge ';'
					{
					pushFollow(FOLLOW_edge_in_rules426);
					edge7=edge();
					state._fsp--;

					pcs.add(edge7);
					match(input,16,FOLLOW_16_in_rules430); 
					}

					}
					break;

				default :
					break loop22;
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



	// $ANTLR start "graph"
	// src/aofDot/AspectParser/AODot.g:87:1: graph returns [GraphPointcutRule pc] : ( '..graph' | 'graph' | 'digraph' | 'subgraph' ) WS n= ( NAME | '..' ) ( WS )? '{' rules '}' ;
	public final GraphPointcut graph() throws RecognitionException {
		GraphPointcut pc = null;


		Token n=null;
		ArrayList<Pointcut> rules8 =null;

		try {
			// src/aofDot/AspectParser/AODot.g:88:3: ( ( '..graph' | 'graph' | 'digraph' | 'subgraph' ) WS n= ( NAME | '..' ) ( WS )? '{' rules '}' )
			// src/aofDot/AspectParser/AODot.g:88:5: ( '..graph' | 'graph' | 'digraph' | 'subgraph' ) WS n= ( NAME | '..' ) ( WS )? '{' rules '}'
			{
			if ( input.LA(1)==14||(input.LA(1) >= 25 && input.LA(1) <= 26)||input.LA(1)==30 ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			match(input,WS,FOLLOW_WS_in_graph468); 
			n=input.LT(1);
			if ( input.LA(1)==NAME||input.LA(1)==13 ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			// src/aofDot/AspectParser/AODot.g:88:73: ( WS )?
			int alt23=2;
			int LA23_0 = input.LA(1);
			if ( (LA23_0==WS) ) {
				alt23=1;
			}
			switch (alt23) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:88:73: WS
					{
					match(input,WS,FOLLOW_WS_in_graph482); 
					}
					break;

			}

			match(input,31,FOLLOW_31_in_graph485); 
			pushFollow(FOLLOW_rules_in_graph487);
			rules8=rules();
			state._fsp--;

			match(input,32,FOLLOW_32_in_graph489); 

			      pc = new GraphPointcut(n.getText(), rules8, new ArrayList<Argument>());
			    
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
	// $ANTLR end "graph"



	// $ANTLR start "edge"
	// src/aofDot/AspectParser/AODot.g:94:1: edge returns [EdgePointcutRule pc] : ( NAME ( WS )? '=' ( WS )? )? '(' ( WS )? s= node ( WS )? ')' ( WS )? ( '->' | '--' ) ( WS )? '(' ( WS )? t= node ( WS )? ')' ( ( WS )? '[' attributes ']' )? ;
	public final EdgePointcut edge() throws RecognitionException {
		EdgePointcut pc = null;


		Token NAME10=null;
		NodePointcut s =null;
		NodePointcut t =null;
		ArrayList<Argument> attributes9 =null;

		try {
			// src/aofDot/AspectParser/AODot.g:95:3: ( ( NAME ( WS )? '=' ( WS )? )? '(' ( WS )? s= node ( WS )? ')' ( WS )? ( '->' | '--' ) ( WS )? '(' ( WS )? t= node ( WS )? ')' ( ( WS )? '[' attributes ']' )? )
			// src/aofDot/AspectParser/AODot.g:95:5: ( NAME ( WS )? '=' ( WS )? )? '(' ( WS )? s= node ( WS )? ')' ( WS )? ( '->' | '--' ) ( WS )? '(' ( WS )? t= node ( WS )? ')' ( ( WS )? '[' attributes ']' )?
			{

			      boolean directed = false;
			    
			// src/aofDot/AspectParser/AODot.g:98:5: ( NAME ( WS )? '=' ( WS )? )?
			int alt26=2;
			int LA26_0 = input.LA(1);
			if ( (LA26_0==NAME) ) {
				alt26=1;
			}
			switch (alt26) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:98:6: NAME ( WS )? '=' ( WS )?
					{
					NAME10=(Token)match(input,NAME,FOLLOW_NAME_in_edge521); 
					// src/aofDot/AspectParser/AODot.g:98:11: ( WS )?
					int alt24=2;
					int LA24_0 = input.LA(1);
					if ( (LA24_0==WS) ) {
						alt24=1;
					}
					switch (alt24) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:98:11: WS
							{
							match(input,WS,FOLLOW_WS_in_edge523); 
							}
							break;

					}

					match(input,17,FOLLOW_17_in_edge526); 
					// src/aofDot/AspectParser/AODot.g:98:19: ( WS )?
					int alt25=2;
					int LA25_0 = input.LA(1);
					if ( (LA25_0==WS) ) {
						alt25=1;
					}
					switch (alt25) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:98:19: WS
							{
							match(input,WS,FOLLOW_WS_in_edge528); 
							}
							break;

					}

					}
					break;

			}

			match(input,8,FOLLOW_8_in_edge533); 
			// src/aofDot/AspectParser/AODot.g:98:29: ( WS )?
			int alt27=2;
			int LA27_0 = input.LA(1);
			if ( (LA27_0==WS) ) {
				alt27=1;
			}
			switch (alt27) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:98:29: WS
					{
					match(input,WS,FOLLOW_WS_in_edge535); 
					}
					break;

			}

			pushFollow(FOLLOW_node_in_edge542);
			s=node();
			state._fsp--;

			// src/aofDot/AspectParser/AODot.g:98:42: ( WS )?
			int alt28=2;
			int LA28_0 = input.LA(1);
			if ( (LA28_0==WS) ) {
				alt28=1;
			}
			switch (alt28) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:98:42: WS
					{
					match(input,WS,FOLLOW_WS_in_edge544); 
					}
					break;

			}

			match(input,9,FOLLOW_9_in_edge547); 
			// src/aofDot/AspectParser/AODot.g:98:50: ( WS )?
			int alt29=2;
			int LA29_0 = input.LA(1);
			if ( (LA29_0==WS) ) {
				alt29=1;
			}
			switch (alt29) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:98:50: WS
					{
					match(input,WS,FOLLOW_WS_in_edge549); 
					}
					break;

			}

			// src/aofDot/AspectParser/AODot.g:98:54: ( '->' | '--' )
			int alt30=2;
			int LA30_0 = input.LA(1);
			if ( (LA30_0==12) ) {
				alt30=1;
			}
			else if ( (LA30_0==11) ) {
				alt30=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 30, 0, input);
				throw nvae;
			}

			switch (alt30) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:98:55: '->'
					{
					match(input,12,FOLLOW_12_in_edge553); 
					directed = true;
					}
					break;
				case 2 :
					// src/aofDot/AspectParser/AODot.g:98:81: '--'
					{
					match(input,11,FOLLOW_11_in_edge559); 
					}
					break;

			}

			// src/aofDot/AspectParser/AODot.g:98:87: ( WS )?
			int alt31=2;
			int LA31_0 = input.LA(1);
			if ( (LA31_0==WS) ) {
				alt31=1;
			}
			switch (alt31) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:98:87: WS
					{
					match(input,WS,FOLLOW_WS_in_edge562); 
					}
					break;

			}

			match(input,8,FOLLOW_8_in_edge565); 
			// src/aofDot/AspectParser/AODot.g:98:95: ( WS )?
			int alt32=2;
			int LA32_0 = input.LA(1);
			if ( (LA32_0==WS) ) {
				alt32=1;
			}
			switch (alt32) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:98:95: WS
					{
					match(input,WS,FOLLOW_WS_in_edge567); 
					}
					break;

			}

			pushFollow(FOLLOW_node_in_edge574);
			t=node();
			state._fsp--;

			// src/aofDot/AspectParser/AODot.g:98:108: ( WS )?
			int alt33=2;
			int LA33_0 = input.LA(1);
			if ( (LA33_0==WS) ) {
				alt33=1;
			}
			switch (alt33) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:98:108: WS
					{
					match(input,WS,FOLLOW_WS_in_edge576); 
					}
					break;

			}

			match(input,9,FOLLOW_9_in_edge579); 
			// src/aofDot/AspectParser/AODot.g:98:116: ( ( WS )? '[' attributes ']' )?
			int alt35=2;
			int LA35_0 = input.LA(1);
			if ( (LA35_0==WS||LA35_0==21) ) {
				alt35=1;
			}
			switch (alt35) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:98:117: ( WS )? '[' attributes ']'
					{
					// src/aofDot/AspectParser/AODot.g:98:117: ( WS )?
					int alt34=2;
					int LA34_0 = input.LA(1);
					if ( (LA34_0==WS) ) {
						alt34=1;
					}
					switch (alt34) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:98:117: WS
							{
							match(input,WS,FOLLOW_WS_in_edge582); 
							}
							break;

					}

					match(input,21,FOLLOW_21_in_edge585); 
					pushFollow(FOLLOW_attributes_in_edge587);
					attributes9=attributes();
					state._fsp--;

					match(input,22,FOLLOW_22_in_edge589); 
					}
					break;

			}


			      ArrayList<Argument> args = new ArrayList<Argument>();
			      if (attributes9 != null) {
			        args = attributes9;
			      }
			     
			      if (NAME10 != null) {
			        args.add(0, new Argument("_this", new Value(NAME10.getText())));
			      }
			      
			      pc = new EdgePointcut(s, t, directed, args);
			    
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
	// $ANTLR end "edge"



	// $ANTLR start "node"
	// src/aofDot/AspectParser/AODot.g:113:1: node returns [NodePointcutRule pc] : n= ( NAME | '..' ) ( ( WS )? '=' ( WS )? m= ( NAME | '..' ) )? ( ( WS )? '[' attributes ']' )? ;
	public final NodePointcut node() throws RecognitionException {
		NodePointcut pc = null;


		Token n=null;
		Token m=null;
		ArrayList<Argument> attributes11 =null;

		try {
			// src/aofDot/AspectParser/AODot.g:115:3: (n= ( NAME | '..' ) ( ( WS )? '=' ( WS )? m= ( NAME | '..' ) )? ( ( WS )? '[' attributes ']' )? )
			// src/aofDot/AspectParser/AODot.g:115:5: n= ( NAME | '..' ) ( ( WS )? '=' ( WS )? m= ( NAME | '..' ) )? ( ( WS )? '[' attributes ']' )?
			{
			n=input.LT(1);
			if ( input.LA(1)==NAME||input.LA(1)==13 ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			// src/aofDot/AspectParser/AODot.g:115:23: ( ( WS )? '=' ( WS )? m= ( NAME | '..' ) )?
			int alt38=2;
			int LA38_0 = input.LA(1);
			if ( (LA38_0==WS) ) {
				int LA38_1 = input.LA(2);
				if ( (LA38_1==17) ) {
					alt38=1;
				}
			}
			else if ( (LA38_0==17) ) {
				alt38=1;
			}
			switch (alt38) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:115:24: ( WS )? '=' ( WS )? m= ( NAME | '..' )
					{
					// src/aofDot/AspectParser/AODot.g:115:24: ( WS )?
					int alt36=2;
					int LA36_0 = input.LA(1);
					if ( (LA36_0==WS) ) {
						alt36=1;
					}
					switch (alt36) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:115:24: WS
							{
							match(input,WS,FOLLOW_WS_in_node628); 
							}
							break;

					}

					match(input,17,FOLLOW_17_in_node631); 
					// src/aofDot/AspectParser/AODot.g:115:32: ( WS )?
					int alt37=2;
					int LA37_0 = input.LA(1);
					if ( (LA37_0==WS) ) {
						alt37=1;
					}
					switch (alt37) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:115:32: WS
							{
							match(input,WS,FOLLOW_WS_in_node633); 
							}
							break;

					}

					m=input.LT(1);
					if ( input.LA(1)==NAME||input.LA(1)==13 ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;

			}

			// src/aofDot/AspectParser/AODot.g:115:56: ( ( WS )? '[' attributes ']' )?
			int alt40=2;
			int LA40_0 = input.LA(1);
			if ( (LA40_0==WS) ) {
				int LA40_1 = input.LA(2);
				if ( (LA40_1==21) ) {
					alt40=1;
				}
			}
			else if ( (LA40_0==21) ) {
				alt40=1;
			}
			switch (alt40) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:115:57: ( WS )? '[' attributes ']'
					{
					// src/aofDot/AspectParser/AODot.g:115:57: ( WS )?
					int alt39=2;
					int LA39_0 = input.LA(1);
					if ( (LA39_0==WS) ) {
						alt39=1;
					}
					switch (alt39) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:115:57: WS
							{
							match(input,WS,FOLLOW_WS_in_node651); 
							}
							break;

					}

					match(input,21,FOLLOW_21_in_node654); 
					pushFollow(FOLLOW_attributes_in_node656);
					attributes11=attributes();
					state._fsp--;

					match(input,22,FOLLOW_22_in_node658); 
					}
					break;

			}


			      ArrayList<Argument> args = new ArrayList<Argument>();
			      if (attributes11 != null) {
			        args = attributes11;
			      }
			      
			      String nodeName = n.getText();
			      if (m != null) {
			        args.add(0, new Argument("_this", new Value(nodeName)));
			        nodeName = m.getText();
			      }
			      
			      pc = new NodePointcut(nodeName, args);
			    
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
	// $ANTLR end "node"



	// $ANTLR start "attributes"
	// src/aofDot/AspectParser/AODot.g:132:1: attributes returns [ArrayList<Argument> args = new ArrayList<Argument>()] : (aname= NAME ( WS )? '=' ( WS )? aValue= ( NAME | '..' ) ( ( WS )? ',' ( WS )? a= attributes )? | '..' );
	public final ArrayList<Argument> attributes() throws RecognitionException {
		ArrayList<Argument> args =  new ArrayList<Argument>();


		Token aname=null;
		Token aValue=null;
		ArrayList<Argument> a =null;

		try {
			// src/aofDot/AspectParser/AODot.g:133:3: (aname= NAME ( WS )? '=' ( WS )? aValue= ( NAME | '..' ) ( ( WS )? ',' ( WS )? a= attributes )? | '..' )
			int alt46=2;
			int LA46_0 = input.LA(1);
			if ( (LA46_0==NAME) ) {
				alt46=1;
			}
			else if ( (LA46_0==13) ) {
				alt46=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 46, 0, input);
				throw nvae;
			}

			switch (alt46) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:133:5: aname= NAME ( WS )? '=' ( WS )? aValue= ( NAME | '..' ) ( ( WS )? ',' ( WS )? a= attributes )?
					{
					aname=(Token)match(input,NAME,FOLLOW_NAME_in_attributes687); 
					// src/aofDot/AspectParser/AODot.g:133:18: ( WS )?
					int alt41=2;
					int LA41_0 = input.LA(1);
					if ( (LA41_0==WS) ) {
						alt41=1;
					}
					switch (alt41) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:133:18: WS
							{
							match(input,WS,FOLLOW_WS_in_attributes689); 
							}
							break;

					}

					match(input,17,FOLLOW_17_in_attributes692); 
					// src/aofDot/AspectParser/AODot.g:133:26: ( WS )?
					int alt42=2;
					int LA42_0 = input.LA(1);
					if ( (LA42_0==WS) ) {
						alt42=1;
					}
					switch (alt42) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:133:26: WS
							{
							match(input,WS,FOLLOW_WS_in_attributes694); 
							}
							break;

					}

					aValue=input.LT(1);
					if ( input.LA(1)==NAME||input.LA(1)==13 ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}

					      Value val = new Value(aValue.getText());
					      args.add(new Argument(aname.getText(), val));
					    
					// src/aofDot/AspectParser/AODot.g:138:5: ( ( WS )? ',' ( WS )? a= attributes )?
					int alt45=2;
					int LA45_0 = input.LA(1);
					if ( (LA45_0==WS||LA45_0==10) ) {
						alt45=1;
					}
					switch (alt45) {
						case 1 :
							// src/aofDot/AspectParser/AODot.g:138:6: ( WS )? ',' ( WS )? a= attributes
							{
							// src/aofDot/AspectParser/AODot.g:138:6: ( WS )?
							int alt43=2;
							int LA43_0 = input.LA(1);
							if ( (LA43_0==WS) ) {
								alt43=1;
							}
							switch (alt43) {
								case 1 :
									// src/aofDot/AspectParser/AODot.g:138:6: WS
									{
									match(input,WS,FOLLOW_WS_in_attributes720); 
									}
									break;

							}

							match(input,10,FOLLOW_10_in_attributes723); 
							// src/aofDot/AspectParser/AODot.g:138:14: ( WS )?
							int alt44=2;
							int LA44_0 = input.LA(1);
							if ( (LA44_0==WS) ) {
								alt44=1;
							}
							switch (alt44) {
								case 1 :
									// src/aofDot/AspectParser/AODot.g:138:14: WS
									{
									match(input,WS,FOLLOW_WS_in_attributes725); 
									}
									break;

							}

							pushFollow(FOLLOW_attributes_in_attributes732);
							a=attributes();
							state._fsp--;

							args.addAll(a);
							}
							break;

					}

					}
					break;
				case 2 :
					// src/aofDot/AspectParser/AODot.g:139:5: '..'
					{
					match(input,13,FOLLOW_13_in_attributes742); 

					      args.add(new Argument(null, new AnyType()));
					    
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return args;
	}
	// $ANTLR end "attributes"



	// $ANTLR start "type"
	// src/aofDot/AspectParser/AODot.g:145:1: type returns [String type] : ( 'Node' | 'Edge' | 'Graph' );
	public final String type() throws RecognitionException {
		String type = null;


		try {
			// src/aofDot/AspectParser/AODot.g:146:3: ( 'Node' | 'Edge' | 'Graph' )
			int alt47=3;
			switch ( input.LA(1) ) {
			case 20:
				{
				alt47=1;
				}
				break;
			case 18:
				{
				alt47=2;
				}
				break;
			case 19:
				{
				alt47=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 47, 0, input);
				throw nvae;
			}
			switch (alt47) {
				case 1 :
					// src/aofDot/AspectParser/AODot.g:146:5: 'Node'
					{
					match(input,20,FOLLOW_20_in_type765); 
					type = "Node";
					}
					break;
				case 2 :
					// src/aofDot/AspectParser/AODot.g:147:5: 'Edge'
					{
					match(input,18,FOLLOW_18_in_type773); 
					type = "Edge";
					}
					break;
				case 3 :
					// src/aofDot/AspectParser/AODot.g:148:5: 'Graph'
					{
					match(input,19,FOLLOW_19_in_type781); 
					type = "Graph";
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return type;
	}
	// $ANTLR end "type"

	// Delegated rules



	public static final BitSet FOLLOW_WS_in_file51 = new BitSet(new long[]{0x0000000039800080L});
	public static final BitSet FOLLOW_order_in_file55 = new BitSet(new long[]{0x0000000039800080L});
	public static final BitSet FOLLOW_pointcut_in_file59 = new BitSet(new long[]{0x0000000039800080L});
	public static final BitSet FOLLOW_advice_in_file63 = new BitSet(new long[]{0x0000000039800080L});
	public static final BitSet FOLLOW_EOF_in_file67 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_28_in_order84 = new BitSet(new long[]{0x0000000080000080L});
	public static final BitSet FOLLOW_WS_in_order86 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_order89 = new BitSet(new long[]{0x00000000000000C0L});
	public static final BitSet FOLLOW_WS_in_order91 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_order98 = new BitSet(new long[]{0x0000000100010080L});
	public static final BitSet FOLLOW_WS_in_order111 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_16_in_order114 = new BitSet(new long[]{0x00000000000000C0L});
	public static final BitSet FOLLOW_WS_in_order116 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_order123 = new BitSet(new long[]{0x0000000100010080L});
	public static final BitSet FOLLOW_WS_in_order138 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_order141 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_23_in_advice152 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_WS_in_advice154 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_advice160 = new BitSet(new long[]{0x0000000000008080L});
	public static final BitSet FOLLOW_WS_in_advice162 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_15_in_advice165 = new BitSet(new long[]{0x0000000009000080L});
	public static final BitSet FOLLOW_WS_in_advice167 = new BitSet(new long[]{0x0000000009000000L});
	public static final BitSet FOLLOW_27_in_advice186 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_24_in_advice199 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_WS_in_advice215 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_advice221 = new BitSet(new long[]{0x0000000000000180L});
	public static final BitSet FOLLOW_WS_in_advice223 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_8_in_advice226 = new BitSet(new long[]{0x00000000001C0280L});
	public static final BitSet FOLLOW_args_in_advice228 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_advice230 = new BitSet(new long[]{0x0000000080000080L});
	public static final BitSet FOLLOW_WS_in_advice232 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_advice235 = new BitSet(new long[]{0x00000001460061C0L});
	public static final BitSet FOLLOW_rules_in_advice237 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_advice239 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_29_in_pointcut259 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_WS_in_pointcut261 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_pointcut267 = new BitSet(new long[]{0x0000000000000180L});
	public static final BitSet FOLLOW_WS_in_pointcut269 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_8_in_pointcut272 = new BitSet(new long[]{0x00000000001C0280L});
	public static final BitSet FOLLOW_args_in_pointcut274 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_pointcut276 = new BitSet(new long[]{0x0000000080000080L});
	public static final BitSet FOLLOW_WS_in_pointcut278 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_pointcut281 = new BitSet(new long[]{0x00000001460061C0L});
	public static final BitSet FOLLOW_rules_in_pointcut283 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_pointcut285 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_WS_in_args307 = new BitSet(new long[]{0x00000000001C0000L});
	public static final BitSet FOLLOW_type_in_args314 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_WS_in_args316 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_args322 = new BitSet(new long[]{0x0000000000000482L});
	public static final BitSet FOLLOW_WS_in_args333 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_args336 = new BitSet(new long[]{0x00000000001C0080L});
	public static final BitSet FOLLOW_WS_in_args338 = new BitSet(new long[]{0x00000000001C0000L});
	public static final BitSet FOLLOW_type_in_args345 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_WS_in_args347 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NAME_in_args353 = new BitSet(new long[]{0x0000000000000482L});
	public static final BitSet FOLLOW_WS_in_args370 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_WS_in_rules391 = new BitSet(new long[]{0x00000000460061C2L});
	public static final BitSet FOLLOW_graph_in_rules400 = new BitSet(new long[]{0x00000000460061C2L});
	public static final BitSet FOLLOW_node_in_rules412 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_16_in_rules416 = new BitSet(new long[]{0x00000000460061C2L});
	public static final BitSet FOLLOW_edge_in_rules426 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_16_in_rules430 = new BitSet(new long[]{0x00000000460061C2L});
	public static final BitSet FOLLOW_set_in_graph452 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_WS_in_graph468 = new BitSet(new long[]{0x0000000000002040L});
	public static final BitSet FOLLOW_set_in_graph474 = new BitSet(new long[]{0x0000000080000080L});
	public static final BitSet FOLLOW_WS_in_graph482 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_graph485 = new BitSet(new long[]{0x00000001460061C0L});
	public static final BitSet FOLLOW_rules_in_graph487 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_graph489 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_edge521 = new BitSet(new long[]{0x0000000000020080L});
	public static final BitSet FOLLOW_WS_in_edge523 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_edge526 = new BitSet(new long[]{0x0000000000000180L});
	public static final BitSet FOLLOW_WS_in_edge528 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_8_in_edge533 = new BitSet(new long[]{0x00000000000020C0L});
	public static final BitSet FOLLOW_WS_in_edge535 = new BitSet(new long[]{0x0000000000002040L});
	public static final BitSet FOLLOW_node_in_edge542 = new BitSet(new long[]{0x0000000000000280L});
	public static final BitSet FOLLOW_WS_in_edge544 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_edge547 = new BitSet(new long[]{0x0000000000001880L});
	public static final BitSet FOLLOW_WS_in_edge549 = new BitSet(new long[]{0x0000000000001800L});
	public static final BitSet FOLLOW_12_in_edge553 = new BitSet(new long[]{0x0000000000000180L});
	public static final BitSet FOLLOW_11_in_edge559 = new BitSet(new long[]{0x0000000000000180L});
	public static final BitSet FOLLOW_WS_in_edge562 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_8_in_edge565 = new BitSet(new long[]{0x00000000000020C0L});
	public static final BitSet FOLLOW_WS_in_edge567 = new BitSet(new long[]{0x0000000000002040L});
	public static final BitSet FOLLOW_node_in_edge574 = new BitSet(new long[]{0x0000000000000280L});
	public static final BitSet FOLLOW_WS_in_edge576 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_edge579 = new BitSet(new long[]{0x0000000000200082L});
	public static final BitSet FOLLOW_WS_in_edge582 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_edge585 = new BitSet(new long[]{0x0000000000002040L});
	public static final BitSet FOLLOW_attributes_in_edge587 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_edge589 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_node619 = new BitSet(new long[]{0x0000000000220082L});
	public static final BitSet FOLLOW_WS_in_node628 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_node631 = new BitSet(new long[]{0x00000000000020C0L});
	public static final BitSet FOLLOW_WS_in_node633 = new BitSet(new long[]{0x0000000000002040L});
	public static final BitSet FOLLOW_set_in_node640 = new BitSet(new long[]{0x0000000000200082L});
	public static final BitSet FOLLOW_WS_in_node651 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_node654 = new BitSet(new long[]{0x0000000000002040L});
	public static final BitSet FOLLOW_attributes_in_node656 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_node658 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_attributes687 = new BitSet(new long[]{0x0000000000020080L});
	public static final BitSet FOLLOW_WS_in_attributes689 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_attributes692 = new BitSet(new long[]{0x00000000000020C0L});
	public static final BitSet FOLLOW_WS_in_attributes694 = new BitSet(new long[]{0x0000000000002040L});
	public static final BitSet FOLLOW_set_in_attributes701 = new BitSet(new long[]{0x0000000000000482L});
	public static final BitSet FOLLOW_WS_in_attributes720 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_attributes723 = new BitSet(new long[]{0x00000000000020C0L});
	public static final BitSet FOLLOW_WS_in_attributes725 = new BitSet(new long[]{0x0000000000002040L});
	public static final BitSet FOLLOW_attributes_in_attributes732 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_13_in_attributes742 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_20_in_type765 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_18_in_type773 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_19_in_type781 = new BitSet(new long[]{0x0000000000000002L});
}
