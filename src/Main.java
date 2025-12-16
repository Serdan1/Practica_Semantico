import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Ejemplo que debería funcionar sintácticamente
        // Nota: Sintácticamente es correcto usar 'x' sin declararla, 
        // pero la Práctica 4 (Semántico) nos obligará a arreglar eso.
        String codigo = "int x; x = 5; print(x + 10);";

        try {
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.escanear(codigo);

            System.out.println("--- Tokens Generados ---");
            for (Token t : tokens) {
                System.out.println(t);
            }

            System.out.println("\n--- Análisis Sintáctico ---");
            Parser parser = new Parser(tokens);
            parser.parse();
            System.out.println("¡El código es sintácticamente correcto!");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}