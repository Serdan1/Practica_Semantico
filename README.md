# Mini-Compilador: Análisis Léxico, Sintáctico y Semántico

Este proyecto implementa las fases fundamentales de un compilador básico en Java. Es el resultado de la integración de las prácticas de nivel léxico, sintáctico y semántico, culminando en un sistema capaz de validar tipos, declaraciones y consistencia lógica mediante una Tabla de Símbolos.

## 🚀 Características

El compilador procesa código fuente de un lenguaje simplificado y realiza las siguientes validaciones:

### 1. Nivel Léxico (Lexer)
* Convierte la cadena de entrada en una secuencia de **Tokens**.
* Identifica: `PALABRA_CLAVE` (int, print), `IDENTIFICADOR`, `LITERAL_NUMERICO`, `OPERADOR` (+, -, *, =, /) y `DELIMITADOR` ((, ), ;).

### 2. Nivel Sintáctico (Parser)
* Valida que la secuencia de tokens cumpla con la Gramática Libre de Contexto (BNF).
* Soporta sentencias:
    * Declaración: `int x;`
    * Asignación: `x = 5 + 2;`
    * Impresión: `print(x);`

### 3. Nivel Semántico (Tabla de Símbolos)
Esta es la capa lógica más avanzada del proyecto. Utiliza una **Tabla de Símbolos** (HashMap) para validar reglas semánticas:
* ✅ **Declaración previa:** No se puede usar una variable que no haya sido declarada con `int`.
* ✅ **Inicialización:** No se puede usar el valor de una variable (en un `print` o una suma) si no se le ha asignado valor previamente.
* ✅ **Doble declaración:** Detecta si intentas declarar la misma variable dos veces.

## 🛠️ Estructura del Proyecto

* `Main.java`: Punto de entrada. Ejecuta tests automáticos para verificar casos correctos e incorrectos.
* `Parser.java`: Contiene la lógica recursiva (Gramática) y las llamadas a la Tabla de Símbolos.
* `Lexer.java`: Escáner que genera los tokens.
* `TablaSimbolos.java`: Estructura de datos que almacena el nombre, tipo y estado (inicializada/no inicializada) de las variables.
* `Token.java` / `TipoToken.java`: Definiciones básicas de las unidades léxicas.

## 📋 Ejemplo de Uso y Salida

El sistema incluye un juego de pruebas automático. Al ejecutar el `Main`, se muestra el estado de la memoria (Tabla de Símbolos) y los errores detectados.

### Caso Correcto
**Código:** `int x; x = 5; print(x + 10);`
```text
=== Tabla de Símbolos ===
Variable | Tipo | Inicializada
------------------------------
x        | int  | true

>> Compilación exitosa.
````
### Caso 2: Error Semántico (Variable no declarada)
**Código:** `x = 5;`

```text
[Tabla de Símbolos vacía]
>> Error semántico: No se puede asignar valor a 'x' porque no está declarada.
[INFO] Intentamos asignar 'x = 5' sin declarar 'int x' antes.
[EXPECTATIVA] Tabla VACÍA (porque 'x' es ilegal y no se registra).
   [Tabla de Símbolos vacía]

```
### Caso 3: Error Semántico (Variable sin inicializar)
**Código:** `int y; print(y);`

```text
   === Tabla de Símbolos ===
   Variable | Tipo | Inicializada
   ------------------------------
   y        | int  | false

>> Error semántico: La variable 'y' se está usando sin haber sido inicializada.

```
