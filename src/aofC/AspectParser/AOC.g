grammar AOC;

options {
  language = Java;
}

@parser::header {
package aofC.AspectParser;

import aof.*;
import aofC.*;
import aofC.Cparser.symbolTable.AnyType;
import aofC.Cparser.symbolTable.ArrayType;
import aofC.Cparser.symbolTable.PrimaryType;
import aofC.Cparser.symbolTable.PointerType;
import aofC.Cparser.symbolTable.Type;
}

@parser::members {
static int counter = 0;
}

@lexer::header {
package aofC.AspectParser;
}

file: (WS | order | pointcut | advice)* EOF;

order
  : {
      String pred = new String();
      String post = new String(); 
    }
    'order' WS? '{' WS? aName = NAME
    {
      assert ($aName != null);  
      pred = "_" + $aName.getText();
    }
    (WS? ';' WS? aName = NAME
    {
      assert ($aName != null);  
      post = "_" + $aName.getText();
      Weaver.addOrder(pred, post);
      pred = post;
    }
    )* WS? '}';

// TODO: different types of after & around.
// TODO: add proceed for around.
advice
  : ('advice' WS aName = NAME
    {
      if ($aName.getText().contains("*"))
      {
        throw new RecognitionException();
      }
    }
    WS? ':' WS?)?
    {Moment moment = null;}
    (('before'  {moment = Moment.BEFORE;})
    | ('after'  {moment = Moment.AFTER;})
    | ('around' {moment = Moment.AROUND;}))
    WS pName = NAME WS? '(' args ')' WS?  // TODO: use args!
    {
      if ($pName.getText().contains("*"))
      {
        throw new RecognitionException();
      }
    }
    '{' value += (.*) '}'
    {
      String code = new String();
      for (Object token: $value) {
        code += ((Token) token).getText();
      }

      String adviceName;
      if ($aName == null)
      {
        adviceName = "UnnamedAdvice_" + counter;
        counter++;
      }
      else
      {
        adviceName = "_" + $aName.getText();
      }
      CAdvice cAd = new CAdvice(adviceName, $args.value, moment, code);
      Weaver.addAdvice(cAd, $pName.getText());
    };

// TODO: add within(), cflow() & cflowBelow()
// TODO: add (':' ('args' | 'this' | 'target'))? !!!
// Note: We then need || && and ()
pointcut
  : 'pointcut' WS NAME WS? '(' args ')' WS? // TODO: use args
    {
      if ($NAME.getText().contains("*")) {
        throw new RecognitionException();
      }
    }
    '{' rules '}'
    {
      // TODO: check only named arguments.
      Pointcut pc = new Pointcut($NAME.getText(), $args.value, $rules.pcs);
      Weaver.addPointcut(pc);
    };

rules returns [ArrayList<PointcutRule> pcs = new ArrayList<PointcutRule>()]
  : (WS
  | (method {$pcs.add($method.pc);})
  | (member {$pcs.add($member.pc);})
    )*;

member returns [MemberPointcutRule pc]
  : {MemberPointcutRule.JoinPoint joinPoint = null;}
    (('set' {joinPoint = MemberPointcutRule.JoinPoint.SET;})
    | ('get' {joinPoint = MemberPointcutRule.JoinPoint.GET;}))
    WS type WS NAME WS? ';'
    {
      Argument arg = new Argument($NAME.getText(), $type.node);
      $pc = new MemberPointcutRule(joinPoint, arg);
    };

method returns [MethodPointcutRule pc]
  : {MethodPointcutRule.JoinPoint joinPoint = null;}
    (('call' {joinPoint = MethodPointcutRule.JoinPoint.CALL;})
    | ('execute' {joinPoint = MethodPointcutRule.JoinPoint.EXECUTE;}))
    WS type WS NAME WS? '(' args ')' WS? ';'
    {
      $pc = new MethodPointcutRule(joinPoint, $type.node, $NAME.getText(), $args.value);
    };

args returns [ArrayList<Argument> value = new ArrayList<Argument>()]
  : {String name = new String();}
    (s = type (WS t = NAME {name = $t.getText();})?
    {
      $value.add(new Argument(name, $s.node));
      name = new String();
    }
    (WS? ',' WS? s = type (WS t = NAME {name = $t.getText();})?
    {
      $value.add(new Argument(name, $s.node));
      name = new String();
    }
    )*)?;

type returns [Type node]
  : (NAME
    {
      if ($NAME.getText().equals("int")) {
        $node = new PrimaryType(PrimaryType.PType.INT);
      } else if ($NAME.getText().equals("float")) {
        $node = new PrimaryType(PrimaryType.PType.FLOAT);
      } else if ($NAME.getText().equals("char")) {
        $node = new PrimaryType(PrimaryType.PType.CHAR);
      } else if ($NAME.getText().equals("void")) {
        $node = new PrimaryType(PrimaryType.PType.VOID);
      } else if ($NAME.getText().equals("bool")) {
        $node = new PrimaryType(PrimaryType.PType.BOOL);
      } else if ($NAME.getText().equals("..")) {
        $node = new AnyType();
      } else {
        // We don't allow this in small C.
        $node = null;
      }
    }
    ('[' ']'
    {
      if ($node instanceof ArrayType) {
        $node = new ArrayType(((ArrayType) $node).getType(), ((ArrayType) $node).dimensions);
      } else {
        $node = new ArrayType($node, 1);
      }
    }
    | '*'
    {
      $node = new PointerType($node);
    }
    )?);

NAME: (LETTER | SPECIALCHARS | '..') (LETTER | DIGIT | SPECIALCHARS | '..')*;
WS: (' ' | '\n' | '\t' | '\r')+; // {$channel = HIDDEN;};
OTHERCHARS: ~(LETTER | DIGIT | SPECIALCHARS);
DIGIT: ('0'..'9');

fragment LETTER: ('a'..'z' | 'A'..'Z');
fragment SPECIALCHARS: ('*' | '_');