import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {

    // Clase interna para agrupar los datos de cada variable
    private static class Identificador {
        String tipo;
        boolean inicializada;

        public Identificador(String tipo) {
            this.tipo = tipo;
            this.inicializada = false; // Al declarar, aún no tiene valor [cite: 218]
        }
    }

    // Mapa para guardar las variables por su nombre
    private Map<String, Identificador> tabla;

    public TablaSimbolos() {
        tabla = new HashMap<>();
    }

    /**
     * Registra una nueva variable.
     * Lanza error si la variable ya existe.
     */
    public void declarar(String nombre, String tipo) {
        if (tabla.containsKey(nombre)) {
            throw new RuntimeException("Error semántico: La variable '" + nombre + "' ya ha sido declarada previamente.");
        }
        tabla.put(nombre, new Identificador(tipo));
    }

    /**
     * Marca una variable como inicializada (ya tiene valor).
     * Lanza error si la variable no existe.
     */
    public void asignar(String nombre) {
        if (!tabla.containsKey(nombre)) {
            throw new RuntimeException("Error semántico: No se puede asignar valor a '" + nombre + "' porque no está declarada. [cite: 230]");
        }
        tabla.get(nombre).inicializada = true;
    }

    /**
     * Verifica si una variable existe y devuelve su inicialización.
     * Se usa cuando leemos una variable (ej: print(x) o y = x + 1).
     */
    public void verificarUso(String nombre) {
        if (!tabla.containsKey(nombre)) {
            throw new RuntimeException("Error semántico: La variable '" + nombre + "' no ha sido declarada. [cite: 231]");
        }
        if (!tabla.get(nombre).inicializada) {
            throw new RuntimeException("Error semántico: La variable '" + nombre + "' se está usando sin haber sido inicializada. [cite: 232]");
        }
    }
    @Override
    public String toString() {
        if (tabla.isEmpty()) {
            return "   [Tabla de Símbolos vacía]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("   === Tabla de Símbolos ===\n");
        sb.append("   Variable | Tipo | Inicializada\n");
        sb.append("   ------------------------------\n");

        for (Map.Entry<String, Identificador> entry : tabla.entrySet()) {
            sb.append(String.format("   %-8s | %-4s | %s\n",
                    entry.getKey(),
                    entry.getValue().tipo,
                    entry.getValue().inicializada));
        }
        return sb.toString();
    }
}