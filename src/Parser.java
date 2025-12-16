import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int actual = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() {
        stmtList();
        if (tokenActual().tipo != TipoToken.EOF) {
            error("Se esperaba fin de archivo.");
        }
    }

    // StmtList -> Stmt StmtList | epsilon
    private void stmtList() {
        if (tokenActual().tipo != TipoToken.EOF) {
            stmt();
            stmtList();
        }
    }

    // Stmt -> ID '=' Expr ';' | 'print' '(' Expr ')' ';'
    // También añadimos soporte para 'int' ID ';' para la fase semántica
    private void stmt() {
        if (tokenActual().tipo == TipoToken.IDENTIFICADOR) {
            match(TipoToken.IDENTIFICADOR);
            match(TipoToken.OPERADOR, "="); // Asignación
            expr();
            match(TipoToken.DELIMITADOR, ";");
        } else if (tokenActual().lexema.equals("print")) {
            match(TipoToken.PALABRA_CLAVE); // print
            match(TipoToken.DELIMITADOR, "(");
            expr();
            match(TipoToken.DELIMITADOR, ")");
            match(TipoToken.DELIMITADOR, ";");
        } else if (tokenActual().lexema.equals("int")) {
            // EXTENSIÓN PARA PRÁCTICA 4: Declaración
            match(TipoToken.PALABRA_CLAVE); // int
            match(TipoToken.IDENTIFICADOR);
            match(TipoToken.DELIMITADOR, ";");
        } else {
            error("Sentencia no válida. Se esperaba ID, 'print' o 'int'.");
        }
    }

    // Expr -> Term { ('+'|'-') Term }
    private void expr() {
        term();
        while (tokenActual().lexema.equals("+") || tokenActual().lexema.equals("-")) {
            avanzar(); // Consumir operador
            term();
        }
    }

    // Term -> Factor { ('*'|'/') Factor }
    private void term() {
        factor();
        while (tokenActual().lexema.equals("*") || tokenActual().lexema.equals("/")) {
            avanzar(); // Consumir operador
            factor();
        }
    }

    // Factor -> ID | NUM | '(' Expr ')'
    private void factor() {
        if (tokenActual().tipo == TipoToken.LITERAL_NUMERICO) {
            avanzar();
        } else if (tokenActual().tipo == TipoToken.IDENTIFICADOR) {
            avanzar();
        } else if (tokenActual().lexema.equals("(")) {
            match(TipoToken.DELIMITADOR, "(");
            expr();
            match(TipoToken.DELIMITADOR, ")");
        } else {
            error("Factor inesperado: " + tokenActual().lexema);
        }
    }

    private Token tokenActual() {
        return tokens.get(actual);
    }

    private void avanzar() {
        if (actual < tokens.size() - 1) actual++;
    }

    private void match(TipoToken tipoEsperado) {
        if (tokenActual().tipo == tipoEsperado) {
            avanzar();
        } else {
            error("Se esperaba " + tipoEsperado + " pero se encontró " + tokenActual().tipo);
        }
    }

    private void match(TipoToken tipoEsperado, String lexemaEsperado) {
        if (tokenActual().tipo == tipoEsperado && tokenActual().lexema.equals(lexemaEsperado)) {
            avanzar();
        } else {
            error("Se esperaba " + lexemaEsperado + " pero se encontró " + tokenActual().lexema);
        }
    }

    private void error(String mensaje) {
        throw new RuntimeException("Error Sintáctico en token '" + tokenActual().lexema + "': " + mensaje);
    }
}