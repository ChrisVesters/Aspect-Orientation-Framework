grammar AODot;

options {
  language = Java;
}

@parser::header {
package aofDot.AspectParser;

import aof.*;
import aofDot.*;
}

@parser::members {
static int counter = 0;
}

@lexer::header {
package aofDot.AspectParser;
}

file: (WS | order | pointcut | advice)* EOF;

// There is an order, if multiple advices match, and act on the same attributes!
order
  : {
      String pred = new String();
      String post = new String(); 
    }
    'order' WS? '{' WS? aname = NAME
    {
      assert ($aname != null);  
      pred = "_" + $aname.getText();
    }
    (WS? ';' WS? aname = NAME
    {
      assert ($aname != null);  
      post = "_" + $aname.getText();
      Weaver.addOrder(pred, post);
      pred = post;
    }
    )* WS? '}';

advice
  : ('advice' WS aname = NAME WS? ':' WS?)? // TODO: check name for wildcards!
    {Action action = null;}
    ( ('insert'  {action = Action.INSERT;})
    | ('delete'  {action = Action.DELETE;})
    )
    WS pname = NAME WS? '(' args ')' WS? '{' rules '}' // TODO: check name for wildcards!
    {
      String advicename;
      if ($aname == null) {
        advicename = "UnnamedAdvice_" + counter;
        counter++;
      } else {
        advicename = "_" + $aname.getText();
      }
      
      DotAdvice dotAd = new DotAdvice(advicename, $args.args, action, $rules.pcs);
      Weaver.addAdvice(dotAd, $pname.getText());
    }
  ;

pointcut
  : 'pointcut' WS pname = NAME WS? '(' args ')' WS? '{' rules '}' // TODO: check name for wildcards
    {
     
      Pointcut pc = new Pointcut($pname.getText(), $args.args, $rules.pcs);
      Weaver.addPointcut(pc);
    };

args returns [ArrayList<Argument> args = new ArrayList<Argument>()]
  : (WS? t = type WS n = NAME {$args.add(new Argument($n.getText(), new Value($t.type)));}
      (WS? ',' WS? t = type WS n = NAME {$args.add(new Argument($n.getText(), new Value($t.type)));}
      )*
    WS?)?
  ;

rules returns [ArrayList<PointcutRule> pcs = new ArrayList<PointcutRule>()]
  : (WS
    | (graph {$pcs.add($graph.pc);})
    | (node {$pcs.add($node.pc);} ';')
    | (edge {$pcs.add($edge.pc);} ';')
    )*;

graph returns [GraphPointcutRule pc]
  : ('..graph' | 'graph' | 'digraph' | 'subgraph') WS n = (NAME | '..') WS? '{' rules '}'
    {
      $pc = new GraphPointcutRule($n.getText(), $rules.pcs, new ArrayList<Argument>());
    }
  ;
  
edge returns [EdgePointcutRule pc]
  : {
      boolean directed = false;
    }
    (NAME WS? '=' WS?)? '(' WS? s = node WS? ')' WS? ('->' {directed = true;} | '--') WS? '(' WS? t = node WS? ')' (WS? '[' attributes ']')?
    {
      ArrayList<Argument> args = new ArrayList<Argument>();
      if ($attributes.args != null) {
        args = $attributes.args;
      }
     
      if ($NAME != null) {
        args.add(0, new Argument("_this", new Value($NAME.getText())));
      }
      
      $pc = new EdgePointcutRule($s.pc, $t.pc, directed, args);
    }
  ;

node returns [NodePointcutRule pc]
// (name WS? '=' WS?)? name 
  : n = (NAME | '..') (WS? '=' WS? m = (NAME | '..'))? (WS? '[' attributes ']')?
    {
      ArrayList<Argument> args = new ArrayList<Argument>();
      if ($attributes.args != null) {
        args = $attributes.args;
      }
      
      String nodeName = $n.getText();
      if (m != null) {
        args.add(0, new Argument("_this", new Value(nodeName)));
        nodeName = $m.getText();
      }
      
      $pc = new NodePointcutRule(nodeName, args);
    }
  ;

attributes returns [ArrayList<Argument> args = new ArrayList<Argument>()]
  : aname = NAME WS? '=' WS? aValue = (NAME | '..')
    {
      Value val = new Value($aValue.getText());
      $args.add(new Argument($aname.getText(), val));
    }
    (WS? ',' WS? a = attributes {$args.addAll($a.args);})?
  | '..'
    {
      $args.add(new Argument(null, new AnyType()));
    }
  ;

type returns [String type]
  : 'Node' {$type = "Node";}
  | 'Edge' {$type = "Edge";}
  | 'Graph' {$type = "Graph";}
  ;

NAME: '..'? ((LETTER | DIGIT | '_' | '"' .* '"') '..'?)*;
WS: (' ' | '\n' | '\t' | '\r')+; // {$channel = HIDDEN;};

fragment DIGIT: ('0'..'9');
fragment LETTER: ('a'..'z' | 'A'..'Z');