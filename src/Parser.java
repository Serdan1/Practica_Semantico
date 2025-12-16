import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int actual = 0;

    // SEMÁNTICO: Instancia de la tabla de símbolos
    private TablaSimbolos tablaSimbolos = new TablaSimbolos();

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

    // Stmt -> ID '=' Expr ';' | 'print' '(' Expr ')' ';' | 'int' ID ';'
    private void stmt() {
        if (tokenActual().tipo == TipoToken.IDENTIFICADOR) {
            // Caso: Asignación (x = 5;)
            String nombreVariable = tokenActual().lexema;

            // SEMÁNTICO: Verificar que existe antes de asignar (opcional,
            // pero el método asignar() ya lanzará error si no existe)

            match(TipoToken.IDENTIFICADOR);
            match(TipoToken.OPERADOR, "=");
            expr();

            // SEMÁNTICO: Si la expresión fue correcta, marcamos la variable como inicializada
            tablaSimbolos.asignar(nombreVariable);

            match(TipoToken.DELIMITADOR, ";");

        } else if (tokenActual().lexema.equals("print")) {
            // Caso: Print (print(x);)
            match(TipoToken.PALABRA_CLAVE);
            match(TipoToken.DELIMITADOR, "(");
            expr();
            match(TipoToken.DELIMITADOR, ")");
            match(TipoToken.DELIMITADOR, ";");

        } else if (tokenActual().lexema.equals("int")) {
            // Caso: Declaración (int x;)
            match(TipoToken.PALABRA_CLAVE); // "int"

            if (tokenActual().tipo == TipoToken.IDENTIFICADOR) {
                String nombreVariable = tokenActual().lexema;

                // SEMÁNTICO: Registrar la variable en la tabla
                tablaSimbolos.declarar(nombreVariable, "int");

                match(TipoToken.IDENTIFICADOR);
            } else {
                error("Se esperaba un identificador después de 'int'.");
            }
            match(TipoToken.DELIMITADOR, ";");

        } else {
            error("Sentencia no válida. Se esperaba ID, 'print' o 'int'.");
        }
    }

    // Expr -> Term { ('+'|'-') Term }
    private void expr() {
        term();
        while (tokenActual().lexema.equals("+") || tokenActual().lexema.equals("-")) {
            avanzar();
            term();
        }
    }

    // Term -> Factor { ('*'|'/') Factor }
    private void term() {
        factor();
        while (tokenActual().lexema.equals("*") || tokenActual().lexema.equals("/")) {
            avanzar();
            factor();
        }
    }

    // Factor -> ID | NUM | '(' Expr ')'
    private void factor() {
        if (tokenActual().tipo == TipoToken.LITERAL_NUMERICO) {
            avanzar();
        } else if (tokenActual().tipo == TipoToken.IDENTIFICADOR) {
            // SEMÁNTICO: Estamos USANDO una variable en una expresión (ej: y = x + 1)
            // Debemos verificar que 'x' existe y tiene valor.
            tablaSimbolos.verificarUso(tokenActual().lexema);

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
        throw new RuntimeException("Error Sintáctico/Semántico: " + mensaje);
    }
}