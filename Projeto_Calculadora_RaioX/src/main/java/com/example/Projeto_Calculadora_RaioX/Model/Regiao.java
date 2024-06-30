package com.example.Projeto_Calculadora_RaioX.Model;

public enum Regiao {
        CRANIO_AP("Cranio-AP"),
        CRANIO_L("Cranio-L"),
        TORAX_PA("Tórax-PA"),
        TORAX_L("Tórax-L"),
        ABDOME_AP("Abdome-AP"),
        PELVE_AP("Pelve-AP"),
        LOMBAR_AP("Lombar-AP"),
        LOMBAR_L("Lombar-L"),
        LOMBAR_JLS("Lombar-JLS");

        private final String descricao;

    Regiao(String descricao) {
            this.descricao = descricao;
        }

    public String getDescricao() {
            return descricao;
        }
    }


