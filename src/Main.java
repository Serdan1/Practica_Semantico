import java.util.List;

public class Main {
    public static void main(String[] args) {
        // PRUEBA 1: Código Correcto
        System.out.println("--- TEST 1: Código Correcto ---");
        probar("int x; x = 5; print(x + 10);");

        // PRUEBA 2: Error semántico (Variable no declarada)
        System.out.println("\n--- TEST 2: Variable no declarada ---");
        probar("x = 5;");

        // PRUEBA 3: Error semántico (Uso sin inicializar)
        System.out.println("\n--- TEST 3: Variable sin inicializar ---");
        probar("int y; print(y);");
    }

    static void probar(String codigo) {
        try {
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.escanear(codigo);
            Parser parser = new Parser(tokens);
            parser.parse();
            System.out.println(">> Compilación exitosa.");
        } catch (Exception e) {
            System.err.println(">> " + e.getMessage());
        }
    }
}