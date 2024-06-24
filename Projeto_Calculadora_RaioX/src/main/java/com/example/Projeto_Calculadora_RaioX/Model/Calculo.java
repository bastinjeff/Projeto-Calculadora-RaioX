package com.example.Projeto_Calculadora_RaioX.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public void setRegiao(String regiao ){
        this.regiao = regiao;

        if(regiao == "Cranio-AP"){
            this.dff = 100;
            referencia = 5;
        }
        else if(regiao == "Cranio-L"){
            this.dff = 100;
            referencia = 3;
        }
        if(regiao == "Tórax-PA"){
            this.dff = 180;
            referencia = 0.4;
        }
        if(regiao == "Tórax-L"){
            this.dff = 180;
            referencia = 1.5;
        }
        if(regiao == "Abdome-AP"){
            this.dff = 100;
            referencia = 10;
        }
        if(regiao == "Pelve-AP"){
            this.dff = 100;
            referencia = 10;
        }
        if(regiao == "Lombar-AP"){
            this.dff = 100;
            referencia = 10;
        }
        if(regiao == "Lombar-L"){
            this.dff = 100;
            referencia = 30;
        }
        if(regiao == "Lombar-JLS"){
            this.dff = 100;
            referencia = 40;
        }
    }

    public void Calcular(){
        this.esak = CalcularEsak();
        this.diferenca = CalcularDiferenca();
    }

    private double CalcularEsak(){
        return this.rendimento*Math.pow(80/(this.dff-this.espessura),2)*this.mAs*this.bsc;
    }

    private double CalcularDiferenca(){
        return this.esak/referencia - 1;
    }
}
