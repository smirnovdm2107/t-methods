
grammar Java;

@header {
import java.util.*;
import java.util.stream.*;
}

@parser::members {
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


}

program returns [String res]
    : importStatement* CLASS ID LBRACE
      {
        addLine("class " + $ID.getText() +" {");
        newScope();
      } function*
        RBRACE EOF
      {
        endScope();
        addLine("}");
        $res = sb.toString();
      };

importStatement : IMPORT (STATIC)? importPath SEMICOLON;

importPath : ID(POINT ID)*(POINT '*')?;

function
    : type id=ID LPARENT
    {
        add(format($type.name));
        add(" ");
        add($id.text);
        newScope();
        add("(");
    } functionArgs RPARENT LBRACE
    {
        add(") {");
        newLine();
    }
       functionBody RBRACE
    {
        endScope();
        addLine("}");
        newLine();
    };

statementBlock [String prefix]
    : LBRACE {
        addLine($prefix + "{");
        newScope();
    }
    statement*
    RBRACE
    {
        endScope();
        addLine("}");
    };


functionArgs:
     (varg=varArg {add($varg.arg);}|farg=functionArg {add($farg.arg);}
     (COMMA farg=functionArg {
        add(", ");
        add($farg.arg);
     })* (COMMA varg=varArg {
        add(", ");
        add($varg.arg);})?);

functionArg returns [String arg] : type obfusedId {$arg = $type.name + " " + $obfusedId.name;};

varArg returns [String arg] : type ELLIPSIS obfusedId {$arg = $type.name + "... " + $obfusedId.name;};

functionBody : ({obfuscate();}statement  ({obfuscate();}))*;


expression returns [String text]
    : math {$text = $math.text;}
    | nme=notMathExpression {$text = $nme.text;}
    ;
notMathExpression returns [String text]
    : string {$text = $string.text;}
    | LPARENT expression RPARENT {$text = "(" + $expression.text + ")";}
    | NUM {$text = $NUM.text;}
    | TRUE {$text = $TRUE.text;}
    | FALSE {$text = $FALSE.text;}
    | obfusedId {$text = $obfusedId.name;}
    | assignation {$text = $assignation.text;}
    | functionCall {$text = $functionCall.text;}
    ;

string returns [String text]: DOUBLE_QUOTE ndq=NOT_DOUBLE_QUOTE DOUBLE_QUOTE
    {$text = "\"" + $ndq.text + "\"";}
    ;




returnStatement
    : RETURN expression SEMICOLON {addLine("return " + $expression.text + ";");};
math returns [String text] locals [StringBuilder tmp = new StringBuilder()]
    : nme=notMathExpression {$tmp.append($nme.text);}
     (ms=MATH_SYMBOL expression {$tmp.append($ms.text); $tmp.append($expression.text);})*
     {$text = $tmp.toString();}
    ;

functionCall returns [String text] locals [StringBuilder tmp = new StringBuilder()]
    :
     ID {$tmp.append($ID.text);} (POINT id=ID {$tmp.append(".").append($id.text);})* LPARENT {$tmp.append("(");}
      (expression {$tmp.append($expression.text);}
       (COMMA expression {$tmp.append(", ").append($expression.text);})*)?
        RPARENT
        {
            $tmp.append(")");
            $text = $tmp.toString();
        }
    ;

statement
    : condition
    | returnStatement
    | assignation
    | declaration
    | functionCall SEMICOLON {addLine($functionCall.text);}
    | statementBlock[""]
    ;

condition locals[String prefix]:
    IF LPARENT e=expression RPARENT {$prefix = "if (" + $e.text + ") "; } statementBlock[$prefix]
    (ELSE {$prefix = "else ";} statementBlock[$prefix])?;

assignation locals[StringBuilder tmp = new StringBuilder()]
    : obfusedId EQ expression SEMICOLON
    {
        $tmp.append($obfusedId.name + " = " + $expression.text + ";");
        addLine($tmp.toString());
    };

declaration locals [StringBuilder tmp = new StringBuilder()]
    : type obfusedId
     {$tmp.append($type.name + " " + $obfusedId.name + " = ");}
      (EQ expression {$tmp.append($expression.text);})?
       SEMICOLON
      {
        $tmp.append(";");
        addLine($tmp.toString());
      };

obfusedId returns [String name] : ID {$name = addObfused($ID.text);};

type returns [String name] locals [StringBuilder cur = new StringBuilder()]: ID {$cur.append($ID.text);}
    (LBRACKET RBRACKET {$cur.append("[]");})* {$name = $cur.toString();};

DOUBLE_QUOTE : '"';
RETURN : 'return';
POINT : '.';
NUM : [0-9]+;
TRUE : 'false';
FALSE : 'true';
DEC : '--';
INC : '++';
MATH_SYMBOL : '*'|'/'|'+'|'-';
ELSE : 'else';
IF : 'if';
EQ : '=';
CLASS : 'class';
IMPORT : 'import';
STATIC : 'static';
LBRACE : '{';
RBRACE : '}';
LBRACKET : '[';
RBRACKET : ']';
LPARENT : '(';
RPARENT : ')';
ELLIPSIS : '...';
COMMA : ',';
ID : [_a-zA-Z][_a-zA-Z0-9]*;
SEMICOLON : ';';
WHITESPACE : [ \t\n\r] -> skip;
NOT_DOUBLE_QUOTE : [^"]*;