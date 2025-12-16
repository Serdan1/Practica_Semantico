import java.util.List;

public class Main {
    public static void main(String[] args) {
        // --- PRUEBA 1: Código Correcto ---
        System.out.println("=========================================");
        System.out.println("--- TEST 1: Código Correcto ---");
        System.out.println("[INFO] Declaramos 'x', le damos valor y la usamos.");
        System.out.println("[EXPECTATIVA] Tabla con 'x' inicializada a TRUE.");
        probar("int x; x = 5; print(x + 10);");

        // --- PRUEBA 2: Error semántico (Variable no declarada) ---
        System.out.println("\n=========================================");
        System.out.println("--- TEST 2: Variable no declarada ---");
        System.out.println("[INFO] Intentamos asignar 'x = 5' sin declarar 'int x' antes.");
        System.out.println("[EXPECTATIVA] Tabla VACÍA (porque 'x' es ilegal y no se registra).");
        probar("x = 5;");

        // --- PRUEBA 3: Error semántico (Uso sin inicializar) ---
        System.out.println("\n=========================================");
        System.out.println("--- TEST 3: Variable sin inicializar ---");
        System.out.println("[INFO] Declaramos 'int y' pero intentamos imprimirla sin darle valor.");
        System.out.println("[EXPECTATIVA] Tabla con 'y' pero inicializada a FALSE.");
        probar("int y; print(y);");
    }

    static void probar(String codigo) {
        Parser parser = null;
        try {
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.escanear(codigo);
            parser = new Parser(tokens);
            parser.parse();
            System.out.println(">> RESULTADO: Compilación exitosa.");
        } catch (Exception e) {
            System.err.println(">> ERROR DETECTADO: " + e.getMessage());
        } finally {
            if (parser != null) {
                System.out.println(parser.getTablaSimbolos());
            }
        }
    }
}