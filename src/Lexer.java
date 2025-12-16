import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    public List<Token> escanear(String entrada) {
        List<Token> tokens = new ArrayList<>();

        // Expresión regular para separar tokens manteniendo los importantes
        // Separa por espacios, pero captura paréntesis, punto y coma, operadores, etc.
        String regex = "\\s*(=>|==|!=|<=|>=|&&|\\|\\||[+\\-*/=();])\\s*|\\s+";

        // Dividimos la entrada (truco simple para evitar un lexer complejo carácter a carácter)
        // Nota: Para una implementación más robusta se usaría un bucle con Matcher,
        // pero para esta práctica usaremos un split manual mejorado o una lógica simple.

        // Enfoque simplificado sugerido por la práctica:
        // Pre-procesamos para rodear símbolos con espacios y hacer split
        String procesado = entrada
                .replace("(", " ( ")
                .replace(")", " ) ")
                .replace(";", " ; ")
                .replace("=", " = ")
                .replace("+", " + ")
                .replace("-", " - ")
                .replace("*", " * ")
                .replace("/", " / ");

        String[] partes = procesado.trim().split("\\s+");

        for (String p : partes) {
            if (p.isEmpty()) continue;
            tokens.add(clasificar(p));
        }

        tokens.add(new Token(TipoToken.EOF, ""));
        return tokens;
    }

    private Token clasificar(String lexema) {
        if (lexema.equals("print") || lexema.equals("int")) {
            return new Token(TipoToken.PALABRA_CLAVE, lexema);
        }
        if (lexema.matches("[0-9]+")) {
            return new Token(TipoToken.LITERAL_NUMERICO, lexema);
        }
        if (lexema.matches("[+\\-*/=]")) {
            return new Token(TipoToken.OPERADOR, lexema);
        }
        if (lexema.matches("[();]")) {
            return new Token(TipoToken.DELIMITADOR, lexema);
        }
        // Si no es nada de lo anterior, asumimos identificador (nombre de variable)
        return new Token(TipoToken.IDENTIFICADOR, lexema);
    }
}