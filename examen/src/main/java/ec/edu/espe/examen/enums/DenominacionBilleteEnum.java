package ec.edu.espe.examen.enums;

public enum DenominacionBilleteEnum {
    USD_1(1),
    USD_5(5),
    USD_10(10),
    USD_20(20),
    USD_50(50),
    USD_100(100);

    private final int valor;

    DenominacionBilleteEnum(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}