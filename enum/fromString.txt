public static TipoConsulta fromString(String text) {
    for (TipoConsulta tipo : TipoConsulta.values()) {
        if (tipo.valor.equalsIgnoreCase(text)) {
            return tipo;
        }
    }
    throw new IllegalArgumentException("No enum constant for value: " + text);
}
/*
Use esse tipo quando:

O valor que vem da API ou do banco é diferente do nome da constante Enum
(exemplo: Enum tem CONSULTA, mas o JSON vem como "Consulta" ou "consulta simples" ou qualquer outro texto customizado).

Quando você tem um atributo extra na enum (ex: String valor) que representa o texto público.

Exemplo:
public enum TipoConsulta {
    CONSULTA("Consulta"),
    RETORNO("Retorno");

    private String valor;

    TipoConsulta(String valor) {
        this.valor = valor;
    }
}
*/