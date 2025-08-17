@JsonCreator
public static MotivoCancelamento fromString(String value) {
    return MotivoCancelamento.valueOf(value.toUpperCase());
}

/*
Use esse tipo quando:

O valor que chega no JSON for igual (ou quase igual) ao nome da enum, mas com diferenças de caixa (maiúscula/minúscula).

Quando você só quer que o Jackson aceite coisas como "cancelado" no lugar de "CANCELADO", por exemplo.

Exemplo:
public enum MotivoCancelamento {
    PACIENTE_DESISTIU,
    MEDICO_CANCELOU,
    OUTROS;
}

*/