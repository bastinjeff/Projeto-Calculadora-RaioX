package com.example.Projeto_Calculadora_RaioX.models.types;

public enum ValoresBSC {
    VALOR_40(40, 1.08),
    VALOR_50(50, 1.11),
    VALOR_60(60, 1.13),
    VALOR_70(70, 1.14),
    VALOR_80(80, 1.158),
    VALOR_90(90, 1.17),
    VALOR_100(100, 1.177),
    VALOR_110(110, 1.183),
    VALOR_120(120, 1.19);

    private final double chave;
    private final double valor;


    ValoresBSC(int chave, double valor) {
        this.valor = valor;
        this.chave = chave;
    }

    public double getValor() {
        return this.valor;
    }

    public double getChave(){
        return this.chave;
    }

    public static ValoresBSC fromChave(double chave) {
        for (ValoresBSC v : ValoresBSC.values()) {
            if (v.getChave() == chave) {
                return v;
            }
        }
        throw new IllegalArgumentException("Chave não encontrada: " + chave);
    }
}
