package com.example.Projeto_Calculadora_RaioX.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Calculo {


    //Valores que o usuário irá alterar
    private double kv;
    private double mAs;
    private double espessura;
    private String regiao;

    //valores que serão calculados
    private double esak;
    private double bsc;
    private double diferenca;

    //Valores fixos dependendo da região
    private double rendimento;
    private double referencia;
    private double dff;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setRegiao(String regiao) {
        this.regiao = regiao;

        if (regiao.equals("Cranio-AP")) {
            this.dff = 100;
            referencia = 5;

        } else if (regiao.equals("Cranio-L")) {
            this.dff = 100;
            referencia = 3;

        } else if (regiao.equals("Tórax-PA")) {
            this.dff = 180;
            referencia = 0.4;

        } else if (regiao.equals("Tórax-L")) {
            this.dff = 180;
            referencia = 1.5;

        } else if (regiao.equals("Abdome-AP")) {
            this.dff = 100;
            referencia = 10;

        } else if (regiao.equals("Pelve-AP")) {
            this.dff = 100;
            referencia = 10;

        } else if (regiao.equals("Lombar-AP")) {
            this.dff = 100;
            referencia = 10;

        } else if (regiao.equals("Lombar-L")) {
            this.dff = 100;
            referencia = 30;

        } else if (regiao.equals("Lombar-JLS")) {
            this.dff = 100;
            referencia = 40;
        }
    }

    public void Calcular() {
        this.rendimento = calcularRendimento(kv);
        this.bsc = calcularBSC();
        this.esak = CalcularEsak();
        this.diferenca = CalcularDiferenca();

    }

    private double calcularRendimento(double kv) {
        return (0.0038 * kv - 0.1193);
    }

    private double calcularBSC() {
        double kvArredondado = Math.floor(kv / 10) * 10;

        ValoresBSC valorEnum = ValoresBSC.fromChave(kvArredondado);

        // Obtém o valor associado ao enum
        double valor = valorEnum.getValor();

        return valor + ((kv - kvArredondado)/(kvArredondado + 10)) * (calcularRendimento(kvArredondado + 10) - calcularRendimento(kvArredondado));
    }

    private double CalcularEsak() {

        return this.rendimento * Math.pow(80 / (this.dff - this.espessura), 2) * this.mAs * this.bsc;
    }

    private double CalcularDiferenca() {
        return this.esak / referencia - 1;
    }
}
