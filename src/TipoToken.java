public enum TipoToken {
    IDENTIFICADOR,
    LITERAL_NUMERICO,
    PALABRA_CLAVE, // para 'int', 'print', etc.
    OPERADOR,      // para +, -, *, /, =
    DELIMITADOR,   // para (, ), ;
    EOF            // Fin de fichero
}