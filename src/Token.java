public class Token {
    public TipoToken tipo;
    public String lexema;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
    }

    @Override
    public String toString() {
        return "<" + tipo + ", \"" + lexema + "\">";
    }
}